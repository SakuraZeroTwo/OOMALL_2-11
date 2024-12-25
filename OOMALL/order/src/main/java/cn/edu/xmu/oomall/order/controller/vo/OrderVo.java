package cn.edu.xmu.oomall.order.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class OrderVo {
    private Long id;
    private String orderSn;
    private String consignee;
    private String address;
    private String mobile;
    private String message;
    private Long expressFee;
    private Long discountPrice;
    private Long originPrice;
    private int status;
    private String creatorName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

}
