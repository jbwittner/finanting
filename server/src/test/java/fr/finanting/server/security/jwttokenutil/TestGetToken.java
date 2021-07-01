package fr.finanting.server.security.jwttokenutil;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import fr.finanting.server.generated.model.LoginParameter;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.security.JwtTokenUtil;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;

public class TestGetToken extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private JwtTokenUtil jwtTokenUtil;

    @Value("${application.jwt.issuer}")
    private String issuer;
 
    @Value("${application.jwt.secret}")
    private String secret;
 
    @Value("${application.jwt.audience}")
    private String audience;
 
    @Value("${application.jwt.ttl-in-seconds}")
    private long timeToLiveInSeconds;

    private SecretKey secretKey;

    @Override
    protected void initDataBeforeEach() {
        this.jwtTokenUtil = new JwtTokenUtil(this.userRepository);
        ReflectionTestUtils.setField(this.jwtTokenUtil, "issuer", this.issuer);
        ReflectionTestUtils.setField(this.jwtTokenUtil, "secret", this.secret);
        ReflectionTestUtils.setField(this.jwtTokenUtil, "audience", this.audience);
        ReflectionTestUtils.setField(this.jwtTokenUtil, "timeToLiveInSeconds", this.timeToLiveInSeconds);
        
        try {
            this.jwtTokenUtil.setUpSecretKey();
            this.secretKey = Keys.hmacShaKeyFor(secret.getBytes("UTF-8"));
        } catch (WeakKeyException | UnsupportedEncodingException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testGetToken(){
        final User user = this.testFactory.getUser();

        final LoginParameter loginParameter = new LoginParameter();
        loginParameter.setUserName(user.getUserName());
        loginParameter.setPassword(user.getPassword());

        final TestAuthentication testAuthentication = new TestAuthentication(user);

        final String token = this.jwtTokenUtil.getToken(testAuthentication);

        final Jws<Claims> claims =
            Jwts.parserBuilder()
                    .setSigningKey(this.secretKey)
                    .build()
                    .parseClaimsJws(token);

        final Claims body = claims.getBody();

        final long duration = body.getExpiration().getTime() - body.getIssuedAt().getTime();
        final long rest = Math.abs(duration - this.timeToLiveInSeconds*1000);

        Assertions.assertEquals(8, body.size());
        Assertions.assertEquals(this.audience, body.getAudience());
        Assertions.assertEquals(this.issuer, body.getIssuer());
        Assertions.assertEquals(user.getUserName(), body.getSubject());
        Assertions.assertEquals(user.getFirstName(), body.get(JwtTokenUtil.CLAIM_FIRST_NAME_KEY));
        Assertions.assertEquals(user.getLastName(), body.get(JwtTokenUtil.CLAIM_LAST_NAME_KEY));
        Assertions.assertTrue(rest < 10);

        Assertions.assertNotNull(token);

    }

    public class TestAuthentication implements Authentication{

        final private User user;

        public TestAuthentication(final User user){
            this.user = user;
        }

        @Override
        public String getName() {
            return this.user.getUserName();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }

        @Override
        public boolean isAuthenticated() {
            return false;
        }

        @Override
        public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {
        }
        
    }
    
}
