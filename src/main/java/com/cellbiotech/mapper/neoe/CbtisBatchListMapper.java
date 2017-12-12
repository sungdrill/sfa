package com.cellbiotech.mapper.neoe;

import java.util.List;
import java.util.Map;

/**
 * 상품 입출고 현황 - 배송실 입고 현황
 */
public interface CbtisBatchListMapper {
    public List<Map<String, Object>> selectCbtisBatchList(Map<String, Object> ajaxDTO) throws Exception;
}
