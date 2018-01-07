package com.sunfund.userSys.dao.slave;

import com.sunfund.userSys.entity.Role;


/**
 * 用户 DAO 接口类
 *
 * Created by bysocket on 07/02/2017.
 */
public interface RoleDao{

    /**
     * 根据用户名获取用户信息
     *
     * @param
     * @return
     */
    public Role findByName();
}
