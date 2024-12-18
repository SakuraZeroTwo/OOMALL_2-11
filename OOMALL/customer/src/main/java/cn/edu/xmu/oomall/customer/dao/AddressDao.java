package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.bo.Address;
import cn.edu.xmu.oomall.customer.mapper.AddressPoMapper;
import cn.edu.xmu.oomall.customer.mapper.po.AddressPo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AddressDao {

    private static final Logger logger = LoggerFactory.getLogger(AddressDao.class);

    private final AddressPoMapper addressPoMapper;

    public Address insert(Address bo) throws RuntimeException {
        bo.setId(null); // Ensure the ID is null for a new record
        bo.setGmtCreate(LocalDateTime.now());

        AddressPo addressPo = new AddressPo();
        BeanUtils.copyProperties(bo, addressPo);

        try {
            AddressPo result = addressPoMapper.save(addressPo);
            bo.setId(addressPo.getId());
        } catch (Exception e) {
            logger.error("Error occurred while inserting address", e);
            throw new RuntimeException("Database insert error", e);
        }

        logger.info("Successfully inserted address with ID: {}", bo.getId());
        return bo;
    }

    public List<Address> retrieveByCustomerId(Long id,Integer page,Integer pageSize) throws RuntimeException {
        Pageable pageable= PageRequest.of(page - 1,pageSize, Sort.by("regionId").descending());
        List<AddressPo> retObj = this.addressPoMapper.findByCustomerIdIs(id,pageable);
        return retObj.stream()
                .map(po -> {
                    Address address = new Address();  // 创建目标对象
                    BeanUtils.copyProperties(po, address);  // 复制属性
                    return address;  // 返回目标对象
                })
                .collect(Collectors.toList());
    }
}
