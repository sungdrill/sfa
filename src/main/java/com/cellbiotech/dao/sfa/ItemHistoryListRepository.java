package com.cellbiotech.dao.sfa;

import com.cellbiotech.model.sfa.ItemHistoryList;
import com.cellbiotech.model.sfa.ItemList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 재고관리 - 품목 재고현황 관련 JPA
 */
@Repository
public interface ItemHistoryListRepository extends JpaRepository<ItemHistoryList, String> {
    Page<ItemHistoryList> findAll(Pageable pageable);

    List<ItemHistoryList> findAllByIdItemCode(String itemCode, Sort sort);
    List<ItemHistoryList> findAllByIdItemCodeAndIdUpdateDateBetween(String itemCode, Date form, Date to, Sort sort);

}
