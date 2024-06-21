package edu.miu.minimarket.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BuyerResponse {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String firstname;
    private String lastname;
    private String shippingAddress;
    private String billingAddress;
}
