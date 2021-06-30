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

import fr.finanting.server.exception.GetSecretKeyException;
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
        this.jwtTokenUtil.setUpSecretKey();

        try {
            this.secretKey = Keys.hmacShaKeyFor(secret.getBytes("UTF-8"));
        } catch (WeakKeyException e) {
            throw new GetSecretKeyException(e);
        } catch (UnsupportedEncodingException e) {
            throw new GetSecretKeyException(e);
        }
    }

    @Test
    public void testGetToken(){
        User user = this.testFactory.getUser();

        LoginParameter loginParameter = new LoginParameter();
        loginParameter.setUserName(user.getUserName());
        loginParameter.setPassword(user.getPassword());

        TestAuthentication testAuthentication = new TestAuthentication(user);

        String token = this.jwtTokenUtil.getToken(testAuthentication);

        Jws<Claims> claims =
            Jwts.parserBuilder()
                    .setSigningKey(this.secretKey)
                    .build()
                    .parseClaimsJws(token);

        Claims body = claims.getBody();

        long duration = body.getExpiration().getTime() - body.getIssuedAt().getTime();
        long rest = Math.abs(duration - this.timeToLiveInSeconds*1000);

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

        private User user;

        public TestAuthentication(User user){
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
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        }
        
    }
    
}
