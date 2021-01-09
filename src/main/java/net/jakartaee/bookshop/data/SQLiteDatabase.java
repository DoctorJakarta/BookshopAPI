package net.jakartaee.bookshop.data;

import java.sql.Connection;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

// import com.mchange.v2.c3p0.ComboPooledDataSource;

public final class SQLiteDatabase {
	
	//private static final String DB_PATH = "/dev/repos/Bookshop/bookshop.sqlite";
	//private static final String DB_PATH = "/opt/apps/data/bookshop.sqlite";
	private static final String DB_PATH = "/home/bitnami/data/bookshop.sqlite";				// This is a better location for the bitnami AWS instances.  Does not require root access to create folder in /opt/apps
	
	private SQLiteDatabase() {}		// Ensure this singleton database Helper is not instantiated.  
	
												// Don't use DriveManager, use DataSource. See: https://stackoverflow.com/questions/41230234/using-datasource-to-connect-to-sqlite-with-xerial-sqlite-jdbc-driver
    private static SQLiteDataSource dataSource;
    static {
		SQLiteConfig  config = new SQLiteConfig();
		config.enforceForeignKeys(true);
    	dataSource = new SQLiteDataSource();
    	dataSource.setUrl("jdbc:sqlite:" + DB_PATH);
    	dataSource.setConfig(config);
    }
    					// DB Pooling is used with SQLite to ensure only 1 connection is available
						// There are other approaches to sharing a single connection, but they have problems with multithreaded JEE apps, and even single threaded apps
						// Try-With-Resources closed the connection asynchronously.  
						// That results in the next getConnection() creating a second connection, which can create a SQLITE_BUSY error if the first one isn't closed yet
						// You can't use a simple NULL check to share/create a connection, because if the connection is re-used, it may subsequently be closed when the T-W-R finishes closing the first use of the connection
//	private static ComboPooledDataSource dataSource = null;
//	static {
//		try {
//			Class.forName("org.sqlite.JDBC");
//			dataSource = new ComboPooledDataSource();
//			dataSource.setJdbcUrl("jdbc:sqlite:" + DB_PATH);
//
//			// Optional Settings
//			dataSource.setInitialPoolSize(1);
//			dataSource.setMinPoolSize(1);
//			dataSource.setAcquireIncrement(1);
//			dataSource.setMaxPoolSize(1);
//			dataSource.setMaxStatements(100);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

    public static Connection getConnection() {
        try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
}
