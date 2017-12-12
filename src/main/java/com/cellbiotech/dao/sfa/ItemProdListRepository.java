package com.cellbiotech.dao.sfa;

import com.cellbiotech.model.sfa.ItemProdList;
import com.cellbiotech.model.sfa.MemberAccountInfoManagerList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 재고관리 - 품목-상품 관련 JPA
 */
@Repository
public interface ItemProdListRepository extends JpaRepository<ItemProdList, String> {

    Page<ItemProdList> findAll(Pageable pageable);

    @Override
    void delete(ItemProdList entity);

}
