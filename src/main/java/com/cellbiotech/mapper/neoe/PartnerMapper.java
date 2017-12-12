package com.cellbiotech.mapper.neoe;

import java.util.List;
import java.util.Map;

/**
 * ERP 거래처 관련 mybatis
 */
public interface PartnerMapper {
    public int selectPartnerListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, Object>> selectPartnerList(Map<String, String> ajaxDTO) throws Exception;
}
