package com.cellbiotech.dao.sfa;

import com.cellbiotech.model.sfa.ItemHistoryList;
import com.cellbiotech.model.sfa.ProdHistoryList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 재고관리 - 상품 재고현황 관련 JPA
 */
@Repository
public interface ProdHistoryListRepository extends JpaRepository<ProdHistoryList, String> {
    Page<ProdHistoryList> findAll(Pageable pageable);

    List<ProdHistoryList> findAllByIdProdCodeAndIdInputDate(String prodCode, Date inputDate);
    List<ProdHistoryList> findAllByIdProdCodeAndIdInputDateBetween(String prodCode, Date form, Date to, Sort sort);

}
