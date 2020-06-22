package com.jdbcPractice.DAO;

import com.jdbcPractice.bean.Customers;
import com.jdbcPractice.utils.JDBC_Utils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class TestDAO {
    CustomerImpl cs = new CustomerImpl();

    @Test
    public void test1() {
        Connection conn = null;
        try {
            Customers alan = new Customers(20, "Alan", "alan@gmail.com", new Date(1231488));
            conn = JDBC_Utils.getConnection();
            cs.insert(conn, alan);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.CloseResources(null, conn);
        }

    }

    @Test
    public void test2() {
        Connection conn = null;
        try {
            conn = JDBC_Utils.getConnection();
            List<Customers> list = cs.getCustomerByID(conn, 20);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.CloseResources(null, conn);
        }

    }
}
