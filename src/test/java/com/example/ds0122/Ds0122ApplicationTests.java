package com.example.ds0122;

import com.example.ds0122.checkout.Checkout;
import com.example.ds0122.model.ToolType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Ds0122ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getTools() throws Exception {
		this.mockMvc.perform(get("/tools")).andExpect(status().isOk());
	}

	@Test
	public void getTool() throws Exception {
		this.mockMvc.perform(get("/tools/1")).andExpect(status().isOk());
	}

	@Test
	public void getToolNotFound() throws Exception {
		this.mockMvc.perform(get("/tools/5")).andExpect(status().isNotFound());
	}

	@Test
	public void test1() throws Exception {
    String checkout = getCheckoutRequestString("JAKR", 5, 101, "2015-09-03");
    this.mockMvc
        .perform(post("/checkout").contentType(MediaType.APPLICATION_JSON).content(checkout))
        .andExpect(status().isBadRequest());
	}

  @Test
  public void test2() throws Exception {
		String checkout = getCheckoutRequestString("LADW", 3, 10, "2020-07-02");
		this.mockMvc
				.perform(post("/checkout").contentType(MediaType.APPLICATION_JSON).content(checkout))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.toolCode").value("LADW"))
				.andExpect(jsonPath("$.toolType").value("LADDER"))
				.andExpect(jsonPath("$.toolBrand").value("Werner"))
				.andExpect(jsonPath("$.rentalDays").value(3))
				.andExpect(jsonPath("$.checkoutDate").value("2020-07-02"))
				.andExpect(jsonPath("$.dailyRentalCharge").value(1.99))
				.andExpect(jsonPath("$.chargeableDays").value(2))
				.andExpect(jsonPath("$.preDiscountCharge").value(3.98))
				.andExpect(jsonPath("$.discountPercent").value(10))
				.andExpect(jsonPath("$.discountAmount").value(0.40))
				.andExpect(jsonPath("$.finalCharge").value(3.58))
				.andExpect(jsonPath("$.dueDate").value("2020-07-05"));
	}

	@Test
	public void test3() throws Exception {
		String checkout = getCheckoutRequestString("CHNS", 5, 25, "2015-07-02");
		this.mockMvc
				.perform(post("/checkout").contentType(MediaType.APPLICATION_JSON).content(checkout))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.toolCode").value("CHNS"))
				.andExpect(jsonPath("$.toolType").value("CHAINSAW"))
				.andExpect(jsonPath("$.toolBrand").value("Stihl"))
				.andExpect(jsonPath("$.rentalDays").value(5))
				.andExpect(jsonPath("$.checkoutDate").value("2015-07-02"))
				.andExpect(jsonPath("$.dailyRentalCharge").value(1.49))
				.andExpect(jsonPath("$.chargeableDays").value(3))
				.andExpect(jsonPath("$.preDiscountCharge").value(4.47))
				.andExpect(jsonPath("$.discountPercent").value(25))
				.andExpect(jsonPath("$.discountAmount").value(1.12))
				.andExpect(jsonPath("$.finalCharge").value(3.35))
				.andExpect(jsonPath("$.dueDate").value("2015-07-07"));
	}

//	@Test
//	public void test4() throws Exception {
//		String checkout = getCheckoutRequestString("JAKR", 5, 10, "2015-09-03");
//		this.mockMvc
//				.perform(post("/checkout").contentType(MediaType.APPLICATION_JSON).content(checkout))
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.toolCode").value("JAKR"))
//				.andExpect(jsonPath("$.toolType").value("JACKHAMMER"))
//				.andExpect(jsonPath("$.toolBrand").value("Ridgid"))
//				.andExpect(jsonPath("$.rentalDays").value(5))
//				.andExpect(jsonPath("$.checkoutDate").value("2015-09-03"))
//				.andExpect(jsonPath("$.dailyRentalCharge").value(2.99))
//				.andExpect(jsonPath("$.chargeableDays").value(2))
//				.andExpect(jsonPath("$.preDiscountCharge").value(5.98))
//				.andExpect(jsonPath("$.discountPercent").value(10))
//				.andExpect(jsonPath("$.discountAmount").value(0.60))
//				.andExpect(jsonPath("$.finalCharge").value(5.38))
//				.andExpect(jsonPath("$.dueDate").value("2015-09-08"));
//	}
//
//	@Test
//	public void test5() throws Exception {
//		String checkout = getCheckoutRequestString("JAKR", 5, 10, "2015-09-03");
//		this.mockMvc
//				.perform(post("/checkout").contentType(MediaType.APPLICATION_JSON).content(checkout))
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.toolCode").value("JAKR"))
//				.andExpect(jsonPath("$.toolType").value("JACKHAMMER"))
//				.andExpect(jsonPath("$.toolBrand").value("Ridgid"))
//				.andExpect(jsonPath("$.rentalDays").value(5))
//				.andExpect(jsonPath("$.checkoutDate").value("2015-09-03"))
//				.andExpect(jsonPath("$.dailyRentalCharge").value(2.99))
//				.andExpect(jsonPath("$.chargeableDays").value(2))
//				.andExpect(jsonPath("$.preDiscountCharge").value(5.98))
//				.andExpect(jsonPath("$.discountPercent").value(10))
//				.andExpect(jsonPath("$.discountAmount").value(0.60))
//				.andExpect(jsonPath("$.finalCharge").value(5.38))
//				.andExpect(jsonPath("$.dueDate").value("2015-09-08"));
//	}
//
//	@Test
//	public void test6() throws Exception {
//		String checkout = getCheckoutRequestString("JAKR", 5, 10, "2015-09-03");
//		this.mockMvc
//				.perform(post("/checkout").contentType(MediaType.APPLICATION_JSON).content(checkout))
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.toolCode").value("JAKR"))
//				.andExpect(jsonPath("$.toolType").value("JACKHAMMER"))
//				.andExpect(jsonPath("$.toolBrand").value("Ridgid"))
//				.andExpect(jsonPath("$.rentalDays").value(5))
//				.andExpect(jsonPath("$.checkoutDate").value("2015-09-03"))
//				.andExpect(jsonPath("$.dailyRentalCharge").value(2.99))
//				.andExpect(jsonPath("$.chargeableDays").value(2))
//				.andExpect(jsonPath("$.preDiscountCharge").value(5.98))
//				.andExpect(jsonPath("$.discountPercent").value(10))
//				.andExpect(jsonPath("$.discountAmount").value(0.60))
//				.andExpect(jsonPath("$.finalCharge").value(5.38))
//				.andExpect(jsonPath("$.dueDate").value("2015-09-08"));
//	}

	private String getCheckoutRequestString(String toolCode, int rentalDays, int discountPercent, String checkoutDate) {
		return String.format("{ \"toolCode\": \"%s\", \"rentalDays\": %d, \"discountPercent\": %d, \"checkoutDate\": \"%s\"}",
				toolCode, rentalDays, discountPercent, checkoutDate);
	}
}
