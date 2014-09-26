package com.secuest.mdog.utils;

import java.io.Closeable;
import android.os.AsyncTask;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import com.mysql.jdbc.Driver;



public class MySQL_SingIn  extends AsyncTask<String,Void,String>{



	/*
	 *  $mysql_host = "mysql5.000webhost.com";
	 *  $mysql_database = "a5903734_sec";
	 *  $mysql_user = "a5903734_user1";
	 *  $mysql_password = "Secuest01";
	 */
	/* publipersonal.es.mysql
	 * Base de datos:	publipersonal_e
	 * Nombre de usuario:	publipersonal_e
	 * Clave de acceso:	9LEJubCv
	 */


	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public void readDataBase() throws Exception {
		try {
			// this will load the MySQL driver, each DB has its own driver
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			final String DB_URL = "jdbc:mysql://publipersonal.es.mysql/publipersonal_e";

			//  Database credentials
			final String USER = "publipersonal_e";
			final String PASS = "9LEJubCv";
			// setup the connection with the DB.
			//connect = DriverManager.getConnection("jdbc:mysql://mysql5.000webhost.com?"
			//       + "user=a5903734_user1&password=Secuest01");
			connect = DriverManager.getConnection(DB_URL,USER,PASS);

			// statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// resultSet gets the result of the SQL query
			resultSet = statement
					.executeQuery("select * from Dueno");
			writeResultSet(resultSet);

			// preparedStatements can use variables and are more efficient
			preparedStatement = connect
					.prepareStatement("insert into  a5903734_sec.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
			// "myuser, webpage, datum, summary, COMMENTS from a5903734_sec.COMMENTS");
			// parameters start with 1
			preparedStatement.setString(1, "Test");
			preparedStatement.setString(2, "TestEmail");
			preparedStatement.setString(3, "TestWebpage");
			preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
			preparedStatement.setString(5, "TestSummary");
			preparedStatement.setString(6, "TestComment");
			preparedStatement.executeUpdate();

			preparedStatement = connect
					.prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from a5903734_sec.COMMENTS");
			resultSet = preparedStatement.executeQuery();
			writeResultSet(resultSet);

			// remove again the insert comment
			preparedStatement = connect
					.prepareStatement("delete from a5903734_sec.COMMENTS where myuser= ? ; ");
			preparedStatement.setString(1, "Test");
			preparedStatement.executeUpdate();

			resultSet = statement
					.executeQuery("select * from a5903734_sec.COMMENTS");
			writeMetaData(resultSet);

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	private void writeMetaData(ResultSet resultSet) throws SQLException {
		// now get some metadata from the database
		System.out.println("The columns in the table are: ");
		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
			System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// resultSet is initialised before the first data set
		while (resultSet.next()) {
			// it is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g., resultSet.getSTring(2);
			String user = resultSet.getString("myuser");
			String website = resultSet.getString("webpage");
			String summary = resultSet.getString("summary");
			Date date = resultSet.getDate("datum");
			String comment = resultSet.getString("comments");
			System.out.println("User: " + user);
			System.out.println("Website: " + website);
			System.out.println("Summary: " + summary);
			System.out.println("Date: " + date);
			System.out.println("Comment: " + comment);
		}
	}

	// you need to close all three to make sure
	private void close() {
		close((Closeable) resultSet);
		close((Closeable) statement);
		close((Closeable) connect);
	}
	private void close(Closeable c) {
		try {
			if (c != null) {
				c.close();
			}
		} catch (Exception e) {
			// don't throw now as it might leave following closables in undefined state
		}
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}
} 