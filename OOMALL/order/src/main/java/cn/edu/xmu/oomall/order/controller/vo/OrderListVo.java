package cn.edu.xmu.oomall.order.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class OrderListVo {
    private Long id;              // 订单ID
    private String orderSn;       // 订单编号
    private int status;           // 订单状态
    private Long originPrice;     // 订单总金额
    private Long finalPrice;      // 实付金额
    private String consignee;     // 收货人姓名（可选）
    private LocalDateTime gmtCreate;  // 订单创建时间
}
