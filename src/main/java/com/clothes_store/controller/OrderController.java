package com.clothes_store.controller;

import com.clothes_store.model.Order;
import com.clothes_store.payload.OrderDto;
import com.clothes_store.repository.OrderRepository;
import com.clothes_store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    // Add an order
    @PostMapping("/add")
    public ResponseEntity<String> addOrder(@RequestBody OrderDto orderDto) {
        Order order = new Order();
        order.setProductNames(orderDto.getProductNames());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setDate(orderDto.getDate());
        order.setPaymentMethod(orderDto.getPaymentMethod());
        order.setAddress(orderDto.getAddress());
        order.setPhoneNum(orderDto.getPhoneNum());
        order.setUserId(orderDto.getUserId());
        try {
            orderRepository.save(order);
            cartService.removeAllItemsFromCart(orderDto.getUserId());
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }

    @GetMapping("/getOrder/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.map(order -> new ResponseEntity<>(order, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getUserOrders/{userId}")
    public ResponseEntity<List<Order>> getOrderByUserId(@PathVariable int userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }

    // Delete an order
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }

        orderRepository.delete(optionalOrder.get());
        return new ResponseEntity<>("Order deleted", HttpStatus.OK);
    }
}
