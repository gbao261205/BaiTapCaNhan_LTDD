package com.vibecoding.baitapapi.config;

import com.vibecoding.baitapapi.entity.Category;
import com.vibecoding.baitapapi.entity.Product;
import com.vibecoding.baitapapi.repository.CategoryRepository;
import com.vibecoding.baitapapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() > 0) return;

        Category cat1 = new Category("Điện thoại");
        Category cat2 = new Category("Laptop");
        Category cat3 = new Category("Phụ kiện");

        categoryRepository.save(cat1);
        categoryRepository.save(cat2);
        categoryRepository.save(cat3);

        Random random = new Random();

        // tạo 30 sản phẩm demo
        for (int i = 1; i <= 30; i++) {
            Category cat;
            if (i % 3 == 1) cat = cat1;
            else if (i % 3 == 2) cat = cat2;
            else cat = cat3;

            Product p = new Product();
            p.setName("Product " + i);
            p.setPrice(BigDecimal.valueOf(1000000 + i * 10000L));
            p.setQuantitySold(random.nextInt(100)); // 0 - 99
            p.setCategory(cat);

            // cho 1 phần sản phẩm có createdAt cách đây >7 ngày
            if (i <= 15) {
                p.setCreatedAt(LocalDateTime.now().minusDays(10));
            } else {
                p.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(6))); // 0-5 ngày
            }

            productRepository.save(p);
        }

        System.out.println("Sample data loaded!");
    }
}
