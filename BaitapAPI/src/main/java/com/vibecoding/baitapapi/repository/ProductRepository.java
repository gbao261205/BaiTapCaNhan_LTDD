package com.vibecoding.baitapapi.repository;

import com.vibecoding.baitapapi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    // hiển thị tất cả sản phẩm theo từng danh mục
    List<Product> findByCategoryId(Long categoryId);

    // 10 sp có số lượng bán nhiều nhất
    List<Product> findTop10ByOrderByQuantitySoldDesc();

    // sp tạo >= fromDate (dùng Pageable để giới hạn 10)
    Page<Product> findByCreatedAtGreaterThanEqual(LocalDateTime fromDate, Pageable pageable);
}
