package com.ss.training.lms.controller.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.junit4.SpringRunner;

import com.ss.training.lms.controller.BorrowerController;
import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.BookCopies;
import com.ss.training.lms.entity.BookLoan;
import com.ss.training.lms.entity.Borrower;
import com.ss.training.lms.entity.LibraryBranch;
import com.ss.training.lms.service.BorrowerService;
import com.ss.training.lms.service.LibrarianService;

import net.minidev.json.JSONArray;
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
	public void testGetBorrower() throws Exception
	{
		Borrower borrower = new Borrower(1, "name", "address", "phone");
		
		Mockito.when(borrowerService.readABorrower(1)).thenReturn(borrower);
		
		JSONObject foundBorrower = new JSONObject();
		foundBorrower.put("cardNo", 1);
		foundBorrower.put("name", "name");
		foundBorrower.put("address", "address");
		foundBorrower.put("phone", "phone");
		
		 mockMvc.perform(MockMvcRequestBuilders.put("/lms/borrower/borrower/1")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(foundBorrower.toString()))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.content().json(foundBorrower.toString()));
		 
		 Mockito.when(borrowerService.readABorrower(1)).thenReturn(borrower);
		 
		 mockMvc.perform(MockMvcRequestBuilders.put("/lms/borrower/borrower/1")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(foundBorrower.toString()))
	                .andExpect(MockMvcResultMatchers.status().isNotFound());

	}
	
	@Test
	public void testGetBranches() throws Exception
	{
		// mock data to be returned. 
		List<Borrower> borrowers = new ArrayList<Borrower>();
		borrowers.add(new Borrower(1, "name", "address", "phone"));
		
		// create json to test the response from the controller.
		JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("cardNo", 1);
		item.put("name", "name");
		item.put("address", "address");
		item.put("phone", "phone");
		array.add(item);
		
		// Tell borrower service to return the branches I created earlier when called.
		Mockito.when(borrowerService.readAllBorrowers()).thenReturn(borrowers);
		
		// make a request to the controller through the mock and check the results
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/borrower/borrowers"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(array.toString()));
		
		// Tell borrower service to return null when get branches is called.
		Mockito.when(borrowerService.readAllBorrowers()).thenReturn(null);
		
		// test the return when the database is empty
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/borrower/borrowers"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}
	
	@Test
	public void testgetLoansFromABorrower() throws Exception
	{
		// mock data to be returned. 
		List<BookLoan> loans = new ArrayList<BookLoan>();
		LocalDateTime dateOut = LocalDateTime.now();
		LocalDateTime dueDate = dateOut.plusDays(7);
		loans.add(new BookLoan(1,1,1, Timestamp.valueOf(dateOut),Timestamp.valueOf(dueDate) ,null));
		
		// create json to test the response from the controller.
		JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("bookId", 1);
		item.put("branchId", 1);
		item.put("cardNo", 1);
		item.put("dateOut", Timestamp.valueOf(dateOut));
		item.put("dueDate", Timestamp.valueOf(dueDate));
		item.put("dateIn", null);
		array.add(item);
		
		Mockito.when(borrowerService.getLoansFromBorrower(1)).thenReturn(loans);
		
		// make a request to the controller through the mock and check the results
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/borrower/borrowerLoans/1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(array.toString()));
		
		// Tell borrower service to return null when get branches is called.
		Mockito.when(borrowerService.getLoansFromBorrower(1)).thenReturn(null);
		
		// test the return when the database is empty
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/borrower/borrowerLoans/1"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		
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
}

// check out book
