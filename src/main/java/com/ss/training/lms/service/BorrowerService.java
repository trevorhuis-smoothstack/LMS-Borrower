package com.ss.training.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.dao.BookCopiesDAO;
import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.dao.BookLoanDAO;
import com.ss.training.lms.dao.BorrowerDAO;
import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.BookCopies;
import com.ss.training.lms.entity.BookLoan;
import com.ss.training.lms.entity.Borrower;
import com.ss.training.lms.jdbc.ConnectionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Trevor Huis in 't Veld
 */
@Component
public class BorrowerService {
    
    @Autowired
    ConnectionUtil connUtil;

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
     * @throws SQLException
     */
    public Borrower readABorrower(Integer cardNo) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            List<Borrower> borrowerList = borDAO.readABorrower(cardNo, conn);
            if(borrowerList.size() == 0) {
                return null;
            }
            return borrowerList.get(0);
        } finally {
            if(conn != null)
                conn.close();
        }
    }

    /**
     * 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Borrower> readAllBorrowers() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            List<Borrower> borrowerList = borDAO.readAllBorrowers(conn);
            if(borrowerList.size() == 0) {
                return null;
            }
            return borrowerList;
        } finally {
            if(conn != null)
                conn.close();
        }
    }

    /**
     * 
     * @param cardNo
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<BookLoan> getLoansFromBorrower(Integer cardNo) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            List<BookLoan> loans = bookLoanDAO.readAllLoansFromABorrower(cardNo, conn);
            if(loans.size() == 0) {
                return null;
            }
            return loans;
        } finally {
            if(conn != null)
                conn.close();
        }
    }

    /**
     * 
     * @param loan
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void returnBook(BookLoan loan) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        boolean success = false;
        try {
            conn = connUtil.getConnection();

            // Add the book to the copies table
            List<BookCopies> entries = entriesDAO.readAnEntry(loan.getBranchId(), loan.getBookId(), conn);
            if (entries.size() == 0) {
                BookCopies entry = new BookCopies(loan.getBookId(), loan.getBranchId(), 1);
                entriesDAO.addBookCopiesEntry(entry, conn);
            } else if (entries.size() > 0){
                BookCopies entry = new BookCopies(loan.getBookId(), loan.getBranchId(), (entries.get(0).getNoOfCopies() + 1));
                entriesDAO.updateBookCopiesEntry(entry, conn);
            }

            Timestamp now = Timestamp.from(Instant.now());
            loan.setDateIn(now);
            
            bookLoanDAO.updateBookLoan(loan, conn);
            success=true;
        } finally {
            if(success)
                conn.commit();
            else
                conn.rollback();
            if(conn != null)
                conn.close();
        }
    }

    // Not needed in API version but could be useful with UI
    /**
     * 
     * @param loans
     * @return
     * @throws SQLException
     */
    public List<Book> getBookNamesFromLoans(List<BookLoan> loans) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            List<Book> books = new ArrayList<>();
            for(BookLoan loan: loans) {
                books.add(bookDAO.readABookById(loan.getBookId(), conn).get(0));
            }
            return books;
        } finally {
            if(conn != null)
                conn.close();
        }
    }

    /**
     * 
     * @param bookId
     * @param branchId
     * @param cardNo
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public BookLoan checkOutBook(Integer bookId, Integer branchId, Integer cardNo) throws SQLException,
            ClassNotFoundException {
        Connection conn = null;
        boolean success = false;
        try {
            conn = connUtil.getConnection();

            // Add the book to the copies table
            List<BookCopies> entries = entriesDAO.readAnEntry(branchId, bookId, conn);
            BookCopies entry = new BookCopies(bookId, branchId, (entries.get(0).getNoOfCopies() - 1));
            entriesDAO.updateBookCopiesEntry(entry, conn);
            
            LocalDateTime weekFromNow = LocalDateTime.now().plusDays(7);
            Timestamp weekFromNowTS = Timestamp.valueOf(weekFromNow);
            Timestamp now = Timestamp.from(Instant.now());

            BookLoan loan = new BookLoan(bookId, branchId, cardNo, now, weekFromNowTS, null);
            
            bookLoanDAO.addBookLoan(loan, conn);
            success=true;
            return loan;
        } finally {
            if(success)
                conn.commit();
            else
                conn.rollback();
            if(conn != null)
                conn.close();
        }
    }

    /**
     * 
     * @param branchId
     * @param bookId
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean checkIfBranchHasACopy(int branchId, int bookId) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();

            // Add the book to the copies table
            List<BookCopies> entries = entriesDAO.readAnEntry(branchId, bookId, conn);
            if (entries.size() != 0 && entries.get(0).getNoOfCopies() > 0)
                return true;
            return false;
        } finally {
            if(conn != null)
                conn.close();
        }
    }
}