package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.Publisher;

import org.springframework.stereotype.Component;

@Component
public class PublisherDAO extends BaseDAO<Publisher> {


    public Integer addPublisher(Publisher publisher, Connection conn) throws ClassNotFoundException, SQLException {
		return saveWithPK("INSERT INTO tbl_publisher (publisherName, publisherAddress) VALUES (?, ?)", new Object[] {publisher.getPublisherName(), publisher.getAddress()},conn);
	}

	public void updatePublisher(Publisher publisher,Connection conn) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_publisher SET publisherName = ?, publisherAddress = ? WHERE publisherId = ?", new Object[] {publisher.getPublisherName(), publisher.getAddress(), publisher.getPublisherID()},conn);
	}

	public void deletePublisher(Publisher publisher,Connection conn)  throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_publisher WHERE publisherId = ?", new Object[]{publisher.getPublisherID()}, conn);
	}
	
	public List<Publisher> readAllPublishers(Connection conn) throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_publisher", null,conn);
	}

	public List<Publisher> readAPublisher(Integer publisherId,Connection conn) throws SQLException{
		return read("SELECT * FROM tbl_publisher WHERE publisherId = ?", new Object[]{ publisherId}, conn);
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publisheres = new ArrayList<>();
		while(rs.next()){
			Publisher publisher = new Publisher();
			publisher.setPublisherID(rs.getInt("publisherId"));
            publisher.setPublisherName(rs.getString("publisherName"));
            publisher.setAddress(rs.getString("publisherAddress"));
			publisheres.add(publisher);
		}
		return publisheres;
	}
}
