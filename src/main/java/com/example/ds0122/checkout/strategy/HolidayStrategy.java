package com.example.ds0122.checkout.strategy;

import com.example.ds0122.checkout.RentalDay;
import java.util.List;
import lombok.NonNull;

/**
 * A class that examines the rental days and identifies if any are a holiday by setting the
 * RentalDay.holiday property to true.
 */
public interface HolidayStrategy {
  void apply(@NonNull List<RentalDay> rentalDays);
}
