package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.oomall.customer.dao.CustomerDao;
import cn.edu.xmu.oomall.customer.dao.bo.Coupon;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Slf4j
public class CouponService {
    private final CustomerDao customerDao;

    /**
     * 获取优惠券列表
     */
    public List<Coupon> getCouponsList(Long id) {
        log.info("Attempting to get coupons list for customer with id: {}", id);
        Optional<Customer> customerOpt = this.customerDao.findById(id);

        if (customerOpt.isPresent()) {
            log.info("Customer found for id: {}. Returning coupons list.", id);
            return customerOpt.get().getCouponsList(); // 直接返回优惠券列表
        } else {
            log.warn("Customer with id: {} not found.", id); // 记录没有找到顾客的日志
            return Collections.emptyList(); // 如果顾客未找到，返回空列表
        }

    }
}
