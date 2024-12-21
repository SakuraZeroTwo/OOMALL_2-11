package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.oomall.customer.dao.CartItemDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.edu.xmu.oomall.customer.dao.bo.CartItem;

    @Service
    @Transactional(propagation = Propagation.REQUIRED)
    @RequiredArgsConstructor
    @Slf4j
    public class CartItemService {
        private final CartItemDao cartItemDao;
        public void updateProductInCart(CartItem cartItem,Long customerId){
            log.debug("updateProductInCart: customerId {},cartItem {}",customerId,cartItem);
            if (cartItem.getProductId() == null || cartItem.getProductId() <= 0) {
                throw new IllegalArgumentException("Invalid product ID.");
            }

            if (cartItem.getQuantity() == null || cartItem.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero.");
            }
            CartItem existingCartItem = cartItemDao.findCartItemByCustomerIdAndProductId(customerId, cartItem.getProductId());

            if (existingCartItem != null) {
                // 如果已存在，则更新数量
                existingCartItem.setQuantity(cartItem.getQuantity());
                cartItemDao.save(existingCartItem);
                log.debug("Updated product {} in the cart for customer {}.", cartItem.getProductId(), customerId);
            } else {
                cartItem.setCustomerId(customerId);
                cartItemDao.save(cartItem);
                log.debug("Added new product {} to the cart for customer {}.", cartItem.getProductId(), customerId);
            }
        }

        public void delProductInCartById(Long cartItemID){
            this.cartItemDao.delProductInCartById(cartItemID);
        }

//        public void delAllProductInCartByCustomerId(Long customerId){
//
//        }

}
