package com.shopping.startup.repository;

import com.shopping.startup.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {
}
