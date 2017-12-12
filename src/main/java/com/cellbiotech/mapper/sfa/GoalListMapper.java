package com.cellbiotech.mapper.sfa;

import java.util.List;
import java.util.Map;

import com.cellbiotech.model.sfa.GoalListVO;

/**
 * 담당자 목표 관리 관련 mybatis
 */
public interface GoalListMapper {
    public int selectGoalListCount(Map<String, String> ajaxDTO) throws Exception;
    public List<GoalListVO> selectGoalList(Map<String, String> ajaxDTO) throws Exception;
    public void mergeGoalInfo(Map<String, String> ajaxDTO) throws Exception;
}
