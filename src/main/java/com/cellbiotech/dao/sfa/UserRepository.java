package com.cellbiotech.dao.sfa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cellbiotech.model.sfa.User;

/**
 * SFA 시스템 사용자 관련 JPA
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findById(String id);

    Page<User> findAll(Pageable pageable);

    Page<User> findAllByIdContaining(String id, Pageable pageable);

    Page<User> findAllByRoleContaining(String role, Pageable pageable);

    Page<User> findAllByIdContainingAndRoleContaining(String id, String role, Pageable pageable);
}
