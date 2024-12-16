package cn.edu.xmu.oomall.customer.mapper.po;

import io.lettuce.core.StrAlgoArgs;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import java.security.Timestamp;
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

    private String couponSn;
    private String couponName;

    private Long customerId;
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

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getCouponSn() {return couponSn;}

    public void setCouponSn(String couponSn) {this.couponSn = couponSn;}

    public String getCouponName() {return couponName;}

    public void setCouponName(String couponName) {this.couponName = couponName;}

    public Long getCustomerId() {return customerId;}

    public void setCustomerId(Long customerId) {this.customerId = customerId;}

    public Long getActivityId() {return activityId;}

    public void setActivityId(Long activityId) {this.activityId = activityId;}

    public Timestamp getBeginTime() {return beginTime;}

    public void setBeginTime(Timestamp beginTime) {this.beginTime = beginTime;}

    public Timestamp getEndTime() {return endTime;}

    public void setEndTime(Timestamp endTime) {this.endTime = endTime;}

    public Short getUsed() {return used;}

    public void setUsed(Short used) {this.used = used;}

    public Long getCreatorId() {return creatorId;}

    public void setCreatorId(Long creatorId) {this.creatorId = creatorId;}

    public String getCreatorName() {return creatorName;}

    public void setCreatorName(String creatorName) {this.creatorName = creatorName;}

    public Long getModifierId() {return modifierId;}

    public void setModifierId(Long modifierId) {this.modifierId = modifierId;}

    public String getModifierName() {return modifierName;}

    public void setModifierName(String modifierName) {this.modifierName = modifierName;}

    public Timestamp getGmtCreate() {return gmtCreate;}

    public void setGmtCreate(Timestamp gmtCreate) {this.gmtCreate = gmtCreate;}

    public Timestamp getGmtModified() {return gmtModified;}

    public void setGmtModified(Timestamp gmtModified) {this.gmtModified = gmtModified;}

    @Override
    public String toString() {
        return "CouponPo{" +
                "id=" + id +
                ", couponSn='" + couponSn + '\'' +
                ", couponName='" + couponName + '\'' +
                ", customerId=" + customerId +
                ", activityId=" + activityId +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", used=" + used +
                ", creatorId=" + creatorId +
                ", creatorName='" + creatorName + '\'' +
                ", modifierId=" + modifierId +
                ", modifierName='" + modifierName + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
