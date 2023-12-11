package com.example.orderms.controller;

import com.example.orderms.dto.PaymentResponseDto;
import com.example.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/info")
    public String getInfo() {
        return "This is order api;";
    }

    @GetMapping("/order-payments/{orderId}")
    public List<PaymentResponseDto> getOrderPayments(@PathVariable Long orderId){
        return orderService.getOrderPayments(orderId);
    }
}
