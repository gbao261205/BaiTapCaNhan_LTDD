package com.vibecoding.baitapapi.controller;

import com.vibecoding.baitapapi.entity.Category;
import com.vibecoding.baitapapi.entity.Product;
import com.vibecoding.baitapapi.repository.CategoryRepository;
import com.vibecoding.baitapapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * 1. Hiển thị tất cả danh mục
     * GET /api/categories
     */
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    /**
     * 2. Hiển thị tất cả sản phẩm theo từng danh mục
     * GET /api/categories/{categoryId}/products
     */
    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<List<Product>> getProductsByCategory(
            @PathVariable Long categoryId) {

        // có thể check tồn tại category nếu muốn
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    /**
     * 3. Hiển thị 10 sản phẩm có số lượng bán nhiều nhất
     * GET /api/products/top-sold
     */
    @GetMapping("/products/top-sold")
    public ResponseEntity<List<Product>> getTopSoldProducts() {
        List<Product> topProducts = productRepository.findTop10ByOrderByQuantitySoldDesc();
        return ResponseEntity.ok(topProducts);
    }

    /**
     * 4. Hiển thị 10 sản phẩm được tạo <= 7 ngày gần đây
     *    (tính từ thời điểm hiện tại trừ 7 ngày)
     * GET /api/products/recent
     */
    @GetMapping("/products/recent")
    public ResponseEntity<List<Product>> getRecentProducts() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        // dùng PageRequest để giới hạn 10 và sắp xếp createdAt desc
        var pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        List<Product> recentProducts =
                productRepository
                        .findByCreatedAtGreaterThanEqual(sevenDaysAgo, pageable)
                        .getContent();

        return ResponseEntity.ok(recentProducts);
    }
}
