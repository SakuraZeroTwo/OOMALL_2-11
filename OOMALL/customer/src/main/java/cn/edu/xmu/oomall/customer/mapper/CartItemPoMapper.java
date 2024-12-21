package cn.edu.xmu.oomall.customer.mapper;

import cn.edu.xmu.oomall.customer.mapper.po.CartItemPo;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface CartItemPoMapper extends JpaRepository<CartItemPo, Long> {
    Page<CartItemPo> findByCustomerId(Long customerId, Pageable pageable);
    CartItemPo findByCustomerIdAndProductId(Long customerId, Long productId);
}