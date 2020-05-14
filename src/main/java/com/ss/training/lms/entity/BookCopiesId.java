package com.ss.training.lms.entity;

import java.io.Serializable;

/**
 * @author Trevor Huis in 't Veld
 */
public class BookCopiesId implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5504505313515789838L;
    private Integer bookId;
    private Integer branchId;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
        result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
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
        BookCopiesId other = (BookCopiesId) obj;
        if (bookId == null) {
            if (other.bookId != null)
                return false;
        } else if (!bookId.equals(other.bookId))
            return false;
        if (branchId == null) {
            if (other.branchId != null)
                return false;
        } else if (!branchId.equals(other.branchId))
            return false;
        return true;
    }
    
}