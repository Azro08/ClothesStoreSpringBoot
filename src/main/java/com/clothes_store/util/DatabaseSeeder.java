package com.clothes_store.util;

import com.clothes_store.model.Category;
import com.clothes_store.model.User;
import com.clothes_store.model.UserRoles;
import com.clothes_store.repository.CategoryRepository;
import com.clothes_store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Check if admin user exists
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setPassword("admin123");
            admin.setRole(UserRoles.admin_role.name());
            userRepository.save(admin);
        }

        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("all"));
            categoryRepository.save(new Category("Shirts"));
            categoryRepository.save(new Category("Jeans"));
            categoryRepository.save(new Category("Shoes"));
            categoryRepository.save(new Category("Suits"));
            categoryRepository.save(new Category("T-Shirts"));
            categoryRepository.save(new Category("Hats"));
            categoryRepository.save(new Category("Jackets"));
            categoryRepository.save(new Category("Coats"));
            categoryRepository.save(new Category("Sweaters"));
            categoryRepository.save(new Category("Skirts"));
            categoryRepository.save(new Category("Dresses"));
            categoryRepository.save(new Category("Socks"));
            categoryRepository.save(new Category("Underwear"));
            categoryRepository.save(new Category("Sleepwear"));
            categoryRepository.save(new Category("Gloves"));
            categoryRepository.save(new Category("Belts"));
        }

    }
}
