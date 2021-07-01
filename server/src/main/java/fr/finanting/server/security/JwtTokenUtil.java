package fr.finanting.server.security;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import fr.finanting.server.model.User;
import fr.finanting.server.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@Component
public class JwtTokenUtil {

    public static final String CLAIM_FIRST_NAME_KEY = "FirstName";
    public static final String CLAIM_LAST_NAME_KEY = "LastName";

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

    private UserRepository userRepository;

    @Autowired
    public JwtTokenUtil(final UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setUpSecretKey() throws WeakKeyException, UnsupportedEncodingException {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes("UTF-8"));
        logger.info("Secret key generation ok !");
    }

    private String createJWT(final String userName) {

        final User user = userRepository.findByUserName(userName).orElseThrow();
        
        final Date issuedAt = Date.from(Instant.now());
        final Date expiration = Date.from(Instant.now().plus(Duration.ofSeconds(this.timeToLiveInSeconds)));

        final String randomUUID = UUID.randomUUID().toString();

        final String pattern = "dd-MM-yyyy HH:mm:ss";
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        this.logger.info("Generation of new JWT [username : " + userName +
            " / randomUUID : " + randomUUID +
            " / issuedAt : " + simpleDateFormat.format(issuedAt) +
            " / expiration : " + simpleDateFormat.format(expiration)
            + "]");
 
        final String jwt =
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

    public Jws<Claims> parseJWT(final String jwtString) {
 
        final Jws<Claims> claims =
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtString);
     
        return claims;
    }

    public String getToken(final Authentication authentication){

        final String userName = authentication.getName();
        final String jwtToken = this.createJWT(userName);
 
        return jwtToken;

    }

}
