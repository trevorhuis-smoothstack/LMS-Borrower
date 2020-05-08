package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.Author;

import org.springframework.stereotype.Component;

@Component
public class AuthorDAO extends BaseDAO<Author>{

    public Integer addAuthor(Author author, Connection conn) throws ClassNotFoundException, SQLException{
		return saveWithPK("INSERT INTO tbl_author (authorName) VALUES (?)", new Object[] {author.getAuthorName()}, conn);
	}

	public void updateAuthor(Author author, Connection conn)  throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_author SET authorName = ? WHERE authorId = ?", new Object[] {author.getAuthorName(), author.getAuthorId()}, conn);
	}

	public void deleteAuthor(Author author, Connection conn)  throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_author WHERE authorId = ?", new Object[]{author.getAuthorId()}, conn);
	}
	
	public List<Author> readAllAuthors(Connection conn) throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_author", null, conn);
	}

	public List<Author> readAnAuthor(Integer authorId, Connection conn) throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_author WHERE authorId = ?", new Object[]{ authorId }, conn);
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while(rs.next()){
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			authors.add(author);
		}
		return authors;
	}
}