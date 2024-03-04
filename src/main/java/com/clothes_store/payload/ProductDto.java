package com.clothes_store.payload;

import lombok.Data;

@Data
public class ProductDto {
    String category;
    String description;
    String name;
    double price;
    String image;
}