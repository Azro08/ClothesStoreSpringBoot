package com.clothes_store.controller;

import com.clothes_store.model.Category;
import com.clothes_store.payload.CategoryDto;
import com.clothes_store.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // Add a category
    @PostMapping("/add")
    @PreAuthorize("hasRole('admin_role')")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDto categoryDto, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        Category category = new Category(categoryDto.getCategory());
        categoryRepository.save(category);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }

    // Delete a category
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('admin_role')")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        categoryRepository.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    // Get all categories
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }
}
