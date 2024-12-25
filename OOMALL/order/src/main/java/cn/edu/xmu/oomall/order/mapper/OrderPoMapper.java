package cn.edu.xmu.oomall.order.mapper;

import cn.edu.xmu.oomall.order.mapper.po.OrderPo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface OrderPoMapper extends JpaRepository<OrderPo, Long> {
    public Optional<OrderPo> findById(Long id);
}
