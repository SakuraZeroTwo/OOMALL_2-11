package cn.edu.xmu.oomall.customer.controller.dto;

import cn.edu.xmu.oomall.customer.dao.bo.Customer;

import java.util.List;

public class CustomerListResponseData {
    private List<Customer> customers;

    public CustomerListResponseData(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "CustomerResponseData{" +
                "customers=" + customers +
                '}';
    }
}
