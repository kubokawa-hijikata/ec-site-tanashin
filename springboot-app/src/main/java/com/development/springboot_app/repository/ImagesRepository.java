package com.development.springboot_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.development.springboot_app.entity.Images;

public interface ImagesRepository extends JpaRepository<Images, Integer> {

    @Modifying
    @Transactional
    @Query(value = 
            "DELETE FROM images i " +
            "WHERE i.work_id = :workId", nativeQuery = true)
    void deleteByWorkId(@Param("workId") int workId);
    
}
