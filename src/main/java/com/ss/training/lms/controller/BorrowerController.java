package com.ss.training.lms.controller;

import java.sql.SQLException;
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

@RestController
public class BorrowerController {
	
	@Autowired
	BorrowerService borrowerService;
	
    @RequestMapping(path="/lms/borrower/borrower/{cardNo}", 
                    method = RequestMethod.GET,
					produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
					})
	public ResponseEntity<Borrower> getBorrowerByCardNo(@PathVariable int cardNo) {
        Borrower borrower = null;
        HttpStatus status = HttpStatus.OK;
        try {
            borrower= borrowerService.readABorrower(cardNo);
            if (borrower == null) {
				status = HttpStatus.NOT_FOUND;
			}
        } catch (SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Borrower>(borrower, status);
	}

    @RequestMapping(path="/lms/borrower/borrowers",
                    method = RequestMethod.GET,
					produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
					})
	public ResponseEntity<List<Borrower>> getAllBorrowers() {
        List<Borrower> borrowers = null;
        HttpStatus status = HttpStatus.OK;
        try {
            borrowers= borrowerService.readAllBorrowers();
            if (borrowers == null) {
				status = HttpStatus.NOT_FOUND;
			}
		} catch (ClassNotFoundException | SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
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
		try {
            loans= borrowerService.getLoansFromBorrower(cardNo); 
            if (loans == null) {
				status = HttpStatus.NO_CONTENT;
			}
		} catch (ClassNotFoundException | SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
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
        HttpStatus status = HttpStatus.OK;
        try {
            borrowerService.returnBook(loan);
        } catch (ClassNotFoundException | SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<BookLoan>(loan, status);
	}

	@RequestMapping(path="/lms/borrower/checkOutBook/{bookId}/branch/{branchId}/borrower/{cardNo}", method = RequestMethod.POST)
	public HttpStatus checkOutBook(@PathVariable int bookId, @PathVariable int branchId, @PathVariable int cardNo) {
        HttpStatus status = HttpStatus.OK;
        try {
            borrowerService.checkOutBook(bookId, branchId, cardNo);
        } catch (ClassNotFoundException | SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return status;
	}
}
