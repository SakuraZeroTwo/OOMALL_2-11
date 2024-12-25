package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.javaee.core.validation.NewGroup;
import cn.edu.xmu.oomall.customer.controller.dto.*;

import cn.edu.xmu.oomall.customer.controller.vo.CartItemVo;
import cn.edu.xmu.oomall.customer.controller.vo.CouponVo;
import cn.edu.xmu.oomall.customer.controller.vo.CustomerVo;
import cn.edu.xmu.oomall.customer.dao.CustomerAddressDao;
import cn.edu.xmu.oomall.customer.dao.bo.CartItem;
import cn.edu.xmu.oomall.customer.dao.bo.Coupon;
import cn.edu.xmu.oomall.customer.dao.bo.CartItem;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.dao.bo.CustomerAddress;
import cn.edu.xmu.oomall.customer.mapper.openfeign.OnsaleMapper;
import cn.edu.xmu.oomall.customer.service.CartService;
import cn.edu.xmu.oomall.customer.service.CouponService;
import cn.edu.xmu.oomall.customer.service.CustomerAddressService;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
        List <CouponVo> couponList = couponService.getCouponsList(id);
//        List<CouponVo> couponVoList = new ArrayList<>();
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

    @PostMapping("/{id}/cart")
    public ReturnObject addToCart(@PathVariable Long id, @Validated @RequestBody CartItemDto dto)
    {
        CartItem cartItem = new CartItem();
        BeanUtils.copyProperties(dto, cartItem);
        CartItem newCartItem = this.cartService.addToCart(id,cartItem);
        CartItemVo vo = new CartItemVo();
        BeanUtils.copyProperties(newCartItem, vo);
        return new ReturnObject(ReturnNo.CREATED,vo);
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
     * 获取地址信息列表
     */
    @GetMapping("/{id}/addresses")
    public ReturnObject getAddressesByCustomerId(@PathVariable Long id,
                                                 @RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        List<CustomerAddress> addresses = this.customerAddressService.retrieveByCustomerId(id,page,pageSize);
        if (addresses.isEmpty() ) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "顾客Id不存在");
        }

        return new ReturnObject(new PageVo<>(addresses,page,pageSize));
    }
    /**
     * 添加用户地址
     */
    @PostMapping("/address/{customerId}")
    public ReturnObject addAddress(@RequestBody CustomerAddressDto addressDto,@PathVariable Long customerId) {
        try {
            CustomerAddress savedAddress = this.customerAddressService.addAddress(addressDto, customerId);
            return new ReturnObject(ReturnNo.CREATED, savedAddress);
        } catch (BusinessException ex) {
            // 根据错误码返回对应的错误信息
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "登录用户id不存在");
        }
    }

    @PutMapping("/{cartItemId}/cart/update")
    public ReturnObject updateProductInCart(@PathVariable Long cartItemId, @RequestParam Long quantity) {
        CartItem savedCartItem = this.cartService.updateProductInCart(cartItemId,quantity);
        return new ReturnObject(ReturnNo.OK,savedCartItem);
    }

    @DeleteMapping("/{cartItemId}/cart/delete")
    public ReturnObject deleteProductInCart(@PathVariable Long cartItemId) {
        this.cartService.deleteProductInCart(cartItemId);
        return new ReturnObject(ReturnNo.OK);
    }
}

