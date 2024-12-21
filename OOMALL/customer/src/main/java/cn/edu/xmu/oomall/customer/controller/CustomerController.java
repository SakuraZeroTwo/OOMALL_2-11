package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.customer.controller.dto.*;

import cn.edu.xmu.oomall.customer.dao.CustomerAddressDao;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.dao.bo.CustomerAddress;
import cn.edu.xmu.oomall.customer.service.CartService;
import cn.edu.xmu.oomall.customer.service.CouponService;
import cn.edu.xmu.oomall.customer.service.CustomerAddressService;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseWrapper> getCustomerByUserName(@PathVariable("username") String userName) {

        ResponseWrapper customer = customerService.getCustomerByUserName(userName);
        return ResponseEntity.ok(customer);
    }

    /**
     * 通过 ID 获取顾客信息
     */
    /**
     * 查询所有顾客
     */

    /**
     * 创建顾客
     */
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }

    /**
     * 更新顾客信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomerMessage(@PathVariable Long id, @RequestBody CustomerDto customerdto) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerdto);
        return ResponseEntity.ok(updatedCustomer);
    }
    @PutMapping("/{id}/password")
    public ResponseEntity<Customer> updateCustomerPassword(@PathVariable Long id, @RequestBody CustomerDto customerdto) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerdto);
        return ResponseEntity.ok(updatedCustomer);
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
    public ResponseEntity<ResponseWrapper> getCouponList(@PathVariable Long id) {
        ResponseWrapper couponList = couponService.getCouponsList(id);
        return ResponseEntity.ok(couponList);
    }

    /**
     * 更新地址信息
     */
    @PutMapping("/addresses/{id}")
    public ResponseEntity<CustomerAddress> updateAddressInfo(@PathVariable Long id, @RequestBody CustomerAddressDto customerAddressDto) {
        CustomerAddress updatedAddress = customerAddressService.updateAddressInfo(id,customerAddressDto);
        return ResponseEntity.ok(updatedAddress);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("/default-address/{customerId}")
    public ReturnObject setDefaultAddress(
            @PathVariable Long customerId,
            @RequestParam Long addressId) {
        customerAddressService.setDefaultAddress(customerId, addressId);
        return new ReturnObject();
    }

    @GetMapping("/{id}/addresses")
    public ReturnObject getAddressesByCustomerId(@PathVariable Long id,@RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        List<CustomerAddress> addresses = this.customerAddressService.retrieveByCustomerId(id,page,pageSize);
        return new ReturnObject(new PageVo<>(addresses,page,pageSize));
    }

    @PostMapping("/address/{customerId}")
    public ResponseEntity<CustomerAddress> addAddress(@RequestBody CustomerAddressDto addressDto,@PathVariable Long customerId) {
        CustomerAddress savedAddress = this.customerAddressService.addAddress(addressDto,customerId);
        return ResponseEntity.ok(savedAddress);
    }


}

