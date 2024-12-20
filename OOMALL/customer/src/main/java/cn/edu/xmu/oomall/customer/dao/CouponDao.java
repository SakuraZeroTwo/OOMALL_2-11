package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.dao.bo.Coupon;
import cn.edu.xmu.oomall.customer.mapper.po.CouponPo;
import cn.edu.xmu.oomall.customer.mapper.CouponPoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

@Repository
@RequiredArgsConstructor
@RefreshScope
@Slf4j
public class CouponDao {
    @Autowired
    private final CouponPoMapper couponPoMapper;



    public List<Coupon> retrieveByCustomerId(Long customerId, Integer page, Integer pageSize) {
        log.debug("retrieveByCustomerId: customerId = {}, page = {}, pageSize = {}", customerId, page, pageSize);
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<CouponPo> couponPos = this.couponPoMapper.findByCustomerId(customerId, pageable);

        return couponPos.stream()
                .map(po -> {
                    Coupon coupon = new Coupon();
                    BeanUtils.copyProperties(po, coupon);
                    return coupon;
                })
                .collect(Collectors.toList());
    }

}


