package com.example.product_service.controller;

import com.example.product_service.dto.ProductRequest;
import com.example.product_service.dto.ProductResponse;
import com.example.product_service.entity.Product;
import com.example.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;


    @PostMapping()
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest){
        return  productService.createProduct(productRequest);
    }

    @GetMapping()
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }



}
