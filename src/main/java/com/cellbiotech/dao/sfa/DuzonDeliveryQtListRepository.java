package com.cellbiotech.dao.sfa;

import com.cellbiotech.model.sfa.DuzonDeliveryQtList;
import com.cellbiotech.model.sfa.ProdInv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 상품 입출고 현황 JPA
 */
@Repository
public interface DuzonDeliveryQtListRepository extends JpaRepository<DuzonDeliveryQtList, String> {
}
