package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.oomall.customer.dao.CouponDao;
import cn.edu.xmu.oomall.customer.dao.CustomerAddressDao;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import cn.edu.xmu.oomall.customer.dao.bo.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.edu.xmu.javaee.core.model.Constants.MAX_RETURN;
@Slf4j
@Data
@Component
public class Customer implements Serializable {
    private Long id;
    private String userName;
    private String password;
    private String name;
    private Byte invalid;
    private Byte be_deleted;
    private String mobile;
    private Long point;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public Customer(){}
    // 状态常量定义
    public static final Byte NOBEDELETED = 0;     // 有效
    public static final Byte BEDELETED = 1;     // 有效
    @ToString.Exclude
    @JsonIgnore
    public static final Byte VALID = 0;     // 有效
    @ToString.Exclude
    @JsonIgnore
    public static final Byte INVALID = 1;  // 停用
    @ToString.Exclude
    @JsonIgnore
    public static final Byte DELETED = 2;  // 删除

    // 状态名称映射
    @ToString.Exclude
    @JsonIgnore
    public static final Map<Byte, String> STATUSNAMES = new HashMap<>() {
        {
            put(VALID, "有效");
            put(INVALID, "停用");
            put(DELETED, "删除");
        }
    };
    @ToString.Exclude
    @JsonIgnore
    private static final Map<Byte, Set<Byte>> toInvalid = new HashMap<>() {
        {
            put(VALID, new HashSet<>() {
                {
                    add(INVALID);  // 有效 -> 停用
                    add(DELETED);  // 有效 -> 删除
                }
            });
            put(INVALID, new HashSet<>() {
                {
                    add(VALID);    // 停用 -> 有效
                    add(DELETED);  // 停用 -> 删除
                }
            });
            put(DELETED, new HashSet<>() {
                // 删除状态不可迁移
            });
        }
    };
    private CustomerAddressDao customerAddressDao;
    public Customer(Long id, String userName, String name, Byte invalid, Byte be_deleted, CustomerAddressDao customerAddressDao) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.invalid = invalid;
        this.be_deleted = be_deleted;
        this.customerAddressDao = customerAddressDao;
    }
    public void convertInvalid() {
        if(this.invalid.equals(INVALID) ){
            this.invalid = VALID;
        }
        else if(this.invalid.equals(VALID)){
            this.invalid = INVALID;
        }
        else {
            throw new IllegalArgumentException("用户状态错误");
        }
    }

//    public CartItem addToCart(CartItem cartItem,Long price)
//    {
//        cartItem.setCustomerId(this.getId());
//        cartItem.setPrice(price);
//        return cartItem;
//    }

    //新增地址addAddress，创建者
    public CustomerAddress addAddress(CustomerAddress address) {
        address.setCreatorName(this.userName);
        return customerAddressDao.save(address);
    }

    public CustomerAddressDao getCustomerAddressDao() {
        return customerAddressDao;
    }

    public void setCustomerAddressDao(CustomerAddressDao customerAddressDao) {
        this.customerAddressDao = customerAddressDao;
    }


}
