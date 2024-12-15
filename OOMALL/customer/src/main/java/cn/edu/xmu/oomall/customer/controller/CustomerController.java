package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

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
    @PutMapping("/{id}/{action}")
    public ResponseEntity<String> updateUserInvalid(@PathVariable Long id,
                                                    @PathVariable String action) {
        // 统一调用服务层方法
        customerService.updateUserInvalid(id);

        // 判断 action，返回不同的消息
        if ("ban".equalsIgnoreCase(action)) {
            return ResponseEntity.ok("Customer " + id + " has been banned.");
        } else if ("release".equalsIgnoreCase(action)) {
            return ResponseEntity.ok("Customer " + id + " has been released.");
        } else {
            return ResponseEntity.badRequest().body("Invalid action: " + action);
        }
    }
}
