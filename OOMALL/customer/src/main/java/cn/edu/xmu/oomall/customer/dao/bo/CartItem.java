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

    public Long getSubtotal() {
        return quantity * price;
    }
}
