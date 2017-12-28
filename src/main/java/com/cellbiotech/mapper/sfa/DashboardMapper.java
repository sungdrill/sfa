package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 대시보드 관련 mybatis
 */
public interface DashboardMapper {
    // TODO 제품명 가져오기 String
    public List<Map<String, Object>> selectItemName(Map<String, String> ajaxDTO) throws Exception;
    // TODO 배송실 재고 수량 가져오기 Integer
    public List<Map<String, Object>> selectDeliveryQty(Map<String, String> ajaxDTO) throws Exception;
    // TODO 자사몰 판매량 가져오기    Integer
    public List<Map<String, Object>> selectDuolacQty(Map<String, String> ajaxDTO) throws Exception;
    // TODO 외부몰 판매량 가져오기    Integer
    public List<Map<String, Object>> selectExmallQty(Map<String, String> ajaxDTO) throws Exception;
}
