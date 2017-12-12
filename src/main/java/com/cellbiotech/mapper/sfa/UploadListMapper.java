package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 엑셀파일 업로드 로그 관련 mybatis
 */
public interface UploadListMapper {
    public int selectUploadListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, Object>> selectUploadList(Map<String, String> ajaxDTO) throws Exception;
}
