package com.jdbcPractice.blob;

import com.jdbcPractice.bean.Customers;
import com.jdbcPractice.utils.JDBC_Utils;
import org.junit.Test;

import java.io.*;
import java.sql.*;


public class BlobTest {
    @Test
    public void testBlobInsert() {
        FileInputStream is = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBC_Utils.getConnection();
            String sql = "insert into customers (name,email,birth,photo) values (?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, "Geralt");
            ps.setObject(2, "geralt@gmail.com");
            ps.setObject(3, "1314-05-20");
            is = new FileInputStream(new File("C:\\jdbc_1\\src\\Geralt.jpg"));
            ps.setBlob(4, is);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBC_Utils.CloseResources(ps, conn);
        }
    }

    @Test
    public void testBlobQuery() {
        FileOutputStream fos = null;
        InputStream is = null;
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = JDBC_Utils.getConnection();
            String sql = "select id, name, email, birth, photo from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 19);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                Date birth = rs.getDate(4);
                Customers cus = new Customers(id, name, email, birth);
                System.out.println(cus);

                Blob photo = rs.getBlob(5);
                is = photo.getBinaryStream();
            }
            fos = new FileOutputStream("C:\\jdbc_1\\src\\Geralt-2.jpg");
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(fos!=null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(is!=null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBC_Utils.CloseResources(ps, conn);
        }
    }
}
