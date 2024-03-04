package com.clothes_store.repository;

import com.clothes_store.model.Cart;
import com.clothes_store.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart, Integer> {
    List<Cart> findByUser(User user);

    List<Cart> findByProduct_Id(int productId);

    long count();
}
