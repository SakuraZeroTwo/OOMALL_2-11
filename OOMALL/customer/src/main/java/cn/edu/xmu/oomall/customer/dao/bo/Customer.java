package cn.edu.xmu.oomall.customer.dao.bo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

@Data
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
            this.beDelete = BEDELETED; // 将 null 设置为 1
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
}
