package com.vibecoding.baitapapi.controller;

import com.vibecoding.baitapapi.entity.Category;
import com.vibecoding.baitapapi.entity.Product;
import com.vibecoding.baitapapi.repository.CategoryRepository;
import com.vibecoding.baitapapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class WebController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    // Trang chủ đơn giản
    @GetMapping("/")
    public String index() {
        return "index";   // /WEB-INF/views/index.jsp
    }

    // Hiển thị danh mục trên JSP (optional)
    @GetMapping("/categories")
    public String categoriesView(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories"; // /WEB-INF/views/categories.jsp
    }

    // 2. View: tất cả sản phẩm theo từng danh mục
    // URL: /categories/{id}/products
    @GetMapping("/categories/{id}/products")
    public String productsByCategory(@PathVariable("id") Long categoryId,
                                     Model model) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        List<Product> products = productRepository.findByCategoryId(categoryId);

        model.addAttribute("category", category);
        model.addAttribute("products", products);

        return "products-by-category"; // /WEB-INF/views/products-by-category.jsp
    }

    // 3. View: 10 sản phẩm bán nhiều nhất
    // URL: /products/top-sold
    @GetMapping("/products/top-sold")
    public String topSoldProducts(Model model) {
        List<Product> products = productRepository.findTop10ByOrderByQuantitySoldDesc();
        model.addAttribute("products", products);
        return "top-sold"; // /WEB-INF/views/top-sold.jsp
    }

    // 4. View: 10 sản phẩm tạo trong 7 ngày gần đây
    // URL: /products/recent
    @GetMapping("/products/recent")
    public String recentProducts(Model model) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        var pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Product> products = productRepository
                .findByCreatedAtGreaterThanEqual(sevenDaysAgo, pageable)
                .getContent();

        model.addAttribute("products", products);
        return "recent-products"; // /WEB-INF/views/recent-products.jsp
    }
}
