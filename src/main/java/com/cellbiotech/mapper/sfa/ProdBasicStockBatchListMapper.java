package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 상품 재고 현황 - 배송실 기초재고
 */
public interface ProdBasicStockBatchListMapper {
    public List<Map<String, String>> selectProdBasicStockBatchList(Map<String, String> ajaxDTO) throws Exception;
}
