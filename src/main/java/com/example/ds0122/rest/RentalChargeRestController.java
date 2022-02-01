package com.example.ds0122.rest;

import com.example.ds0122.model.RentalCharge;
import com.example.ds0122.repository.RentalChargeRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rental-charges")
@AllArgsConstructor
public class RentalChargeRestController {

  private final RentalChargeRepository rentalChargeRepository;

  @GetMapping
  public List<RentalCharge> getRentalCharges() {
    return rentalChargeRepository.findAll();
  }

  @GetMapping("/{id}")
  public RentalCharge getRentalCharge(@PathVariable String id) {
    return rentalChargeRepository
        .findById(Long.parseLong(id))
        .orElseThrow(EntityNotFoundException::new);
  }
}
