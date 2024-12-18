package cn.edu.xmu.oomall.customer.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerAddressDto {
    private Long id;
    @NotNull(message = "地区不能为空")
    private Long regionId;
    @NotNull(message = "详细地址不能为空")
    private String address;
    private String consignee;
    private String mobile;
    private LocalDateTime gmtCreate;   // 创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "地区不能为空") Long getRegionId() {
        return regionId;
    }

    public void setRegionId(@NotNull(message = "地区不能为空") Long regionId) {
        this.regionId = regionId;
    }

    public @NotNull(message = "详细地址不能为空") String getAddress() {
        return address;
    }

    public void setAddress(@NotNull(message = "详细地址不能为空") String address) {
        this.address = address;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
