package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.BookCopies;

import org.springframework.stereotype.Component;

@Component
public class BookCopiesDAO extends BaseDAO<BookCopies> {

    public void addBookCopiesEntry(BookCopies entry, Connection conn) throws ClassNotFoundException, SQLException{
		save("INSERT INTO tbl_book_copies (bookId, branchId, NoOfCopies) VALUES (?, ?, ?)", new Object[] {entry.getBookId(), entry.getBranchId(), entry.getNoOfCopies()}, conn);
	}

	public void updateBookCopiesEntry(BookCopies entry, Connection conn)  throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? AND branchId = ?", new Object[] {entry.getNoOfCopies(), entry.getBookId(), entry.getBranchId()}, conn);
	}

	public void deleteBookCopiesEntry(BookCopies entry, Connection conn)  throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_book_copies WHERE bookId = ? AND branchId = ?", new Object[]{entry.getBookId(), entry.getBranchId()}, conn);
	}

	public void deleteBookLoansByBranch(Integer branchId, Connection conn) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_copies WHERE branchId = ?", new Object[] { branchId }, conn);
	}
	
	public List<BookCopies> readAllEntries(Connection conn) throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_book_copies", null, conn);
	}
	
	public List<BookCopies> readAnEntry(Integer branchId, Integer bookId, Connection conn) throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_book_copies WHERE branchId = ? AND bookId = ?", new Object[] {branchId, bookId}, conn);
	}
	
	public List<BookCopies> readBooksFromABranch(Integer branchId, Connection conn) throws SQLException {
		return read("SELECT * FROM tbl_book_copies WHERE branchId = ? AND noOfCopies > 0", new Object[] {branchId}, conn);
	}

    @Override
    public List<BookCopies> extractData(ResultSet rs) throws SQLException {
        List<BookCopies> entries = new ArrayList<>();
		while(rs.next()){
			BookCopies entry = new BookCopies();
            entry.setBookId(rs.getInt("bookId"));
            entry.setBranchId(rs.getInt("branchId"));
			entry.setNoOfCopies(rs.getInt("noOfCopies"));
			entries.add(entry);
		}
		return entries;
    }
}