package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.customer.dao.CartItemDao;
import cn.edu.xmu.oomall.customer.mapper.openfeign.OnsaleMapper;
import cn.edu.xmu.oomall.customer.mapper.po.CartItemPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Getter
@Setter
public class OnSale {
    private Long id;
    private Long price;
    private Byte type;
    private Long productId;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private CartItemDao cartItemDao;

    /**
     * 正常
     */
    @JsonIgnore
    public static final Byte NORMAL = 0;
    /**
     * 秒杀
     */
    @JsonIgnore
    public static final Byte SECONDKILL = 1;

    /**
     * 预售
     */
    @JsonIgnore
    public static final Byte GROUPON = 2;

    /**
     * 预售
     */
    @JsonIgnore
    public static final Byte ADVSALE = 3;


    public CartItem addToCart(Long customerId,CartItem newCartItem)
    {
        if (OnSale.ADVSALE.equals(this.type) || OnSale.GROUPON.equals(this.type)) {
            throw new BusinessException(ReturnNo.CUSTOMER_CARTNOTALLOW);
        }
        else {
            // 检查购物车中是否已有该商品
            List<CartItemPo> cartItems = this.cartItemDao.findByCustomerId(customerId);
            Optional<CartItemPo> existCartItem = cartItems.stream()
                    .filter(cartItem -> cartItem.getProductId().equals(productId))
                    .findFirst();
            if (!existCartItem.isPresent()) {  // 检查是否没有找到匹配的 CartItemPo
                // 没有找到，创建新的购物车项
                newCartItem.setCustomerId(customerId);
                newCartItem.setOnsaleId(this.id);
                newCartItem.setPrice(this.price);
                newCartItem.setProductId(this.productId);
                newCartItem.setGmtCreate(LocalDateTime.now());
                return newCartItem;
            }
            else {

                // 找到已存在的购物车项，更新数量
                CartItem cartItemToUpdate = new CartItem();
                BeanUtils.copyProperties(existCartItem.get(), cartItemToUpdate);
                cartItemToUpdate.setQuantity(cartItemToUpdate.getQuantity() + newCartItem.getQuantity());
                return this.cartItemDao.save(cartItemToUpdate);
            }
        }
    }
}
