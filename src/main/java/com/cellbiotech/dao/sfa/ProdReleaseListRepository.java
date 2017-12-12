package com.cellbiotech.dao.sfa;

import com.cellbiotech.model.sfa.ProdReleaseList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 재고관리 - 상품 판매현황 리스트 관련 JPA
 */
@Repository
public interface ProdReleaseListRepository extends JpaRepository<ProdReleaseList, String> {
    Page<ProdReleaseList> findAllByIdUpdateDateBetweenAndIdProdCodeContainingAndProdNameContainingAndIdMallCodeContainingAndMallSiteContaining(Date from, Date to, String prodCode, String prodName, String mallCode, String mallSite, Pageable pageable);
    Page<ProdReleaseList> findAllByIdProdCodeContainingAndProdNameContainingAndIdMallCodeContainingAndMallSiteContaining(String prodCode, String prodName, String mallCode, String mallSite, Pageable pageable);

    List<ProdReleaseList> findAllByIdUpdateDateBetweenAndIdProdCodeContainingAndProdNameContainingAndIdMallCodeContainingAndMallSiteContaining(Date from, Date to, String prodCode, String prodName, String mallCode, String mallSite);
    List<ProdReleaseList> findAllByIdProdCodeContainingAndProdNameContainingAndIdMallCodeContainingAndMallSiteContaining(String prodCode, String prodName, String mallCode, String mallSite);
}
