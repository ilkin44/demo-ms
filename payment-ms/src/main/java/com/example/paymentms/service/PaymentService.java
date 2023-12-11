package com.example.paymentms.service;

import com.example.paymentms.dto.PaymentResponseDto;

import java.util.List;

public interface PaymentService {
    List<PaymentResponseDto> getPaymentsByOrderId(Long orderId);
}
