package com.clothes_store.controller;

import com.clothes_store.model.Cart;
import com.clothes_store.model.Product;
import com.clothes_store.model.User;
import com.clothes_store.repository.ProductsRepository;
import com.clothes_store.repository.UserRepository;
import com.clothes_store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductsRepository productsRepository;


    @PostMapping("/add")
    public ResponseEntity<String> addToCart(int userId, int productId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Product> productOptional = productsRepository.findById(productId);

        if (userOptional.isPresent() && productOptional.isPresent()) {
            cartService.addToCart(userId, productId);
            return new ResponseEntity<>("Item added to cart", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid user or product ID", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/get")
    public ResponseEntity<List<Cart>> getCartItems(@RequestParam int userId) {
        List<Cart> cartItems = cartService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(cartItems, HttpStatus.OK);
        }
    }

    @DeleteMapping("/removeCartItem")
    public ResponseEntity<String> removeItemFromCart(@RequestParam int cartId) {
        cartService.removeItemFromCart(cartId);
        return new ResponseEntity<>("Item removed from cart", HttpStatus.OK);
    }

    @DeleteMapping("/removeAllCartItems")
    @PreAuthorize("hasRole('user_role')")
    public ResponseEntity<String> removeAllItemsFromCart(@RequestParam int userId) {
        cartService.removeAllItemsFromCart(userId);
        return new ResponseEntity<>("All items removed from cart", HttpStatus.OK);
    }
}
