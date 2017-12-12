package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 재고관리 - 품목-상품 관리 관련 mybatis
 */
public interface ItemProdListMapper {
    public int selectItemProdListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, String>> selectItemProdList(Map<String, String> ajaxDTO) throws Exception;
}
