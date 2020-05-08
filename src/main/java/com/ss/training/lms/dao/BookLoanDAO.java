package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ss.training.lms.entity.BookLoan;

@Component
public class BookLoanDAO extends BaseDAO<BookLoan> {

	public void addBookLoan(BookLoan loan, Connection conn) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate, dateIn) VALUES (?, ?, ?, ?, ?, ?)",
				new Object[] { loan.getBookId(), loan.getBranchId(), loan.getCardNo(), loan.getDateOut(),
						loan.getDueDate(), loan.getDateIn() },
				conn);
	}

	public void updateBookLoan(BookLoan loan, Connection conn) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book_loans SET dueDate = ?, dateIn = ? WHERE bookId = ? AND branchId = ? AND cardNo = ?",
				new Object[] { loan.getDueDate(), loan.getDateIn(), loan.getBookId(), loan.getBranchId(),
						loan.getCardNo() },
				conn);
	}

	public void deleteBookLoan(BookLoan loan, Connection conn) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo = ?",
				new Object[] { loan.getBookId(), loan.getBranchId(), loan.getBranchId() }, conn);
	}

	public void deleteBookLoansByBorrower(Integer borrowerCard, Connection conn)
			throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_loans WHERE cardNo = ?", new Object[] { borrowerCard }, conn);
	}

	public void deleteBookLoansByBranch(Integer branchId, Connection conn) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_loans WHERE branchId = ?", new Object[] { branchId }, conn);
	}

	public List<BookLoan> readAllLoans(Connection conn) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book_loans", null, conn);
	}

	public List<BookLoan> readAllLoansFromABorrower(Integer cardNo, Connection conn)
			throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book_loans WHERE cardNo = ? AND dateIn IS NULL", new Object[] { cardNo }, conn);
	}

	@Override
	public List<BookLoan> extractData(ResultSet rs) throws SQLException {
		List<BookLoan> loans = new ArrayList<>();
		while (rs.next()) {
			BookLoan loan = new BookLoan();
			loan.setBookId(rs.getInt("bookId"));
			loan.setBranchId(rs.getInt("branchId"));
			loan.setCardNo(rs.getInt("cardNo"));
			loan.setDateOut(rs.getTimestamp("dateOut"));
			loan.setDueDate(rs.getTimestamp("dueDate"));
			loan.setDateIn(rs.getTimestamp("dateIn"));
			loans.add(loan);
		}
		return loans;
	}

}