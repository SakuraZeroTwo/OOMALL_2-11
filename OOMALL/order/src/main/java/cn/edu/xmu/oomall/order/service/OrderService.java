package cn.edu.xmu.oomall.order.service;


import cn.edu.xmu.oomall.order.controller.dto.OrderDto;
import cn.edu.xmu.oomall.order.controller.dto.OrderItemDto;
import cn.edu.xmu.oomall.order.controller.vo.OrderVo;
import cn.edu.xmu.oomall.order.dao.OrderDao;
import cn.edu.xmu.oomall.order.dao.OrderItemDao;
import cn.edu.xmu.oomall.order.dao.bo.Order;
import cn.edu.xmu.oomall.order.dao.bo.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final static Logger logger = LoggerFactory.getLogger(OrderService.class);
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
}
