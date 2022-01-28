package com.example.ds0122.checkout;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RentalDay {
  private final LocalDate date;
  private boolean holiday;
}
