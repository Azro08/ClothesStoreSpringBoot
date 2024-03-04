package com.clothes_store.payload;

import lombok.Data;

@Data
public class OrderDto {
    int userId;
    String productNames;
    double totalPrice;
    String date;
    String paymentMethod;
    String address;
    String phoneNum;
}
