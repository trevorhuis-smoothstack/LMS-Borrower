package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public abstract class BaseDAO<T> {
    
    public void save(String sql, Object[] vals, Connection conn) throws ClassNotFoundException, SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);

        if(vals!=null){
            int index = 1;
            for(Object o: vals) {
                pstmt.setObject(index, o);
                index++;
            }
        }
        pstmt.executeUpdate();
    }

    public Integer saveWithPK(String sql, Object[] vals, Connection conn) throws ClassNotFoundException, SQLException{
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		if(vals!=null){
			int index =1;
			for(Object o: vals){
				pstmt.setObject(index, o);
				index++;
			}
		}
        pstmt.executeUpdate();

        int autoIncKeyFromApi = -1;
        ResultSet rs = pstmt.getGeneratedKeys();

        if (rs.next()) {
            autoIncKeyFromApi = rs.getInt(1);
        } else {
            throw new SQLException();
        }

        return autoIncKeyFromApi;
	}

    public List<T> read(String sql, Object[] vals, Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);

        if(vals!=null) {
            if(vals[0] == Boolean.TRUE) {
                pstmt.setString(1, "%" + vals[1] + "%");
                vals = null;
            }
        }

        if(vals!=null) {
            int index = 1;
            for(Object o: vals) {
                pstmt.setObject(index, o);
                index++;
            }
        }

        return extractData(pstmt.executeQuery());
    }

    public abstract List<T> extractData(ResultSet rs) throws SQLException;
}