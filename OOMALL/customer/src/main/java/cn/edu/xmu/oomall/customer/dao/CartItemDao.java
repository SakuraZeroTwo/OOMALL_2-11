package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.config.OpenFeignConfig;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.customer.controller.dto.CartResponseData;
import cn.edu.xmu.oomall.customer.dao.bo.CartItem;
import cn.edu.xmu.oomall.customer.mapper.CartItemPoMapper;
import cn.edu.xmu.oomall.customer.mapper.po.CartItemPo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CartItemDao {
    @Autowired
    private CartItemPoMapper cartItemPoMapper;

    public List<CartItemPo> findByCustomerId(Long customerId) {
        return cartItemPoMapper.findByCustomerId(customerId);
    }

    /**
     * 获取购物车列表
     */
    public CartResponseData getCartList(Long customerId){
        List<CartItemPo> cartItemPo = this.findByCustomerId(customerId);
        if (cartItemPo.isEmpty()) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST,"用户不存在");
        }

        List<CartItem> cartItems = cartItemPo.stream()
                .map(this::convertCartItemPoToBo)
                .collect(Collectors.toList());

        Long totalPrice = cartItems.stream()
                .mapToLong(CartItem::getSubtotal)
                .sum();
        return new CartResponseData(cartItems,totalPrice);
    }
    private CartItem convertCartItemPoToBo(CartItemPo po) {
        CartItem item = new CartItem();
        BeanUtils.copyProperties(po, item);
        return item;
    }

    public CartItem findById(Long id) {
        // 使用 JPA 方法获取 CartItemPo
        Optional<CartItemPo> optionalCartItemPo = cartItemPoMapper.findById(id);
        if (optionalCartItemPo.isPresent()) {
            // 转换 CartItemPo 为 CartItem
            return convertCartItemPoToBo(optionalCartItemPo.get());
        } else {
            // 如果未找到，抛出异常
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "购物车项", id));
        }
    }

    public void save(CartItemPo cartItemPo) {
        cartItemPoMapper.save(cartItemPo);
    }

    public CartItemPo findPoById(Long id) {
        Optional<CartItemPo> optionalCartItemPo = cartItemPoMapper.findById(id);
        if (optionalCartItemPo.isEmpty()) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "购物车项", id));
        }
        else {
            return optionalCartItemPo.get();
        }
    }

    public void deleteProductInCart(Long cartItemId){
        this.cartItemPoMapper.deleteById(cartItemId);
    }

}