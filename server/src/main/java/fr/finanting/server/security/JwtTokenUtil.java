package fr.finanting.server.security;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fr.finanting.server.model.User;
import fr.finanting.server.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${application.jwt.issuer}")
    private String issuer;
 
    @Value("${application.jwt.secret}")
    private String secret;
 
    @Value("${application.jwt.audience}")
    private String audience;
 
    @Value("${application.jwt.ttl-in-seconds}")
    private long timeToLiveInSeconds;

    private SecretKey secretKey;

    private final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Autowired
    private UserRepository userRepository;

    private static final String CLAIM_FIRST_NAME_KEY = "FirstName";
    private static final String CLAIM_LAST_NAME_KEY = "LastName";

    @PostConstruct
    public void setUpSecretKey() {
        try {
            secretKey = Keys.hmacShaKeyFor(secret.getBytes("UTF-8"));
            logger.info("Secret key generation ok !");
        } catch (UnsupportedEncodingException e) {
            logger.error("Error generating JWT Secret Key : {}", e.getMessage());
            throw new RuntimeException("Error generating JWT Secret Key", e);
        }
    }

    public String createJWT(String userName) {

        User user = userRepository.findByUserName(userName).orElseThrow();
        
        Date issuedAt = Date.from(Instant.now());
        Date expiration = Date.from(Instant.now().plus(Duration.ofSeconds(this.timeToLiveInSeconds)));

        String randomUUID = UUID.randomUUID().toString();

        this.logger.info("Generation of new JWT [username : " + userName +
            " / randomUUID : " + randomUUID +
            " / issuedAt : " + issuedAt.toString() +
            " / expiration : " + expiration.toString()
            + "]");
 
        String jwt =
            Jwts.builder()
                .setId(randomUUID)
                .setSubject(user.getUserName())
                .setIssuer(this.issuer)
                .setAudience(this.audience)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .claim(CLAIM_FIRST_NAME_KEY, user.getFirstName())
                .claim(CLAIM_LAST_NAME_KEY, user.getLastName())
                .signWith(secretKey)
                .compact();

        return jwt;
    }

    public Jws<Claims> parseJWT(String jwtString) {
 
        Jws<Claims> claims =
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtString);
     
        return claims;
    }

    
}
