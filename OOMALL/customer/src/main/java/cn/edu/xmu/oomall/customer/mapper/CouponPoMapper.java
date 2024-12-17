package cn.edu.xmu.oomall.customer.mapper;

import cn.edu.xmu.oomall.customer.mapper.po.CouponPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponPoMapper extends JpaRepository<CouponPo, Long> {
    List<CouponPo> findByCustomerIdEquals(Long customerId, Pageable pageable);
}

