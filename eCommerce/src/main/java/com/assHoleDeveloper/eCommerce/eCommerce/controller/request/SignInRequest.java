package com.assHoleDeveloper.eCommerce.eCommerce.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ToString
@EqualsAndHashCode
public class SignInRequest {

    @NotBlank(message = "{username.not.valid}")
    @Size(min=3, max = 25, message = "{username.length.not.valid}")
    private String username;

    @NotBlank(message = "{password.not.valid}")
    @Size(min = 6, max = 200, message = "{password.length.not.valid}")
    private String password;

}
