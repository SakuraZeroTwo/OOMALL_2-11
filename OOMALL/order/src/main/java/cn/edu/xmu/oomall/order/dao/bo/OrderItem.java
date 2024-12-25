package cn.edu.xmu.oomall.order.dao.bo;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.oomall.order.controller.dto.OrderDto;
import lombok.*;

import lombok.extern.slf4j.Slf4j;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@ToString(callSuper = true)
@NoArgsConstructor
@Slf4j
@Data
public class OrderItem extends OOMallObject {

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

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}
