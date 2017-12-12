package com.cellbiotech.controller;

import com.cellbiotech.mapper.neoe.NeoeListMapper;
import com.cellbiotech.mapper.sfa.*;
import com.cellbiotech.model.sfa.ItemHistoryList;
import com.cellbiotech.util.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 재고관리 관련 컨트롤러
 * POST, AJAX 맵핑관련
 */
@Controller
public class NeoeController {

    @Autowired
    private NeoeListMapper neoeListMapper;

    @Autowired
    private ProdReleaseListMapper prodReleaseListMapper;

    @Autowired
    private  DeliveryQtListMapper deliveryQtListMapper;

    @Value("${page.size}")
    private int LIST_PAGE_SIZE;


    /**
     * 재고현황 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/neoe/neoeList.ajax")
    public ResponseEntity<?> neoeNeoeListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        String strDate = "";
        if (ajaxDTO.get("searchDateInput").isEmpty()) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            strDate = df.format(today);
        } else {
            strDate = ajaxDTO.get("searchDateInput");
        }
        ajaxDTO.put("searchDateInput", strDate.replaceAll("-", ""));
        int totalCnt = neoeListMapper.selectNeoeListCount(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(200);//재고현황은 한번에 보이도록 작성
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, String>> pageList = neoeListMapper.selectNeoeList(ajaxDTO);
        List<Map<String, String>> prodList = prodReleaseListMapper.selectProdReleaseList(ajaxDTO);
        List<Map<String, String>> deliveryQtList = deliveryQtListMapper.selectDeliveryQtList(ajaxDTO);

        List<Map<String, String>> newList = new ArrayList<>();
        List<Map<String, String>> delQtList = new ArrayList<>();
        for (Map<String, String> neoeMap : pageList) {

            for (Map<String, String> prodMap : prodList) {
                if(neoeMap.get("CD_ITEM").equals(prodMap.get("ITEM_CODE"))) {
                    neoeMap.put("dQt", prodMap.get("dQt"));
                    neoeMap.put("dSum", prodMap.get("dSum"));
                    neoeMap.put("eQt", prodMap.get("eQt"));
                    neoeMap.put("eSum", prodMap.get("eSum"));
                } else {
                }
            }
            newList.add(neoeMap);

            for (Map<String, String> prodMap : deliveryQtList) {
                if(neoeMap.get("CD_ITEM").equals(prodMap.get("cdItem"))) {
                    neoeMap.put("qtGoodInv", prodMap.get("qtGoodInv"));
                } else {}
            }
            delQtList.add(neoeMap);
        }

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList);
        rtn.put("prodList", prodList);
        rtn.put("newList", newList);
        rtn.put("delQtList", delQtList);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 품목코드에 따른 재고현황 상세 리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/neoe/neoeDetailList.ajax")
    public ResponseEntity<?> imaItemHistoryListByItemCodeAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String strDate = "";
        if (ajaxDTO.get("searchDateInput").isEmpty()) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            strDate = df.format(today);
        } else {
            strDate = ajaxDTO.get("searchDateInput");
        }
        String searchDate = strDate.replaceAll("-", "");
        String compareDate = searchDate.substring(0,4);
        ajaxDTO.put("searchDateInput", searchDate);
        ajaxDTO.put("compareDate", compareDate);
        List<Map<String, String>> detailList = neoeListMapper.selectNeoeDetailList(ajaxDTO);
        List<Map<String, String>> prodDetailList = prodReleaseListMapper.selectProdReleaseDetailList(ajaxDTO);

        List<Map<String, String>> newList = new ArrayList<>();
        for (Map<String, String> neoeMap : detailList) {

            for (Map<String, String> prodMap : prodDetailList) {
                if(neoeMap.get("CD_ITEM").equals(prodMap.get("ITEM_CODE")) && neoeMap.get("DT").equals(prodMap.get("UPDATE_DATE"))) {
                    neoeMap.put("qt", prodMap.get("qt"));
                    neoeMap.put("dQt", prodMap.get("dQt"));
                    neoeMap.put("dSum", prodMap.get("dSum"));
                    neoeMap.put("eQt", prodMap.get("eQt"));
                    neoeMap.put("eSum", prodMap.get("eSum"));
                } else {
                }
            }
            newList.add(neoeMap);
        }

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", newList);
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

}
