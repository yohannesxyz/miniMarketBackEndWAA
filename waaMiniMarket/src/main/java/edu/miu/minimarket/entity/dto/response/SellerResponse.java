package edu.miu.minimarket.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class SellerResponse {
    private Long id;
    private String storeName;
    private String storeDescription;
    private Boolean approved;
    private String username;
    private String email;
    private String phoneNumber;
    private String firstname;
    private String lastname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
