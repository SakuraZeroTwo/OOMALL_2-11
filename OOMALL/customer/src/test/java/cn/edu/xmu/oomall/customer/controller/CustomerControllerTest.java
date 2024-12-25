package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.customer.CustomerApplication;
import cn.edu.xmu.oomall.customer.controller.dto.CartItemDto;
import cn.edu.xmu.oomall.customer.controller.vo.CartItemVo;
import cn.edu.xmu.oomall.customer.dao.bo.CartItem;
import cn.edu.xmu.oomall.customer.mapper.openfeign.OnsaleMapper;
import cn.edu.xmu.oomall.customer.mapper.openfeign.po.OnsalePo;
import cn.edu.xmu.oomall.customer.service.CartService;
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
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;


import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;


@SpringBootTest(classes = CustomerApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OnsaleMapper onsaleMapper;

    private final String CUSTOMER_HAS_COUPONS = "/customers/{id}/coupon";

    @Test
    void testCreateCustomerSuccess() throws Exception {
        String requestBody = "{\"userName\":\"testUser111\", \"password\":\"testPassword\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));
    }

    @Test
    void testCreateCustomerMissingUserName() throws Exception {
        String requestBody = "{\"password\":\"testPassword\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.FIELD_NOTVALID.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("用户名不能为空")));
    }

    @Test
    void testCreateCustomerMissingPassword() throws Exception {
        String requestBody = "{\"userName\":\"testUser\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.FIELD_NOTVALID.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("密码不能为空")));
    }
    @Test
    void testCreateCustomerNAMEEXIST() throws Exception {
        String requestBody = "{\"userName\":\"699275\", \"password\":\"testPassword\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.FIELD_NOTVALID.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("用户名已存在")));
    }
    @Test
    void getCouponList() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_HAS_COUPONS,1001)
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].couponName", is("店铺1-优惠活动3-2件9折"))) // 验证第一个元素的 couponName 是否为 "店铺1-优惠活动3-2件9折"
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].couponName", is("店铺1-优惠活动3-2件9折"))); // 验证第二个元素的 couponName 是否为 "店铺1-优惠活动3-2件9折"

    }
    @Test
    void getCouponListGivenNotExist() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_HAS_COUPONS,-1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(608)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("登录用户id不存在")));
    }

    @Test
    void testgetCartListSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}/cart", 706)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.items.length()", is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.items[0].productId", is(4163)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.items[1].productId", is(1901)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.items[2].productId", is(3148)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPrice", is(157156)));
    }
    @Test
    void testgetCartList_UserNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}/cart", 30000)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound()) //
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo()))) // 验证错误码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("用户不存在"))); // 验证错误消息
    }
    @Test
    void testgetCartList_CartisEmpty() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}/cart", 9999)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()) //
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo()))) // 验证错误码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("购物车为空"))); // 验证错误消息
    }
    @Test
    void testUpdateCustomerMessage() throws Exception {
        Long Id = 123L;
        String body = "{\"name\":\"新名字\", \"mobile\":\"1223455\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}", Id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));
    }
    @Test
    void testUpdateCustomerPassword() throws Exception {
        Long Id = 123L;
        String body = "{\"password\":\"32145\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}/password", Id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));
    }
    @Test
    void testSetDefaultAddressSuccess() throws Exception {
        Long customerId = 13L;
        Long addressId = 12L;

        // 模拟成功设置默认地址
        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/default-address/{customerId}", customerId)
                        .param("addressId", addressId.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));
    }
    @Test
    void testSetDefaultAddress_UserNotFound() throws Exception {
        Long customerId = 132222L;
        Long addressId = 12L;

        // 模拟成功设置默认地址
        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/default-address/{customerId}", customerId)
                        .param("addressId", addressId.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("顾客不存在")));
    }
    @Test
    void testSetDefaultAddress_AddressNotFound() throws Exception {
        Long customerId = 1233L;
        Long addressId = 12L;
        // 模拟成功设置默认地址
        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/default-address/{customerId}", customerId)
                        .param("addressId", addressId.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("地址不存在")));
    }

    @Test
    void updateAddressInfo() throws Exception {
        String body = "{\"regionId\":2417, \"address\":\"人民南路\", \"consignee\":\"祁同伟\", \"mobile\":\"15970114514\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/addresses/{addressId}",123)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));
    }
    @Test
    void updateAddressInfoGivenAddressNotExist() throws Exception {
        String body = "{\"regionId\":2417, \"address\":\"人民南路\", \"consignee\":\"祁同伟\", \"mobile\":\"15970114514\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/addresses/{addressId}",-1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("地址不存在")));
    }

    @Test
    void testAddToCart() throws Exception {
        OnsalePo onsalePo = new OnsalePo();
        onsalePo.setId(1L);
        onsalePo.setPrice(53295L);
        onsalePo.setType((byte) 0);
        onsalePo.setProductId(1550L);
        InternalReturnObject<OnsalePo> onsaleReturnObject = new InternalReturnObject<>();
        onsaleReturnObject.setData(onsalePo);

        when(onsaleMapper.findOnsaleById(eq(1L))).thenReturn(onsaleReturnObject);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/customers/{id}/cart", 16666L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"onsaleId\": 1, \"quantity\": 5 }"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("创建成功")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(1550)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.customerId", is(16666)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.quantity", is(5)))  // 验证 quantity
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.price", is(53295)));  // 验证 price
    }

    @Test
    void testAddToCartCartItemExist() throws Exception {
        // 创建测试数据
        OnsalePo onsalePo = new OnsalePo();
        onsalePo.setId(749L);
        onsalePo.setPrice(29923L);
        onsalePo.setType((byte) 0);
        onsalePo.setProductId(2298L);

        // 创建返回对象
        InternalReturnObject<OnsalePo> onsaleReturnObject = new InternalReturnObject<>();
        onsaleReturnObject.setData(onsalePo);

        // 确保模拟返回值正确
        when(onsaleMapper.findOnsaleById(eq(749L))).thenReturn(onsaleReturnObject);

        // 执行请求并验证
        this.mockMvc.perform(MockMvcRequestBuilders.post("/customers/{id}/cart", 16666L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"onsaleId\": 749, \"quantity\": 5 }"))  // 确保 onsaleId 传递正确
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("创建成功")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(2298)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.customerId", is(16666)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.quantity", is(11)))  // 验证 quantity
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.price", is(29923)));  // 验证 price
    }


    @Test
    void testAddToCartThrowBusinessException() throws Exception {
        // 设置OnsalePo对象
        OnsalePo onsalePo = new OnsalePo();
        onsalePo.setId(1L);
        onsalePo.setPrice(53295L);
        onsalePo.setType((byte) 3);  // 设置为预售活动类型，触发异常
        onsalePo.setProductId(1550L);

        InternalReturnObject<OnsalePo> onsaleReturnObject = new InternalReturnObject<>();
        onsaleReturnObject.setData(onsalePo);

        // 模拟onsaleMapper.findOnsaleById返回的内容
        when(onsaleMapper.findOnsaleById(eq(1L))).thenReturn(onsaleReturnObject);

        // 发送请求，模拟添加商品到购物车
        this.mockMvc.perform(MockMvcRequestBuilders.post("/customers/{id}/cart", 16666L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"onsaleId\": 1, \"quantity\": 5 }"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())  // 验证返回状态码是 403
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CUSTOMER_CARTNOTALLOW.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("商品(id = 1550)不能加入购物车")));
    }

    @Test
    void AddAddressSuccess() throws Exception {
        Long customerId = 1L;
        String body = "{\"regionId\":2417, \"address\":\"人民南路\", \"consignee\":\"祁同伟\", \"mobile\":\"15970114514\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/customers/address/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("创建成功")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.regionId", is(2417)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.address", is("人民南路")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.consignee", is("祁同伟")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.mobile", is("15970114514")));
    }
    @Test
    void AddAddressFailure_customerNotFound() throws Exception {
        Long customerId = -1L;
        String body = "{\"regionId\":2417, \"address\":\"人民南路\", \"consignee\":\"祁同伟\", \"mobile\":\"15970114514\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/customers/address/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("登录用户id不存在")));
    }

    @Test
    void testUpdateProductInCartSuccess() throws Exception {
        Long cartItemId = 124L;
        Long quantity = 5L;

        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{cartItemId}/cart/update", cartItemId)
                        .param("quantity", quantity.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.quantity", is(quantity.intValue())));
    }

    @Test
    void testGetAddressesByCustomerId() throws Exception {
        // 假设测试的用户 ID 为 2
        Long customerId = 2L;

        // 模拟一个返回的地址列表，检查其中的一些字段
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}/addresses", customerId)
                        .param("page", "1")  // 假设我们查询第一页
                        .param("pageSize", "10")  // 每页显示 10 个地址
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 确认 HTTP 状态码为 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(0))) // 确认错误码为成功
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功"))) // 确认错误信息是 "成功"
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list.length()", is(4))) // 返回的地址列表长度为 4
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.page", is(1))) // 当前页是第 1 页
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageSize", is(10))) // 每页显示 10 个地址

                // 检查返回的第一个地址的字段
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[0].id", is(324))) // 地址 ID
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[0].customerId", is(2))) // customerId
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[0].address", is("广场南路95号"))) // 地址
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[0].consignee", is("赵俊峻"))) // 收货人
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[0].mobile", is("13959298012"))) // 手机号
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[0].beDefault", is(0))) // beDefault 值

                // 检查返回的第二个地址的字段
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[1].id", is(2))) // 地址 ID
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[1].address", is("大同街"))) // 地址
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[1].consignee", is("赵俊峻"))) // 收货人
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[1].mobile", is("13959298012"))) // 手机号

                // 检查返回的第三个地址的字段
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[2].id", is(3))) // 地址 ID
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[2].address", is("大同街"))) // 地址
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[2].consignee", is("赵俊峻"))) // 收货人
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[2].mobile", is("13959298012"))) // 手机号

                // 检查返回的第四个地址的字段
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[3].id", is(45))) // 地址 ID
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[3].address", is("广场"))) // 地址
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[3].consignee", is("赵俊峻"))) // 收货人
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[3].mobile", is("13959298012"))); // 手机号
    }


    @Test
    void testGetAddressesByCustomerId_UserNotFound() throws Exception {
        // 假设顾客ID为-1L，这个顾客ID不存在
        Long customerId = -1L;

        // 执行请求
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}/addresses", customerId)
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())  // 期望返回 404 错误
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(4)))  // 错误码为 4
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("顾客Id不存在")));  // 错误消息为 "顾客Id不存在"
    }

    @Test
    void testDeleteProductInCart_Success() throws Exception {
        // 假设 cartItemId 为一个有效的购物车项ID，例如 124L
        Long cartItemId = 124L;

        // 执行删除请求
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{cartItemId}/cart/delete", cartItemId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())  // 期望返回 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(0)))  // errno 为 0 表示成功
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));  // errmsg 为 "成功"
    }

    @Test
    void testDeleteProductInCart_ItemNotFound() throws Exception {
        // 假设 cartItemId 为一个无效的购物车项ID，例如 -1L（假设这个ID不存在）
        Long cartItemId = -1L;

        // 执行删除请求
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{cartItemId}/cart/delete", cartItemId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())  // 期望返回 404 Not Found
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(4)))  // errno 为 4 表示资源不存在
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("购物车项对象(id=-1)不存在")));  // 错误消息为 "购物车项对象(id=-1)不存在"
    }

}
