package edu.miu.minimarket.entity.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private Long buyerId;
    private String shippingAddress;
    private String billingAddress;
    private List<OrderItemRequest> orderItems;
}
