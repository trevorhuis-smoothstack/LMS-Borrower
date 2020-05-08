package com.ss.training.lms.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.dao.PublisherDAO;
import com.ss.training.lms.entity.Publisher;
import com.ss.training.lms.jdbc.ConnectionUtil;

@Component
public class AdminPublisherService {
	
	@Autowired
	ConnectionUtil connUtil;
	@Autowired
	PublisherDAO pubDAO;
	@Autowired
	BookDAO bookDAO;

	public Integer addAPublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			Integer primaryKey = pubDAO.addPublisher(publisher, conn);
			conn.commit();
			return primaryKey;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("We could not add that publisher.");
			e.printStackTrace();
			conn.rollback();
			return 0;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public void deleteAPublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			bookDAO.deleteBooksByPublisher(publisher.getPublisherID(), conn);
			pubDAO.deletePublisher(publisher, conn);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("We could not delete that publisher.");
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public void updateAPublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			pubDAO.updatePublisher(publisher, conn);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("We could not update that publisher.");
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public Publisher readAPublisher(Integer pubId) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			List<Publisher> publishers = pubDAO.readAPublisher(pubId, conn);
			if (publishers.size() == 0) {
				return null;
			}
			return publishers.get(0);
		} catch (SQLException e) {
			System.out.println("We could not read the publisher.");
			conn.rollback();
			return null;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public List<Publisher> readAllPublishers() throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			List<Publisher> publishers = pubDAO.readAllPublishers(conn);
			if (publishers.size() == 0) {
				return null;
			}
			return publishers;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("We could not read the publishers.");
			conn.rollback();
			return null;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}