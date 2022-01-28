package com.example.ds0122.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.example.ds0122.model.RentalAgreement;
import com.example.ds0122.model.RentalCharge;
import com.example.ds0122.model.Tool;
import com.example.ds0122.model.ToolType;
import com.example.ds0122.repository.RentalAgreementRepository;
import com.example.ds0122.repository.RentalChargeRepository;
import com.example.ds0122.repository.ToolRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CheckoutManagerTest {

  private CheckoutManager checkoutManager;
  private final ToolRepository toolRepository = Mockito.mock(ToolRepository.class);
  private final RentalChargeRepository rentalChargeRepository =
      Mockito.mock(RentalChargeRepository.class);
  private final RentalAgreementRepository rentalAgreementRepository =
      Mockito.mock(RentalAgreementRepository.class);

  @BeforeEach
  public void init() {
    checkoutManager =
        new CheckoutManager(toolRepository, rentalChargeRepository, rentalAgreementRepository);
  }

  @Test
  public void checkoutTest() {
    Checkout checkout = createCheckout(LocalDate.of(2015, Month.SEPTEMBER, 4), 10, 3, "LADW");

    //set up mocks
    Tool tool = Tool.builder().id(1L).code("LADW").brand("Brand").type(ToolType.LADDER).build();
    when(toolRepository.getByCode(eq("LADW"))).thenReturn(tool);

    RentalCharge rentalCharge =
        RentalCharge.builder()
            .id(1L)
            .dailyCharge(BigDecimal.valueOf(2.99))
            .weekdayCharge(true)
            .weekendCharge(true)
            .holidayCharge(false)
            .build();
    when(rentalChargeRepository.findByType(eq(tool.getType()))).thenReturn(rentalCharge);

    //exec test method
    RentalAgreement rentalAgreement = checkoutManager.checkout(checkout);
    rentalAgreement.writeToConsole();//TODO remove

    //verify
    assertEquals("LADW", rentalAgreement.getToolCode());
    assertEquals(ToolType.LADDER, rentalAgreement.getToolType());
    assertEquals("Brand", rentalAgreement.getToolBrand());
    assertEquals(3, rentalAgreement.getRentalDays());
    assertEquals(LocalDate.of(2015, Month.SEPTEMBER, 4), rentalAgreement.getCheckoutDate());
    assertEquals(LocalDate.of(2015, Month.SEPTEMBER, 7), rentalAgreement.getDueDate());
    assertEquals(BigDecimal.valueOf(2.99), rentalAgreement.getDailyRentalCharge());
    assertEquals(2, rentalAgreement.getChargeableDays());
    assertEquals(BigDecimal.valueOf(5.98), rentalAgreement.getPreDiscountCharge());
    assertEquals(10, rentalAgreement.getDiscountPercent());
    assertEquals(BigDecimal.valueOf(.60).setScale(2), rentalAgreement.getDiscountAmount());
    assertEquals(BigDecimal.valueOf(5.38), rentalAgreement.getFinalCharge());
  }

  private Checkout createCheckout(
      LocalDate checkoutDate, int discountPercent, int rentalDays, String toolCode) {
    return Checkout.builder()
        .checkoutDate(checkoutDate)
        .discountPercent(discountPercent)
        .rentalDays(rentalDays)
        .toolCode(toolCode)
        .build();
  }

  @Test
  public void getRentalDaysTest() {
    LocalDate checkoutDate = LocalDate.of(2022, Month.JANUARY, 5);
    int rentalDays = 3;
    List<RentalDay> rentalDayList = checkoutManager.getRentalDays(checkoutDate, rentalDays);

    assertEquals(rentalDayList.size(), 3);
    assertEquals(rentalDayList.get(0).getDate(), LocalDate.of(2022, Month.JANUARY, 6));
    assertEquals(rentalDayList.get(1).getDate(), LocalDate.of(2022, Month.JANUARY, 7));
    assertEquals(rentalDayList.get(2).getDate(), LocalDate.of(2022, Month.JANUARY, 8));
  }

  @Test
  public void isNoChargeDayTest_NoHolidayCharge() {
    LocalDate date = LocalDate.of(2021, Month.SEPTEMBER, 6);//Labor Day
    RentalDay rentalDay = RentalDay.builder().date(date).holiday(true).build();
    RentalCharge rentalCharge =
        RentalCharge.builder()
            .dailyCharge(BigDecimal.valueOf(2.99))
            .weekdayCharge(true)
            .weekendCharge(true)
            .holidayCharge(false)
            .build();
    boolean noChargeDay = checkoutManager.isNoChargeDay(rentalDay, rentalCharge);
    assertTrue(noChargeDay);
  }

  @Test
  public void isNoChargeDayTest_NoWeekendCharge() {
    LocalDate date = LocalDate.of(2022, Month.JANUARY, 8);//Saturday
    RentalDay rentalDay = RentalDay.builder().date(date).holiday(false).build();
    RentalCharge rentalCharge =
        RentalCharge.builder()
            .dailyCharge(BigDecimal.valueOf(2.99))
            .weekdayCharge(true)
            .weekendCharge(false)
            .holidayCharge(false)
            .build();
    boolean noChargeDay = checkoutManager.isNoChargeDay(rentalDay, rentalCharge);
    assertTrue(noChargeDay);
  }

  @Test
  public void isNoChargeDayTest_NoWeekdayCharge() {
    LocalDate date = LocalDate.of(2022, Month.JANUARY, 4);//Tuesday
    RentalDay rentalDay = RentalDay.builder().date(date).holiday(false).build();
    RentalCharge rentalCharge =
        RentalCharge.builder()
            .dailyCharge(BigDecimal.valueOf(2.99))
            .weekdayCharge(false)
            .weekendCharge(true)
            .holidayCharge(false)
            .build();
    boolean noChargeDay = checkoutManager.isNoChargeDay(rentalDay, rentalCharge);
    assertTrue(noChargeDay);
  }
}
