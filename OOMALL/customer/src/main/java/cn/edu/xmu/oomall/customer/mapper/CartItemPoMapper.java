package cn.edu.xmu.oomall.customer.mapper;

import cn.edu.xmu.oomall.customer.mapper.po.CartItemPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemPoMapper extends JpaRepository<CartItemPo, Long> {
    CartItemPo findByCustomerIdAndProductId(Long customerId, Long productId);
}
