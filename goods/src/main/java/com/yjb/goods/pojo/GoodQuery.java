package com.yjb.goods.pojo;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Timestamp;

/*
* 定义Good类查询条件
* */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodQuery {

    //@ApiModelProperty(name = "商品id查询")
    private Integer id;

    //@ApiModelProperty(name = "商品名称查询")
    private String name;

    //@ApiModelProperty(name = "商品价格区间查询,初始价格")
    private String lessPrice ;

    //@ApiModelProperty(name = "结束价格")
    private String morePrice;


    //@ApiModelProperty(value = "创建时间查询")
    private String creat_time;



}
