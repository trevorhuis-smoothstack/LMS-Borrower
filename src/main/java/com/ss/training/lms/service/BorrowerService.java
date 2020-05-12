package com.ss.training.lms.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import com.ss.training.lms.dao.BookCopiesDAO;
import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.dao.BookLoanDAO;
import com.ss.training.lms.dao.BorrowerDAO;
import com.ss.training.lms.entity.BookCopies;
import com.ss.training.lms.entity.BookLoan;
import com.ss.training.lms.entity.Borrower;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Trevor Huis in 't Veld
 */
@Component
public class BorrowerService {
    @Autowired
    BookLoanDAO bookLoanDAO;

    @Autowired
    BorrowerDAO borDAO;

    // This variable could be named better
    @Autowired
    BookCopiesDAO entriesDAO;

    @Autowired
    BookDAO bookDAO;

    /**
     * 
     * @param cardNo
     * @return
     */
    public Borrower readBorrower(Integer cardNo) {
        List<Borrower> borrowerList = borDAO.findByCardNo(cardNo);
        if(borrowerList.size() == 0) {
            return null;
        }
        return borrowerList.get(0);
    }

    /**
     * 
     * @return
     */
    public List<Borrower> readAllBorrowers(){
        List<Borrower> borrowerList = borDAO.findAll();
        if(borrowerList.size() == 0) {
            return null;
        }
        return borrowerList;
    }

    /**
     * 
     * @param cardNo
     * @return
     */
    public List<BookLoan> getLoansFromBorrower(Integer cardNo) {
        List<BookLoan> loans = bookLoanDAO.findByCardNo(cardNo);
        if(loans.size() == 0) {
            return null;
        }
        return loans;
 
    }

    /**
     * 
     * @param loan
     */
    public void returnBook(BookLoan loan) {
        BookCopies entry;
        entry = entriesDAO.findByBranchIdAndBookId(loan.getBranchId(), loan.getBookId());

        if (entry == null) 
            entry = new BookCopies(loan.getBookId(), loan.getBranchId(), 1);
        else 
            entry = new BookCopies(loan.getBookId(), loan.getBranchId(), (entry.getNoOfCopies() + 1));
        
        entriesDAO.save(entry);

        Timestamp now = Timestamp.from(Instant.now());
        loan.setDateIn(now);
        
        bookLoanDAO.save(loan);
            
    }

    /**
     * 
     * @param bookId
     * @param branchId
     * @param cardNo
     */
    public void checkOutBook(Integer bookId, Integer branchId, Integer cardNo) {

        BookCopies entry = entriesDAO.findByBranchIdAndBookId(branchId, bookId);
        entry = new BookCopies(bookId, branchId, (entry.getNoOfCopies() - 1));
        entriesDAO.save(entry);
        
        LocalDateTime weekFromNow = LocalDateTime.now().plusDays(7);
        Timestamp weekFromNowTS = Timestamp.valueOf(weekFromNow);
        Timestamp now = Timestamp.from(Instant.now());

        BookLoan loan = new BookLoan(bookId, branchId, cardNo, now, weekFromNowTS, null);
        
        bookLoanDAO.save(loan);
    }

    /**
     * 
     * @param loan
     * @return
     */
    public boolean checkIfLoanExists(BookLoan loan) {
        BookLoan loanCheck;
        loanCheck = bookLoanDAO.findByBranchIdAndBookIdAndCardNo(loan.getBranchId(), loan.getBookId(), loan.getCardNo());

        if(loanCheck == null)
            return false;
        return true;
    } 
}

    