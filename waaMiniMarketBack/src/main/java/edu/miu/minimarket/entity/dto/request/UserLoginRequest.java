package edu.miu.minimarket.entity.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class UserLoginRequest {
    private String email;
    private String password;
}
