package cn.edu.xmu.oomall.order.mapper;

import cn.edu.xmu.oomall.order.mapper.po.OrderItemPo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
public interface OrderItemPoMapper extends JpaRepository<OrderItemPo, Long> {
    public Optional<OrderItemPo> findById(Long id);
}
