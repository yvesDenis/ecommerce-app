package com.assHoleDeveloper.eCommerce.eCommerce.controller.response;

import com.assHoleDeveloper.eCommerce.eCommerce.dto.UserDTO;
import lombok.Data;

@Data
public class SignInResponse extends GenericResponse {

    private UserDTO userDTO;

}
