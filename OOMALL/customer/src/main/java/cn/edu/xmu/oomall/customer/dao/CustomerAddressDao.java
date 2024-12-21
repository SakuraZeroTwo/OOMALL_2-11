package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.dao.bo.CustomerAddress;
import cn.edu.xmu.oomall.customer.mapper.CustomerAddressPoMapper;
import cn.edu.xmu.oomall.customer.mapper.CustomerPoMapper;
import cn.edu.xmu.oomall.customer.mapper.po.CustomerAddressPo;
import cn.edu.xmu.oomall.customer.mapper.po.CustomerPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@RefreshScope
@Slf4j
public class CustomerAddressDao {
    @Autowired
    private final CustomerAddressPoMapper customerAddressPoMapper;
    private CustomerAddressPo customerAddressPo;
    @Autowired
    private CustomerPoMapper customerPoMapper;
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

    public void setDefaultAddress(Long customerId, Long addressId) {
        Optional<CustomerPo> customer = customerPoMapper.findById(customerId);
        if (!customer.isPresent()) {
            // 如果顾客不存在，抛出异常
            throw new RuntimeException("Customer not found!");
        }
        Optional<CustomerAddressPo> customerAddress = customerAddressPoMapper.findByCustomerIdAndId(customerId, addressId);
        if (!customerAddress.isPresent()) {
            // 如果顾客没有该地址，抛出异常
            throw new RuntimeException("Address not found for the given customer.");
        }
        else {
            customerAddressPoMapper.updateDefaultAddressByCustomerId(customerId, addressId);
            CustomerAddressPo addressPo = customerAddress.get();
            addressPo.setBeDefault((byte) 1);
            customerAddressPoMapper.save(addressPo);
        }

    }
    public List<CustomerAddress> retrieveByCustomerId(Long id,Integer page,Integer pageSize) throws RuntimeException {
        Pageable pageable= PageRequest.of(page - 1,pageSize, Sort.by("regionId").descending());
        List<CustomerAddressPo> retObj = this.customerAddressPoMapper.findByCustomerIdIs(id,pageable);
        return retObj.stream()
                .map(po -> {
                    CustomerAddress address = new CustomerAddress(); // 创建目标对象
                    BeanUtils.copyProperties(po, address); // 复制属性
                    return address; // 返回目标对象
                })
                .collect(Collectors.toList());
    }
}

