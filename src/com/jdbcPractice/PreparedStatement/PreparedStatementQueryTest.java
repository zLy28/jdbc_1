package com.jdbcPractice.PreparedStatement;

import com.jdbcPractice.bean.Customers;
import com.jdbcPractice.bean.User;
import com.jdbcPractice.utils.JDBC_Utils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class PreparedStatementQueryTest {
    @Test
    public void queryTest() {
        String sql = "select id,name,password,phone from user where id < ?";
        List<User> q = query(User.class, sql, 12);
        System.out.print(q);
    }

    public <T> List<T> query(Class<T> tClass, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //Get Connection
            conn = JDBC_Utils.getConnection();
            //Get PreparedStatement Object
            ps = conn.prepareStatement(sql);
            //fill out sql
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            //Use ResultSetMetaData to get The number of columns and column labels
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            //creat a list for storing sql results which are more than one line
            ArrayList<T> list = new ArrayList<T>();
            while (rs.next()) {
                T t = tClass.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    //get the value of a column
                    Object columnValue = rs.getObject(i + 1);

                    //get the column label of a column using reflection
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    //match fields in beans with column label
                    Field field = tClass.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,columnValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.CloseResources(ps, conn, rs);
        }
        return null;
    }
}
