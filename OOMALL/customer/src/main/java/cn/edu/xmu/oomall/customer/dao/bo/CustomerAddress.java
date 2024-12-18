package cn.edu.xmu.oomall.customer.dao.bo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

@Slf4j
@Data
public class CustomerAddress implements Serializable {
    private Long id;
    private Long customerId;
    private Long regionId;
    private String address;
    private String consignee;
    private String mobile;
    private Byte beDefault;
    private String creatorName;
    private Long modifierName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public CustomerAddress() {}


}
