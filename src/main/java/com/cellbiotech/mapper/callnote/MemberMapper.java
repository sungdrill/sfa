package com.cellbiotech.mapper.callnote;

import java.util.List;
import java.util.Map;

import com.cellbiotech.model.callnote.MemberVO;

/**
 * callNOte 데이터베이스 영업담당자 관련 mybatis
 */
public interface MemberMapper {
    public int selectMemberListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<MemberVO> selectMemberList(Map<String, String> ajaxDTO) throws Exception;
}
