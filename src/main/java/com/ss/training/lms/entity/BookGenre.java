package com.ss.training.lms.entity;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class BookGenre implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = -2215002821278598222L;
    private Integer genre_id;
    private Integer bookId;

    public BookGenre(Integer genre_id, int bookId) {
        this.genre_id = genre_id;
        this.bookId = bookId;
	}

	public BookGenre() {
	}

	public Integer getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(Integer genre_id) {
        this.genre_id = genre_id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
        result = prime * result + ((genre_id == null) ? 0 : genre_id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BookGenre other = (BookGenre) obj;
        if (bookId == null) {
            if (other.bookId != null)
                return false;
        } else if (!bookId.equals(other.bookId))
            return false;
        if (genre_id == null) {
            if (other.genre_id != null)
                return false;
        } else if (!genre_id.equals(other.genre_id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BookGenre [bookId=" + bookId + ", genre_id=" + genre_id + "]";
    }



}