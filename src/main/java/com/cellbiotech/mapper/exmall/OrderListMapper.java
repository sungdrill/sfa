package com.cellbiotech.mapper.exmall;

import java.util.List;
import java.util.Map;

/**
 * 외부몰
 */
public interface OrderListMapper {
    public int selectOrderListCount() throws Exception;
    public List<Map<String, Object>> selectOrderList(Map<String, Object> ajaxDTO) throws Exception;
}
