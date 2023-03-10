package com.shopping.startup.repository;

import com.shopping.startup.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Category findByCategoryName(String categoryName);
}
