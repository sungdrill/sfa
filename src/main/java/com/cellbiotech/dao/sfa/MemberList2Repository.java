package com.cellbiotech.dao.sfa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cellbiotech.model.sfa.MemberList2;

/**
 * SFA 데이터베이스 영업담당자 관련 JPA
 */
@Repository
public interface MemberList2Repository extends JpaRepository<MemberList2, String> {

    Page<MemberList2> findAll(Pageable pageable);

    List<MemberList2> findAllBySalesManagerCode(String salesManagerCode);
}
