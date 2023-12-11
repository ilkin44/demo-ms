package com.example.paymentms.controller;

import com.example.paymentms.dto.PaymentResponseDto;
import com.example.paymentms.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/info")
    public String getInfo() {
        return "This is payment api;";
    }

    @GetMapping("/all/{orderId}")
    @Operation(summary = "getPaymentsByOrderId")
    List<PaymentResponseDto> getPaymentsByOrderId(@PathVariable Long orderId){
        return paymentService.getPaymentsByOrderId(orderId);
    }
}
