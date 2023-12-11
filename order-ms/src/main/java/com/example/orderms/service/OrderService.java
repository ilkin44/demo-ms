package com.example.orderms.service;

import com.example.orderms.dto.PaymentResponseDto;

import java.util.List;

public interface OrderService {
    List<PaymentResponseDto> getOrderPayments(Long orderId);
}
