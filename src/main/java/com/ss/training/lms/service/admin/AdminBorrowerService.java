package com.ss.training.lms.service.admin;

import com.ss.training.lms.jdbc.ConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.lms.dao.BookLoanDAO;
import com.ss.training.lms.dao.BorrowerDAO;
import com.ss.training.lms.entity.Borrower;

@Component
public class AdminBorrowerService {
	
	@Autowired
    ConnectionUtil connUtil;
	
	@Autowired
    BorrowerDAO borDAO;
	
	@Autowired
    BookLoanDAO loanDAO;
    
	/**
	 * 
	 * @param borrower
	 * @return
	 * @throws SQLException
	 */
    public Integer addABorrower(Borrower borrower) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            Integer primaryKey = borDAO.addBorrower(borrower, conn);
            conn.commit();
            return primaryKey;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not add that borrower.");
            e.printStackTrace();
            conn.rollback();
            return 0;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    /**
     * 
     * @param borrower
     * @throws SQLException
     */
    public void deleteABorrower(Borrower borrower) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            loanDAO.deleteBookLoansByBorrower(borrower.getCardNo(), conn);
            borDAO.deleteBorrower(borrower, conn);
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not delete that borrower.");
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    /**
     * 
     * @param borrower
     * @throws SQLException
     */
    public void updateABorrower(Borrower borrower) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            borDAO.updateBorrower(borrower, conn);
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not update that borrower.");
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    /**
     * 
     * @param cardNo
     * @return
     * @throws SQLException
     */
    public Borrower readABorrower(Integer cardNo) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            List<Borrower> borrowers = borDAO.readABorrower(cardNo, conn);
            if(borrowers.size() == 0) {
                return null;
            }
            return borrowers.get(0);
        } catch ( SQLException e) {
            System.out.println("We could not read the borrower.");
            conn.rollback();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    /**
     * 
     * @return
     * @throws SQLException
     */
    public List<Borrower> readAllBorrowers() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            List<Borrower> borrowers = borDAO.readAllBorrowers(conn);
            if(borrowers.size() == 0) {
                return null;
            }
            return borrowers;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not read the borrowers.");
            conn.rollback();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }
}