package com.example.orderms.service.impl;

import com.example.orderms.client.PaymentClient;
import com.example.orderms.dto.PaymentResponseDto;
import com.example.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final PaymentClient paymentClient;
    @Override
    public List<PaymentResponseDto> getOrderPayments(Long orderId) {
        return paymentClient.getPaymentsByOrderId(orderId);
    }
}
