package com.example.ds0122;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class Ds0122ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

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

	@Test
	public void test4() throws Exception {
		String checkout = getCheckoutRequestString("JAKD", 6, 0, "2015-09-03");
		this.mockMvc
				.perform(post("/checkout").contentType(MediaType.APPLICATION_JSON).content(checkout))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.toolCode").value("JAKD"))
				.andExpect(jsonPath("$.toolType").value("JACKHAMMER"))
				.andExpect(jsonPath("$.toolBrand").value("DeWalt"))
				.andExpect(jsonPath("$.rentalDays").value(6))
				.andExpect(jsonPath("$.checkoutDate").value("2015-09-03"))
				.andExpect(jsonPath("$.dailyRentalCharge").value(2.99))
				.andExpect(jsonPath("$.chargeableDays").value(3))
				.andExpect(jsonPath("$.preDiscountCharge").value(8.97))
				.andExpect(jsonPath("$.discountPercent").value(0))
				.andExpect(jsonPath("$.discountAmount").value(0.00))
				.andExpect(jsonPath("$.finalCharge").value(8.97))
				.andExpect(jsonPath("$.dueDate").value("2015-09-09"));
	}

	@Test
	public void test5() throws Exception {
		String checkout = getCheckoutRequestString("JAKR", 9, 0, "2015-07-02");
		this.mockMvc
				.perform(post("/checkout").contentType(MediaType.APPLICATION_JSON).content(checkout))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.toolCode").value("JAKR"))
				.andExpect(jsonPath("$.toolType").value("JACKHAMMER"))
				.andExpect(jsonPath("$.toolBrand").value("Ridgid"))
				.andExpect(jsonPath("$.rentalDays").value(9))
				.andExpect(jsonPath("$.checkoutDate").value("2015-07-02"))
				.andExpect(jsonPath("$.dailyRentalCharge").value(2.99))
				.andExpect(jsonPath("$.chargeableDays").value(6))
				.andExpect(jsonPath("$.preDiscountCharge").value(17.94))
				.andExpect(jsonPath("$.discountPercent").value(0))
				.andExpect(jsonPath("$.discountAmount").value(0.00))
				.andExpect(jsonPath("$.finalCharge").value(17.94))
				.andExpect(jsonPath("$.dueDate").value("2015-07-11"));
	}

	@Test
	public void test6() throws Exception {
		String checkout = getCheckoutRequestString("JAKR", 4, 50, "2015-07-02");
		this.mockMvc
				.perform(post("/checkout").contentType(MediaType.APPLICATION_JSON).content(checkout))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.toolCode").value("JAKR"))
				.andExpect(jsonPath("$.toolType").value("JACKHAMMER"))
				.andExpect(jsonPath("$.toolBrand").value("Ridgid"))
				.andExpect(jsonPath("$.rentalDays").value(4))
				.andExpect(jsonPath("$.checkoutDate").value("2015-07-02"))
				.andExpect(jsonPath("$.dailyRentalCharge").value(2.99))
				.andExpect(jsonPath("$.chargeableDays").value(2))
				.andExpect(jsonPath("$.preDiscountCharge").value(5.98))
				.andExpect(jsonPath("$.discountPercent").value(50))
				.andExpect(jsonPath("$.discountAmount").value(2.99))
				.andExpect(jsonPath("$.finalCharge").value(2.99))
				.andExpect(jsonPath("$.dueDate").value("2015-07-06"));
	}

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
	public void getRentalCharges() throws Exception {
		this.mockMvc.perform(get("/rental-charges")).andExpect(status().isOk());
	}

	@Test
	public void getRentalCharge() throws Exception {
		this.mockMvc.perform(get("/rental-charges/2")).andExpect(status().isOk());
	}

	@Test
	public void getRentalChargeNotFound() throws Exception {
		this.mockMvc.perform(get("/rental-charges/33")).andExpect(status().isNotFound());
	}

	private String getCheckoutRequestString(String toolCode, int rentalDays, int discountPercent, String checkoutDate) {
		return String.format("{ \"toolCode\": \"%s\", \"rentalDays\": %d, \"discountPercent\": %d, \"checkoutDate\": \"%s\"}",
				toolCode, rentalDays, discountPercent, checkoutDate);
	}
}
