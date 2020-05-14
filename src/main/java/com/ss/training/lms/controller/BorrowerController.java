package com.ss.training.lms.controller;

import com.ss.training.lms.entity.BookLoan;
import com.ss.training.lms.service.BorrowerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Trevor Huis in 't Veld
 */
@RestController
@RequestMapping(path="/lms/borrower")
public class BorrowerController {
	
	@Autowired
	BorrowerService borrowerService;
    
    /**
     * 
     * @param loan
     * @return
     */
	@PutMapping(path="/returnBook")
	public ResponseEntity<BookLoan> returnBook(@RequestBody BookLoan loan) {
        if(loan.getBookId() == null || loan.getBranchId() == null || loan.getCardNo() == null || loan.getDateOut() == null)
            return new ResponseEntity<BookLoan>(loan, HttpStatus.BAD_REQUEST);

        boolean doesLoanExist = borrowerService.checkIfLoanExists(loan);
        if(!doesLoanExist)
            return new ResponseEntity<BookLoan>(loan, HttpStatus.NOT_FOUND);

        borrowerService.returnBook(loan);
        return new ResponseEntity<BookLoan>(loan, HttpStatus.OK);
	}

    /**
     * 
     * @param bookId
     * @param branchId
     * @param cardNo
     * @return
     */
	@PostMapping(path="/checkOutBook/{bookId}/branch/{branchId}/borrower/{cardNo}")
	public HttpStatus checkOutBook(@PathVariable int bookId, @PathVariable int branchId, @PathVariable int cardNo) {
        boolean isBookAvailable = borrowerService.checkIfBookIsAvailable(bookId, branchId);

        if(!isBookAvailable)
            return HttpStatus.NOT_FOUND;
        borrowerService.checkOutBook(bookId, branchId, cardNo);
        return HttpStatus.ACCEPTED;
	}
}
