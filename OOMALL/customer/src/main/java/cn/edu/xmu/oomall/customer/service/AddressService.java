package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.controller.dto.AddressDto;
import cn.edu.xmu.oomall.customer.dao.AddressDao;
import cn.edu.xmu.oomall.customer.dao.CustomerDao;
import cn.edu.xmu.oomall.customer.dao.bo.Address;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {
    private final CustomerDao customerDao;
    private final AddressDao addressDao;
    public Address addAddress(Address address, Long id) {
        // 从数据库中查找 Customer，如果不存在，抛出异常
        Customer customer = customerDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + id + " not found"));

        customer.setAddressDao(this.addressDao);
        address.setCustomerId(id);

        // 调用 Customer 的方法添加地址
        return customer.addAddress(address);
    }

    public List<Address> retrieveByCustomerId(Long id,Integer page,Integer pageSize) {
        log.debug("retrieveOnSale: CustomerId = {}", id);
        List<Address> addresses = this.addressDao.retrieveByCustomerId(id,page,pageSize);
        return addresses;
    }
}
