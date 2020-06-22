package com.jdbcPractice.DAO;

import com.jdbcPractice.bean.Customers;

import java.sql.Connection;
import java.util.List;

public class CustomerImpl extends BaseDAO implements CustomerDAO{
    @Override
    public void insert(Connection conn, Customers customers) {
        String sql = "insert into customers(id,name,email,birth) values (?,?,?,?)";
        update(conn,sql,customers.getId(),customers.getName(),customers.getEmail(),customers.getBirth());
    }

    @Override
    public void deleteByID(Connection conn, int id) {
        String sql = "delete from customer where id=?";
        update(conn,sql,id);
    }

    @Override
    public void update(Connection conn, Customers customers) {
        String sql = "update customers set name = ?, email=? birth =? where id = ?";
        update(conn,sql,customers.getName(),customers.getEmail(),customers.getBirth(),customers.getId());
    }

    @Override
    public List<Customers> getCustomerByID(Connection conn, int id) {
        String sql = "select name,email,birth from customers where id = ?";
        List<Customers> result = query(conn, Customers.class, sql, id);
        return result;
    }
}
