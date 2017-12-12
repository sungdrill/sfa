package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 재고관리 - 기초 재고 관리 리스트
 */
public interface BasicStockListMapper {
    public int selectBasicStockListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, String>> selectBasicStockList(Map<String, String> ajaxDTO) throws Exception;
}
