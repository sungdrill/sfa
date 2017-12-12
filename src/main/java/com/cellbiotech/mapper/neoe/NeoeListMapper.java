package com.cellbiotech.mapper.neoe;

import java.util.List;
import java.util.Map;

/**
 * 재고관리 - 품목 재고현황 리스트 mybatis
 */
public interface NeoeListMapper {
    public int selectNeoeListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, String>> selectNeoeList(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, String>> selectNeoeDetailList(Map<String, String> ajaxDTO) throws Exception;
}
