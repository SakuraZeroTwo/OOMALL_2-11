package cn.edu.xmu.oomall.order.service;


import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.oomall.order.controller.dto.OrderDto;
import cn.edu.xmu.oomall.order.controller.vo.OrderListVo;
import cn.edu.xmu.oomall.order.controller.dto.OrderItemDto;
import cn.edu.xmu.oomall.order.controller.vo.OrderVo;
import cn.edu.xmu.oomall.order.dao.OrderDao;
import cn.edu.xmu.oomall.order.dao.OrderItemDao;
import cn.edu.xmu.oomall.order.dao.bo.Order;
import cn.edu.xmu.oomall.order.mapper.openfeign.ShopMapper;
import cn.edu.xmu.oomall.order.mapper.openfeign.po.ShopPo;
import cn.edu.xmu.oomall.order.dao.bo.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final static Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final RefundService refundService;
    private final ExpressService expressService;
    private final ShopMapper shopMapper;
    /**
     * 根据ID获取订单
     * @param id
     * @return
     */
    public OrderVo getOrderById(Long id) {
        Order order = this.orderDao.findById(id);
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        return orderVo;
    }

    /**
     * 顾客修改订单
     */
    public OrderVo changeCustomerOrder(Long id, OrderDto orderDto) {
        Order order = this.orderDao.findById(id);
        order = order.updateOrderInfo(orderDto);
        Order orderbo = orderDao.updateOrder(order);
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(orderbo, orderVo);
        return orderVo;
    }

    /**
     * 获得OrderItem的OnsaleId
     */
    public OrderItemDto getOnsaleByOrderItemId(Long orderItemId){
        OrderItem orderItem = orderItemDao.findById(orderItemId);
        OrderItemDto orderItemDto = new OrderItemDto();
        BeanUtils.copyProperties(orderItem, orderItemDto);
        orderItemDto.setOrderItemId(orderItem.getId());
        logger.info("Get onsaleId = ", orderItemDto.getOnsaleId());
        return orderItemDto;
    }
    /**
     * 通过shopId和orderId获取商户的某个订单
     * @param shopId 商户ID
     * @param orderId 订单ID
     * @return OrderVo
     */
    public OrderVo getShopOrderById(Long shopId, Long orderId) {
        InternalReturnObject<ShopPo> shopCheck = shopMapper.findShopById(shopId);
        Order order = orderDao.findShopOrderById(shopId, orderId);
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        return orderVo;
    }

    /**
     * 根据shopId获取某个商户下的所有订单
     * @param shopId 商户ID
     * @return List<OrderVo>
     */
    public List<OrderListVo> getShopOrders(Long shopId) {
        InternalReturnObject<ShopPo> shopCheck = shopMapper.findShopById(shopId);
        List<Order> orders = orderDao.findShopOrders(shopId);
        return orders.stream().map(order -> {
            OrderListVo orderVo = new OrderListVo();
            BeanUtils.copyProperties(order, orderVo);
            return orderVo;
        }).collect(Collectors.toList());
    }
    public ReturnObject deleteShopOrder(Long customerId, Long shopId, Long orderId) {
        Order order = orderDao.findById(orderId);
        if (order == null) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "订单不存在");
        }
        try {
            order.deleteShopOrder(customerId, shopId);
//            if (order.getStatus() == 201 || order.getStatus() == 202 || order.getStatus() == 203) {
//                refundService.createRefund(shopId, order.getPaymentId(), order.getDiscountPrice(), order.getDiscountPrice(), userId);
//                expressService.cancelExpress(userId, shopId, order.getExpressId());
//            }
            orderDao.updateOrder(order);
            return new ReturnObject(ReturnNo.OK, "订单删除成功");
        } catch (BusinessException e) {
            return new ReturnObject(e.getErrno(), e.getMessage());
        }
    }
}
