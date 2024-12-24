package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.oomall.customer.controller.dto.CartResponseData;
import cn.edu.xmu.oomall.customer.dao.CartItemDao;
import cn.edu.xmu.oomall.customer.dao.bo.CartItem;
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
    /**
     * 获取购物车列表
     */
    public CartResponseData getCartList(Long customerId) {
        return cartItemDao.getCartList(customerId);
    }


//    public CartItem addToCart(UserDto user, CartItem cartItem)
//    {
//        OnSale onSale = this.onSaleDao.findLatestValidOnsaleByProductId(cartItem.getProductId());
//        assert (onSale!=null):"no related onsale.";
//        if(OnSale.ADVSALE.equals(onSale.getType())||OnSale.GROUPON.equals(onSale.getType()))
//        {
//            throw new BusinessException(ReturnNo.CUSTOMER_CARTNOTALLOW, String.format(ReturnNo.CUSTOMER_CARTNOTALLOW.getMessage(), cartItem.getProductId()));
//        }
//        else
//        {
//            CartItem existCartItem = this.cartItemDao.findByProductId(user.getId(),cartItem.getProductId());
//            if(Objects.isNull(existCartItem)) //如果existCartItem是空，则需要重新创建一个
//            {
//                Optional<Customer> customer = customerDao.findById(user.getId());
//                customer.ifPresent(cust -> {
//                    CartItem newCartItem = cust.addToCart(cartItem, onSale.getPrice());
//                    cartItemDao.insert(user, newCartItem);
//                });
//            }
//            else
//            {
//                existCartItem.setQuantity(existCartItem.getQuantity()+cartItem.getQuantity()); //如果购物车已有商品则数量增加
//                return this.cartItemDao.update(user,existCartItem);
//            }
//        }
//        return null;
//    }
}
