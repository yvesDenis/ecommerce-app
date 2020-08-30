package com.assHoleDeveloper.eCommerce.eCommerce.assembler;

import com.assHoleDeveloper.eCommerce.eCommerce.dto.UserDTO;
import com.assHoleDeveloper.eCommerce.eCommerce.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

public class UserAssembler {

    private static final Logger logger = LoggerFactory.getLogger(UserAssembler.class);

    public static Optional<UserDTO> fromUserModelTOUserDTO(Optional<User> userOptional){
        UserDTO userDTO = null;
        if(userOptional.isPresent()){
            userDTO = new UserDTO();
            BeanUtils.copyProperties(userOptional.get(),userDTO);
        }
        return Optional.of(userDTO);
    }

    public static Optional<User> fromUserDTOTOModelDTO(Optional<UserDTO> userDTOOptional){
        User user = null;
        if(userDTOOptional.isPresent()){
            user = new User();
            BeanUtils.copyProperties(userDTOOptional.get(),user);
        }
        return Optional.of(user);
    }
}
