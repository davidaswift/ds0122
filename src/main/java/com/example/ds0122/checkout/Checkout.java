package com.example.ds0122.checkout;

import java.time.LocalDate;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Checkout {
  @NotBlank
  private String toolCode;
  @Min(value = 1)
  private int rentalDays;
  @Min(value = 0)
  @Max(value = 100)
  private int discountPercent;
  @NotNull
  private LocalDate checkoutDate;
}
