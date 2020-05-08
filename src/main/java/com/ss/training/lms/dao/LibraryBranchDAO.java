package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.LibraryBranch;

import org.springframework.stereotype.Component;

@Component
public class LibraryBranchDAO extends BaseDAO<LibraryBranch>{


    public Integer addBranch(LibraryBranch branch, Connection conn) throws ClassNotFoundException, SQLException {
		return saveWithPK("INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES (?, ?)", new Object[] {branch.getBranchName(), branch.getBranchAddress()}, conn);
	}

	public void updateBranch(LibraryBranch branch, Connection conn) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ?", new Object[] {branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId()}, conn);
	}

	public void deleteBranch(LibraryBranch branch, Connection conn)  throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[]{branch.getBranchId()}, conn);
	}
	
	public List<LibraryBranch> readAllBranches(Connection conn) throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_library_branch", null, conn);
	}

	public List<LibraryBranch> readABranch(Integer branchId, Connection conn) throws SQLException{
		return read("SELECT * FROM tbl_library_branch WHERE branchId = ?", new Object[]{ branchId }, conn);
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> branches = new ArrayList<>();
		while(rs.next()){
			LibraryBranch branch = new LibraryBranch();
			branch.setBranchId(rs.getInt("branchId"));
            branch.setBranchName(rs.getString("branchName"));
            branch.setBranchAddress(rs.getString("branchAddress"));
			branches.add(branch);
		}
		return branches;
	}
}
