package com.itmo.simaland.dto.installment;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstallmentRequest {
    private Long orderId;

    @Min(1)
    private Integer months;
    private Double initialPayment;
}
