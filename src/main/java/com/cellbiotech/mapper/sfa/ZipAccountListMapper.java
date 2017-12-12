package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 행정구역 거래처 정보 관리 관련 mybatis
 */
public interface ZipAccountListMapper {
    public int selectZipAccountListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, String>> selectZipAccountList(Map<String, String> ajaxDTO) throws Exception;
}

