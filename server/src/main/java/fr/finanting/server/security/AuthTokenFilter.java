package fr.finanting.server.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    
    /*
    public static final String _BEARER = "Bearer ";
 
    @Autowired
    private UserDetailsService UserDetailsService;
 
    @Autowired
    private JwtTokenUtil jwtUtil;

    private final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    */
 
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        
        /*
        try {
            String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
 
 
            if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(_BEARER)) {
                String jwtToken = headerAuth.substring(7);

                Jws<Claims> jwsClaims = jwtUtil.parseJWT(jwtToken);
                Claims claims = jwsClaims.getBody();
                String username = claims.getSubject();
 
        
                UserDetails userDetails = UserDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                            
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
 
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
            }
 
        } catch (Exception ex) {
            logger.error("Error authenticating user request : {}", ex.getMessage());
        }
        */
 
        filterChain.doFilter(request, response);
    }
}
