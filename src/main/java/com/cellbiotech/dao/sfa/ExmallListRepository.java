package com.cellbiotech.dao.sfa;

import com.cellbiotech.model.sfa.ExmallList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 외부몰리스트 관련 JPA
 */
@Repository
public interface ExmallListRepository extends JpaRepository<ExmallList, String> {

}
