package com.mr.clock.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.ConfigurationException;

//数据库连接工具类
public class JDBCUtil {
	private static String driver_name;// 驱动类
	private static String username;// 账号
	private static String password;// 密码
	private static String url;// 数据库地址
	private static Connection con = null;// 数据库连接
	private static final String CONFLG_FILE = "src/com/mr/clock/config/jdbc.properties"; // 数据库配置文件地址
	static {
		Properties pro = new Properties();// 配置文件解析类
		try {// 配置文件的文件对象
			File config=new File(CONFLG_FILE);
			if(!config.exists()) {// 如果配置文件不存在
				throw new FileNotFoundException("缺少文件："+ config.getAbsolutePath());
			}
			pro.load(new FileInputStream(config));// 加载配置文件
			driver_name=pro.getProperty("driver_name");// 获取指定字段值
			username=pro.getProperty("username","");
			password=pro.getProperty("password","");
			url=pro.getProperty("url");
			if (driver_name==null||url==null) {
				throw new ConfigurationException("jdbc.properties文件缺少配置文件");
			}
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (ConfigurationException e) {//输出发生异常的配置文件内容
			// TODO: handle exception
			System.err.println("配置文件获取的内容:[driver_name=" + driver_name + "],[username=" + username + "],[password="+ password + "],[url=" + url + "]");
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {//获取数据库连接。根据jdbc.properties中的配置信息返回对应的Connection对象
		try {
			if (con==null||con.isClosed()){// 如果连接对象被关闭
				Class.forName(driver_name);// 加载驱动类
				con=DriverManager.getConnection(url,username,password); // 根据URL、账号密码获取数据库连接
			}
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return con;
	}
	   public static void close(ResultSet rs) {//关闭ResultSet
	        try {
	            if (rs != null)
	                rs.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	   public static void close(Statement stmt) {//关闭Statement
	        try {
	            if (stmt != null)
	                stmt.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	   public static void close(PreparedStatement ps) {//关闭PreparedStatement
	        try {
	            if (ps != null)
	                ps.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	   public static void close(Statement stmt, PreparedStatement ps, ResultSet rs) {//安全的关闭数据库接口
	        close(rs);
	        close(ps);
	        close(stmt);
	    }
	   public static void closeConnection() {//关闭Connection
	        if (con != null) {
	            try {
	                con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}
