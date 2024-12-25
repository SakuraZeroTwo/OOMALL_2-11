package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.controller.dto.CartResponseData;
import cn.edu.xmu.oomall.customer.dao.CartItemDao;
import cn.edu.xmu.oomall.customer.dao.bo.CartItem;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.dao.bo.OnSale;
import cn.edu.xmu.oomall.customer.mapper.openfeign.OnsaleMapper;
import cn.edu.xmu.oomall.customer.mapper.openfeign.po.OnsalePo;
import cn.edu.xmu.oomall.customer.mapper.po.CartItemPo;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import cn.edu.xmu.oomall.customer.dao.CustomerDao;
@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    @Autowired
    private CartItemDao cartItemDao;
    @Autowired
    private CustomerDao customerDao;

    private final OnsaleMapper onsaleMapper;
    /**
     * 获取购物车列表
     */
    public CartResponseData getCartList(Long customerId) {
        return cartItemDao.getCartList(customerId);
    }

    public CartItem addToCart(Long customerId, CartItem cartItem) {
        InternalReturnObject<OnsalePo> onsaleCheck = onsaleMapper.findOnsaleById(cartItem.getOnsaleId());
        OnSale onsale = new OnSale();
        BeanUtils.copyProperties(onsaleCheck.getData(), onsale);
        onsale.setCartItemDao(cartItemDao);
        return onsale.addToCart(customerId,cartItem);
    }

    public CartItem updateProductInCart(Long cartItemId,Long quantity){
        CartItemPo cartItemPo = this.cartItemDao.findPoById(cartItemId);
        cartItemPo.setQuantity(quantity);
        cartItemPo.setGmtModified(LocalDateTime.now());
        cartItemPo.setGmtCreate(LocalDateTime.now());
        this.cartItemDao.save(cartItemPo);
        CartItem cartItem = new CartItem();
        BeanUtils.copyProperties(cartItemPo, cartItem);
        return cartItem;
    }

    public void deleteProductInCart(Long cartItemId){
        CartItem cartItem = this.cartItemDao.findById(cartItemId);
        this.cartItemDao.deleteProductInCart(cartItemId);
    }
}
