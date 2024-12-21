package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.customer.CustomerApplication;
import cn.edu.xmu.oomall.customer.controller.dto.CartItemDto;
import cn.edu.xmu.oomall.customer.dao.bo.CartItem;
import cn.edu.xmu.oomall.customer.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(classes = CustomerApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final String CUSTOMER_HAS_COUPONS = "/customers/{id}/coupon";

    @Autowired
    private  CartService cartService;

    @Test
    void getCouponList() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_HAS_COUPONS,1001)
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].couponName", is("店铺1-优惠活动3-2件9折"))) // 验证第一个元素的 couponName 是否为 "店铺1-优惠活动3-2件9折"
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].couponName", is("店铺1-优惠活动3-2件9折"))); // 验证第二个元素的 couponName 是否为 "店铺1-优惠活动3-2件9折"

    }
    @Test
    void testUpdateCustomerMessage() throws Exception {
        Long Id = 123L;
        String body = "{\"name\":\"新名字\", \"mobile\":\"1223455\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}", Id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(Id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("新名字")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile", is("1223455")));
    }
    @Test
    void testUpdateCustomerPassword() throws Exception {
        Long Id = 123L;
        String body = "{\"password\":\"32145\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}/password", Id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(Id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", is("32145")));
    }

    //addToCart
    //正常流程测试
    @Test
    void addToCart_ShouldReturnSuccess() throws Exception {
        // Arrange
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(1L);
        cartItemDto.setQuantity(2L);

        CartItem newCartItem = new CartItem();
        newCartItem.setId(1L);
        newCartItem.setProductId(1L);
        newCartItem.setQuantity(2L);

        Mockito.when(cartService.addToCart(Mockito.any(UserDto.class), Mockito.any(CartItem.class)))
                .thenReturn(newCartItem);

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cart")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(cartItemDto))
                        .header("Authorization", "Bearer valid-token"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.quantity", is(2)));
    }

    //cartItemDto输入错误
    @Test
    void addToCart_ShouldFailValidation_WhenDtoInvalid() throws Exception {
        // Arrange
        CartItemDto invalidDto = new CartItemDto(); // 未设置必填字段，如 productId 和 quantity

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cart")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(invalidDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // HTTP 400 状态码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.FIELD_NOTVALID.getErrNo()))) // 验证错误码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("字段不合法"))); // 验证错误消息
    }

    //商品类型不允许加入购物车
    @Test
    void addToCart_ShouldFail_WhenOnSaleNotAllowed() throws Exception {
        // Arrange
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(1L);
        cartItemDto.setQuantity(1L);

        Mockito.when(cartService.addToCart(Mockito.any(UserDto.class), Mockito.any(CartItem.class)))
                .thenThrow(new BusinessException(ReturnNo.CUSTOMER_CARTNOTALLOW, "不允许加入购物车的商品类型"));

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cart")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(cartItemDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CUSTOMER_CARTNOTALLOW.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("不允许加入购物车的商品类型")));
    }

    //用户未登录
    @Test
    void addToCart_ShouldFail_WhenUserNotLoggedIn() throws Exception {
        // Arrange
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(1L);
        cartItemDto.setQuantity(1L);

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cart")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(cartItemDto)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized()) // 验证返回401状态码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.AUTH_NEED_LOGIN.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("请先登录")));
    }

    //购物车已有商品，更新数量
    @Test
    void addToCart_ShouldUpdateQuantity_WhenProductExists() throws Exception {
        // Arrange
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(1L);
        cartItemDto.setQuantity(2L);

        CartItem existingCartItem = new CartItem();
        existingCartItem.setId(1L);
        existingCartItem.setProductId(1L);
        existingCartItem.setQuantity(5L); // 购物车已有5件

        CartItem updatedCartItem = new CartItem();
        updatedCartItem.setId(1L);
        updatedCartItem.setProductId(1L);
        updatedCartItem.setQuantity(7L); // 更新后为7件

        Mockito.when(cartService.addToCart(Mockito.any(UserDto.class), Mockito.any(CartItem.class)))
                .thenReturn(updatedCartItem);

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cart")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(cartItemDto))
                        .header("Authorization", "Bearer valid-token"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.quantity", is(7))); // 验证数量更新
    }

    //数据库异常
    @Test
    void addToCart_ShouldFail_WhenDatabaseError() throws Exception {
        // Arrange
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(1L);
        cartItemDto.setQuantity(2L);

        Mockito.when(cartService.addToCart(Mockito.any(UserDto.class), Mockito.any(CartItem.class)))
                .thenThrow(new RuntimeException("数据库插入失败"));

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cart")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(cartItemDto)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.INTERNAL_SERVER_ERR.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("系统内部错误")));
    }

}
