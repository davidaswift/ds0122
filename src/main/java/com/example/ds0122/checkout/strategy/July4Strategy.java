package com.example.ds0122.checkout.strategy;

import com.example.ds0122.checkout.RentalDay;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import lombok.NonNull;

/**
 * Identifies a July 4th rental day.
 */
public class July4Strategy implements HolidayStrategy {

  @Override
  public void apply(@NonNull List<RentalDay> rentalDays) {
    for (ListIterator<RentalDay> iterator = rentalDays.listIterator(); iterator.hasNext(); ) {
      RentalDay rentalDay = iterator.next();
      if (isJuly4th(rentalDay)) {
        //Adjust if on a weekend
        DayOfWeek dayOfWeek = rentalDay.getDate().getDayOfWeek();
        if (DayOfWeek.SATURDAY.equals(dayOfWeek)) {
          if (iterator.hasPrevious()) {
            iterator.previous().setHoliday(true);
          }
        } else if (DayOfWeek.SUNDAY.equals(dayOfWeek)) {
          if (iterator.hasNext()) {
            iterator.next().setHoliday(true);
          }
        } else {
          rentalDay.setHoliday(true);
        }
        break;
      }
    }
  }

  private boolean isJuly4th(@NonNull RentalDay rentalDay) {
    return Month.JULY.equals(rentalDay.getDate().getMonth())
        && rentalDay.getDate().getDayOfMonth() == 4;
  }
}
