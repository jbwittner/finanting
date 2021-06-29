package fr.finanting.server.security;

import fr.finanting.server.generated.model.LoginParameter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface JwtTokenUtil {

    String getToken(final LoginParameter loginParameter);

    Jws<Claims> parseJWT(String jwtString);

}
