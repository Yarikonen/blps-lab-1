package com.itmo.simaland.dto.installment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstallmentRequest {
    private Long orderId;
    private Integer months;
    private Double initialPayment;
}
