package cn.edu.xmu.oomall.order.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OrderDto {
    private Long customerId;
    private String consignee;
    private Long regionId;
    private String address;
    private String mobile;
    private String message;
}
