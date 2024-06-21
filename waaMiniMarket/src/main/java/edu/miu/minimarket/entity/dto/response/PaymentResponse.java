package edu.miu.minimarket.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentResponse {
    private Long id;
    private String paymentMethod;
    private String paymentStatus;
    private Double amount;
    private Long orderId;
}
