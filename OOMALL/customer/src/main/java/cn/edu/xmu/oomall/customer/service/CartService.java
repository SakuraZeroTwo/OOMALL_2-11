package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.oomall.customer.controller.dto.CartResponseData;
import cn.edu.xmu.oomall.customer.controller.dto.ResponseWrapper;
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
import java.util.List;
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
    private CustomerDao customerDao;

    @Autowired
    private CartItemDao cartItemDao;
    // 获取购物车列表
    public ResponseWrapper getCartList(Long customerId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<CartItemPo> cartItemPosPage = cartItemDao.findByCustomerId(customerId, pageable);

        if (cartItemPosPage.isEmpty()) {
            return new ResponseWrapper("Customer not found", null, 1);
        }

        List<CartItem> cartItems = cartItemPosPage.getContent().stream()
                .map(this::convertCartItemPoToBo)
                .collect(Collectors.toList());

        Long totalPrice = cartItems.stream()
                .mapToLong(CartItem::getSubtotal)
                .sum();

        return new ResponseWrapper("success", new CartResponseData(cartItems, totalPrice), 0);
    }

    // 将CartItemPo转换为CartItem
    private CartItem convertCartItemPoToBo(CartItemPo po) {
        CartItem item = new CartItem();
        BeanUtils.copyProperties(po, item);
        return item;
    }

}
