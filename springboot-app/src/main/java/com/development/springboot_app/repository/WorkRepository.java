package com.development.springboot_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.development.springboot_app.entity.Work;

public interface WorkRepository extends JpaRepository<Work, Integer> {
    
    @Query(value = 
            "SELECT id, name, description, vertical_size, horizontal_size, price, order_id " +
            "FROM work w " +
            "where w.order_id = :orderId", nativeQuery = true)
    List<Work> findByOrderId(@Param("orderId") Integer orderId);

    @Query(value = 
            "SELECT id, name, description, vertical_size, horizontal_size, price, order_id " +
            "FROM work w " +
            "ORDER BY w.id DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Work> findOneOrderByIdDesc();
}
