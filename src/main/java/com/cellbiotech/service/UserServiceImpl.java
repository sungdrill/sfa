package com.cellbiotech.service;

import com.cellbiotech.dao.sfa.RoleRepository;
import com.cellbiotech.dao.sfa.UserRepository;
import com.cellbiotech.model.sfa.Role;
import com.cellbiotech.model.sfa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * SFA 시스템 사용자 관련 서비스 구현
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findUserAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Page<User> findAllPage(int page, int size, String sort) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Sort.Direction.DESC, sort));

        return userRepository.findAll(pageRequest);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole(user.getRole());
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
}
