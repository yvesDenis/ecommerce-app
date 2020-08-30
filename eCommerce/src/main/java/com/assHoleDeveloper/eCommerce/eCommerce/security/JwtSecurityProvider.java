package com.assHoleDeveloper.eCommerce.eCommerce.security;

import com.assHoleDeveloper.eCommerce.eCommerce.configuration.SecurityConstants;
import com.assHoleDeveloper.eCommerce.eCommerce.model.UserSecurity;
import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtSecurityProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtSecurityProvider.class);

    @Autowired
    private SecurityConstants securityConstants;

    public String generateJwtToken(Authentication authentication) {

        logger.info("generateJwtToken");

        UserSecurity userPrincipal = (UserSecurity) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + securityConstants.getJwtExpiration()*1000))
                .signWith(SignatureAlgorithm.HS512, this.tobase64Encode(securityConstants.getJwtSecret()))
                .compact();
    }

    public boolean validateJwtToken(String authToken) throws Exception{
        try {
            if(authToken != null) {
                Jwts.parser().setSigningKey(this.tobase64Encode(securityConstants.getJwtSecret())).parseClaimsJws(authToken);
            }

        } catch (Exception e){
            throw e;
        }

        return true;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(this.tobase64Encode(securityConstants.getJwtSecret()))
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public Long getExpirationFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(this.tobase64Encode(securityConstants.getJwtSecret()))
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .getTime();
    }

    private byte[] tobase64Encode(String secret){
        return Base64.encodeBase64(secret.getBytes());
    }
}
