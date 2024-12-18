package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.oomall.customer.controller.dto.CustomerAddressDto;
import cn.edu.xmu.oomall.customer.dao.CustomerAddressDao;
import cn.edu.xmu.oomall.customer.dao.bo.CustomerAddress;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerAddressService {


    private final CustomerAddressDao customerAddressDao;
    private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);
    /**
     * 顾客更新地址信息
     *
     */
    public CustomerAddress updateAddressInfo(Long id, CustomerAddressDto customeraddressdto) {
        logger.info("Attempting to find address information by id: {}", id);
        CustomerAddress exitingCustomerAddress = customerAddressDao.findById(id).orElseThrow(() -> new RuntimeException("CustomerAddress not found"));
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
}
