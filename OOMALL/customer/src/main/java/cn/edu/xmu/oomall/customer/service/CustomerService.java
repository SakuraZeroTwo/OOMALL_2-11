package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerDto;
import cn.edu.xmu.oomall.customer.dao.CustomerAddressDao;
import cn.edu.xmu.oomall.customer.dao.CustomerDao;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.dao.bo.CustomerAddress;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerAddressDto;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDao customerDao;
    private final CustomerAddressDao customerAddressDao;
    private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);
    /**
     * 根据用户名获取顾客
     */
    public Customer getCustomerByUserName(String userName) {
        Customer customer = customerDao.findByUserName(userName).orElse(null);
        if (customer == null) {
            throw new BusinessException(ReturnNo.CUSTOMERNAME_NOTEXIST);
        }
        return customer;
    }

    /**
     * 根据 ID 获取顾客
     */
    public Customer getCustomerById(Long id) {
        Customer customer = customerDao.findById(id).orElse(null);
        if (customer == null) {
            throw new BusinessException(ReturnNo.AUTH_ID_NOTEXIST);
        }
        return customer;
    }
    /**
     * 查询所有顾客列表
     */
    public List<Customer> retriveUsers() {
        // 获取所有顾客
        List<Customer> customers = customerDao.findAll();
//        if (customers.isEmpty()) {
////            return new ResponseWrapper("User not Found!", null ,2);
//            throw new RuntimeException("User not Found!");
//        }
//        return new ResponseWrapper("success",new CustomerListResponseData(customers),1);
//        List <CustomerDto> customerDtoList = new List<CustomerDto>();
//        BeanUtils.copyProperties(customers, customerDtoList);
        return customers;
    }

    /**
     * 创建顾客用户
     */
    public Customer createCustomer(Customer customer) {
    // 只允许创建时传入用户名和密码
    if (customer.getUserName() == null) {
        throw new BusinessException(ReturnNo.CUSTOMERNAME_ISNULL);
    } else if (customer.getPassword() == null) {
        throw new BusinessException(ReturnNo.CUSTOMERPASSWORD_ISNULL);

    } else if(customerDao.findByUserName(customer.getUserName()).isPresent()){
        throw new BusinessException(ReturnNo.CUSTOMER_NAMEEXIST);
    }
    // 设置创建时间为当前时间
    customer.setGmtCreate(LocalDateTime.now());

    // 保存顾客
    return customerDao.save(customer);
}

    /**
     * 更新顾客信息
     */
    public Customer updateCustomer(Long id, CustomerDto customerdto) {
        // 找到已有顾客
        Customer existingCustomer = customerDao.findById(id).orElseThrow(() -> new BusinessException(ReturnNo.CUSTOMERID_NOTEXIST));

        // 只允许更新指定字段
        if (customerdto.getPassword() != null) {
            existingCustomer.setPassword(customerdto.getPassword());
        }
        if (customerdto.getName() != null) {
            existingCustomer.setName(customerdto.getName());
        }
        if (customerdto.getMobile() != null) {
            existingCustomer.setMobile(customerdto.getMobile());
        }
        existingCustomer.setGmtModified(LocalDateTime.now());
        // 保存更新后的顾客
        return customerDao.save(existingCustomer);
    }
    /**
     * 修改顾客invalid字段
     */
    public void updateUserInvalid(Long id){
        Customer customer = customerDao.findById(id).orElse(null);
        if(customer!= null){
        customer.convertInvalid();
        customerDao.save(customer);
        }
    }
    /**
     * 删除用户
     */
    public void deleteUser(Long id){
        Customer customer = customerDao.findById(id).orElse(null);
        if(customer != null)
        {
            customer.setInvalid(Customer.DELETED);
            customer.setBe_deleted(Customer.BEDELETED);
            customerDao.save(customer);
        }

    }


}
