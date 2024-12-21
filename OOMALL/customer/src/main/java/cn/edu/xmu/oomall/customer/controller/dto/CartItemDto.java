package cn.edu.xmu.oomall.customer.controller.dto;

import cn.edu.xmu.javaee.core.validation.NewGroup; //一个验证分组。验证是新的还是修改的
import com.fasterxml.jackson.annotation.JsonInclude; //用于java对象和json的转换
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor; //创建有所有参数的构造函数
import lombok.Getter;
import lombok.NoArgsConstructor; //创建无参构造

import jakarta.validation.constraints.NotBlank; //合法性检验，不为空且不为空格
import jakarta.validation.constraints.NotNull; //合法性检验，不为空
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemDto {

    @NotBlank(message = "商品id不能为空", groups = {NewGroup.class})
    private Long productId;
    @Min(value = 1, message = "数量必须大于等于 1")
    private Long quantity;
}
