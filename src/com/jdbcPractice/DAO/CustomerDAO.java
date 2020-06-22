package com.jdbcPractice.DAO;

import com.jdbcPractice.bean.Customers;

import java.sql.Connection;
import java.util.List;

public interface CustomerDAO {
    void insert(Connection conn, Customers customers);

    void deleteByID(Connection conn, int id);

    void update(Connection conn, Customers customers);

    List<Customers> getCustomerByID(Connection conn, int id);
}
