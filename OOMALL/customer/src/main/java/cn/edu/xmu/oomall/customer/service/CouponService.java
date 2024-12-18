package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
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
import static java.util.Objects.isNull;

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
    public ResponseWrapper getCouponsList(Long id) {
        log.info("Attempting to get coupons list for customer with id: {}", id);
        Customer customer = customerDao.findById(id).orElse(null);
        List<Coupon> CouponBoList = couponDao.retrieveByCustomerId(id,1,MAX_RETURN);
        if(isNull(customer)) {
            return new ResponseWrapper("customer is not exist",null,1);
        }
        if(CouponBoList == null || CouponBoList.isEmpty()) {
            return new ResponseWrapper("coupons list is empty",null,1);
        }

        return new ResponseWrapper("success",CouponBoList,1);
    }

}
