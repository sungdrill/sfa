package com.cellbiotech.dao.sfa;

import com.cellbiotech.model.sfa.ProdBasicStock;
import com.cellbiotech.model.sfa.ProdInv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 상품 기초재고 JPA
 */
@Repository
public interface ProdBasicStockRepository extends JpaRepository<ProdBasicStock, String> {
}
