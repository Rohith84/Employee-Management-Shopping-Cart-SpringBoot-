package com.employee.management.dto;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer stockQuantity;
    private String imageUrl;
    private Boolean active;
}
