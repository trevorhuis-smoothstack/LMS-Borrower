package com.ss.training.lms.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Trevor Huis in 't Veld
 */
public class BookLoanId implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 2731226830537739427L;
    private Integer bookId;
    private Integer branchId;
    private Integer cardNo;
    private Timestamp dateOut;

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

    public Integer getCardNo() {
        return cardNo;
    }

    public void setCardNo(Integer cardNo) {
        this.cardNo = cardNo;
    }

    public Timestamp getDateOut() {
        return dateOut;
    }

    public void setDateOut(Timestamp dateOut) {
        this.dateOut = dateOut;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
        result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
        result = prime * result + ((cardNo == null) ? 0 : cardNo.hashCode());
        result = prime * result + ((dateOut == null) ? 0 : dateOut.hashCode());
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
        BookLoanId other = (BookLoanId) obj;
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
        if (cardNo == null) {
            if (other.cardNo != null)
                return false;
        } else if (!cardNo.equals(other.cardNo))
            return false;
        if (dateOut == null) {
            if (other.dateOut != null)
                return false;
        } else if (!dateOut.equals(other.dateOut))
            return false;
        return true;
    }

    
}