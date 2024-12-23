package cn.edu.xmu.oomall.customer.controller.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CustomerVo {
    private Long id;
    private String userName;   // 用户名// 密码
    private String name;       // 真实姓名
    private String mobile; //联系电话

}
