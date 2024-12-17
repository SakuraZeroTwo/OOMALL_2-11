package cn.edu.xmu.oomall.customer.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerDto {
    private int id;
    @NotNull(message = "用户名不能为空")
    private String userName;
    @NotNull(message = "密码不能为空")
    private String password;
    private String name;
    private String mobile;
    private LocalDateTime gmtCreate;   // 创建时间

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "用户名不能为空") String getUserName() {
        return userName;
    }

    public void setUserName(@NotNull(message = "用户名不能为空") String userName) {
        this.userName = userName;
    }

    public @NotNull(message = "密码不能为空") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "密码不能为空") String password) {
        this.password = password;
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