package com.itmo.simaland.service;

import com.itmo.simaland.dto.installment.InstallmentRequest;
import com.itmo.simaland.dto.installment.InstallmentResponse;
import com.itmo.simaland.model.entity.Order;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstallmentService {
    private final OrderService orderService;

    public InstallmentResponse processInstallment(InstallmentRequest request) {
        Order order = orderService.getOrderById(request.getOrderId());
        Double monthlyPayment = calculateMonthlyPayment(request.getMonths(), request.getInitialPayment());
        InstallmentResponse response = new InstallmentResponse(request.getOrderId(), monthlyPayment, true);
        orderService.markOrderAsPaid(order.getId());
        return response;
    }

    private Double calculateMonthlyPayment(int months, Double initialPayment) {
        return ( initialPayment) / months;
    }

}
