package com.cellbiotech.dao.sfa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cellbiotech.model.sfa.AdminDistrictList;

/**
 * 행정구역 정보 관련 JPA
 */
@Repository
public interface AdminDistrictListRepository extends JpaRepository<AdminDistrictList, String> {

    Page<AdminDistrictList> findAll(Pageable pageable);

        Page<AdminDistrictList> findAllByAdministrativeDistrictNameContaining(String accountName, Pageable pageable);

}
