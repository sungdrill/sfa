package com.cellbiotech.dao.sfa;

import com.cellbiotech.model.sfa.AccountGradeList;
import com.cellbiotech.model.sfa.ItemList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 재고관리 - 품목 관련 JPA
 */
@Repository
public interface ItemListRepository extends JpaRepository<ItemList, String> {
    Page<ItemList> findAll(Pageable pageable);
    Page<ItemList> findAllByItemCodeContainingAndItemNameContainingAndUseYnContaining(String itemCode, String itemName, String useYn, Pageable pageable);

    List<ItemList> findAllByItemCodeContainingAndItemNameContainingAndUseYnContaining(String itemCode, String itemName, String useYn);
}
