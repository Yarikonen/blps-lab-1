package com.itmo.simaland.service;

import com.itmo.simaland.dto.installment.InstallmentRequest;
import com.itmo.simaland.dto.installment.InstallmentResponse;
import com.itmo.simaland.model.entity.Order;
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
        orderService.updateOrderPaidStatus(order.getId(), true);
        return response;
    }

    private Double calculateMonthlyPayment(Integer months, Double initialPayment) {
        return  initialPayment / months;
    }

}
