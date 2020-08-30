package com.assHoleDeveloper.eCommerce.eCommerce.dto;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Mail {

    private String from;
    private String to;
    private String subject;
    private String content;
    private Map model;

}
