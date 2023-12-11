package com.example.paymentms.service.impl;

import com.example.paymentms.dto.PaymentResponseDto;
import com.example.paymentms.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    @Override
    public List<PaymentResponseDto> getPaymentsByOrderId(Long orderId) {

        if (orderId == 1) {
            List<PaymentResponseDto> payments = new ArrayList<>();

            payments.add(PaymentResponseDto
                    .builder()
                    .author("Ilkin Aghayev")
                    .paymentDate(LocalDateTime.now().minusDays(1))
                    .id(4342L)
                    .amount(989.0)
                    .build());

            return payments;
        } else return Collections.emptyList();
    }
}
