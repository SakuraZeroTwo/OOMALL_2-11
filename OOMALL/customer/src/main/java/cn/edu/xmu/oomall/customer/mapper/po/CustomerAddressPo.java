package cn.edu.xmu.oomall.customer.mapper.po;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "customer_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerAddressPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public String toString() {
        return "CustomerAddressPo{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", regionId=" + regionId +
                ", address='" + address + '\'' +
                ", consignee='" + consignee + '\'' +
                ", mobile='" + mobile + '\'' +
                ", beDefault=" + beDefault +
                ", creatorName='" + creatorName + '\'' +
                ", modifierName=" + modifierName +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
