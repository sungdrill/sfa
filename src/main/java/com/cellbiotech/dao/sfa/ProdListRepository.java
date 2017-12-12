package com.cellbiotech.dao.sfa;

import com.cellbiotech.model.sfa.ProdList;
import com.cellbiotech.model.sfa.ProdListId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 재고관리 - 상품리스트 관련 JPA
 */
@Repository
public interface ProdListRepository extends JpaRepository<ProdList, String> {
    Page<ProdList> findAllByIdProdCodeContainingAndProdNameContainingAndProdTypeContainingAndIdMallSiteContainingAndUseYnContaining(String prodCode, String prodName, String ProdType, String mallSite, String useYn, Pageable pageable);
    List<ProdList> findAllByIdProdCodeContainingAndProdNameContainingAndProdTypeContainingAndIdMallSiteContainingAndUseYnContaining(String prodCode, String prodName, String ProdType, String mallSite, String useYn);

    ProdList findByIdProdCodeAndIdMallSite(String prodCode, String mallSite);
}
