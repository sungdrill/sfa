package com.cellbiotech.dao.sfa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cellbiotech.model.sfa.AccountInfoList;

import java.util.List;

/**
 * 거래처별 정보 관련 JPA
 */
@Repository
public interface AccountInfoListRepository extends JpaRepository<AccountInfoList, String> {

    Page<AccountInfoList> findAll(Pageable pageable);

    Page<AccountInfoList> findAllByAccountNameContaining(String accountName, Pageable pageable);

    List<AccountInfoList> findAllByAccountNameContaining(String accountName);

    Page<AccountInfoList> findAllByAccountNameContainingAndAccountCodeContaining(String accountName, String accountCode, Pageable pageable);

    List<AccountInfoList> findAllByAccountNameContainingAndAccountCodeContaining(String accountName, String accountCode);
}
