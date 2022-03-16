package com.kevin.onlinetest.mapper;

import java.util.List;

/**
 * @author herokilito
 * @version V1.0
 * @Package com.kevin.onlinetest.mapper
 * @date 2020/12/15 18:20
 */
public interface FatherMapper<T> {
    /**
     * 根据主键删除
     * @param id id
     * @return 影响行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入记录
     * @param record 记录
     * @return 影响行数
     */
    int insert(T record);

    /**
     * 可选择属性的插入
     * @param record 记录
     * @return 影响行数
     */
    int insertSelective(T record);

    /**
     * 分页查询
     * @return
     */
    List<T> list();

    /**
     * 根据主键查询
     * @param id id
     * @return 结果
     */
    T selectByPrimaryKey(Integer id);

    /**
     * 根据主键非空属性进行更新
     * @param record 记录
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 根据主键进行更新
     * @param record 记录
     * @return 影响行数
     */
    int updateByPrimaryKey(T record);
}
