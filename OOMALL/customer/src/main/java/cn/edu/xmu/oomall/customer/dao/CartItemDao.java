package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.oomall.customer.mapper.CartItemPoMapper;
import cn.edu.xmu.oomall.customer.mapper.po.CartItemPo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartItemDao {
    @Autowired
    private CartItemPoMapper cartItemPoMapper;

    // 查询指定 customerId 的购物车项
    public Page<CartItemPo> findByCustomerId(Long customerId, Pageable pageable) {
        return cartItemPoMapper.findByCustomerId(customerId, pageable);
    }
}