package com.kevin.onlinetest.service;

import java.util.List;

/**
 * @author herokilito
 */
public interface BaseService<T> {

    /**
     * 根据id获取实体
     * @param id 实体的id
     * @return 实体对象
     */
    T get(Integer id);

    /**
     * 查询实体集合
     * @return 实体集合
     */
    List<T> list();

    /**
     * 插入实体
     * @param object 实体对象
     */
    void insert(T object);

    /**
     * 修改实体
     * @param object 实体对象
     */
    void update(T object);

    /**
     * 删除实体
     * @param id 实体id
     */
    void delete(Integer id);
}
