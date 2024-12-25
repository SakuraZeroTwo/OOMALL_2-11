package cn.edu.xmu.oomall.order.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.order.controller.vo.OrderVo;
import cn.edu.xmu.oomall.order.dao.bo.Order;
import cn.edu.xmu.oomall.order.dao.bo.OrderItem;
import cn.edu.xmu.oomall.order.mapper.OrderItemPoMapper;
import cn.edu.xmu.oomall.order.mapper.OrderPoMapper;
import cn.edu.xmu.oomall.order.mapper.po.OrderItemPo;
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
public class OrderItemDao {
    private final static Logger logger = LoggerFactory.getLogger(OrderDao.class);
    private final OrderItemPoMapper orderItemPoMapper;

    public OrderItem findById(Long id) {
        Optional<OrderItemPo> orderItemPo = orderItemPoMapper.findById(id);
        if (!orderItemPo.isPresent()) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST,"该OrderItem不存在");
        }
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(orderItemPo.get(), orderItem);
        return orderItem;
    }

}
