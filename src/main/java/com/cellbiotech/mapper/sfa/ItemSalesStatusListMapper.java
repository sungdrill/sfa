package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 담당별/품목별 매출 현황 관련 mybatis
 */
public interface ItemSalesStatusListMapper {
    public List<Map<String, Object>> selectItemSalesStatusList(Map<String, String> ajaxDTO) throws Exception;
}
