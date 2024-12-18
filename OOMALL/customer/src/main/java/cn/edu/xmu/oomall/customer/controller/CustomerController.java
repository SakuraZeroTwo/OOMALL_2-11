package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.customer.controller.dto.AddressDto;
import cn.edu.xmu.oomall.customer.controller.dto.CartItemDto;
import cn.edu.xmu.oomall.customer.dao.bo.Address;
import cn.edu.xmu.oomall.customer.dao.bo.CartItem;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.service.AddressService;
import cn.edu.xmu.oomall.customer.service.CartItemService;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private final AddressService addressService;

    private final CartItemService cartItemService;

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

    @PostMapping("/address/{id}")
    public ResponseEntity<Address> addAddress(@RequestBody AddressDto addressDto, @PathVariable Long id) {
        Address address = new Address();
        address.setAddress(addressDto.getAddress());
        Address savedAddress = this.addressService.addAddress(address,id);
        return ResponseEntity.ok(savedAddress);
    }

    @GetMapping("/{id}/addresses")
    public ReturnObject getAddressesByCustomerId(@PathVariable Long id,@RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Address> addresses = this.addressService.retrieveByCustomerId(id,page,pageSize);
        return new ReturnObject(new PageVo<>(addresses,page,pageSize));
    }

    /**
     * 店家修改无需审核的货品信息
     * @param customerId 顾客Id
     * @param dto 购物车明细dto
     * @return
     */
    @PutMapping("/cartItem/{customerId}")
    public ReturnObject updateProductInCart(@PathVariable Long customerId,@RequestBody CartItemDto dto) {
        CartItem cartItem = new CartItem();
        BeanUtils.copyProperties(dto, cartItem);
        cartItem.setCustomerId(customerId);
        this.cartItemService.updateProductInCart(cartItem,customerId);
        return new ReturnObject();
    }
}
