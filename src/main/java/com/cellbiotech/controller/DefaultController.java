package com.cellbiotech.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cellbiotech.mapper.sfa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cellbiotech.model.sfa.User;
import com.cellbiotech.service.UserService;
import com.cellbiotech.util.PagingUtil;

/**
 * 모든 권한에 대한 컨트롤러
 */
@Controller
public class DefaultController {

    @Autowired
    private UserService userService;

    @Autowired
    private MemberList2Mapper memberList2Mapper;

    @Autowired
    private CpListMapper cpListMapper;

    @Autowired
    private CpList2Mapper cpList2Mapper;

    @Autowired
    private AccountStatusListMapper accountStatusListMapper;

    @Autowired
    private ItemSalesStatusListMapper itemSalesStatusListMapper;

    @Autowired
    private AreaStatusListMapper areaStatusListMapper;

    @Value("${page.size}")
    private int LIST_PAGE_SIZE;

    @RequestMapping(value={"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserById(auth.getName());
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());
        System.out.println("auth :: getAuthorities() :: "+auth.getAuthorities());
        System.out.println("auth :: getCredentials() :: "+auth.getCredentials());
        System.out.println("auth :: getPrincipal() :: "+auth.getPrincipal());
        System.out.println("auth :: getDetails() :: "+auth.getDetails());
        System.out.println("user :: "+user);
        String message = "";
        if (user != null) {
            modelAndView.addObject("userName", "Welcome " + user.getLastName() + " " + user.getName() + " (" + user.getId() + ")");
            modelAndView.addObject("adminMessage","Content Available Only for Users with "+ auth.getAuthorities() +" Role");
            modelAndView.addObject("module", "home");
            modelAndView.setViewName("home");
        } else {
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }


    /**
     * 담당자별 종합실적(callnote)
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/cpList.ajax")
    public ResponseEntity<?> cpListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        System.out.println("searchYearMonth :: "+ajaxDTO.get("searchYearMonth"));
        String[] searchYearMonth = (ajaxDTO.get("searchYearMonth")).split("-");
        ajaxDTO.put("goalMonth", searchYearMonth[1]);
        ajaxDTO.put("searchYear", searchYearMonth[0]);
        System.out.println("goalMonth : "+searchYearMonth[1]);
        System.out.println("searchYear : "+searchYearMonth[0]);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(searchYearMonth[0]), Integer.parseInt(searchYearMonth[1])-1, Integer.parseInt("1"));
        int year = cal.get ( cal.YEAR );
        int month = cal.get ( cal.MONTH )+1 ;
        int startDay = cal.get(cal.DAY_OF_MONTH);
        int endDay = cal.getActualMaximum(cal.DAY_OF_MONTH);

        String fromYMD = year+"-"+month+"-"+startDay;
        String toYMD = year+"-"+month+"-"+endDay;
        System.out.println("fromYMD :: "+fromYMD);
        System.out.println("toYMD :: "+toYMD);
        ajaxDTO.put("startSearchDate", fromYMD);
        ajaxDTO.put("endSearchDate", toYMD);



        List<Map<String, Object>> pageList = null;
        pageList = cpListMapper.selectCpList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList);
        rtn.put("totalPageCount", 1);
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 담당자별 종합실적(sfa)
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/cpList2.ajax")
    public ResponseEntity<?> cpList2Ajax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        String[] searchYearMonth = (ajaxDTO.get("searchYearMonth")).split("-");
        ajaxDTO.put("goalMonth", searchYearMonth[1]);
        ajaxDTO.put("searchYear", searchYearMonth[0]);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(searchYearMonth[0]), Integer.parseInt(searchYearMonth[1])-1, Integer.parseInt("1"));
        int year = cal.get ( cal.YEAR );
        int month = cal.get ( cal.MONTH )+1 ;
        int startDay = cal.get(cal.DAY_OF_MONTH);
        int endDay = cal.getActualMaximum(cal.DAY_OF_MONTH);

        String fromYMD = year+"-"+month+"-"+startDay;
        String toYMD = year+"-"+month+"-"+endDay;
        ajaxDTO.put("startSearchDate", fromYMD);
        ajaxDTO.put("endSearchDate", toYMD);

        List<Map<String, Object>> pageList = null;
        pageList = cpList2Mapper.selectCpList2(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList);
        rtn.put("totalCount", memberList2Mapper.selectMemberList2Count(ajaxDTO));   // 담당자수
        rtn.put("totalPageCount", 1);
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 등급별 거래처 현황
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/getAccountStatusList.ajax")
    public ResponseEntity<?> getAccountStatusListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        String[] searchYearMonth = (ajaxDTO.get("searchYearMonth")).split("-");
        ajaxDTO.put("goalMonth", searchYearMonth[1]);
        ajaxDTO.put("searchYear", searchYearMonth[0]);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(searchYearMonth[0]), Integer.parseInt(searchYearMonth[1])-1, Integer.parseInt("1"));
        int year = cal.get ( cal.YEAR );
        int month = cal.get ( cal.MONTH )+1 ;
        int startDay = cal.get(cal.DAY_OF_MONTH);
        int endDay = cal.getActualMaximum(cal.DAY_OF_MONTH);

        String fromYMD = year+"-"+month+"-"+startDay;
        String toYMD = year+"-"+month+"-"+endDay;
        ajaxDTO.put("startSearchDate", fromYMD);
        ajaxDTO.put("endSearchDate", toYMD);

        List<Map<String, Object>> pageList = null;
        pageList = accountStatusListMapper.selectAccountStatusList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList);
        rtn.put("totalPageCount", 1);
        rtn.put("totalCount", memberList2Mapper.selectMemberList2Count(ajaxDTO)); // 담당자수
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 담당별/품목별 매출 현황
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/getItemSalesStatusList.ajax")
    public ResponseEntity<?> getItemSalesStatusListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        String[] searchYearMonth = (ajaxDTO.get("searchYearMonth")).split("-");
        ajaxDTO.put("goalMonth", searchYearMonth[1]);
        ajaxDTO.put("searchYear", searchYearMonth[0]);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(searchYearMonth[0]), Integer.parseInt(searchYearMonth[1])-1, Integer.parseInt("1"));
        int year = cal.get ( cal.YEAR );
        int month = cal.get ( cal.MONTH )+1 ;
        int startDay = cal.get(cal.DAY_OF_MONTH);
        int endDay = cal.getActualMaximum(cal.DAY_OF_MONTH);

        String fromYMD = year+"-"+month+"-"+startDay;
        String toYMD = year+"-"+month+"-"+endDay;
        ajaxDTO.put("startSearchDate", fromYMD);
        ajaxDTO.put("endSearchDate", toYMD);

        List<Map<String, Object>> pageList = null;
        pageList = itemSalesStatusListMapper.selectItemSalesStatusList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList);
        rtn.put("totalPageCount", 1);
        rtn.put("totalCount", memberList2Mapper.selectMemberList2Count(ajaxDTO));   // 담당자수
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 지역별 약국 거래율 현황
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/getAreaStatusList.ajax")
    public ResponseEntity<?> getAreaStatusListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        String[] searchYearMonth = (ajaxDTO.get("searchYearMonth")).split("-");
        ajaxDTO.put("goalMonth", searchYearMonth[1]);
        ajaxDTO.put("searchYear", searchYearMonth[0]);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(searchYearMonth[0]), Integer.parseInt(searchYearMonth[1])-1, Integer.parseInt("1"));
        int year = cal.get ( cal.YEAR );
        int month = cal.get ( cal.MONTH )+1 ;
        int startDay = cal.get(cal.DAY_OF_MONTH);
        int endDay = cal.getActualMaximum(cal.DAY_OF_MONTH);

        String fromYMD = year+"-"+month+"-"+startDay;
        String toYMD = year+"-"+month+"-"+endDay;
        ajaxDTO.put("startSearchDate", fromYMD);
        ajaxDTO.put("endSearchDate", toYMD);

        int totalCnt = areaStatusListMapper.selectAreaStatusListCount(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(LIST_PAGE_SIZE);
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, Object>> pageList = areaStatusListMapper.selectAreaStatusList(ajaxDTO);
        Map<String, Object> totalInfo = areaStatusListMapper.selectAreaStatusListTotal(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList);
        rtn.put("totalInfo", totalInfo);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());


        return new ResponseEntity(rtn, HttpStatus.OK);
    }

}
