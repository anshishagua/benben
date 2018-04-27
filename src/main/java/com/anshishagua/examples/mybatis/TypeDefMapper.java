package com.anshishagua.examples.mybatis;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/3/2
 * Time: 下午2:23
 */

@Mapper
public interface TypeDefMapper {
    TypeDef getTypeById(long id);
    TypeDef getTypeByCode(String code);
    List<TypeDef> getAll();
}