package cn.edu.xmu.oomall.customer.controller;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.oomall.customer.controller.dto.ResponseWrapper;

import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.service.CartService;
import cn.edu.xmu.oomall.customer.service.CouponService;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

@RestController
@RequestMapping(value = "/{did}/customers", produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
public class AdminController {
    private final CustomerService customerService;
    public static final Long PLATFORM = 0L;


    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getCustomerById(@PathVariable Long id,@PathVariable Long did) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "地区", id, did));
        }
        ResponseWrapper customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }
    @PutMapping("/{id}/{action:ban|release}")
    public ResponseEntity<String> updateUserInvalid(@PathVariable Long id,
                                                    @PathVariable String action,
                                                    @PathVariable Long did) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "地区", did));
        }

        customerService.updateUserInvalid(id);
        if ("ban".equalsIgnoreCase(action)) {
            return ResponseEntity.ok("Customer " + id + " has been banned.");
        } else {
            return ResponseEntity.ok("Customer " + id + " has been released.");
        }
    }
    @PutMapping("/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long id,@PathVariable Long did) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "地区", id, did));
        }
        customerService.deleteUser(id);
        return ResponseEntity.ok("Customer " + id + " has been deleted.");
    }
    @GetMapping("/getAllCustomers")
    public ResponseEntity<ResponseWrapper> retriveUsers(@PathVariable Long did) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "地区", did));
        }
        ResponseWrapper customers = customerService.retriveUsers();
        return ResponseEntity.ok(customers);
    }

}
