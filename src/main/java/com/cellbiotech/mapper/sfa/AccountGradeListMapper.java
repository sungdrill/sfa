package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 거래처별 등급 관리 관련 mybatis
 */
public interface AccountGradeListMapper {
    public int selectAccountGradeListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, String>> selectAccountGradeList(Map<String, String> ajaxDTO) throws Exception;
}
