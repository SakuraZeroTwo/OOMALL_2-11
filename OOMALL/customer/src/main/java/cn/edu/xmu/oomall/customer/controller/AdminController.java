package cn.edu.xmu.oomall.customer.controller;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerDto;
import cn.edu.xmu.oomall.customer.controller.dto.ResponseWrapper;

import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.service.CartService;
import cn.edu.xmu.oomall.customer.service.CouponService;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

@RestController
@RequestMapping(value = "customers", produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
public class AdminController {
    private final CustomerService customerService;
    public static final Long PLATFORM = 0L;


    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getCustomerById(@PathVariable Long id) {
        ResponseWrapper customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }
    @PutMapping("/{id}/{action:ban|release}")
    public ResponseEntity<String> updateUserInvalid(@PathVariable Long id,
                                                    @PathVariable String action) {
        try {
            customerService.updateUserInvalid(id);
            if ("ban".equalsIgnoreCase(action)) {
                return ResponseEntity.ok("Customer " + id + " has been banned.");
            } else {
                return ResponseEntity.ok("Customer " + id + " has been released.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("用户状态错误");
        }
    }
    @PutMapping("/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        customerService.deleteUser(id);
        return ResponseEntity.ok("Customer " + id + " has been deleted.");
    }
    @GetMapping("/getAllCustomers")
    public ReturnObject retriveUsers() {
        List<Customer> customers =  customerService.retriveUsers();
//        return ResponseEntity.ok(customers);
        return new ReturnObject(customers);
    }

}
