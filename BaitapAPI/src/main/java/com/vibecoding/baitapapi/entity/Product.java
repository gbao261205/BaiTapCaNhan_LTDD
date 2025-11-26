package com.vibecoding.baitapapi.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    // số lượng đã bán
    private Integer quantitySold;

    // ngày tạo sản phẩm
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {
    }

    public Product(String name, BigDecimal price, Integer quantitySold, Category category) {
        this.name = name;
        this.price = price;
        this.quantitySold = quantitySold;
        this.category = category;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (quantitySold == null) {
            quantitySold = 0;
        }
    }

    // getter/setter
    public Long getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantitySold() { return quantitySold; }

    public void setQuantitySold(Integer quantitySold) { this.quantitySold = quantitySold; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; }
}
