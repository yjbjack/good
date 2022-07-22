package com.yjb.goods.biz;

import com.yjb.goods.pojo.Good;
import com.yjb.goods.pojo.GoodQuery;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GoodService {

    //分页查询商品，page接口接收商品页数和商品信息,无条件
    Page<Good> findGoodNoCriteria(Integer pageNo, Integer size);

    //分页查询商品，page接口接收商品页数和商品信息,可以带上查询的条件
    Page<Good> findGoodCriteria(Integer pageNo, Integer size, GoodQuery goodQuery);

   //根据商品id查询商品,用户修改商品
    Good findById(Integer gid);

    //商品的删除，增加或修改

    void deleteGood(Good good);

    Good addOrSaveGood(Good good);

}
