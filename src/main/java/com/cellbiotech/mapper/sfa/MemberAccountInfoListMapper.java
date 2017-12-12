package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

/**
 * 영업담당자 거래처정보 관리 관련 mybatis
 */
public interface MemberAccountInfoListMapper {
    public int selectMemberAccountInfoListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, String>> selectMemberAccountInfoList(Map<String, String> ajaxDTO) throws Exception;
    public void insertMemberAccountInfo(Map<String, Object> ajaxDTO) throws Exception;
}
