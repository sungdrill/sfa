package com.cellbiotech.dao.sfa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cellbiotech.model.sfa.CollectionList;
import com.cellbiotech.model.sfa.UploadList;

import java.util.Date;
import java.util.List;

/**
 * 엑셀 파일 업로드 리스트 관련 JPA
 */
@Repository
public interface UploadListRepository extends CrudRepository<UploadList, String> {

    Page<UploadList> findAll(Pageable pageable);

    Page<UploadList> findAllByOriginalFileNameContaining(String originalFileName, Pageable pageable);

    Page<UploadList> findAllByCreaDtmBetween(Date startDtm, Date endDtm, Pageable pageable);

    Page<UploadList> findAllByOriginalFileNameContainingAndCreaDtmBetween(String originalFileName, Date startDtm, Date endDtm, Pageable pageable);

}
