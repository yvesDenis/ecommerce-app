package com.assHoleDeveloper.eCommerce.eCommerce.controller;

import com.assHoleDeveloper.eCommerce.eCommerce.configuration.LabelsConfiguration;
import com.assHoleDeveloper.eCommerce.eCommerce.controller.request.SignInRequest;
import com.assHoleDeveloper.eCommerce.eCommerce.controller.request.SignUpRequest;
import com.assHoleDeveloper.eCommerce.eCommerce.controller.response.GenericResponse;
import com.assHoleDeveloper.eCommerce.eCommerce.controller.response.SignInResponse;
import com.assHoleDeveloper.eCommerce.eCommerce.controller.response.SignUpResponse;
import com.assHoleDeveloper.eCommerce.eCommerce.dto.UserDTO;
import com.assHoleDeveloper.eCommerce.eCommerce.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Optional;

@Api(produces = "application/json", value = "API for login operations")
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LabelsConfiguration labelsConfiguration;

    @Autowired
    private LoginService loginService;

    @PostMapping("/signin")
    @ApiOperation(value = "Autenticate user", response = SignInResponse.class)
    public ResponseEntity<SignInResponse> authenticateUser(@Valid @RequestBody SignInRequest signInRequest, HttpServletResponse response) throws Exception{

        logger.info("enter call signin!!!");

        try {
            SignInResponse signInResponse = loginService.autenticateUser(signInRequest.getUsername(),
                    signInRequest.getPassword(),response);
            return new ResponseEntity<SignInResponse>(signInResponse, HttpStatus.OK);
        } catch(DisabledException e) {
            throw new ValidationException(labelsConfiguration.getAccountDisabled());
        } catch(LockedException e) {
            throw new ValidationException(labelsConfiguration.getAccountBlocked());
        }
    }

    @PostMapping("/signup")
    @ApiOperation(value = "Register a new user", response = SignUpResponse.class)
    public ResponseEntity<SignUpResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws Exception{

        logger.info("enter call signup!!!");

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(signUpRequest,userDTO);
        loginService.saveUser(Optional.of(userDTO));

        SignUpResponse signUpResponse = new SignUpResponse();
        signUpResponse.setSuccessMessage(labelsConfiguration.getSuccessRegistration());

        return new ResponseEntity<SignUpResponse>(signUpResponse, HttpStatus.CREATED);

    }

    @GetMapping("/signout")
    @ApiOperation(value = "Logout from application", response = Object.class)
    public ResponseEntity<GenericResponse> signOut() throws Exception{

        logger.info("enter call signOut!!!");

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccessMessage(labelsConfiguration.getLogoutSuccessFull());

        return new ResponseEntity<>(genericResponse, HttpStatus.OK);

    }
}
