package com.example.ds0122.checkout;

import com.example.ds0122.checkout.strategy.HolidayStrategy;
import com.example.ds0122.checkout.strategy.July4Strategy;
import com.example.ds0122.checkout.strategy.LaborDayStrategy;
import com.example.ds0122.model.RentalAgreement;
import com.example.ds0122.model.RentalCharge;
import com.example.ds0122.model.Tool;
import com.example.ds0122.repository.RentalAgreementRepository;
import com.example.ds0122.repository.RentalChargeRepository;
import com.example.ds0122.repository.ToolRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CheckoutManager {

  private final ToolRepository toolRepository;
  private final RentalChargeRepository rentalChargeRepository;
  private final RentalAgreementRepository rentalAgreementRepository;

  private final List<HolidayStrategy> holidayStrategies =
      Arrays.asList(new July4Strategy(), new LaborDayStrategy());//new holidays can be added here

  /**
   * Creates a RentalAgreement based on the checkout parameters.
   * The RentalAgreement is persisted for later lookup if necessary.
   * @param checkout The details of the rental
   * @return the rental agreement
   */
  public RentalAgreement checkout(@NonNull Checkout checkout) {
    //get a list of the rental days
    List<RentalDay> rentalDays =
        getRentalDays(checkout.getCheckoutDate(), checkout.getRentalDays());
    //identify days that are holidays
    holidayStrategies.forEach(holidayStrategy -> holidayStrategy.apply(rentalDays));
    //lookup rental charge
    Tool tool = toolRepository.getByCode(checkout.getToolCode());
    RentalCharge rentalCharge = rentalChargeRepository.findByType(tool.getType());
    //get chargeable days
    List<RentalDay> chargeableRentalDays =
        rentalDays.stream()
            .filter(rentalDay -> !isNoChargeDay(rentalDay, rentalCharge))
            .collect(Collectors.toList());
    // pre-discount total
    BigDecimal preDiscountTotal =
        rentalCharge
            .getDailyCharge()
            .multiply(BigDecimal.valueOf(chargeableRentalDays.size()))
            .setScale(2, RoundingMode.HALF_UP);
    //calculate discount
    BigDecimal discountPercent = BigDecimal.valueOf(checkout.getDiscountPercent()).movePointLeft(2);
    BigDecimal discountAmount =
        preDiscountTotal.multiply(discountPercent).setScale(2, RoundingMode.HALF_UP);
    //final charge
    BigDecimal finalCharge = preDiscountTotal.subtract(discountAmount);
    //create rental agreement
    RentalAgreement rentalAgreement =
        RentalAgreement.builder()
            .toolCode(checkout.getToolCode())
            .toolType(tool.getType())
            .toolBrand(tool.getBrand())
            .rentalDays(checkout.getRentalDays())
            .checkoutDate(checkout.getCheckoutDate())
            .dailyRentalCharge(rentalCharge.getDailyCharge())
            .chargeableDays(chargeableRentalDays.size())
            .preDiscountCharge(preDiscountTotal)
            .discountPercent(checkout.getDiscountPercent())
            .discountAmount(discountAmount)
            .finalCharge(finalCharge)
            .build();
    //save
    rentalAgreementRepository.save(rentalAgreement);

    return rentalAgreement;
  }

  List<RentalDay> getRentalDays(@NonNull LocalDate checkoutDate, int rentalDays) {
    return Stream.iterate(checkoutDate.plusDays(1), date -> date.plusDays(1))
        .limit(rentalDays)
        .map(date -> RentalDay.builder().date(date).build())
        .collect(Collectors.toList());
  }

  boolean isNoChargeDay(@NonNull RentalDay rentalDay, @NonNull RentalCharge rentalCharge) {
    boolean isNoCharge;
    LocalDate date = rentalDay.getDate();
    isNoCharge = (rentalDay.isHoliday() && !rentalCharge.isHolidayCharge()) ||
                 (isWeekend(date) && !rentalCharge.isWeekendCharge()) ||
                 (isWeekday(date) && !rentalCharge.isWeekdayCharge());
    return isNoCharge;
  }

  private boolean isWeekday(@NonNull LocalDate date) {
    return !isWeekend(date);
  }

  private boolean isWeekend(@NonNull LocalDate date) {
    DayOfWeek dayOfWeek = date.getDayOfWeek();
    return DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek);
  }
}
