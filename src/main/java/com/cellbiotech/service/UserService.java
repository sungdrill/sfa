package com.cellbiotech.service;


import com.cellbiotech.model.sfa.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * SFA 시스템 사용자 관련 서비스
 */
public interface UserService {
    public User findUserById(String email);
    public void saveUser(User user);
    public List<User> findUserAll();
    public Page<User> findAllPage(int page, int size, String sort);
}
