package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.oomall.customer.controller.dto.CartItemDto;
import cn.edu.xmu.oomall.customer.dao.CartItemDao;
import cn.edu.xmu.oomall.customer.mapper.po.CartItemPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@CopyFrom({CartItemPo.class, CartItemDto.class})
public class CartItem extends OOMallObject implements Serializable {
    private Long id;
    private Long customerId;
    private Long productId;
    private Long quantity;
    private Long price;

    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    @Override
    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    // 计算购物车项的小计价格
//    public Long calculateSubtotal() {
//        return this.price * this.quantity;
//    }
    public Long getSubtotal() {
        return quantity * price;
    }
}
