package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.Genre;

import org.springframework.stereotype.Component;

@Component
public class GenreDAO extends BaseDAO<Genre> {

    public Integer addGenre(Genre genre, Connection conn) throws ClassNotFoundException, SQLException {
		return saveWithPK("INSERT INTO tbl_genre (genre_name) VALUES (?)", new Object[] {genre.getGenreName()}, conn);
	}

	public void updateGenre(Genre genre, Connection conn) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_genre SET genre_name = ? WHERE genre_id = ?", new Object[] {genre.getGenreName(), genre.getGenreID()}, conn);
	}

	public void deleteGenre(Genre genre, Connection conn)  throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_genre WHERE genre_id = ?", new Object[]{ genre.getGenreID() }, conn);
	}
	
	public List<Genre> readAllGenres(Connection conn) throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_genre", null, conn);
	}

	public List<Genre> readAGenre(Integer genreId, Connection conn) throws SQLException{
		return read("SELECT * FROM tbl_genre WHERE genre_id = ?", new Object[]{ genreId }, conn);
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<>();
		while(rs.next()){
			Genre genre = new Genre();
			genre.setGenreID(rs.getInt("genre_id"));
            genre.setGenreName(rs.getString("genre_name"));
			genres.add(genre);
		}
		return genres;
	}
}
