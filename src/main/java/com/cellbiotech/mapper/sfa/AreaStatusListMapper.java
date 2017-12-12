package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 지역별 약국 거래율 현황 관련 mybatis
 */
public interface AreaStatusListMapper {
    public Map<String, Object> selectAreaStatusListTotal(Map<String, String> ajaxDTO) throws Exception;
    public int selectAreaStatusListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, Object>> selectAreaStatusList(Map<String, String> ajaxDTO) throws Exception;
}
