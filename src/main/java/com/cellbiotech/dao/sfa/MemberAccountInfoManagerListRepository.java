package com.cellbiotech.dao.sfa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cellbiotech.model.sfa.MemberAccountInfoManagerList;

/**
 * 영업담당자 거래처정보 관리 관련 JPA
 */
@Repository
public interface MemberAccountInfoManagerListRepository extends JpaRepository<MemberAccountInfoManagerList, String> {

    Page<MemberAccountInfoManagerList> findAll(Pageable pageable);

    @Override
    void delete(MemberAccountInfoManagerList entity);

}
