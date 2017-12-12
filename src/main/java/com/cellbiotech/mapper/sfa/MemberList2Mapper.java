package com.cellbiotech.mapper.sfa;

import com.cellbiotech.model.callnote.MemberVO;
import com.cellbiotech.model.sfa.User;

import java.util.List;
import java.util.Map;

/**
 * 영업담당자(sfa) 관련 mybatis
 */
public interface MemberList2Mapper {
    public int selectMemberList2Count(Map<String, String> ajaxDTO) throws Exception;
    public List<Map<String, String>> selectMemberList2(Map<String, String> ajaxDTO) throws Exception;
}
