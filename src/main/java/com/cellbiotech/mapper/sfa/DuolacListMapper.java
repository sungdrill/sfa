package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 판매현황 - 자사몰 판매현황 리스트 mybatis
 */
public interface DuolacListMapper {
    public int selectDuolacListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, String>> selectDuolacList(Map<String, String> ajaxDTO) throws Exception;
}
