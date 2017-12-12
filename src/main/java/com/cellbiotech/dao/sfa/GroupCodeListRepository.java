package com.cellbiotech.dao.sfa;

import com.cellbiotech.model.sfa.GroupCodeList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 공통코드 - 그룹 관련 JPA
 */
public interface GroupCodeListRepository  extends JpaRepository<GroupCodeList, String> {

    Page<GroupCodeList> findAll(Pageable pageable);

    List<GroupCodeList> findAllByGroupCodeId(String id);
}
