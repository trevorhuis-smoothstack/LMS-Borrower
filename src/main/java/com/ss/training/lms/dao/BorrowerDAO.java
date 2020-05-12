package com.ss.training.lms.dao;

import java.util.List;

import com.ss.training.lms.entity.Borrower;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BorrowerDAO extends JpaRepository<Borrower, Long> {
	List<Borrower> findByCardNo(Integer cardNo);


}

