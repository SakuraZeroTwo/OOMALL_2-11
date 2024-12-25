package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.oomall.customer.mapper.openfeign.OnsaleMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class OnSale {
    private Long price;
    private Byte type;
    private Long ProductId;
    /**
     * 正常
     */
    @JsonIgnore
    public static final Byte NORMAL = 0;
    /**
     * 秒杀
     */
    @JsonIgnore
    public static final Byte SECONDKILL = 1;

    /**
     * 预售
     */
    @JsonIgnore
    public static final Byte GROUPON = 2;

    /**
     * 预售
     */
    @JsonIgnore
    public static final Byte ADVSALE = 3;

    @Autowired
    private OnsaleMapper onsaleMapper;
}
