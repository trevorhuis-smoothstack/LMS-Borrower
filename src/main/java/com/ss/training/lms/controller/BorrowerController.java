package com.ss.training.lms.controller;

import java.util.List;

import com.ss.training.lms.entity.BookLoan;
import com.ss.training.lms.entity.Borrower;
import com.ss.training.lms.service.BorrowerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Trevor Huis in 't Veld
 */
@RestController
public class BorrowerController {
	
	@Autowired
	BorrowerService borrowerService;
    
    /**
     * 
     * @param cardNo
     * @return
     */
    @RequestMapping(path="/lms/borrower/borrower/{cardNo}", 
                    method = RequestMethod.GET,
					produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
					})
	public ResponseEntity<Borrower> getBorrowerByCardNo(@PathVariable int cardNo) {
        Borrower borrower = null;
        HttpStatus status = HttpStatus.OK;
        borrower= borrowerService.readBorrower(cardNo);

        if (borrower == null) {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<Borrower>(borrower, status);
	}

    /**
     * 
     * @return
     */
    @RequestMapping(path="/lms/borrower/borrowers",
                    method = RequestMethod.GET,
					produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
					})
	public ResponseEntity<List<Borrower>> getAllBorrowers() {
        List<Borrower> borrowers = null;
        HttpStatus status = HttpStatus.OK;
        borrowers= borrowerService.readAllBorrowers();

        if (borrowers == null) 
            status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<List<Borrower>>(borrowers, status);
	}
	
	@RequestMapping(path="/lms/borrower/borrowerLoans/{cardNo}",
					method = RequestMethod.GET,
					produces = {
						MediaType.APPLICATION_JSON_VALUE,
						MediaType.APPLICATION_XML_VALUE
					})
	public ResponseEntity<List<BookLoan>> getLoansFromABorrower(@PathVariable int cardNo)  {
        List<BookLoan> loans= null;
        HttpStatus status = HttpStatus.OK;
        loans= borrowerService.getLoansFromBorrower(cardNo); 
        if (loans == null) 
            status = HttpStatus.NO_CONTENT;
        
        return new ResponseEntity<List<BookLoan>>(loans, status);
	}

	@RequestMapping(path="/lms/borrower/returnBook",
					method = RequestMethod.PUT,
					consumes = {
						MediaType.APPLICATION_JSON_VALUE,
						MediaType.APPLICATION_XML_VALUE
					})
	public ResponseEntity<BookLoan> returnBook(@RequestBody BookLoan loan) {
        if(loan.getBookId() == null || loan.getBranchId() == null || loan.getCardNo() == null)
            return new ResponseEntity<BookLoan>(loan, HttpStatus.BAD_REQUEST);

        boolean doesLoanExist = borrowerService.checkIfLoanExists(loan);
        if(!doesLoanExist)
            return new ResponseEntity<BookLoan>(loan, HttpStatus.NOT_FOUND);
        borrowerService.returnBook(loan);
        return new ResponseEntity<BookLoan>(loan, HttpStatus.OK);
	}

	@RequestMapping(path="/lms/borrower/checkOutBook/{bookId}/branch/{branchId}/borrower/{cardNo}", method = RequestMethod.POST)
	public HttpStatus checkOutBook(@PathVariable int bookId, @PathVariable int branchId, @PathVariable int cardNo) {
        HttpStatus status = HttpStatus.OK;
        borrowerService.checkOutBook(bookId, branchId, cardNo);
        return status;
	}
}
