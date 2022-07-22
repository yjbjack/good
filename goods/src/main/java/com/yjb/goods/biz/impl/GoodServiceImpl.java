package com.yjb.goods.biz.impl;

import com.mysql.cj.util.StringUtils;
import com.yjb.goods.biz.GoodService;
import com.yjb.goods.dao.GoodMapper;
import com.yjb.goods.pojo.Good;
import com.yjb.goods.pojo.GoodQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodMapper goodMapper;
    @Override
    public Page<Good> findGoodNoCriteria(Integer pageNo, Integer size) {
        PageRequest pageRequest = PageRequest.of(pageNo, size, Sort.Direction.ASC,"id");
        //pageRequest用于实现分页
        return goodMapper.findAll(pageRequest);
    }

    @Override
    public Page<Good> findGoodCriteria(Integer pageNo, Integer size, final GoodQuery goodQuery) {
        PageRequest pageRequest = PageRequest.of(pageNo, size, Sort.Direction.ASC, "id");
        Page<Good> goodPage = goodMapper.findAll(new Specification<Good>() {
            @Override
            public Predicate toPredicate(Root<Good> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                //编写查询条件,需要判断当前数据是否为空
                if (goodQuery.getId() != null){
                     predicateList.add(criteriaBuilder.equal(root.get("id").as(Integer.class), goodQuery.getId()));
                }
                if (goodQuery.getLessPrice() != "" && goodQuery.getMorePrice() != ""){
                    predicateList.add(criteriaBuilder.between(root.get("price").as(String.class), goodQuery.getLessPrice().toString(), goodQuery.getMorePrice().toString()));
                }
                if (!StringUtils.isNullOrEmpty(goodQuery.getName())){
                    predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + goodQuery.getName() + "%"));
                }
                if (goodQuery.getCreat_time() != null){
                    predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("creat_time").as(String.class), goodQuery.getCreat_time()));
                }

                query.where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
                return query.getRestriction();
            }
        }, pageRequest);
        return goodPage;
    }

    @Override
    public Good findById(Integer gid) {
        return goodMapper.findGoodById(gid);
    }



    @Override
    public void deleteGood(Good good) {
        goodMapper.deleteById(good.getId());
    }

    @Override
    public Good addOrSaveGood(Good good) {
        return goodMapper.save(good);
    }


}
