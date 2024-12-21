package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.controller.dto.CartResponseData;
import cn.edu.xmu.oomall.customer.controller.dto.ResponseWrapper;
import cn.edu.xmu.oomall.customer.dao.CartItemDao;
import cn.edu.xmu.oomall.customer.dao.bo.CartItem;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.mapper.po.CartItemPo;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import cn.edu.xmu.oomall.customer.dao.CustomerDao;
import cn.edu.xmu.oomall.product.dao.onsale.OnSaleDao;
import cn.edu.xmu.oomall.product.dao.bo.OnSale;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    @Autowired
    private CartItemDao cartItemDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private OnSaleDao onSaleDao;
    /**
     * 获取购物车列表
     */
    public ResponseWrapper getCartList(Long customerId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<CartItemPo> cartItemPosPage = cartItemDao.findByCustomerId(customerId, pageable);

        if (cartItemPosPage.isEmpty()) {
//            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST,"用户不存在！", null);
            return new ResponseWrapper("false", null ,2);
        }

        List<CartItem> cartItems = cartItemPosPage.getContent().stream()
                .map(this::convertCartItemPoToBo)
                .collect(Collectors.toList());

        Long totalPrice = cartItems.stream()
                .mapToLong(CartItem::getSubtotal)
                .sum();
//        return new ReturnObject(ReturnNo.OK, "success",new CartResponseData(cartItems, totalPrice));
        return new ResponseWrapper("success", new CartResponseData(cartItems, totalPrice) ,1);
    }

    // 将CartItemPo转换为CartItem
    private CartItem convertCartItemPoToBo(CartItemPo po) {
        CartItem item = new CartItem();
        BeanUtils.copyProperties(po, item);
        return item;
    }

    public CartItem addToCart(UserDto user, CartItem cartItem)
    {
        OnSale onSale = this.onSaleDao.findLatestValidOnsaleByProductId(cartItem.getProductId());
        assert (onSale!=null):"no related onsale.";
        if(OnSale.ADVSALE.equals(onSale.getType())||OnSale.GROUPON.equals(onSale.getType()))
        {
            throw new BusinessException(ReturnNo.CUSTOMER_CARTNOTALLOW, String.format(ReturnNo.CUSTOMER_CARTNOTALLOW.getMessage(), cartItem.getCustomerId()));
        }
        else
        {
            CartItem existCartItem = this.cartItemDao.findByProductId(user.getId(),cartItem.getProductId());
            if(Objects.isNull(existCartItem)) //如果existCartItem是空，则需要重新创建一个
            {
                Optional<Customer> customer = customerDao.findById(user.getId());
                customer.ifPresent(cust -> {
                    CartItem newCartItem = cust.addToCart(cartItem, onSale.getPrice());
                    cartItemDao.insert(user, newCartItem);
                });
            }
            else
            {
                existCartItem.setQuantity(existCartItem.getQuantity()+cartItem.getQuantity()); //如果购物车已有商品则数量增加
                return this.cartItemDao.update(user,existCartItem);
            }
        }
        return null;
    }
}
