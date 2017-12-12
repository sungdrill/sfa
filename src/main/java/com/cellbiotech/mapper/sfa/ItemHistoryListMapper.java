package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 재고관리 - 품목 재고현황 리스트 mybatis
 */
public interface ItemHistoryListMapper {
    public int selectItemHistoryListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, String>> selectItemHistoryList(Map<String, String> ajaxDTO) throws Exception;
}
