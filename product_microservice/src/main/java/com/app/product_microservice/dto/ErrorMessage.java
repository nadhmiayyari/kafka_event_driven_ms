package com.app.product_microservice.dto;

import lombok.*;

import java.util.Date;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class ErrorMessage {

    private Date timestamp ;

    private String message ;

    private String details;

}
