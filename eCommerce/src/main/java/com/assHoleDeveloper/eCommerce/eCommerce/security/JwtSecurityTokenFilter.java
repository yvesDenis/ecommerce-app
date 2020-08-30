package com.assHoleDeveloper.eCommerce.eCommerce.security;

import com.assHoleDeveloper.eCommerce.eCommerce.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtSecurityTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtSecurityProvider jwtSecurityProvider;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JwtSecurityTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            logger.info("JwtAuthTokenFilter pass");

            String jwt = getJwt(request);

            logger.info("jwt " + jwt);

            if (jwt != null && jwtSecurityProvider.validateJwtToken(jwt)) {
                String username = jwtSecurityProvider.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                logger.info("authentication worked !");
            } else {
                logger.info("String jwt is null!");
                throw new ServletException();
            }
        }catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
            throw e;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
            throw e;
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
            throw e;
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
            throw e;
        } catch (Exception e) {
            logger.error("Can NOT set user authentication -> Message: {}", e);
        }

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Expose-Headers", "ExpirationTime");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, ExpirationTime, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }
}
