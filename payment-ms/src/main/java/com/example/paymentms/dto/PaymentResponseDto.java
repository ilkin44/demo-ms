package com.example.paymentms.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class PaymentResponseDto {

    private Long id;
    private String author;
    private LocalDateTime paymentDate;
    private Double amount;
}
