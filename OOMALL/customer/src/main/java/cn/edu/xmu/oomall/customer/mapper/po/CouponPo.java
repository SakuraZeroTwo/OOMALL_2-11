package cn.edu.xmu.oomall.customer.mapper.po;

import io.lettuce.core.StrAlgoArgs;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;


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
    private LocalDateTime beginTime;
    private LocalDateTime endTime;

    private Short used;
    private Long creatorId;
    private String creatorName;
    private Long modifierId;
    private String modifierName;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

//    @Override
//    public String toString() {
//        return "CouponPo{" +
//                "id=" + id +
//                ", couponSn='" + couponSn + '\'' +
//                ", couponName='" + couponName + '\'' +
//                ", customerId=" + customerId +
//                ", activityId=" + activityId +
//                ", beginTime=" + beginTime +
//                ", endTime=" + endTime +
//                ", used=" + used +
//                ", creatorId=" + creatorId +
//                ", creatorName='" + creatorName + '\'' +
//                ", modifierId=" + modifierId +
//                ", modifierName='" + modifierName + '\'' +
//                ", gmtCreate=" + gmtCreate +
//                ", gmtModified=" + gmtModified +
//                '}';
//    }

}
