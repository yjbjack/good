package com.yjb.goods.pojo;

import com.yjb.goods.util.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_goods")
public class Good implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "注解，自动加1")
    private Integer id;

    @Column(name = "name",nullable = false)
    @ApiModelProperty(value = "商品名称")
    private String name;

    @Column(name = "price",nullable = false)
    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;

    @Column(nullable = false)
    @ApiModelProperty(value = "数量")
    private Integer total;

    @Column
    @ApiModelProperty(value = "长度")
    private Double length = 0.0;

    @Column
    @ApiModelProperty(value = "宽度")
    private Double width = 0.0;
    @Column
    @ApiModelProperty(value = "高度")
    private Double heigth = 0.0;

    @Column
    @ApiModelProperty(value = "重量")
    private Double weigth;

    @Column
    @ApiModelProperty(value = "描述")
    private String descrip;

    @Column
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间，默认为系统时间")
    private Timestamp creat_time = DateUtil.getCurrentTimeStamp();

    @Column
    @UpdateTimestamp
    @ApiModelProperty(value = "上次修改时间")
    private Timestamp modify_time;

    @Column
    @ApiModelProperty(value = "操作人的id")
    private String user_id;

    @Column(columnDefinition = "integer default 0")
    @ApiModelProperty(value = "为删除0，已删除1")
    private Integer del = 0 ;

    @Column(columnDefinition = "integer default 1")
    @ApiModelProperty(value = "下架0，上架1")
    private Integer shelves = 0 ;
    @Column
    @ApiModelProperty(value = "默认的图片路径")
    private String img;

    public Good(Integer id) {
        this.id = id;
    }
}
