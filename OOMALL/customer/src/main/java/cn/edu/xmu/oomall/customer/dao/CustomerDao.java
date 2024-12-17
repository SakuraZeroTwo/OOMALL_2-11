package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.mapper.CustomerPoMapper;
import cn.edu.xmu.oomall.customer.mapper.po.CustomerPo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerDao {

    private final static Logger logger = LoggerFactory.getLogger(CustomerDao.class);
    private final CustomerPoMapper customerPoMapper;

    /**
     * 根据 ID 查询顾客
     */
    public Optional<Customer> findById(Long id) {
        logger.info("Attempting to find customer by id: {}", id);
        return customerPoMapper.findById(id).map(po -> {
            Customer bo = new Customer();
            BeanUtils.copyProperties(po, bo);
            return bo;
        });
    }

    /**
     * 根据用户名查询顾客
     */
    public Optional<Customer> findByUserName(String userName) {
        return Optional.ofNullable(customerPoMapper.findByUserName(userName))
                .map(po -> {
                    Customer bo = new Customer();
                    BeanUtils.copyProperties(po, bo);
                    return bo;
                });
    }

    /**
     * 保存或更新顾客信息
     */
    public Customer save(Customer customer) {
        CustomerPo customerPo = new CustomerPo();
        BeanUtils.copyProperties(customer, customerPo);
        customerPoMapper.save(customerPo);

        Customer bo = new Customer();
        BeanUtils.copyProperties(customerPo, bo);
        return bo;
    }
}
