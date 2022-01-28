package com.example.ds0122.checkout.strategy;

import com.example.ds0122.checkout.RentalDay;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;

public class LaborDayStrategy implements HolidayStrategy {

  private final TemporalAdjuster FIRST_MONDAY_IN_MONTH_TEMPORAL_ADJUSTER =
      TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY);

  @Override
  public void apply(@NonNull List<RentalDay> rentalDays) {
    Optional<RentalDay> rentalDayOptional =
        rentalDays.stream()
            .filter(this::isLaborDay)
            .findFirst();
    rentalDayOptional.ifPresent(rentalDay -> rentalDay.setHoliday(true));
  }

  private boolean isLaborDay(@NonNull RentalDay rentalDay) {
    LocalDate date = rentalDay.getDate();
    return Month.SEPTEMBER.equals(date.getMonth())
        && date.equals(date.with(FIRST_MONDAY_IN_MONTH_TEMPORAL_ADJUSTER));
  }
}
