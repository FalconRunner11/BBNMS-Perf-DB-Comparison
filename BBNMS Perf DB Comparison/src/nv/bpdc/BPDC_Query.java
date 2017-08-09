/**
 * This object class connects to a database, and executes a query on the database.
 * The caller provides the connection URL, username, and password for the database/schema, as well as the query to be executed.
 * The resulting data is stored as an ArrayList<ArrayList<String>>, which can be retrieved with the getResult() method.
 * Any exceptions throw by this class can be retrieved with the getException() method.
 */

package nv.bpdc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BPDC_Query {
	
	//-----------------------------------------------------------------//
	
	/** Declare and initialize final variables **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Declare fields **/
	
	private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private String dbUrl;
	private String dbUsername;
	private String dbPassword;
	private String query;
	private ArrayList<ArrayList<String>> result;
	private Exception exceptionThrown;
	
	//-----------------------------------------------------------------//
	
	/** Constructors **/
	
	protected BPDC_Query(String inc_url, String inc_username, String inc_password, String inc_query) {
		
		dbUrl = inc_url;
		dbUsername = inc_username;
		dbPassword = inc_password;
		query = inc_query;
		result = new ArrayList<ArrayList<String>>();
		exceptionThrown = null;
		
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				ArrayList<String> row = new ArrayList<String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.add(rs.getString(i));
				}
				result.add(row);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			exceptionThrown = e;
		} catch (Exception e) {
			exceptionThrown = e;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				exceptionThrown = e;
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				exceptionThrown = e;
			}
		}
		
	}
	
	//-----------------------------------------------------------------//
	
	/** Protected Methods **/
	
	protected ArrayList<ArrayList<String>> getResult() {
		return result;
	}
	
	protected Exception getException() {
		return exceptionThrown;
	}
	
	//-----------------------------------------------------------------//
	
}