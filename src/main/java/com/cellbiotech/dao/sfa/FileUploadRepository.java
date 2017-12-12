package com.cellbiotech.dao.sfa;

import org.springframework.data.repository.CrudRepository;

import com.cellbiotech.model.sfa.FileUpload;

/**
 * 파일 업로드 관련 JPA
 */
public interface FileUploadRepository extends CrudRepository<FileUpload, Long> {
}
