package com.vibecoding.baitapapi.repository;

import com.vibecoding.baitapapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
