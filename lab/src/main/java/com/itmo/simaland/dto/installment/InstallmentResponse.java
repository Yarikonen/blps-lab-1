package com.itmo.simaland.dto.installment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentResponse {
    private Long orderId;
    private Double monthlyPayment;
    private boolean approved;
}
