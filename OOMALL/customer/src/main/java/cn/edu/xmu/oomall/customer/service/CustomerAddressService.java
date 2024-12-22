package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerAddressDto;
import cn.edu.xmu.oomall.customer.dao.CustomerAddressDao;
import cn.edu.xmu.oomall.customer.dao.CustomerDao;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.dao.bo.CustomerAddress;
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
public class CustomerAddressService {


    private final CustomerDao customerDao;
    private final CustomerAddressDao customerAddressDao;
    private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);
    /**
     * 顾客更新地址信息
     *
     */
    public CustomerAddress updateAddressInfo(Long id, CustomerAddressDto customeraddressdto) {
        logger.info("Attempting to find address information by id: {}", id);
        CustomerAddress exitingCustomerAddress = customerAddressDao.findById(id).orElseThrow(() -> new BusinessException(ReturnNo.CUSTOMERID_NOTEXIST));
        if (customeraddressdto.getRegionId() != null) {
            exitingCustomerAddress.setRegionId(customeraddressdto.getRegionId());
        }
        if (customeraddressdto.getAddress() != null) {
            exitingCustomerAddress.setAddress(customeraddressdto.getAddress());
        }
        if (customeraddressdto.getMobile() != null) {
            exitingCustomerAddress.setMobile(customeraddressdto.getMobile());
        }
        if (customeraddressdto.getConsignee() != null) {
            exitingCustomerAddress.setConsignee(customeraddressdto.getConsignee());
        }
        exitingCustomerAddress.setGmtModified(LocalDateTime.now());
        // 保存更新后的顾客
        return customerAddressDao.save(exitingCustomerAddress);
    }
    /**
     * 顾客设置默认地址
     *
     */
    public void setDefaultAddress(Long customerId, Long addressId) {

        customerAddressDao.setDefaultAddress(customerId, addressId);
    }

    public List<CustomerAddress> retrieveByCustomerId(Long id, Integer page, Integer pageSize) {
        List<CustomerAddress> addresses = this.customerAddressDao.retrieveByCustomerId(id,page,pageSize);
        return addresses;
    }

    public CustomerAddress addAddress(CustomerAddressDto addressDto,Long customerId) {
        CustomerAddress address = new CustomerAddress();
        BeanUtils.copyProperties(addressDto, address);
        Customer customer = customerDao.findById(customerId).orElseThrow(() -> new BusinessException(ReturnNo.CUSTOMERID_NOTEXIST));
        customer.setCustomerAddressDao(this.customerAddressDao);
        address.setCustomerId(customerId);
        return customer.addAddress(address);
    }
}
