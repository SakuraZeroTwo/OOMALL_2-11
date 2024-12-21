package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.customer.controller.dto.*;

import cn.edu.xmu.oomall.customer.controller.vo.CouponVo;
import cn.edu.xmu.oomall.customer.controller.vo.CustomerVo;
import cn.edu.xmu.oomall.customer.dao.CustomerAddressDao;
import cn.edu.xmu.oomall.customer.dao.bo.Coupon;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.dao.bo.CustomerAddress;
import cn.edu.xmu.oomall.customer.service.CartService;
import cn.edu.xmu.oomall.customer.service.CouponService;
import cn.edu.xmu.oomall.customer.service.CustomerAddressService;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.ReusableMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CouponService couponService;
    private final CustomerService customerService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerAddressService customerAddressService;

    /**
     * 通过用户名获取顾客信息
     */
    @GetMapping("/username/{username}")
    public ReturnObject getCustomerByUserName(@PathVariable("username") String userName) {

        Customer customer = customerService.getCustomerByUserName(userName);
        CustomerVo customerVo = new CustomerVo();
        BeanUtils.copyProperties(customer, customerVo);
        return new ReturnObject(customerVo);
    }

    /**
     * 创建顾客
     */
    @PostMapping
    public ReturnObject createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ReturnObject();
    }

    /**
     * 更新顾客信息
     */
    @PutMapping("/{id}")
    public ReturnObject updateCustomerMessage(@PathVariable Long id, @RequestBody CustomerDto customerdto) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerdto);
        return new ReturnObject();
    }
    @PutMapping("/{id}/password")
    public ReturnObject updateCustomerPassword(@PathVariable Long id, @RequestBody CustomerDto customerdto) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerdto);
        return new ReturnObject();
    }



    /**
     * 获取购物车列表
     */
    @GetMapping("/{id}/cart")
    public ReturnObject getCartList(@PathVariable Long id) {
        CartResponseData response = cartService.getCartList(id);
        return new ReturnObject(response);
    }
    /**
     * 获取优惠券列表
     */
    @GetMapping("/{id}/coupon")
    public ReturnObject getCouponList(@PathVariable Long id) {
        List <Coupon> couponList = couponService.getCouponsList(id);
        List<CouponVo> couponVoList = new ArrayList<>();
//        BeanUtils(couponList,couponVoList);
        return new ReturnObject(couponList);
    }

    /**
     * 更新地址信息
     */
    @PutMapping("/addresses/{id}")
    public ReturnObject updateAddressInfo(@PathVariable Long id, @RequestBody CustomerAddressDto customerAddressDto) {
        CustomerAddress updatedAddress = customerAddressService.updateAddressInfo(id,customerAddressDto);
        return new ReturnObject();
    }

    /**
     * 设置默认地址
     */
    @PutMapping("/default-address/{customerId}")
    public ReturnObject setDefaultAddress(@PathVariable Long customerId, @RequestParam Long addressId) {
        customerAddressService.setDefaultAddress(customerId, addressId);
        return new ReturnObject();
    }
    /**
     * 获取地址信息
     */
    @GetMapping("/{id}/addresses")
    public ReturnObject getAddressesByCustomerId(@PathVariable Long id,
                                                 @RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        List<CustomerAddress> addresses = this.customerAddressService.retrieveByCustomerId(id,page,pageSize);
        return new ReturnObject(new PageVo<>(addresses,page,pageSize));
    }
    /**
     * 添加用户地址
     */
    @PostMapping("/address/{customerId}")
    public ReturnObject addAddress(@RequestBody CustomerAddressDto addressDto,@PathVariable Long customerId) {
        CustomerAddress savedAddress = this.customerAddressService.addAddress(addressDto,customerId);
        return new ReturnObject();
    }
}

