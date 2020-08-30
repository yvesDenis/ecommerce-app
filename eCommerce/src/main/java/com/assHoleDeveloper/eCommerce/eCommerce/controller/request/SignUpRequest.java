package com.assHoleDeveloper.eCommerce.eCommerce.controller.request;

import com.assHoleDeveloper.eCommerce.eCommerce.model.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@ToString
@EqualsAndHashCode
public class SignUpRequest {

    @NotBlank(message = "{username.not.valid}")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(message = "{email.not.valid}")
    @Email
    private String email;

    @NotNull(message = "{role.not.valid}")
    private Set<Role> roles;

    @NotBlank(message = "{password.not.valid}")
    @Size(min = 6, max = 200)
    private String password;

    private String phone;

    private String imageUrl;

}
