package com.jdbcPractice.updateInTransaction;

import com.jdbcPractice.utils.JDBC_Utils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestUpdateInTransaction {
    @Test
    public void test() {
        Connection conn = null;
        try {
            conn = JDBC_Utils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "update user_table set balance=balance-100 where user = ?";
            String sql2 = "update user_table set balance=balance+100 where user = ?";
            updateInTransaction(conn,sql1,"AA");
//            System.out.println(10/0);
            updateInTransaction(conn,sql2,"BB");
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            JDBC_Utils.CloseResources(null,conn);
        }
    }


    public void updateInTransaction(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        try {
            //Get PreparedStatement Object and fill out sql sentence
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //execute sql
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //close resources
            JDBC_Utils.CloseResources(ps, null);
        }
    }
}
