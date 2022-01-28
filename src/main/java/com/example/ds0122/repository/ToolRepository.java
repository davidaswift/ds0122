package com.example.ds0122.repository;

import com.example.ds0122.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
  Tool getByCode(String toolCode);
}
