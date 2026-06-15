package com.employee.management.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long product_id;
    private Integer quantity;
    private BigDecimal unit_price;
    private BigDecimal subtotal;
}
