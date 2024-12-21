package cn.edu.xmu.oomall.customer.mapper;

import cn.edu.xmu.oomall.customer.mapper.po.CustomerAddressPo;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerAddressPoMapper extends JpaRepository<CustomerAddressPo, Long> {
    Optional<CustomerAddressPo> findById(Long id);

    Optional<CustomerAddressPo> findByCustomerIdAndId(Long customerId, Long addressId);
    // 更新指定顾客的所有地址的 beDefault 为 false
    @Modifying
    @Query("UPDATE CustomerAddressPo ca SET ca.beDefault = 0 WHERE ca.customerId = ?1")
    void updateDefaultAddressByCustomerId(Long customerId, Long addressId);

}
