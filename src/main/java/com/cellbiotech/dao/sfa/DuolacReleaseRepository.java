package com.cellbiotech.dao.sfa;

import com.cellbiotech.model.sfa.DuolacReleaseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 재고관리 - 상품 판매현황 리스트 관련 JPA
 */
@Repository
public interface DuolacReleaseRepository extends JpaRepository<DuolacReleaseList, String> {
}
