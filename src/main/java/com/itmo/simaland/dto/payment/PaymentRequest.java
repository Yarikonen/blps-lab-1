package com.itmo.simaland.dto.payment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    @NotNull(message = "Order id is required")
    private Long orderId;

    @NotNull(message = "Card number is required")
    @NotEmpty(message = "Card number is required")
    private String cardNumber;

    @NotNull(message = "Card holder name is required")
    @NotEmpty(message = "Card holder name is required")
    private String cardHolderName;

    private String expirationDate; // MM/YY

    @NotNull(message = "CVV is required")
    @NotEmpty(message = "CVV is required")
    private String cvv;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be positive")
    private Double amount;
}
