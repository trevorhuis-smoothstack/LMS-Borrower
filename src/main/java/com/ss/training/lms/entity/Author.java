package com.ss.training.lms.entity;

import java.io.Serializable;
import java.util.List;

public class Author implements Serializable{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int authorId;
    private String authorName;
    private List<Book> books;

    public Author(int authorId, String authorName, List<Book> books) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.books = books;
    }

    public Author() {

    }

    public Author(Integer authorId, String authorName) {
        this.authorId = authorId;
        this.authorName = authorName;
	}

	public Author(String authorName) {
        this.authorName = authorName;
	}

	@Override
    public String toString() {
        return authorId + ", " + authorName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + authorId;
        result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
        result = prime * result + ((books == null) ? 0 : books.hashCode());
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
        Author other = (Author) obj;
        if (authorId != other.authorId)
            return false;
        if (authorName == null) {
            if (other.authorName != null)
                return false;
        } else if (!authorName.equals(other.authorName))
            return false;
        if (books == null) {
            if (other.books != null)
                return false;
        } else if (!books.equals(other.books))
            return false;
        return true;
    }
}