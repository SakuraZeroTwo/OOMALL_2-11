package cn.edu.xmu.oomall.order.mapper.po;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long onsaleId;
    private int quantity;
    private Long price;
    private Long discountPrice;
    private Long point;
    private String name;
    private Long activityId;
    private Long couponId;
    private Byte commented;
    private Long creatorId;
    private String creatorName;
    private Long modifierId;
    private String modifierName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
