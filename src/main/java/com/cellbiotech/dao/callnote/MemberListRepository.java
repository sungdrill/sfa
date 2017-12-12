package com.cellbiotech.dao.callnote;

import com.cellbiotech.model.callnote.MemberList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 콜노트 영업담당자 관련 JPA
 */
@Repository
public interface MemberListRepository extends JpaRepository<MemberList, Long> {
    Page<MemberList> findAllByDept(String Dept, Pageable pageable);

    Page<MemberList> findAllByNameContaining(String name, Pageable pageable);
}
