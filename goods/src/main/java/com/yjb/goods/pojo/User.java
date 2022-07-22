package com.yjb.goods.pojo;

import com.yjb.goods.util.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @ApiModelProperty(value = "自增主键")
    private Integer id;

    @Column(nullable = false)
    @ApiModelProperty(value = "用户名")
    private String username;

    @Column(nullable = false)
    @ApiModelProperty(value = "用户密码")
    private  String password;


    @Column
    @CreationTimestamp
    @ApiModelProperty(value = "用户创建时间,默认为系统时间")
    private Timestamp creat_time = DateUtil.getCurrentTimeStamp();

    @Column
    @ApiModelProperty(value = "用户权限,0默认未普通用户,1为超级用户")
    private Integer user_status = 0;

    @Column(nullable = false)
    @ApiModelProperty(value = "用户是否删除，0未删除，1已经删除")
    private Integer del = 0;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
