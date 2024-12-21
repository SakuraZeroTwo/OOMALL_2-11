package cn.edu.xmu.oomall.customer.controller.vo;

import java.time.LocalDateTime;

public class CustomerVo {
    private Long id;
    private String userName;   // 用户名// 密码
    private String name;       // 真实姓名
    private String mobile; //联系电话

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
