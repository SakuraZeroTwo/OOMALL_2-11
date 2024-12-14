package cn.edu.xmu.oomall.customer.dao.bo;

import lombok.Data;

import java.time.LocalDateTime;

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

    public Customer(Long id, String userName, String name, Byte invalid) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.invalid = invalid;
    }
}
