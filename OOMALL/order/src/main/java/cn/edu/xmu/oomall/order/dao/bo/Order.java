package cn.edu.xmu.oomall.order.dao.bo;


import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.oomall.order.controller.dto.OrderDto;
import cn.edu.xmu.oomall.order.dao.OrderDao;
import cn.edu.xmu.oomall.order.service.ExpressService;
import cn.edu.xmu.oomall.order.service.RefundService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Arrays;

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
    private Long weight;
    private Long point;
    private int status;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private OrderDao orderDao;

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
    /**
     * 检查订单是否可以被删除
     * @return 删除结果
     */
    public boolean canDelete(Long customerId, Long shopId) {
        if (!this.getCustomerId().equals(customerId) || !this.getShopId().equals(shopId)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, "该订单不属于对应商户或顾客");
        }
        return Arrays.asList(101, 102, 201, 202, 203).contains(this.getStatus());
    }

    /**
     * 删除订单，如果状态不允许，则不进行任何操作
     */
    public void deleteShopOrder(Long customerId, Long shopId) {
        if (canDelete(customerId, shopId)) {
            this.setStatus(999); // 设999是删除状态
        } else {
            throw new BusinessException(ReturnNo.STATENOTALLOW, "订单状态不允许删除");
        }
    }

    public void sendOrder(Long shopId,Order order)
    {
        if(shopId!=this.shopId)
        {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "订单", id, shopId));
        }
        if(this.status==203)
        {
            this.setWeight(order.getWeight());
            orderDao.updateOrder(this);
        }
        else {
            throw new BusinessException(ReturnNo.STATENOTALLOW,String.format(ReturnNo.STATENOTALLOW.getMessage(), "订单", id, "非确认"));
        }
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
