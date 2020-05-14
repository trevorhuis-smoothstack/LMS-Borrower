package com.ss.training.lms.dao;

import java.sql.Timestamp;
import java.util.List;

import com.ss.training.lms.entity.BookLoan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookLoanDAO extends JpaRepository<BookLoan, Long> {
	List<BookLoan> findByCardNo(Integer cardNo);

	BookLoan findByBranchIdAndBookIdAndCardNoAndDateOut(Integer branchId, Integer bookId, Integer cardNo, Timestamp dateOut);
}
