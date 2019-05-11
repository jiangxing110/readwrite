package com.zhiyun.readwrite.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//加入@ApiModel
@ApiModel
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "ID", dataType = "Integer", name = "ID", example = "1020332806740959233")
    private Integer id;
    @ApiModelProperty(value = "名称", dataType = "String", name = "name", example = "小明")
    @NotBlank(message = "编码不能为空")
    private String name;
    @ApiModelProperty(value = "stock", dataType = "Integer", name = "stock", example = "001")
    @NotBlank(message = "编码不能为空")
    private Integer stock;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}