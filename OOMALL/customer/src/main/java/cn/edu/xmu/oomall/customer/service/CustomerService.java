package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.oomall.customer.dao.CustomerDao;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDao customerDao;

    /**
     * 根据用户名获取顾客
     */
    public Customer getCustomerByUserName(String userName) {
        return customerDao.findByUserName(userName).orElse(null);
    }

    /**
     * 根据 ID 获取顾客
     */
    public Customer getCustomerById(Long id) {
        return customerDao.findById(id).orElse(null);
    }

    /**
     * 创建或更新顾客
     */
    public Customer createOrUpdateCustomer(Customer customer) {
        return customerDao.save(customer);
    }
    public void updateUserInvalid(Long id){
        Customer customer = customerDao.findById(id).orElse(null);
        if(customer!= null){
        customer.convertInvalid();
        customerDao.save(customer);
        }
    }
    public void deleteUser(Long id){
        Customer customer = customerDao.findById(id).orElse(null);
        if(customer != null)
        {
            customer.setInvalid(Customer.DELETED);
            customer.setBeDelete();
            customerDao.save(customer);
        }

    }
}
