package com.clothes_store.controller;

import com.clothes_store.model.Product;
import com.clothes_store.repository.ProductsRepository;
import com.clothes_store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@MultipartConfig
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CartService cartService;

    // Add new product
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(
            @RequestPart("imageFile") MultipartFile imageFile,
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam("category") String category
    ) {
        try {
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setImage(imageFile.getOriginalFilename());
            product.setDescription(description);
            product.setCategory(category);

            productsRepository.save(product);
            return new ResponseEntity<>("Saved", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update existing product
    @PutMapping("/update/{productId}")
    public ResponseEntity<String> updateProduct(
            @PathVariable(value = "productId") int productId,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "category", required = false) String category
    ) {
        try {
            // Retrieve the existing product
            System.out.println(name + " --- " + productId + " --- " + price + " --- " + description + " --- " + category + " --- " + imageFile + " --- " + imageFile.getOriginalFilename() + " --- " + imageFile.getContentType() + " --- " + imageFile.getSize() + " --- ");
            Optional<Product> optionalProduct = productsRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                // Update the product attributes if the request parameters are provided
                if (name != null) {
                    product.setName(name);
                }
                if (price != null) {
                    product.setPrice(price);
                }
                if (description != null) {
                    product.setDescription(description);
                }
                if (category != null) {
                    product.setCategory(category);
                }
                if (imageFile != null) {
                    product.setImage(imageFile.getOriginalFilename());
                }

                productsRepository.save(product);
                return new ResponseEntity<>("Updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getByCategory")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam String category) {
        List<Product> products = productsRepository.getProductsByCategory(category);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @GetMapping("details/{id}")
    public ResponseEntity<Product> getProductDetails(@PathVariable int id) {
        Optional<Product> product = productsRepository.findById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Optional<Product> optionalProduct = productsRepository.findById(id);
        if (optionalProduct.isPresent()) {
            // Delete associated records from cart table
            cartService.deleteCartItemsByProductId(id);

            // Delete the product
            productsRepository.deleteById(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    // Get all products
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productsRepository.findAll();
    }
}