package com.cellbiotech.dao.sfa;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cellbiotech.model.sfa.SalesDeadlineList;

/**
 * 매출마감리스트 관련 JPA
 */
@Repository
public interface SalesDeadlineListRepository extends JpaRepository<SalesDeadlineList, String> {

    Page<SalesDeadlineList> findAllByIdSalesNumContainingAndAccountNameContainingAndAccountCodeContainingAndItemNameContainingAndIdItemCodeContaining(String id, String accountName, String accountCode, String itemName, String itemCode, Pageable pageable);
    Page<SalesDeadlineList> findAllByIdSalesNumContainingAndAccountNameContainingAndAccountCodeContainingAndItemNameContainingAndIdItemCodeContainingAndSalesDateBetween(String id, String accountName, String accountCode, String itemName, String itemCode, Date fromDate, Date toDate, Pageable pageable);

    List<SalesDeadlineList> findAllByIdSalesNumContainingAndAccountNameContainingAndAccountCodeContainingAndItemNameContainingAndIdItemCodeContaining(String id, String name, String code, String itemName, String itemCode);
    List<SalesDeadlineList> findAllByIdSalesNumContainingAndAccountNameContainingAndAccountCodeContainingAndItemNameContainingAndIdItemCodeContainingAndSalesDateBetween(String id, String name, String code, String itemName, String itemCode, Date fromDate, Date toDate);
}
