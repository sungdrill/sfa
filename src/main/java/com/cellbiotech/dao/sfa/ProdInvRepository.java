package com.cellbiotech.dao.sfa;

import com.cellbiotech.model.sfa.ProdInv;
import com.cellbiotech.model.sfa.ProdList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 상품 입출고 현황 JPA
 */
@Repository
public interface ProdInvRepository extends JpaRepository<ProdInv, String> {
}
