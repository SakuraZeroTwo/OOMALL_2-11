package cn.edu.xmu.oomall.customer.dao.bo;

import java.util.ArrayList;
import java.util.List;

public class CartResponse {
    private List<CartItem> items;
    private Long totalPrice;
    private int totalPages;
    private boolean hasNext;
    private String message;

    public CartResponse(String message, List<CartItem> items, Long totalPrice, int totalPages, boolean hasNext) {
        this.message = message;
        this.items = items;
        this.totalPrice = totalPrice;
        this.totalPages = totalPages;
        this.hasNext = hasNext;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}