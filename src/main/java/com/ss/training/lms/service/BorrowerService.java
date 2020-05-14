package com.ss.training.lms.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import com.ss.training.lms.dao.BookCopiesDAO;
import com.ss.training.lms.dao.BookLoanDAO;
import com.ss.training.lms.dao.BorrowerDAO;
import com.ss.training.lms.entity.BookCopies;
import com.ss.training.lms.entity.BookLoan;

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
     * @param loan
     * @return
     */
    public boolean checkIfLoanExists(BookLoan loan) {
        BookLoan loanCheck;
        loanCheck = bookLoanDAO.findByBranchIdAndBookIdAndCardNoAndDateOut(loan.getBranchId(), loan.getBookId(), loan.getCardNo(), loan.getDateOut());

        if(loanCheck != null && loan.getDateIn() == null)
            return true;
        return false;
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
     * @param bookId
     * @param branchId
     * @return
     */
    public boolean checkIfBookIsAvailable(Integer bookId, Integer branchId) {
        BookCopies entry = entriesDAO.findByBranchIdAndBookId(branchId, bookId);

        if(entry != null && entry.getNoOfCopies() > 0)
            return true;
        return false;
    }



}

    