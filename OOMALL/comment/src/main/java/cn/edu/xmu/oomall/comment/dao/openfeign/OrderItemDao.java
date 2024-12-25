package cn.edu.xmu.oomall.comment.dao.openfeign;

import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.oomall.comment.mapper.openfeign.OrderItemMapper;
import cn.edu.xmu.oomall.comment.mapper.openfeign.po.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OrderItemDao {
    private final OrderItemMapper orderItemMapper;

    public OrderItem findById(Long id) {
        InternalReturnObject<OrderItem> ret = this.orderItemMapper.getOnsaleByOrderItemId(id);
        return ret.getData();
    }
}
