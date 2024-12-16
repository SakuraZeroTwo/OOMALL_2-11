package cn.edu.xmu.oomall.customer.controller.dto;

import cn.edu.xmu.oomall.customer.dao.bo.CartItem;

import java.util.List;

public class CartResponseData {
    private List<CartItem> items;
    private Long totalPrice;

    public CartResponseData(List<CartItem> items, Long totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CartResponseData{" +
                "items=" + items +
                ", totalPrice=" + totalPrice +
                '}';
    }
}