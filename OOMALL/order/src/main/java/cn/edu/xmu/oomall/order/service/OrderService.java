package cn.edu.xmu.oomall.order.service;


import cn.edu.xmu.oomall.order.controller.dto.OrderDto;
import cn.edu.xmu.oomall.order.controller.vo.OrderVo;
import cn.edu.xmu.oomall.order.dao.OrderDao;
import cn.edu.xmu.oomall.order.dao.bo.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
