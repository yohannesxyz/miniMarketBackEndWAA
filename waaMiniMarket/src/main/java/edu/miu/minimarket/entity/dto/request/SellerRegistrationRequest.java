package edu.miu.minimarket.entity.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerRegistrationRequest {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String firstname;
    private String lastname;
    private String storeName;
    private String storeDescription;
}
