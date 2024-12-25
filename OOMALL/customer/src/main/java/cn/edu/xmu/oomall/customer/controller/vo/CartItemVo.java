package cn.edu.xmu.oomall.customer.controller.vo;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CartItemVo {
    private Long customerId;
    private Long productId;
    private Long quantity;
    private Long price;
    private LocalDateTime gmtCreate;
}
