package com.example.paymentms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/payment")
@RestController
public class PaymentController {

    @GetMapping("/info")
    public String getInfo() {
        return "This is payment api;";
    }
}
