package com.itmo.simaland.service;

import com.itmo.simaland.dto.payment.PaymentRequest;
import com.itmo.simaland.model.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final OrderService orderService;

    public boolean processPayment(PaymentRequest paymentRequest) {
        // имитация
        // в реальном приложении здесь будет интеграция с платежной системой
        Order order = orderService.getOrderById(paymentRequest.getOrderId());
        orderService.updateOrderPaidStatus(order.getId(),true);
        return paymentRequest.getCardNumber() != null && paymentRequest.getCardNumber().length() == 16;
    }
}
