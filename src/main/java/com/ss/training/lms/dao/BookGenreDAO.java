package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ss.training.lms.entity.BookGenre;

@Component
public class BookGenreDAO extends BaseDAO<BookGenre> {

	public void addBookGenreEntry(BookGenre bookGenre, Connection conn) throws ClassNotFoundException, SQLException {
		System.out.println("Genre ID" + bookGenre.getGenre_id());
		System.out.println("Book ID" + bookGenre.getBookId());
		save("INSERT INTO tbl_book_genres (genre_id, bookId) VALUES (?, ?)",
				new Object[] { bookGenre.getGenre_id(), bookGenre.getBookId() }, conn);
	}

	public void updateBookGenreByGenre(Integer oldId, Integer newId, Connection conn)
			throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book_genres SET genre_id = ? WHERE genre_id = ?", new Object[] { oldId, newId }, conn);
	}

	public void updateBookGenreByBook(Integer oldId, Integer newId, Connection conn)
			throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book_genres SET bookId = ? WHERE bookId = ?", new Object[] { oldId, newId }, conn);
	}

	public void deleteGenresReferenceByGenre(Integer genre_id, Connection conn)
			throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_genres WHERE genre_id = ?", new Object[] { genre_id }, conn);
	}

	public void deleteGenresReferenceByBook(Integer bookId, Connection conn)
			throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_genres WHERE bookId = ?", new Object[] { bookId }, conn);
	}

	public List<BookGenre> readAllGenreReferences(Connection conn) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book_genres", null, conn);
	}

	@Override
	public List<BookGenre> extractData(ResultSet rs) throws SQLException {
		List<BookGenre> entries = new ArrayList<>();
		while (rs.next()) {
			BookGenre bookGenre = new BookGenre();
			bookGenre.setGenre_id(rs.getInt("bookGenre_id"));
			bookGenre.setBookId(rs.getInt("bookId"));
			entries.add(bookGenre);
		}
		return entries;
	}
}