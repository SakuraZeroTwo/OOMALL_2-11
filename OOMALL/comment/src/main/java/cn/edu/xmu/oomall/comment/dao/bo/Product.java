package cn.edu.xmu.oomall.comment.dao.bo;

import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.oomall.comment.dao.openfeign.ProductDao;
import cn.edu.xmu.oomall.comment.mapper.openfeign.ProductMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Product {
    @Getter
    @Setter
    private Long id;


//    @Autowired
//    private ProductMapper productMapper;
//
//    public InternalReturnObject getProductId() {
//        return productMapper.findProductById(this.id);
//    }
}
