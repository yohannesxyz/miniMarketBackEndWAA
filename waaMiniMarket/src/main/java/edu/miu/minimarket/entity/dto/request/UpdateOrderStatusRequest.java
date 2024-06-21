package edu.miu.minimarket.entity.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateOrderStatusRequest {
    private String orderStatus;
}
