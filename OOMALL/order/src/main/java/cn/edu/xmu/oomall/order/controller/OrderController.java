package cn.edu.xmu.oomall.order.controller;

import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.order.OrdersApplication;
import cn.edu.xmu.oomall.order.controller.dto.*;

import cn.edu.xmu.oomall.order.controller.vo.OrderListVo;
import cn.edu.xmu.oomall.order.controller.vo.OrderVo;
import cn.edu.xmu.oomall.order.dao.OrderDao;
import cn.edu.xmu.oomall.order.dao.bo.Order;
import cn.edu.xmu.oomall.order.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 根据id获取订单
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ReturnObject getOrderById(@PathVariable Long id) {
        OrderVo orderVo = orderService.getOrderById(id);
        return new ReturnObject(orderVo);
    }

    /**
     * 顾客修改订单
     */
    @PutMapping("{orderId}")
    public ReturnObject changeCustomerOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDto) {
        OrderVo orderVo = orderService.changeCustomerOrder(orderId,orderDto);
        return new ReturnObject(orderVo);
    }
    /**
     * 通过shopId和orderId获取商户的某个订单
     * @param shopId 商户ID
     * @param orderId 订单ID
     * @return ReturnObject
     */
    @GetMapping("/shops/{shopId}/orders/{orderId}")
    public ReturnObject getShopOrderById(@PathVariable Long shopId, @PathVariable Long orderId) {
        OrderVo orderVo = orderService.getShopOrderById(shopId, orderId);
        return new ReturnObject(orderVo);
    }

    /**
     * 根据shopId获取某个商户下的所有订单
     * @param shopId 商户ID
     * @return ReturnObject
     */
    @GetMapping("/shops/{shopId}/orders")
    public ReturnObject getShopOrders(@PathVariable Long shopId) {
        List<OrderListVo> orderListVo = orderService.getShopOrders(shopId);
        return new ReturnObject(orderListVo);
    }
    /**
     * 管理员删除订单
     * @return
     */
    @DeleteMapping("/deleteShopOrder")
    public ReturnObject deleteShopOrder(@RequestBody OrderDto request) {
        return orderService.deleteShopOrder(request.getCustomerId(), request.getShopId(), request.getOrderId());
    }
}
