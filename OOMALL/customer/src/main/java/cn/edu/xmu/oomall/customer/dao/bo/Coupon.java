package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.customer.mapper.po.CouponPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.security.Timestamp;
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({CouponPo.class})
@Data
public class Coupon implements Serializable {
    @ToString.Exclude
    @JsonIgnore
    private final static Logger logger = LoggerFactory.getLogger(Coupon.class);

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
