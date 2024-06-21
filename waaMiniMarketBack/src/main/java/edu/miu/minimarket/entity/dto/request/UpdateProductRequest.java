package edu.miu.minimarket.entity.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductRequest {
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String category;
    private String subcategory;
    private String brand;
}
