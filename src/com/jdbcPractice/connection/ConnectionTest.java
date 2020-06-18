package com.jdbcPractice.connection;

import jdk.jfr.StackTrace;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
    @Test
    public void testConnection1() throws SQLException {
        Driver driver = new com.mysql.jdbc.Driver();
        String url = "jdbc:mysql://localhost:3306/test";
        Properties userInfo = new Properties();
        userInfo.setProperty("user", "root");
        userInfo.setProperty("password", "abc123");
        Connection conn = driver.connect(url, userInfo);
        System.out.println(conn);
    }

    @Test
    public void testConnection2() throws Exception{
        Class aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) aClass.newInstance();
        String url = "jdbc:mysql://localhost:3306/test";
        Properties userInfo = new Properties();
        userInfo.setProperty("user", "root");
        userInfo.setProperty("password", "abc123");
        Connection conn = driver.connect(url, userInfo);
        System.out.println(conn);
    }

    @Test
    public void testConnection3() throws Exception{
        Class aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) aClass.newInstance();
        DriverManager.registerDriver(driver);
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "abc123";
        Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);
    }

    @Test
    public void testConnection4() throws Exception{
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "abc123";

        Class.forName("com.mysql.jdbc.Driver");
//        Driver driver = (Driver) aClass.newInstance();
//        DriverManager.registerDriver(driver);

        Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);
    }

    @Test
    public void testConnection5() throws Exception {
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driver = properties.getProperty("driver");

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
}
