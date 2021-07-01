package fr.finanting.server.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

public class AuthTokenFilter extends OncePerRequestFilter {
    
    
    public static final String _BEARER = "Bearer ";
 
    private UserDetailsService UserDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    private final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    public AuthTokenFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil){
        this.UserDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }
 
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

        logger.debug("headerAuth : " + headerAuth);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(_BEARER)) {
            String jwtToken = headerAuth.substring(7);

            Jws<Claims> jwsClaims = this.jwtTokenUtil.parseJWT(jwtToken);
            Claims claims = jwsClaims.getBody();
            String username = claims.getSubject();

            logger.debug("Username : " + username);
    
            UserDetails userDetails = this.UserDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                        
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            
        }

        filterChain.doFilter(request, response);
    }
}
