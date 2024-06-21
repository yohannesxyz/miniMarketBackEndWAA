package edu.miu.minimarket.entity.dto.response;

import edu.miu.minimarket.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OrderResponse {
    private Long id;
    private String orderStatus;
    private Double totalPrice;
    private String shippingAddress;
    private String billingAddress;
    private Long buyerId;
    private List<OrderItemResponse> orderItems;

}
