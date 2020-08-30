package com.assHoleDeveloper.eCommerce.eCommerce.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:messages.properties")
public class LabelsConfiguration {

    @Value("${saveError}")
    private String saveError;

    @Value("${userAlreadyUsed}")
    private String userAlreadyUedError;

    @Value("${emailAreadyUsed}")
    private String emailAlreadyUedError;

    @Value("${successRegistration}")
    private String successRegistration;

    @Value("${userNotFoundForAutentication}")
    private String userNotFoundForAutentication;

    @Value("${accountDisabled}")
    private String accountDisabled;

    @Value("${accountBlocked}")
    private String accountBlocked;

    @Value("${logoutSuccessFull}")
    private String logoutSuccessFull;

}
