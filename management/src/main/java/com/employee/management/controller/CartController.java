package com.employee.management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.dto.CartItemRequest;
import com.employee.management.service.CartItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartItemService cartItemService;
    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId, 
        @RequestBody CartItemRequest cartItemRequest){
        if(!cartItemService.addToCart(userId,cartItemRequest)){
            return ResponseEntity.badRequest().body("Product is out of Stock");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
