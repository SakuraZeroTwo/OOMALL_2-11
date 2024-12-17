package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.service.CouponService;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import cn.edu.xmu.oomall.customer.dao.bo.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CouponService couponService;

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

    @GetMapping("/{id}/coupon")
    public ResponseEntity<List<Coupon>> getCouponList(@PathVariable Long id) {
        List<Coupon> couponList = couponService.getCouponsList(id);
        if (couponList != null && !couponList.isEmpty()) {
            return ResponseEntity.ok(couponList);
        } else {
            return ResponseEntity.noContent().build(); // No coupons available
        }
    }

}
