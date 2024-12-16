package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.oomall.customer.mapper.po.CartItemPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long id;
    private Long customerId;
    private Long productId;
    private Long quantity;
    private Long price;



    // 计算购物车项的小计价格
//    public Long calculateSubtotal() {
//        return this.price * this.quantity;
//    }
    public Long getSubtotal() {
        return quantity * price;
    }
}
