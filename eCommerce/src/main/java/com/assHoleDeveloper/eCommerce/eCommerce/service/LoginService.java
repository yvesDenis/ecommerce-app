package com.assHoleDeveloper.eCommerce.eCommerce.service;

import com.assHoleDeveloper.eCommerce.eCommerce.assembler.UserAssembler;
import com.assHoleDeveloper.eCommerce.eCommerce.configuration.ConstantsConfiguration;
import com.assHoleDeveloper.eCommerce.eCommerce.configuration.LabelsConfiguration;
import com.assHoleDeveloper.eCommerce.eCommerce.controller.response.SignInResponse;
import com.assHoleDeveloper.eCommerce.eCommerce.dto.Mail;
import com.assHoleDeveloper.eCommerce.eCommerce.dto.UserDTO;
import com.assHoleDeveloper.eCommerce.eCommerce.exception.ValidationException;
import com.assHoleDeveloper.eCommerce.eCommerce.repository.RoleRepository;
import com.assHoleDeveloper.eCommerce.eCommerce.repository.UserRepository;
import com.assHoleDeveloper.eCommerce.eCommerce.security.JwtSecurityProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private LabelsConfiguration labelsConfiguration;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtSecurityProvider jwtSecurityProvider;

    @Autowired
    private ConstantsConfiguration constantsConfiguration;

    @Autowired
    private EmailService emailService;


    public void saveUser(Optional<UserDTO> userDTOOptional) throws Exception {

        if(userDTOOptional.isPresent()){

            UserDTO userDTO = userDTOOptional.get();


            if (userRepository.existsByUsername(userDTO.getUsername())) {
                throw new ValidationException(labelsConfiguration.getUserAlreadyUedError());
            }

            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new ValidationException(labelsConfiguration.getEmailAlreadyUedError());
            }

            userDTO.setPassword(encoder.encode(userDTO.getPassword()));

            UserAssembler.fromUserDTOTOModelDTO(userDTOOptional)
                    .map( user -> userRepository.save(user))
                    .orElseThrow(() -> new ValidationException(labelsConfiguration.getSaveError()));

            // now the user is registered , i can send him the email confirmation
            Mail mail = new Mail();
            mail.setFrom(constantsConfiguration.getMailSender());
            mail.setTo(userDTO.getEmail().toLowerCase());
            mail.setSubject(constantsConfiguration.getMailSubject());

            Map model = new HashMap();
            model.put("name", userDTO.getUsername());
            mail.setModel(model);

            try{
                emailService.sendSimpleMessage(mail);
            } catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }

    public SignInResponse autenticateUser(String username , String password , HttpServletResponse response) throws Exception {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtSecurityProvider.generateJwtToken(authentication);

        Long expirationTime = jwtSecurityProvider.getExpirationFromJwtToken(jwt);

        SignInResponse signInResponse = new SignInResponse();

        signInResponse.setUserDTO(UserAssembler.fromUserModelTOUserDTO(
                userRepository.findByUsername(username)).get());

        response.addHeader("Authorization",jwt);
        response.addHeader("ExpirationTime",expirationTime.toString());

        return signInResponse;
    }

    public void revokeAutenfication() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
