package com.example.orderms.client;

import com.example.orderms.dto.PaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "payment-service", url = "http://localhost:8080/payment")
public interface PaymentClient {
    @GetMapping("/all/{orderId}")
    List<PaymentResponseDto> getPaymentsByOrderId(@PathVariable Long orderId);
}
