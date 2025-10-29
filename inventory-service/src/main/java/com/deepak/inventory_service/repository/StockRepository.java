package com.deepak.inventory_service.repository;

import com.deepak.inventory_service.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {
}
