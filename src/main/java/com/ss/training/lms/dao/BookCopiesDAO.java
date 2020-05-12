package com.ss.training.lms.dao;

import com.ss.training.lms.entity.BookCopies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookCopiesDAO extends JpaRepository<BookCopies, Long> {
	BookCopies findByBranchIdAndBookId(Integer branchId, Integer bookId);
}

