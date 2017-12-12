package com.cellbiotech.mapper.sfa;

import java.util.List;

import com.cellbiotech.model.sfa.User;

/**
 * sfa 시스템 사용자 관련 mybatis
 */
public interface UserMapper {
    public List<User> selectUserList() throws Exception;
}
