package edu.miu.minimarket.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ShoppingCartResponse {
    private Long id;
    private Long buyerId;
    private List<CartItemResponse> cartItems;
}
