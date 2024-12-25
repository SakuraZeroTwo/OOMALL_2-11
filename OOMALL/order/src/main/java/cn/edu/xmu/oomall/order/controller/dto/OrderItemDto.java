package cn.edu.xmu.oomall.order.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OrderItemDto {
    private Long orderItemId;
    private Long onsaleId;
    private Long orderId;
    private Byte commented;
}
