package com.assHoleDeveloper.eCommerce.eCommerce.commons;

import com.assHoleDeveloper.eCommerce.eCommerce.controller.response.GenericResponse;

import java.util.List;

public class EcommerceUtils {

    public GenericResponse generateResponse(String errorMessage , List<String> listErrorMessages,
                                            String successMessage) {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setErrorMessage(errorMessage);
        genericResponse.setListErrorMessages(listErrorMessages);
        genericResponse.setSuccessMessage(successMessage);

        return genericResponse;
    }
}
