package com.itmo.simaland.controller;

import com.itmo.simaland.dto.payment.PaymentRequest;
import com.itmo.simaland.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Payment controller")
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    @PreAuthorize("hasAnyAuthority('VIEW_ORDERS')")
    @Operation(summary = "Process payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Оплата проведена успешно"),
            @ApiResponse(responseCode = "400", description = "Неверный формат данных")
    })
    public String processPayment(@RequestBody @Valid PaymentRequest paymentRequest) {
        return paymentService.processPayment(paymentRequest);
    }
}
