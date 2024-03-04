package com.clothes_store.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    int id;
    int userId;
    String productNames;
    double totalPrice;
    String date;
    String paymentMethod;
    String address;
    String phoneNum;

}
