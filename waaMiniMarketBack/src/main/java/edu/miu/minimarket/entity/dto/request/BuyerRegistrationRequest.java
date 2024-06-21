package edu.miu.minimarket.entity.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyerRegistrationRequest {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String firstname;
    private String lastname;
    private String shippingAddress;
    private String billingAddress;
}
