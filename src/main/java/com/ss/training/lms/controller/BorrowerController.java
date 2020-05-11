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
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/lms/borrower")
public class BorrowerController {
	
	@Autowired
	BorrowerService borrowerService;
    
    /**
     * 
     * @param cardNo
     * @return
     */
    @GetMapping(path="/{cardNo}", 
					produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
					})
	public ResponseEntity<Borrower> getBorrowerByCardNo(@PathVariable int cardNo) {
        Borrower borrower= null;
        HttpStatus status= HttpStatus.OK;
        try {
            borrower= borrowerService.readABorrower(cardNo);
            if (borrower == null) {
				status= HttpStatus.NOT_FOUND;
			}
        } catch (SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Borrower>(borrower, status);
	}

    /**
     * 
     * @return
     */
    @GetMapping(path="/borrowers",
					produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
					})
	public ResponseEntity<List<Borrower>> getAllBorrowers() {
        List<Borrower> borrowers= null;
        HttpStatus status= HttpStatus.OK;
        try {
            borrowers= borrowerService.readAllBorrowers();
            if (borrowers == null) {
				status= HttpStatus.NO_CONTENT;
			}
		} catch (ClassNotFoundException | SQLException e) {
            status= HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<List<Borrower>>(borrowers, status);
	}
    
    /**
     * 
     * @param cardNo
     * @return
     */
	@GetMapping(path="/loansByBorrower/{cardNo}",
					produces = {
						MediaType.APPLICATION_JSON_VALUE,
						MediaType.APPLICATION_XML_VALUE
					})
	public ResponseEntity<List<BookLoan>> getLoansFromABorrower(@PathVariable int cardNo)  {
        List<BookLoan> loans= null;
        Borrower borrower= null;
        HttpStatus status = HttpStatus.OK;
		try {
            borrower = borrowerService.readABorrower(cardNo);
            if (borrower == null)
                status = HttpStatus.NOT_FOUND;
            else
                loans= borrowerService.getLoansFromBorrower(cardNo); 
                if (loans == null)
                    status = HttpStatus.NO_CONTENT;
                    
		} catch (ClassNotFoundException | SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<List<BookLoan>>(loans, status);
	}


    /**
     * This functionality is not very well suited for an api interface, with UI implemented the user would select from their checked out books to return.
     * It doesn't make much sense to add the fourth primary key, the date out, in the request body in postman. This will be much easier when the loan data is sent to client and then back to LMS.
     * 
     * @param loan
     * @return
     */
	@PutMapping(path="/returnBook",
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

    /**
     * 
     * @param bookId
     * @param branchId
     * @param cardNo
     * @return
     */
	@PostMapping(path="/checkOutBook",
                    consumes = {
                        MediaType.APPLICATION_JSON_VALUE,
                        MediaType.APPLICATION_XML_VALUE
                    })
	public ResponseEntity<BookLoan> checkOutBook(@RequestBody BookLoan loan) {
        HttpStatus status = HttpStatus.OK;
        try {
            boolean hasBook = borrowerService.checkIfBranchHasACopy(loan.getBranchId(), loan.getBookId());
            Borrower borrower = borrowerService.readABorrower(loan.getCardNo());
            if(!hasBook || borrower == null)
                status = HttpStatus.NOT_FOUND;
            else
                loan = borrowerService.checkOutBook(loan.getBookId(), loan.getBranchId(), loan.getCardNo());
        } catch (ClassNotFoundException | SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<BookLoan>(loan, status);
	}
}
