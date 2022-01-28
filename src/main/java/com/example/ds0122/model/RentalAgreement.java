package com.example.ds0122.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalAgreement {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String toolCode;
  private ToolType toolType;
  private String toolBrand;
  private int rentalDays;
  private LocalDate checkoutDate;
  private BigDecimal dailyRentalCharge;
  private int chargeableDays;
  private BigDecimal preDiscountCharge;
  private int discountPercent;
  private BigDecimal discountAmount;
  private BigDecimal finalCharge;

  public LocalDate getDueDate() {
    return checkoutDate.plusDays(rentalDays);
  }

  public void writeToConsole() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
    System.out.printf("Tool code: %s%n", getToolCode());
    System.out.printf("Tool type: %s%n", getToolType().name());
    System.out.printf("Tool brand: %s%n", getToolBrand());
    System.out.printf("Rental days: %s%n", getRentalDays());
    System.out.printf("Checkout date: %s%n", getCheckoutDate().format(dateTimeFormatter));
    System.out.printf("Due date: %s%n", getDueDate().format(dateTimeFormatter));
    System.out.printf("Daily rental charge: %s%n", numberFormat.format(getDailyRentalCharge()));
    System.out.printf("Charge days: %s%n", getChargeableDays());
    System.out.printf("Pre-discount charge: %s%n", numberFormat.format(getPreDiscountCharge()));
    System.out.printf("Discount percent: %s%% %n", getDiscountPercent());
    System.out.printf("Discount amount: %s%n", numberFormat.format(getDiscountAmount()));
    System.out.printf("Final charge: %s%n", numberFormat.format(getFinalCharge()));
  }
}
