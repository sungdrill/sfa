package com.cellbiotech.dao.sfa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cellbiotech.model.sfa.AccountGradeList;

/**
 * 거래처별 등급관리 관련 JPA
 */
@Repository
public interface AccountGradeListRepository extends JpaRepository<AccountGradeList, String> {

    Page<AccountGradeList> findAll(Pageable pageable);
    
    List<AccountGradeList> findAllByAccountCode(String accountCode);

}
