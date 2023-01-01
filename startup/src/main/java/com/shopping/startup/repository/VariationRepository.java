package com.shopping.startup.repository;

import com.shopping.startup.entity.Variation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariationRepository extends JpaRepository<Variation, Long> {
}
