package com.example.ds0122.checkout.strategy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.ds0122.checkout.RentalDay;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LaborDayStrategyTest {

  private final LaborDayStrategy laborDayStrategy = new LaborDayStrategy();

  @Test
  public void applyTest() {
    List<RentalDay> rentalDays =
        Arrays.asList(
            RentalDay.builder().date(LocalDate.of(2021, Month.SEPTEMBER, 4)).build(),
            RentalDay.builder().date(LocalDate.of(2021, Month.SEPTEMBER, 5)).build(),
            RentalDay.builder().date(LocalDate.of(2021, Month.SEPTEMBER, 6)).build(),//Labor day
            RentalDay.builder().date(LocalDate.of(2021, Month.SEPTEMBER, 7)).build());

    laborDayStrategy.apply(rentalDays);

    assertFalse(rentalDays.get(0).isHoliday());
    assertFalse(rentalDays.get(1).isHoliday());
    assertTrue(rentalDays.get(2).isHoliday());
    assertFalse(rentalDays.get(3).isHoliday());
  }

}
