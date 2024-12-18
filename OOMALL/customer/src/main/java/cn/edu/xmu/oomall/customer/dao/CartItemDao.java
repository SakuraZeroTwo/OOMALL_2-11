package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.oomall.customer.dao.bo.CartItem;
import cn.edu.xmu.oomall.customer.mapper.CartItemPoMapper;
import cn.edu.xmu.oomall.customer.mapper.po.CartItemPo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class CartItemDao {
    private final static Logger logger = LoggerFactory.getLogger(CartItemDao.class);
    private final CartItemPoMapper cartItemPoMapper;

    public void save(CartItem cartItem) {
        CartItemPo cartItemPo = new CartItemPo();
        BeanUtils.copyProperties(cartItem, cartItemPo);
        cartItemPo.setGmtCreate(LocalDateTime.now());
        cartItemPo.setGmtModified(LocalDateTime.now());
        cartItemPo.setCreatorId(cartItem.getCustomerId());
        cartItemPo.setCreatorName(cartItemPo.getCreatorName());
        cartItemPoMapper.save(cartItemPo);
    }
    public CartItem findCartItemByCustomerIdAndProductId(Long customerId, Long productId) {
        CartItemPo cartItemPo = cartItemPoMapper.findByCustomerIdAndProductId(customerId, productId);
        if (cartItemPo != null) {
            CartItem cartItem = new CartItem();
            BeanUtils.copyProperties(cartItemPo, cartItem);
            return cartItem;
        }
        return null;
    }
}
