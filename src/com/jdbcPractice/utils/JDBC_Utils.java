package com.jdbcPractice.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBC_Utils {
    public static Connection getConnection() throws Exception{
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static void CloseResources(PreparedStatement ps, Connection conn) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void CloseResources(PreparedStatement ps, Connection conn, ResultSet rs) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
