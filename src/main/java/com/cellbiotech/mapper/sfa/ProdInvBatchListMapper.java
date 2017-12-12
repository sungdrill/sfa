package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 상품 입출고 현황 - 배송실 입고 현황
 */
public interface ProdInvBatchListMapper {
    public List<Map<String, String>> selectProdInvBatchList(Map<String, String> ajaxDTO) throws Exception;
}
