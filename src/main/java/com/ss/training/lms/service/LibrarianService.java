package com.ss.training.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.lms.dao.BookCopiesDAO;
import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.dao.LibraryBranchDAO;
import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.BookCopies;
import com.ss.training.lms.entity.LibraryBranch;
import com.ss.training.lms.jdbc.ConnectionUtil;

@Component
public class LibrarianService {
	@Autowired
    public ConnectionUtil connUtil;
	
	@Autowired
	LibraryBranchDAO libDAO;
	
	@Autowired
	BookCopiesDAO entriesDAO;

	@Autowired
	BookDAO bookDAO;

	/**
	 * 
	 * @param entry
	 * @return
	 * @throws SQLException
	 */
    public boolean updateCopies(BookCopies entry) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            List<BookCopies> entries = entriesDAO.readAnEntry(entry.getBranchId(), entry.getBookId(), conn);
            if (entries.size() == 0) {
                entriesDAO.addBookCopiesEntry(entry, conn);
            } else {
                System.out.println("No Entry found!!!");
                entriesDAO.updateBookCopiesEntry(entry, conn); 
            }
            conn.commit();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
            return false;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    /**
     * 
     * @param branch
     * @return
     * @throws SQLException
     */
    public boolean updateBranch(LibraryBranch branch) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            libDAO.updateBranch(branch, conn);
            conn.commit();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
            return false;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    /**
     * 
     * @return
     * @throws SQLException
     */
    public List<LibraryBranch> getBranches() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            return libDAO.readAllBranches(conn);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    /**
     * 
     * @param search
     * @return
     * @throws SQLException
     */
    public List<Book> getBooksWithSearch(String search) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            return bookDAO.readAllBooksWithSearch(search, conn);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    /**
     * 
     * @param branchId
     * @param bookId
     * @return
     * @throws SQLException
     */
    public BookCopies getAnEntryOfBookCopies(Integer branchId, Integer bookId) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            List<BookCopies> entries = entriesDAO.readAnEntry(branchId, bookId, conn);
            if(entries.size() == 0) {
                return null;
            }
            return entriesDAO.readAnEntry(branchId, bookId, conn).get(0);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    /**
     * 
     * @param branch
     * @return
     * @throws SQLException
     */
    public List<Book> getBooksAtABranch(LibraryBranch branch) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            List<Book> books = new ArrayList<>();
            List<BookCopies> entries = entriesDAO.readBooksFromABranch(branch.getBranchId(), conn);
            if(entries.size() == 0) {
                return null;
            }
            for(BookCopies entry: entries) {
                books.add(bookDAO.readABookById(entry.getBookId(), conn).get(0));
            }
            return books;
        } catch ( SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }
}