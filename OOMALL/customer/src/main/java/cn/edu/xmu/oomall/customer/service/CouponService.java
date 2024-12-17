package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.oomall.customer.controller.dto.ResponseWrapper;
import cn.edu.xmu.oomall.customer.dao.CouponDao;
import cn.edu.xmu.oomall.customer.dao.CustomerDao;
import cn.edu.xmu.oomall.customer.dao.bo.Coupon;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static cn.edu.xmu.javaee.core.model.Constants.MAX_RETURN;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Slf4j
public class CouponService {
    private final CustomerDao customerDao;
    private final CouponDao couponDao;
    /**
     * 获取优惠券列表
     */
    public List<Coupon> getCouponsList(Long id) {
        log.info("Attempting to get coupons list for customer with id: {}", id);
        return couponDao.retrieveByCustomerId(id,1,MAX_RETURN);
    }

}
