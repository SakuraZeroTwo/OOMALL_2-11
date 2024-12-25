package cn.edu.xmu.oomall.order.dao.bo;


import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.oomall.order.controller.dto.OrderDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@ToString(callSuper = true)
@NoArgsConstructor
@Slf4j
@Data
public class Order extends OOMallObject{

    private Long customerId;
    private Long shopId;
    private String orderSn;
    private Long pid;
    private String consignee;
    private Long regionId;
    private String address;
    private String mobile;
    private String message;
    private Long packageId;
    private Long expressFee;
    private Long discountPrice;
    private Long originPrice;
    private Long point;
    private int status;

    public Order updateOrderInfo(OrderDto orderDto) {
        if(this.getCustomerId() != orderDto.getCustomerId()){
            throw new BusinessException(ReturnNo.AUTH_NO_RIGHT,"非本用户订单");
        }
        if(status == 101 || status == 102 || status == 201 || status == 202 || status == 203){
            if(orderDto.getRegionId() != null){
                this.setRegionId(orderDto.getRegionId());
            }
            if(orderDto.getAddress() != null){
                this.setAddress(orderDto.getAddress());
            }
            if(orderDto.getMobile() != null){
                this.setMobile(orderDto.getMobile());
            }
            if(orderDto.getConsignee() != null){
                this.setConsignee(orderDto.getConsignee());
            }
        }
        else{
            throw new BusinessException(ReturnNo.STATENOTALLOW,"订单已发出，修改操作被禁止");
        }
        return this;
    }

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
