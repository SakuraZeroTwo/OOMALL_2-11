package cn.edu.xmu.oomall.customer.mapper.po;

import io.lettuce.core.StrAlgoArgs;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import java.security.Timestamp;
@Setter
@Getter
@Entity
@Table(name = "customer_coupon")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CouponPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_sn")
    private String couponSn;

    @Column(name = "name")
    private String couponName;

    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "activity_id")
    private Long activityId;


    private Timestamp beginTime;
    private Timestamp endTime;

    private Short used;
    private Long creatorId;
    private String creatorName;
    private Long modifierId;
    private String modifierName;

    private Timestamp gmtCreate;
    private Timestamp gmtModified;

}
