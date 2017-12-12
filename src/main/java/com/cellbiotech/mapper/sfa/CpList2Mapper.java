package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 담당자별 종합실적(sfa) 관련 mybatis
 */
public interface CpList2Mapper {
    public int selectCpList2Count(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, Object>> selectCpList2(Map<String, String> ajaxDTO) throws Exception;
}
