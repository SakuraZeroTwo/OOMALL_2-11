package cn.edu.xmu.oomall.customer.controller.dto;

import cn.edu.xmu.oomall.customer.dao.bo.Customer;

import java.util.List;

public class CustomerResponseData {
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerResponseData(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "CustomerResponseData{" +
                "customer=" + customer +
                '}';
    }
}
