package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.oomall.customer.dao.AddressDao;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Customer {
    private Long id;
    private String userName;
    private String password;
    private String name;
    private Byte invalid;
    private Byte beDelete;
    private String mobile;
    private Integer point;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public Customer() {}
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

    @Setter
    @ToString.Exclude
    @JsonIgnore
    private AddressDao addressDao;

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

    @Autowired
    public Customer(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    /**
     * 是否允许状态迁移
     * @param newStatus 迁移去的状态
     */
    public boolean allowTransitInvalid(Byte newStatus) {
        boolean ret = false;
        assert (!Objects.isNull(this.invalid)) : String.format("对象(id=%d)的状态为null", this.getId());
        Set<Byte> allowStatusSet = toInvalid.get(this.invalid);
        if (!Objects.isNull(allowStatusSet)) {
            ret = allowStatusSet.contains(newStatus);
        }
        return ret;
    }
    public Customer(Long id, String userName, String name, Byte invalid) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.invalid = invalid;
    }
    public void setBeDelete() {
            this.beDelete = BEDELETED;
    }
    public void convertInvalid() {
        if(this.invalid.equals(INVALID) ){
            this.invalid = VALID;
        }
        else if(this.invalid.equals(VALID)){
            this.invalid = INVALID;
        }
        else {

        }
    }

    public Address addAddress(Address address) {
        address.setConsignee(this.name);
        address.setMobile(this.mobile);
        address.setCreatorId(this.id);
        address.setCreatorName(this.userName);
        return this.addressDao.insert(address);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getInvalid() {
        return invalid;
    }

    public void setInvalid(Byte invalid) {
        this.invalid = invalid;
    }

    public Byte getBeDelete() {
        return beDelete;
    }

    public void setBeDelete(Byte beDelete) {
        this.beDelete = beDelete;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}
