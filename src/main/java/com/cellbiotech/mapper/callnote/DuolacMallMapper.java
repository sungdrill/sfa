package com.cellbiotech.mapper.callnote;

import java.util.List;
import java.util.Map;

/**
 * 듀오락몰 매출 관련 mybatis
 */
public interface DuolacMallMapper {
    public List<Map<String, Object>> selectDuolacList(Map<String, Object> ajaxDTO) throws Exception;
}
