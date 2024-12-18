package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.dao.bo.CustomerAddress;
import cn.edu.xmu.oomall.customer.mapper.CustomerAddressPoMapper;
import cn.edu.xmu.oomall.customer.mapper.po.CustomerAddressPo;
import cn.edu.xmu.oomall.customer.mapper.po.CustomerPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@RefreshScope
@Slf4j
public class CustomerAddressDao {
    @Autowired
    private final CustomerAddressPoMapper customerAddressPoMapper;

    /**
     * 根据id查找对应address信息
     */
    private final static Logger logger = LoggerFactory.getLogger(CustomerAddressDao.class);
    public Optional<CustomerAddress> findById(Long id) {
        logger.info("Attempting to find address information by id: {}", id);
        return customerAddressPoMapper.findById(id).map(po -> {
            CustomerAddress bo = new CustomerAddress();
            BeanUtils.copyProperties(po, bo);
            return bo;
        });
    }

    public CustomerAddress save(CustomerAddress customerAddress) {
        CustomerAddressPo customerAddressPo = new CustomerAddressPo();
        BeanUtils.copyProperties(customerAddress, customerAddressPo);
        customerAddressPoMapper.save(customerAddressPo);

        CustomerAddress bo = new CustomerAddress();
        BeanUtils.copyProperties(customerAddressPo, bo);
        return bo;
    }

}
