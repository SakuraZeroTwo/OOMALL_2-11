package cn.edu.xmu.oomall.order.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.order.controller.vo.OrderVo;
import cn.edu.xmu.oomall.order.dao.bo.Order;
import cn.edu.xmu.oomall.order.mapper.OrderPoMapper;
import cn.edu.xmu.oomall.order.mapper.po.OrderPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderDao {
    private final static Logger logger = LoggerFactory.getLogger(OrderDao.class);
    private final OrderPoMapper orderPoMapper;

    public Order findById(Long id) {
        Optional<OrderPo> orderPo = orderPoMapper.findById(id);
        if (!orderPo.isPresent()) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST,"订单不存在");
        }
        else{
            Order bo = new Order();
            BeanUtils.copyProperties(orderPo.get(), bo);
            return bo;
        }
    }

    public Order updateOrder(Order order) {
        OrderPo orderPo = new OrderPo();
        BeanUtils.copyProperties(order, orderPo);
        orderPo.setGmtCreate(order.getGmtCreate());
        orderPoMapper.save(orderPo);

        Order orderBo = new Order();
        BeanUtils.copyProperties(orderPo, orderBo);

        return orderBo;
    }
}