package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.dao.bo.CartItem;
import cn.edu.xmu.javaee.core.config.OpenFeignConfig;
import cn.edu.xmu.oomall.customer.controller.dto.CartResponseData;
import cn.edu.xmu.oomall.customer.dao.bo.CartItem;
import cn.edu.xmu.oomall.customer.mapper.CartItemPoMapper;
import cn.edu.xmu.oomall.customer.mapper.po.CartItemPo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Objects;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;

import static cn.edu.xmu.javaee.core.model.Constants.IDNOTEXIST;

@Repository
@RequiredArgsConstructor
public class CartItemDao {
    private final static Logger logger = LoggerFactory.getLogger(CartItemDao.class);
    private final static String KEY = "C%d";
    private final static String CUSTOMER_PRO_KEY = "C%dP%d";


    @Value("${oomall.customer.timeout}")
    private int timeout;

    @Autowired
    private CartItemPoMapper cartItemPoMapper;

    private final RedisUtil redisUtil;
    // 查询指定 customerId 的购物车项
    public Optional<CartItemPo> findByCustomerId(Long customerId) {
        return cartItemPoMapper.findBycustomerId(customerId);
    }

    /**
     * 获取购物车列表
     */
    public CartResponseData getCartList(Long customerId){
        Optional<CartItemPo> cartItemPo = this.findByCustomerId(customerId);
        if (cartItemPo.isEmpty()) {
            throw new RuntimeException("User not found!");
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

    public CartItem findByProductId(Long customerId,Long productId)
    {
        assert (!Objects.isNull(productId)) : "id can not be null"; //如果条件为假则抛错

        String key = String.format(CUSTOMER_PRO_KEY, customerId, productId);
        CartItem bo = (CartItem) redisUtil.get(CUSTOMER_PRO_KEY); //从缓存中获取该顾客该商品的cartItem
        if (Objects.isNull(bo)) {
            // 缓存中没有
            CartItemPo po = cartItemPoMapper.findByCustomerIdAndProductId(customerId,productId); //在数据库中查找
            bo = CloneFactory.copy(new CartItem(), po);
            redisUtil.set(key, bo, timeout);
            return bo;
        }
        else
        {
            logger.debug("findById: hit in redis product_key = {}, cartItem = {}", key, bo);
            return bo;
        }
    }

    public CartItem insert (UserDto user, CartItem bo) throws RuntimeException
    {
        bo.setId(null);
        bo.setCreator(user); //记录操作者信息
        bo.setGmtCreate(LocalDateTime.now()); //设置操作时间
        CartItemPo po = CloneFactory.copy(new CartItemPo(), bo); //进行bo和po的转换
        logger.debug("save: po = {}", po);
        po = cartItemPoMapper.save(po);
        bo.setId(po.getId());

        String key = String.format(CUSTOMER_PRO_KEY, bo.getCustomerId(), bo.getProductId());
        redisUtil.set(key, bo, timeout); //新加入购物车商品置入缓存
        return bo;
    }

    public CartItem update (UserDto user, CartItem bo) throws RuntimeException
    {
        bo.setModifier(user);
        bo.setGmtModified(LocalDateTime.now());
        CartItemPo po = CloneFactory.copy(new CartItemPo(), bo);
        logger.debug("save: po = {}", po);
        CartItemPo updatePo = cartItemPoMapper.save(po);
        if (IDNOTEXIST.equals(updatePo.getId())) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "购物车商品", bo.getId()));
        } //更新需要检查更新结果
        String key = String.format(CUSTOMER_PRO_KEY, bo.getCustomerId(), bo.getProductId());
        redisUtil.set(key, bo, timeout);
        return bo;
    }
}