package com.kevin.onlinetest.mapper;

import java.util.List;

/**
 * @author herokilito
 */
public interface BaseMapper<T> {

    /**
     *
     * @param num
     * @return
     */
    T get(Integer num);

    /**
     *
     * @param id
     * @return
     */
    T getById(Integer id);

    List<T> list();

    void insert(T object);

    void update(T object);

    void delete(Integer id);

    void resetPassword(Integer id, String pwd);
}
