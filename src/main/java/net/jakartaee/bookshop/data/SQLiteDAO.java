package net.jakartaee.bookshop.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;

import net.jakartaee.bookshop.exceptions.DatabaseException;

public class SQLiteDAO {

//	protected static final String DB_PATH = "/dev/repos/Bookshop/bookshop.db";
//	
//	protected Connection openConnection( String filepath) throws SQLException {
//		Connection conn = null;
//		try {
//			Class.forName("org.sqlite.JDBC");
//			SQLiteConfig  config = new SQLiteConfig();
//			config.enforceForeignKeys(true);
//			conn = DriverManager.getConnection("jdbc:sqlite:" + filepath, config.toProperties());
//		} catch (ClassNotFoundException e) {
//			throw new SQLException(e.getMessage());
//		} 
//		return conn;
//	}
	
	protected int getNewId(Connection conn) throws DatabaseException {
		String sql = "SELECT last_insert_rowid()";
		int newId = 0;
		try (   Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql); ){
			while ( rs.next() ) {
				newId = rs.getInt(1);
			}
			return newId;
		} catch (SQLException e) {
			throw new DatabaseException("Get last insert id failed", e);
		}
	}
}
