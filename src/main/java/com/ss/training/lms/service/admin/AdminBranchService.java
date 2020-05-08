package com.ss.training.lms.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.lms.dao.BookCopiesDAO;
import com.ss.training.lms.dao.BookLoanDAO;
import com.ss.training.lms.dao.LibraryBranchDAO;
import com.ss.training.lms.entity.LibraryBranch;
import com.ss.training.lms.jdbc.ConnectionUtil;

@Component
public class AdminBranchService {
	
	@Autowired
	ConnectionUtil connUtil;
	@Autowired
	LibraryBranchDAO branchDAO;
	@Autowired
	BookCopiesDAO copiesDAO;
	@Autowired
	BookLoanDAO loanDAO;

	public Integer addABranch(LibraryBranch branch) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			Integer primaryKey = branchDAO.addBranch(branch, conn);
			conn.commit();
			return primaryKey;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("We could not add that branch.");
			e.printStackTrace();
			conn.rollback();
			return 0;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public void deleteABranch(LibraryBranch branch) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			loanDAO.deleteBookLoansByBranch(branch.getBranchId(), conn);
			copiesDAO.deleteBookLoansByBranch(branch.getBranchId(), conn);
			branchDAO.deleteBranch(branch, conn);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("We could not delete that branch.");
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public void updateABranch(LibraryBranch branch) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			branchDAO.updateBranch(branch, conn);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("We could not update that branch.");
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public LibraryBranch readABranch(Integer branchId) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			List<LibraryBranch> branches = branchDAO.readABranch(branchId, conn);
			if (branches.size() == 0) {
				return null;
			}
			return branches.get(0);
		} catch (SQLException e) {
			System.out.println("We could not read the branch.");
			conn.rollback();
			return null;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public List<LibraryBranch> readAllBranches() throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			List<LibraryBranch> branches = branchDAO.readAllBranches(conn);
			if (branches.size() == 0) {
				return null;
			}
			return branches;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("We could not read the branch.");
			conn.rollback();
			return null;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}