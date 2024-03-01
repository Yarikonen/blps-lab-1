package com.itmo.simaland.dto.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate; // MM/YY
    private String cvv;
    private Double amount;
}
