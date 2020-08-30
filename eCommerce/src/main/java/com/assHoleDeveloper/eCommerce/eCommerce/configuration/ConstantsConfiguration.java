package com.assHoleDeveloper.eCommerce.eCommerce.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource(ignoreResourceNotFound = true, value = "classpath:constants.properties")
public class ConstantsConfiguration {

    @Value("${mailSender}")
    private String mailSender;

    @Value("${mailSubject}")
    private String mailSubject;
}
