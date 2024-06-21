package edu.miu.minimarket.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItemResponse {
    private Long id;
    private Integer quantity;
    private Long productId;
    private Long buyerId;
}
