package com.cellbiotech.dao.sfa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cellbiotech.model.sfa.Role;

/**
 * 권한 관련 JPA
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);

}
