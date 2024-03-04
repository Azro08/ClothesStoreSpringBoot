package com.clothes_store.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category", uniqueConstraints = {
        @UniqueConstraint(columnNames = "category")
})
public class Category {
    @Id
    @GeneratedValue
    int id;
    String category;

    public Category(String  category) {
        this.category = category;
    }

    public Category() {

    }
}
