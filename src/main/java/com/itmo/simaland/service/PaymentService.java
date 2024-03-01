package com.itmo.simaland.service;

import com.itmo.simaland.dto.payment.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public boolean processPayment(PaymentRequest paymentRequest) {
        // имитация
        // в реальном приложении здесь будет интеграция с платежной системой
        return paymentRequest.getCardNumber() != null && paymentRequest.getCardNumber().length() == 16;
    }
}
