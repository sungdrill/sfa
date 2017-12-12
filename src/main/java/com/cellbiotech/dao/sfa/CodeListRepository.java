package com.cellbiotech.dao.sfa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cellbiotech.model.sfa.CodeList;

import java.util.List;
import java.util.Map;

/**
 * 공통코드 코드 관련 JPA
 */
@Repository
public interface CodeListRepository extends JpaRepository<CodeList, String> {

    Page<CodeList> findAll(Pageable pageable);

    Page<CodeList> findAllByIdGroupCodeId(String groupCodeId, Pageable pageable);

    List<CodeList> findAllByIdGroupCodeId(String groupCodeId, Sort sort);

    List<CodeList>findAllByIdGroupCodeIdAndIdCodeId(String groupCodeId, String codeId);
}
