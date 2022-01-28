package com.example.ds0122.checkout.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.example.ds0122.checkout.RentalDay;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class July4StrategyTest {

  private final July4Strategy july4Strategy = new July4Strategy();

  @Test
  public void applyTest_Weekday() {
    List<RentalDay> rentalDays =
        Arrays.asList(
            RentalDay.builder().date(LocalDate.of(2019, Month.JULY, 2)).build(),
            RentalDay.builder().date(LocalDate.of(2019, Month.JULY, 3)).build(),
            RentalDay.builder().date(LocalDate.of(2019, Month.JULY, 4)).build(),
            RentalDay.builder().date(LocalDate.of(2019, Month.JULY, 5)).build());
    july4Strategy.apply(rentalDays);
    assertTrue(rentalDays.get(2).isHoliday());
  }

  @Test
  public void applyTest_Weekend() {
    List<RentalDay> rentalDays =
        Arrays.asList(
            RentalDay.builder().date(LocalDate.of(2021, Month.JULY, 2)).build(),
            RentalDay.builder().date(LocalDate.of(2021, Month.JULY, 3)).build(),
            RentalDay.builder().date(LocalDate.of(2021, Month.JULY, 4)).build(),//Sunday
            RentalDay.builder().date(LocalDate.of(2021, Month.JULY, 5)).build());
    july4Strategy.apply(rentalDays);
    assertTrue(rentalDays.get(3).isHoliday());
  }

  @Test
  public void applyTest_WeekendNoObserved() {
    List<RentalDay> rentalDays =
        Arrays.asList(
            RentalDay.builder().date(LocalDate.of(2021, Month.JULY, 2)).build(),
            RentalDay.builder().date(LocalDate.of(2021, Month.JULY, 3)).build(),
            RentalDay.builder().date(LocalDate.of(2021, Month.JULY, 4)).build());//Sunday
    july4Strategy.apply(rentalDays);
    assertFalse(rentalDays.get(0).isHoliday());
    assertFalse(rentalDays.get(1).isHoliday());
    assertFalse(rentalDays.get(2).isHoliday());
  }
}
