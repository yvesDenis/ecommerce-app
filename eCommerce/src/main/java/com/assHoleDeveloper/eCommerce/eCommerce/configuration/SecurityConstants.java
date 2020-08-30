package com.assHoleDeveloper.eCommerce.eCommerce.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource(ignoreResourceNotFound = true, value = "classpath:security.properties")
public class SecurityConstants {

    @Value("${ecommerce.app.jwtExpiration}")
    private int jwtExpiration;

    @Value("${ecommerce.app.jwtSecret}")
    private String jwtSecret;
}
