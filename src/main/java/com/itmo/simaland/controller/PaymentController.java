package com.itmo.simaland.controller;

import com.itmo.simaland.dto.payment.PaymentRequest;
import com.itmo.simaland.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    @Operation(summary = "Process payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Оплата проведена успешно"),
            @ApiResponse(responseCode = "400", description = "Неверный формат данных")
    })
    public String processPayment(@RequestBody PaymentRequest paymentRequest) {
        boolean success = paymentService.processPayment(paymentRequest);
        if (success) {
            return "Payment processed successfully";
        } else {
            return "Payment processing failed";
        }
    }
}
