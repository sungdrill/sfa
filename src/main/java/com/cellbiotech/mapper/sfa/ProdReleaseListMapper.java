package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 재고관리 - 상품 재고현황 리스트 mybatis
 */
public interface ProdReleaseListMapper {
    public List<Map<String, String>> selectProdReleaseList(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, String>> selectProdReleaseDetailList(Map<String, String> ajaxDTO) throws Exception;
}
