package com.jdbcPractice.insertMultipleRows;

import com.jdbcPractice.utils.JDBC_Utils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class TestInsertMultipleRows {
    @Test
    public void testInsert() {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = JDBC_Utils.getConnection();
            String sql = "insert into goods(name) values (?)";
            ps = conn.prepareStatement(sql);
            long begin = System.currentTimeMillis();
            for (int i = 1; i <= 10000; i++) {
                ps.setObject(1, "name_" + i);
                ps.execute();
            }
            long end = System.currentTimeMillis();
            System.out.println(end - begin);//22s for inserting 10000 rows
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JDBC_Utils.CloseResources(ps, conn);
        }
    }

    @Test
    public void testInsert1() {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = JDBC_Utils.getConnection();
            String sql = "insert into goods(name) values (?)";
            ps = conn.prepareStatement(sql);
            long begin = System.currentTimeMillis();
            for (int i = 1; i <= 1000000; i++) {
                ps.setObject(1, "name_" + i);

                ps.addBatch();

                if (i % 500 == 0) {
                    ps.executeBatch();
                    ps.clearBatch();
                }

            }
            long end = System.currentTimeMillis();
            System.out.println(end - begin);//7.7s for inserting 1000000 rows
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JDBC_Utils.CloseResources(ps, conn);
        }
    }

    @Test
    public void testInsert2() {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = JDBC_Utils.getConnection();
            conn.setAutoCommit(false);
            String sql = "insert into goods(name) values (?)";
            ps = conn.prepareStatement(sql);
            long begin = System.currentTimeMillis();
            for (int i = 1; i <= 1000000; i++) {
                ps.setObject(1, "name_" + i);
                ps.addBatch();
                if (i % 1000 == 0) {
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }
            conn.commit();
            long end = System.currentTimeMillis();
            System.out.println(end - begin);//3.7s for inserting 1000000 rows
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JDBC_Utils.CloseResources(ps, conn);
        }
    }
}
