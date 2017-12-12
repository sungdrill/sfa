package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 담당자별 종합실적(callnote) 관련 mybatis
 */
public interface CpListMapper {
    public int selectCpListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, Object>> selectCpList(Map<String, String> ajaxDTO) throws Exception;
}
