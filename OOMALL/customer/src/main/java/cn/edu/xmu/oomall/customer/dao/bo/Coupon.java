package cn.edu.xmu.oomall.customer.dao.bo;
import lombok.Data;

import java.security.Timestamp;

@Data
public class Coupon {
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
}
