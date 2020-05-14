package com.ss.training.lms;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ss.training.lms.controller.BorrowerController;
import com.ss.training.lms.entity.BookLoan;
import com.ss.training.lms.service.BorrowerService;

import net.minidev.json.JSONObject;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class BorrowerControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
    BorrowerService borrowerService;
	
	@InjectMocks
	private BorrowerController borrowerController;
	
	@BeforeAll
	public void setup() throws Exception
	{
		mockMvc = MockMvcBuilders.standaloneSetup(borrowerController).build();
	}
	
	@Test
	public void testReturnABook() throws Exception
	{
		LocalDateTime dateOut = LocalDateTime.now();
		LocalDateTime dueDate = dateOut.plusDays(7);
		BookLoan loan = new BookLoan(1,1,1,Timestamp.valueOf(dateOut), Timestamp.valueOf(dueDate),null);
		
		JSONObject item = new JSONObject();
		item.put("bookId", 1);
		item.put("branchId", 1);
		item.put("cardNo", 1);
		item.put("dateOut", Timestamp.valueOf(dateOut));
		item.put("dueDate", Timestamp.valueOf(dueDate));
		item.put("dateIn", null);
		
		 mockMvc.perform(MockMvcRequestBuilders.put("/lms/borrower/return")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(item.toString()))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.content().json(item.toString()));
		 
		JSONObject badItem = new JSONObject();
		badItem.put("bookId", 1);
		badItem.put("branchId", null);
		badItem.put("cardNo", "address");
		badItem.put("dateOut", null);
		badItem.put("dueDate", null);
		badItem.put("dateIn", null);
		
		 mockMvc.perform(MockMvcRequestBuilders.put("/lms/borrower/return")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(badItem.toString()))
	                .andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	public void testCheckOutABook() throws Exception
	{
		int bookId = 1;
		int branchId = 1;
		
		Mockito.when(borrowerService.checkIfBookIsAvailable(bookId, branchId)).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/checkOutBook/1/branch/{branchId}/borrower/1")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.when(borrowerService.checkIfBookIsAvailable(bookId, branchId)).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.post("/checkOutBook/1/branch/{branchId}/borrower/1")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(MockMvcResultMatchers.status().isNotFound());
		 
	}
}

