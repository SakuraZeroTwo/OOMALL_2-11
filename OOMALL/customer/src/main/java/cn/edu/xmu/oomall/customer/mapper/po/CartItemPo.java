package cn.edu.xmu.oomall.customer.mapper.po;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
@Entity
@Table(name = "customer_cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartItemPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private Long productId;
    private Long quantity;
    private Long price;
    private Long creatorId;
    private String creatorName;
    private Long modifierId;
    private String modifierName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;


    @Override
    public String toString() {
        return "CartItemPo{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", creatorId=" + creatorId +
                ", creatorName='" + creatorName + '\'' +
                ", modifierId=" + modifierId +
                ", modifierName='" + modifierName + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
