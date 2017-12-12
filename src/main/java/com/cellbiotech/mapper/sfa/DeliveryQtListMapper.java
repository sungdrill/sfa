package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 배송실 재고 수량 mybatis
 */
public interface DeliveryQtListMapper {
    public List<Map<String, String>> selectDeliveryQtList(Map<String, String> ajaxDTO) throws Exception;
}
