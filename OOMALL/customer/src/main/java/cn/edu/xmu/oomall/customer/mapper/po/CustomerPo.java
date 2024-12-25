package cn.edu.xmu.oomall.customer.mapper.po;

import io.lettuce.core.StrAlgoArgs;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "customer_customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;   // 用户名
    private String password;   // 密码
    private String name;       // 真实姓名

    private Long point;
    private Byte invalid;      // 0 有效，1 无效
    private Byte be_deleted;     // 删除标志位

    private Long creatorId;
    private String creatorName;

    private Long modifierId;
    private String modifierName;

    private LocalDateTime gmtCreate;   // 创建时间
    private LocalDateTime gmtModified; // 修改时间
    private String mobile; //联系电话

//    @Override
//    public String toString() {
//        return "CustomerPo{" +
//                "id=" + id +
//                ", userName='" + userName + '\'' +
//                ", password='" + password + '\'' +
//                ", name='" + name + '\'' +
//                ", point=" + point +
//                ", invalid=" + invalid +
//                ", beDeleted=" + be_deleted +
//                ", creatorId=" + creatorId +
//                ", creatorName='" + creatorName + '\'' +
//                ", modifierId=" + modifierId +
//                ", modifierName='" + modifierName + '\'' +
//                ", gmtCreate=" + gmtCreate +
//                ", gmtModified=" + gmtModified +
//                ", mobile='" + mobile + '\'' +
//                '}';
//    }
}
