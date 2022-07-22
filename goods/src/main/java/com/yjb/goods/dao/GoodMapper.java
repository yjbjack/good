package com.yjb.goods.dao;

import com.yjb.goods.pojo.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodMapper extends JpaRepository<Good,Integer>, JpaSpecificationExecutor<Good> {


    Good findGoodById(Integer gid);

}
