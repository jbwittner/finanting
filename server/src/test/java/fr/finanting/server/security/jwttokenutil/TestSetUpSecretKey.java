package fr.finanting.server.security.jwttokenutil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.security.JwtTokenUtil;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import io.jsonwebtoken.security.WeakKeyException;

public class TestSetUpSecretKey extends AbstractMotherIntegrationTest {

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

    @Override
    protected void initDataBeforeEach() {
        this.jwtTokenUtil = new JwtTokenUtil(this.userRepository);
        ReflectionTestUtils.setField(this.jwtTokenUtil, "issuer", this.issuer);
        ReflectionTestUtils.setField(this.jwtTokenUtil, "audience", this.audience);
        ReflectionTestUtils.setField(this.jwtTokenUtil, "timeToLiveInSeconds", this.timeToLiveInSeconds);
    }

    @Test
    public void testSetUpSecretKeyOk() {
        String key = this.testFactory.getRandomAlphanumericString(32);
        ReflectionTestUtils.setField(this.jwtTokenUtil, "secret", key);
        Assertions.assertDoesNotThrow(() -> this.jwtTokenUtil.setUpSecretKey());
    }

    @Test
    public void testSetUpSecretKeyWeakKeyException() {
        String key = this.testFactory.getRandomAlphanumericString(31);
        ReflectionTestUtils.setField(this.jwtTokenUtil, "secret", key);
        Assertions.assertThrows(WeakKeyException.class, () -> this.jwtTokenUtil.setUpSecretKey());
    }
    
}
