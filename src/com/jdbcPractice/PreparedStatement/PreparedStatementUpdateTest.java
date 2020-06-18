package com.jdbcPractice.PreparedStatement;

import com.jdbcPractice.utils.JDBC_Utils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PreparedStatementUpdateTest {
    @Test
    public void testUpdate() {
        String sql = "update customers set email= '12345@gmail.com' where id = ?";
        update(sql,18);
    }

    public void update(String sql, Object ...args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //Get Connection from Mysql
            conn = JDBC_Utils.getConnection();
            //Get PreparedStatement Object and fill out sql sentence
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //execute sql
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //close resources
            JDBC_Utils.CloseResources(ps, conn);
        }
    }
}
