package com.cellbiotech.controller;

import com.cellbiotech.dao.sfa.ExmallListRepository;
import com.cellbiotech.mapper.exmall.OrderListMapper;
import com.cellbiotech.model.sfa.ExmallList;
import com.cellbiotech.model.sfa.ExmallListId;
import com.cellbiotech.util.DateUtils;
import com.cellbiotech.util.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * INV 권한 관련 컨트롤러
 * POST, AJAX 맵핑관련
 */
@Controller
public class ExmallController {

    @Autowired
    private OrderListMapper orderListMapper;

    @Autowired
    private ExmallListRepository exmallListRepository;

    @Value("${page.size}")
    private int LIST_PAGE_SIZE;

    /**
     * 외부몰 리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/exmall/orderList.ajax")
    public ResponseEntity<?> adminMemberListAjax(@RequestParam Map<String, Object> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.valueOf((String) ajaxDTO.get("pageNumber"));
        }

        int totalCnt = orderListMapper.selectOrderListCount();

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(LIST_PAGE_SIZE);
        ajaxDTO.put("startPageNum", pageInfo.getStartPageNum()-1);
        System.out.println("startPageNum :: "+(pageInfo.getStartPageNum()-1));
        System.out.println("listPageSize :: "+LIST_PAGE_SIZE);
        ajaxDTO.put("listPageSize", LIST_PAGE_SIZE);

        /*
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse("2016-05-25");

            for (int i = 1; i<365+289; i++) {

                // 날짜 더하기
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, i);
//            cal.add(Calendar.MONTH, 2);

                System.err.println(df.format(cal.getTime()));

                ajaxDTO.put("regDate", df.format(cal.getTime()));
                List<Map<String, Object>> list = orderListMapper.selectOrderList(ajaxDTO);

                List<ExmallList> exmallList = new ArrayList<>();

                for (Map orderInfo : list) {
                    ExmallList exmallInfo = new ExmallList();
                    exmallInfo.setId(new ExmallListId(DateUtils.getStringtoDate((String) orderInfo.get("uploadDate"), "-"), (String) orderInfo.get("prodno"), (String) orderInfo.get("omDesc")));
                    exmallInfo.setProdName((String) orderInfo.get("prodnm"));
                    exmallInfo.setExmallName((String) orderInfo.get("omDesc"));
                    exmallInfo.setProdQty(Integer.parseInt(orderInfo.get("sqty").toString()));
                    exmallList.add(exmallInfo);
                }

                if (exmallList.size() > 0) {
                    exmallListRepository.save(exmallList);
                }

            }
            */

            /*
        List<Map<String, Object>> list = orderListMapper.selectOrderList(ajaxDTO);

        List<ExmallList> exmallList = new ArrayList<>();

        for (Map orderInfo : list) {
            ExmallList exmallInfo = new ExmallList();
            exmallInfo.setId(new ExmallListId(DateUtils.getStringtoDate((String) orderInfo.get("uploadDate"), "-"), (String) orderInfo.get("prodno"), (String) orderInfo.get("omDesc")));
            exmallInfo.setProdName((String) orderInfo.get("prodnm"));
            exmallInfo.setExmallName((String) orderInfo.get("omDesc"));
            exmallInfo.setProdQty(Integer.parseInt(orderInfo.get("sqty").toString()));
            exmallList.add(exmallInfo);
        }

        if (exmallList.size() > 0) {
            exmallListRepository.save(exmallList);
        }
        */

        Map<String, Object> rtn = new HashMap<>();
        //rtn.put("list", list);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

}
