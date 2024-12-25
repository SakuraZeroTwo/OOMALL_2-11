package cn.edu.xmu.oomall.order.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.oomall.order.OrdersApplication;
import cn.edu.xmu.oomall.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = OrdersApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private OrderService orderService;
    @Test
    void testgetShopOrderByIdSuccess() throws Exception{
        Long shopId = 1L;
        Long orderId = 1L;
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImRlcGFydElkIjowLCJ0b2tlbklkIjoiMjAyNDEyMjUxOTU1NTgzRVdBIiwidXNlck5hbWUiOiIxMzA4OGFkbWluIiwidXNlckxldmVsIjoxLCJpc3MiOiJPT0FEIiwic3ViIjoidGhpcyBpcyBhIHRva2VuIiwiYXVkIjoiTUlOSUFQUCIsImlhdCI6MTczNTEyNzc1OCwiZXhwIjoxNzM1MTMxMzU4fQ.fUnFWiicK07l7lCgvFA-rVX3e5b0bJ-k3YVYw9bddvs";
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/shops/{shopId}/orders/{orderId}",shopId,orderId)
                        //.header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(orderId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderSn").value("2016102361242"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.consignee").value("赵永波"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.address").value("人民北路"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.mobile").value("13959235540"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message").value("等待所有商品备齐后再发"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.expressFee").value(545))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.discountPrice").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.originPrice").value(799121))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(300))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.creatorName").value("赵永波"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtCreate").value("2016-10-23T12:07:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtModified").doesNotExist());
    }
    @Test
    void testgetShopOrderById_OrderNotFound() throws Exception{
        Long shopId = 1L;
        Long orderId = 10099L;
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/shops/{shopId}/orders/{orderId}",shopId,orderId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("订单不存在或不属于该商户")));
    }
    @Test
    void testgetShopOrdersSuccess() throws Exception {
        Long shopId = 1L;
        // 执行GET请求
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/shops/{shopId}/orders", shopId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 验证状态码为200
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo()))) // 验证返回的错误码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功"))) // 验证返回的消息
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray()) // 验证返回的数据是一个数组
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1)) // 验证第一个订单的id
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].orderSn").value("2016102361242")) // 验证第一个订单的订单号
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].status").value(300)) // 验证第一个订单的状态
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].originPrice").value(799121)) // 验证第一个订单的原价
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].consignee").value("赵永波")) // 验证第一个订单的收货人
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].gmtCreate").value("2016-10-23T12:07:00")) // 验证第一个订单的创建时间
        ;
    }
    @Test
    void testgetShopOrders_ShopIdNotExist() throws Exception {
        Long shopId = 11L;
        // 执行GET请求
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/shops/{shopId}/orders", shopId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("商铺对象(id=11)不存在")));

        ;
    }
    @Test
    void testDeleteShopOrderSuccess() throws Exception {
        Long customerId = 2L;
        Long shopId = 1L;
        Long orderId = 2L;

        // 创建请求体
        String requestJson = """
            {
                "customerId": %d,
                "shopId": %d,
                "orderId": %d
            }
            """.formatted(customerId, shopId, orderId);

        // 执行DELETE请求
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/deleteShopOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 验证状态码为200
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo()))) // 验证返回的错误码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("订单删除成功"))); // 验证返回的消息
    }
    @Test
    void testDeleteShopOrder_1() throws Exception {
        Long customerId = 1L;
        Long shopId = 1L;
        Long orderId = 40000L;

        // 创建请求体
        String requestJson = """
            {
                "customerId": %d,
                "shopId": %d,
                "orderId": %d
            }
            """.formatted(customerId, shopId, orderId);

        // 执行DELETE请求
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/deleteShopOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("订单不存在")));
    }
    @Test
    void testDeleteShopOrder_2() throws Exception {
        Long customerId = 1L;
        Long shopId = 1L;
        Long orderId = 1L;

        // 创建请求体
        String requestJson = """
            {
                "customerId": %d,
                "shopId": %d,
                "orderId": %d
            }
            """.formatted(customerId, shopId, orderId);

        // 执行DELETE请求
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/deleteShopOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 验证状态码为200
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.STATENOTALLOW.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("订单状态不允许删除")));
    }
    @Test
    void testDeleteShopOrder_3() throws Exception {
        Long customerId = 2L;
        Long shopId = 1L;
        Long orderId = 4L;

        // 创建请求体
        String requestJson = """
            {
                "customerId": %d,
                "shopId": %d,
                "orderId": %d
            }
            """.formatted(customerId, shopId, orderId);

        // 执行DELETE请求
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/deleteShopOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound()) // 验证状态码为200
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("该订单不属于对应商户或顾客")));
    }
    @Test
    void testgetOrderByIdSuccess() throws Exception {
        Long orderId = 1L;
        // 执行GET请求
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1)) // 验证订单的id
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderSn").value("2016102361242")) // 验证订单的订单号
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.consignee").value("赵永波")) // 验证订单的收货人
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.address").value("人民北路")) // 验证订单的地址
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.mobile").value("13959235540")) // 验证订单的手机号
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message").value("等待所有商品备齐后再发")) // 验证订单的留言
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.expressFee").value(545)) // 验证订单的快递费
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.discountPrice").value(0)) // 验证订单的优惠价
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.originPrice").value(799121)) // 验证订单的原价
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(300)) // 验证订单的状态
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.creatorName").value("赵永波")) // 验证订单的创建者
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtCreate").value("2016-10-23T12:07:00")) // 验证订单的创建时间
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtModified").doesNotExist()); // 验证订单的修改时间为null
        ;
    }
    @Test
    void testgetOrderById_IdNotFound() throws Exception {
        Long orderId = 40000L;
        // 执行GET请求
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("订单不存在")))

        ;
    }
    @Test
    void testSendOrder() throws Exception {
        // 执行 PUT 请求
        mockMvc.perform(MockMvcRequestBuilders.put("/orders/shops/{shopId}/orders/{id}/send", 1L, 200L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"weight\": 10 }"))  // 仅设置 weight
                .andExpect(MockMvcResultMatchers.status().isOk()) // 验证返回的状态码是200（OK）
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));
    }
    @Test
    void testSendOrderShopNotAllow() throws Exception {
        // 执行 PUT 请求
        mockMvc.perform(MockMvcRequestBuilders.put("/orders/shops/{shopId}/orders/{id}/send", 2L, 200L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"weight\": 10 }"))  // 仅设置 weight
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno").value(ReturnNo.RESOURCE_ID_OUTSCOPE.getErrNo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg").value("订单对象(id=200)超出商铺（id = 2）的操作范围"));  // 验证错误消息
    }
    @Test
    void testSendOrderStatusNotAllow() throws Exception {
        // 执行 PUT 请求
        mockMvc.perform(MockMvcRequestBuilders.put("/orders/shops/{shopId}/orders/{id}/send", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"weight\": 10 }"))  // 仅设置 weight
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno").value(ReturnNo.STATENOTALLOW.getErrNo()))  // 验证返回的错误代码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg").value("订单对象（id=1）非确认状态禁止此操作"));  // 验证错误消息
    }
    
    @Test
    void changeCustomerOrder() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.put("/orders/13")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"customerId\": 7, \"consignee\": \"赵良缘\", \"regionId\": 264962, \"address\": \"南火车站\", \"mobile\": \"13959221894\", \"message\": \"等待所有商品备齐后再发\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));
    }

    @Test
    void changeCustomerOrderUnMatchedCustomer() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.put("/orders/13")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customerId\": 12, \"consignee\": \"赵良缘\", \"regionId\": 264962, \"address\": \"南火车站\", \"mobile\": \"13959221894\", \"message\": \"等待所有商品备齐后再发\" }"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.AUTH_NO_RIGHT.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("非本用户订单")));
    }
}
