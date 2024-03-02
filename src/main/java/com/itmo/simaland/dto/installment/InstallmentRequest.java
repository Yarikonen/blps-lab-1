package com.itmo.simaland.dto.installment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstallmentRequest {
    private Long orderId;
    private int months;
    private Double initialPayment;
}
