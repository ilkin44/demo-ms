package com.example.orderms.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PaymentResponseDto {

    private Long id;
    private String author;
    private LocalDateTime paymentDate;
    private Double amount;
}
