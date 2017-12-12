package com.cellbiotech.dao.sfa;

import com.cellbiotech.model.sfa.ErrorLogList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 행정구역 정보 관련 JPA
 */
@Repository
public interface ErrorLogListRepository extends JpaRepository<ErrorLogList, String> {

}
