/**
 * 项目名：student
 * 修改历史：
 * 作者： MZ
 * 创建时间： 2016年1月6日-上午9:43:21
 */
package com.qust.factory.utils;

import java.sql.*;

import com.qust.factory.AppConstants;


/**
 * 模块说明：数据库工具类
 * 
 */
public class DBUtil {
	private static DBUtil db;

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	private DBUtil() {

	}

	public static DBUtil getDBUtil() {
		if (db == null) {
			db = new DBUtil();
		}
		return db;
	}

	public int executeUpdate(String sql) {
		int result = -1;
		if (getConn() == null) {
			return result;
		}
		try {
			ps = conn.prepareStatement(sql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int executeUpdate(String sql, Object[] obj) {
		int result = -1;
		if (getConn() == null) {
			return result;
		}
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				ps.setObject(i + 1, obj[i]);
			}
			result = ps.executeUpdate();
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ResultSet executeQuery(String sql) {
		if (getConn() == null) {
			return null;
		}
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet executeQuery(String sql, Object[] obj) {
		System.out.println("DBUtil->executeQuery()" + sql);
		if (getConn() == null) {
			return null;
		}
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				ps.setObject(i + 1, obj[i]);
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	public boolean execute(String sql) {
		System.out.println("DBUtil->execute(1) sql:"+sql);
		if (getConn() == null) {
			System.out.println("DBUtil->execute(2)");
			return false;
		}
		System.out.println("DBUtil->execute(3)");
		try {
			Statement statement = conn.createStatement();
			statement.execute(sql);
			statement.close();
			System.out.println("DBUtil->execute(4)");
			return true;
		} catch (SQLException e) {
			System.out.println("DBUtil->execute(5)"+e);
//			e.printStackTrace();
			return false;
		}
	}

	private Connection getConn() {
		try {
			if (conn == null || conn.isClosed()) {
				Class.forName(AppConstants.JDBC_DRIVER);
				conn = DriverManager.getConnection(AppConstants.JDBC_URL, AppConstants.JDBC_USERNAME,
						com.qust.factory.AppConstants.JDBC_PASSWORD);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("jdbc driver is not found.");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
