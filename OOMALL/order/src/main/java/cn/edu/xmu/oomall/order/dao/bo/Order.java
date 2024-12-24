package cn.edu.xmu.oomall.order.dao.bo;


import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
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


    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {

    }

    @Override
    public void setGmtModified(LocalDateTime gmtModified) {

    }
}
