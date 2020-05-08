package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ss.training.lms.entity.Book;

@Component
public class BookDAO extends BaseDAO<Book> {

	public Integer addBook(Book book, Connection conn) throws ClassNotFoundException, SQLException {
		return saveWithPK("INSERT INTO tbl_book (title, pubId) VALUES (?, ?)",
				new Object[] { book.getTitle(), book.getPublisherId() }, conn);
	}

	public void updateBook(Book book, Connection conn) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book SET title = ? WHERE pubId = ?", new Object[] { book.getTitle(), book.getPublisherId() },
				conn);
	}

	public void deleteBook(Book book, Connection conn) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book WHERE bookId = ?", new Object[] { book.getBookId() }, conn);
	}

	public void deleteBooksByPublisher(Integer pubId, Connection conn) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book WHERE pubId = ?", new Object[] { pubId }, conn);
	}

	public List<Book> readAllBooks(Connection conn) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book", null, conn);
	}

	public List<Book> readAllBooksWithSearch(String search, Connection conn) throws SQLException {
		return read("SELECT * FROM tbl_book WHERE title LIKE ?;", new Object[] { true, search }, conn);
	}

	public List<Book> readABookById(Integer bookId, Connection conn) throws SQLException {
		return read("SELECT * FROM tbl_book WHERE bookId = ?;", new Object[] { bookId }, conn);
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setPublisherId(rs.getInt("pubId"));
			books.add(book);
		}
		return books;
	}
}