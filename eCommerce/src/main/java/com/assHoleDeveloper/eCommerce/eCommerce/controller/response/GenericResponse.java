package com.assHoleDeveloper.eCommerce.eCommerce.controller.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse implements Serializable {

    private String errorCode;

    private String errorMessage;

    private List<String> listErrorMessages;

    private String successMessage;
}
