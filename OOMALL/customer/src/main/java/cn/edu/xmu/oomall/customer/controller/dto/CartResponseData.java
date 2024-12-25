package cn.edu.xmu.oomall.customer.controller.dto;

import cn.edu.xmu.oomall.customer.dao.bo.CartItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class CartResponseData {
    private List<CartItem> items;
    private Long totalPrice;

    public CartResponseData(List<CartItem> items, Long totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }

//    @Override
//    public String toString() {
//        return "CartResponseData{" +
//                "items=" + items +
//                ", totalPrice=" + totalPrice +
//                '}';
//    }
}