package com.clothes_store.service;

import com.clothes_store.model.Cart;
import com.clothes_store.model.Product;
import com.clothes_store.model.User;
import com.clothes_store.repository.CartRepository;
import com.clothes_store.repository.ProductsRepository;
import com.clothes_store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    public void addToCart(int userId, int productId) {
        Product product = productsRepository.findById(productId)
                .get();
        User user = userRepository.findById(userId).get();

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);

        cartRepository.save(cart);

    }

    public List<Cart> getCartItems(int userId) {
        User user = userRepository.findById(userId).get();
        return cartRepository.findByUser(user);
    }

    public void removeItemFromCart(int cartId) {
        cartRepository.deleteById(cartId);
    }

    public void removeAllItemsFromCart(int userId) {
        User user = userRepository.findById(userId).get();
        List<Cart> cartItems = cartRepository.findByUser(user);
        cartRepository.deleteAll(cartItems);
    }

    public void deleteCartItemsByProductId(int productId) {
        List<Cart> cartItems = cartRepository.findByProduct_Id(productId);
        cartRepository.deleteAll(cartItems);
    }

}
