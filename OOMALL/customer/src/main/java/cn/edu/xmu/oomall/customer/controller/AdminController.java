package cn.edu.xmu.oomall.customer.controller;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerDto;
import cn.edu.xmu.oomall.customer.controller.dto.ResponseWrapper;

import cn.edu.xmu.oomall.customer.controller.vo.CustomerVo;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.service.CartService;
import cn.edu.xmu.oomall.customer.service.CouponService;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
    public ReturnObject getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        CustomerVo customerVo = new CustomerVo();
        BeanUtils.copyProperties(customer, customerVo);
        return new ReturnObject(customerVo);
    }
    @PutMapping("/{id}/{action:ban|release}")
    public ReturnObject updateUserInvalid(@PathVariable Long id,
                                                    @PathVariable String action) {
        try {
            customerService.updateUserInvalid(id);
            if ("ban".equalsIgnoreCase(action)) {
                return new ReturnObject();
            } else {
                return new ReturnObject();
            }
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ReturnNo.STATENOTALLOW,
                    String.format(ReturnNo.STATENOTALLOW.getMessage(), "顾客", id,"已删除"));
        }
    }
    @PutMapping("/{id}/delete")
    public ReturnObject deleteUser(@PathVariable Long id) {
        customerService.deleteUser(id);
        return new ReturnObject();
    }
    @GetMapping("/getAllCustomers")
    public ReturnObject retriveUsers() {
        List<Customer> customers =  customerService.retriveUsers();
        return new ReturnObject(customers);
    }

}
