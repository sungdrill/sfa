package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 등급별 거래처 현황 관련 mybatis
 */
public interface AccountStatusListMapper {
    public List<Map<String, Object>> selectAccountStatusList(Map<String, String> ajaxDTO) throws Exception;
}
