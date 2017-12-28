package com.cellbiotech.mapper.neoe;

import java.util.List;
import java.util.Map;

/**
 * ERP 대시보드 관련 mybatis
 */
public interface DashboardNeoeMapper {
    // TODO 창고재고 수량 가져오기
    public List<Map<String, Object>> selectWarehouseQty(Map<String, String> ajaxDTO) throws Exception;
}
