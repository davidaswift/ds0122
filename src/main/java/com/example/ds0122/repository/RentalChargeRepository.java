package com.example.ds0122.repository;

import com.example.ds0122.model.RentalCharge;
import com.example.ds0122.model.ToolType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalChargeRepository extends JpaRepository<RentalCharge, Long> {

  RentalCharge findByType(ToolType toolType);
}
