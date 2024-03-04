package com.clothes_store.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    int id;
    String category;
    String description;
    String name;
    double price;
    String image;
}
