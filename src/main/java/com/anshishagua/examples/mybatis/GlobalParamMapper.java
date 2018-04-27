package com.anshishagua.examples.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * User: lixiao
 * Date: 2018/4/26
 * Time: 上午9:52
 */

@Mapper
public interface GlobalParamMapper {
    GlobalParam getByName(@Param("name") String name);
}