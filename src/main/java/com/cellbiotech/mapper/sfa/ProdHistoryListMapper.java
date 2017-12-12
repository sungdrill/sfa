package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 재고관리 - 상품 재고현황 리스트 mybatis
 */
public interface ProdHistoryListMapper {
    public int selectProdStandardListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, String>> selectProdStandardList(Map<String, String> ajaxDTO) throws Exception;

    public int selectProdHistoryListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, String>> selectProdHistoryList(Map<String, String> ajaxDTO) throws Exception;
}
