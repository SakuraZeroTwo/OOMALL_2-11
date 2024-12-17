package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.oomall.customer.controller.dto.ResponseWrapper;
import cn.edu.xmu.oomall.customer.dao.bo.CartItem;
import cn.edu.xmu.oomall.customer.dao.bo.CartResponse;
import cn.edu.xmu.oomall.customer.dao.bo.Coupon;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.service.CartService;
import cn.edu.xmu.oomall.customer.service.CouponService;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 通过用户名获取顾客信息
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<Customer> getCustomerByUserName(@PathVariable("username") String userName) {
        Customer customer = customerService.getCustomerByUserName(userName);
        return ResponseEntity.ok(customer);
    }

    /**
     * 通过 ID 获取顾客信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }
    /**
     * 创建或更新顾客信息
     */
    @PostMapping
    public ResponseEntity<Customer> createOrUpdateCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.createOrUpdateCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }
    @PutMapping("/{id}/{action:ban|release}")
    public ResponseEntity<String> updateUserInvalid(@PathVariable Long id,
                                                    @PathVariable String action) {
        customerService.updateUserInvalid(id);
        if ("ban".equalsIgnoreCase(action)) {
            return ResponseEntity.ok("Customer " + id + " has been banned.");
        } else {
            return ResponseEntity.ok("Customer " + id + " has been released.");
        }
    }
    @PutMapping("/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        customerService.deleteUser(id);
        return ResponseEntity.ok("Customer " + id + " has been deleted.");
    }

    // 获取购物车列表
    @GetMapping("/{customerId}/cart")
    public ResponseEntity<ResponseWrapper> getCartList(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseWrapper response = cartService.getCartList(customerId, page, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/coupon")
    public ResponseEntity<List<Coupon>> getCouponList(@PathVariable Long id) {
        List<Coupon> couponList = couponService.getCouponsList(id);
        return ResponseEntity.ok(couponList);
    }

}

