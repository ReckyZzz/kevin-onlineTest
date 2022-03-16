package com.kevin.onlinetest.mapper;

import com.kevin.onlinetest.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author herokilito
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 根据账号来获取管理员实体
     * @param account 账号
     * @return 管理员对象
     */
    Admin getAdminByAccount(String account);
}
