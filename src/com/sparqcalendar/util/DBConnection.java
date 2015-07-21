package com.sparqcalendar.util;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.sparqcalendar.util.Constants;

public class DBConnection {

	public DBConnection(){	}
	
	public static Connection getDBConnection()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(Constants.DATABASE, Constants.USERNAME, Constants.PASSWORD);
			con.setAutoCommit(false);
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
				
		
		return con;
	}
	
	public static void closeConnection(Connection con)
	{
		try
		{
			if (con != null)
				con.close();
		}
		catch (SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
	}
	
	public static void rollbackConnection(Connection con)
	{
		try
		{
			if (con != null)
				con.rollback();
		}
		catch (SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
	}
	
	public static void closeStatement(Statement stmt)
	{
		try
		{
			if (stmt != null)
				stmt.close();
		}
		catch (SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
	}
	
}
