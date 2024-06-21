package edu.miu.minimarket.entity.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private Long sellerId;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String category;
    private String subcategory;
    private String brand;
    private Double discount;
    private Integer rating;
    private Boolean onSale;
    private Boolean newArrival;
    private Boolean bestSeller;
    private String color;
    private String size;
    private String material;
    private String features;
    private String compatibility;
    private String model;
    private Integer year;
    private Long imageId;
}
