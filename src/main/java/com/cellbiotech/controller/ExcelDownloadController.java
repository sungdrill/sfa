package com.cellbiotech.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cellbiotech.dao.sfa.*;
import com.cellbiotech.mapper.sfa.*;
import com.cellbiotech.model.sfa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cellbiotech.dao.callnote.MemberListRepository;
import com.cellbiotech.mapper.callnote.MemberMapper;
import com.cellbiotech.service.UserService;
import com.cellbiotech.util.CommonUtils;
import com.cellbiotech.util.DateUtils;
import com.cellbiotech.util.excel.ExcelWriteXlsx;
import com.cellbiotech.util.excel.ExcelWriteXlsx2;

/**
 * 엑셀 다운로드 컨트롤러
 */
@Controller
public class ExcelDownloadController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    OrderListRepository orderListRepository;

    @Autowired
    private SalesDeadlineListRepository salesDeadlineListRepository;

    @Autowired
    private CollectionListRepository collectionListRepository;

    @Autowired
    private DeliveryRequestListRepository deliveryRequestListRepository;

    @Autowired
    private AccountInfoListRepository accountInfoListRepository;

    @Autowired
    private MemberListRepository memberListRepository;

    @Autowired
    private CodeListRepository codeListRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private GoalListMapper goalListMapper;

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

    @Autowired
    private UploadListRepository uploadListRepository;

    @Autowired
    private ItemListRepository itemListRepository;

    @Autowired
    private ItemHistoryListMapper itemHistoryListMapper;

    @Autowired
    private ProdListRepository prodListRepository;

    @Autowired
    private ProdReleaseListRepository prodReleaseListRepository;

    @Autowired
    private DuolacListMapper duolacListMapper;

    @PostMapping(value = "/admin/cpListExcelCount.ajax")
    public ResponseEntity<?> adminCpListListExcelCountAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {

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

        List<Map<String, Object>> pageList = cpList2Mapper.selectCpList2(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("cnt", pageList.size());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    @GetMapping(value="/admin/cpListExcel")
    public ModelAndView adminCpListExcel(@RequestParam Map<String, String> ajaxDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
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

        List<Map<String, Object>> pageList = cpList2Mapper.selectCpList2(ajaxDTO);

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("sheetname", "담당자별종합실적"+ajaxDTO.get("searchYearMonth")); //Sheet Name

        List<String> titles = new ArrayList<String>();
        titles.add("담당자별종합실적"+ajaxDTO.get("searchYearMonth"));
        titles.add("");
        titles.add("");
        model.put("titles", titles); //titles List

        List<String> headers = new ArrayList<String>();
        headers.add("소속");
        headers.add("지역");
        headers.add("담당자");
        headers.add("목표");
        headers.add("실적");
        headers.add("반품");
        headers.add("최종금액");
        headers.add("달성율");
        headers.add("잔여목표");
        headers.add("목표");
        headers.add("금액");
        headers.add("달성율");
        headers.add("처수");
        headers.add("매출");
        headers.add("처수");
        headers.add("매출");
        model.put("headers", headers); //headers List

        List<List<String>> bodyList = new ArrayList<List<String>>();
            for(Map<String, Object> cp : pageList) {

                List<String> body = new ArrayList<String>();

                // 전체합계, 소계
                if (Integer.parseInt(cp.get("num").toString()) == 9999) {
                    // 전체합계
                    if (Integer.parseInt(cp.get("teamOrders").toString()) == 7777) {
                        body.add("전체합계");
                        body.add("전체합계");
                        body.add("전체합계");
                        body.add(CommonUtils.Comma_won(cp.get("goal").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("orderAmount").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("deliveryRequestAmount").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("sealesDeadlineAmount").toString()));
                        body.add(((BigDecimal)cp.get("goalRate")).setScale(0,BigDecimal.ROUND_HALF_UP).toString()+"%");
                        body.add(CommonUtils.Comma_won(cp.get("remainingGoal").toString()));
                        body.add("목표");
                        body.add(CommonUtils.Comma_won(cp.get("collectionAmount").toString()));
                        body.add("달성율");
                        body.add("처수");
                        body.add("매출");
                        body.add("처수");
                        body.add("매출");
                        // 소계
                    } else {
                        body.add(cp.get("teamNm").toString());
                        body.add("소계");
                        body.add("소계");
                        body.add(CommonUtils.Comma_won(cp.get("goal").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("orderAmount").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("deliveryRequestAmount").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("sealesDeadlineAmount").toString()));
                        body.add(((BigDecimal)cp.get("goalRate")).setScale(0,BigDecimal.ROUND_HALF_UP).toString()+"%");
                        body.add(CommonUtils.Comma_won(cp.get("remainingGoal").toString()));
                        body.add("목표");
                        body.add(CommonUtils.Comma_won(cp.get("collectionAmount").toString()));
                        body.add("달성율");
                        body.add("처수");
                        body.add("매출");
                        body.add("처수");
                        body.add("매출");
                    }
                } else {
                    body.add(cp.get("teamNm").toString());
                    body.add(cp.get("areaNm").toString());
                    body.add(cp.get("name").toString());
                    body.add(CommonUtils.Comma_won(cp.get("goal").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("orderAmount").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("deliveryRequestAmount").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("sealesDeadlineAmount").toString()));
                    body.add(((BigDecimal)cp.get("goalRate")).setScale(0,BigDecimal.ROUND_HALF_UP).toString()+"%");
                    body.add(CommonUtils.Comma_won(cp.get("remainingGoal").toString()));
                    body.add("목표");
                    body.add(CommonUtils.Comma_won(cp.get("collectionAmount").toString()));
                    body.add("달성율");
                    body.add("처수");
                    body.add("매출");
                    body.add("처수");
                    body.add("매출");
                }
                bodyList.add(body);
            }

        model.put("body", bodyList); //body List

        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=cplist.xlsx" );
        return new ModelAndView(new ExcelWriteXlsx(), model);
    }

    /**
     * 등급별 거래처 현황 엑셀 다운로드
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/admin/accountStatusListExcelCount.ajax")
    public ResponseEntity<?> adminAccountStatusListExcelCountAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {

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

        List<Map<String, Object>> pageList = accountStatusListMapper.selectAccountStatusList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("cnt", pageList.size());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 등급별 거래처 현황 엑셀 다운로드
     * @param ajaxDTO
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping(value="/admin/accountStatusListExcel")
    public ModelAndView adminAccountStatusListExcel(@RequestParam Map<String, String> ajaxDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
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

        List<Map<String, Object>> pageList = accountStatusListMapper.selectAccountStatusList(ajaxDTO);

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("sheetname", "등급별거래처현황"+ajaxDTO.get("searchYearMonth")); //Sheet Name

        List<String> titles = new ArrayList<String>();
        titles.add("등급별거래처현황"+ajaxDTO.get("searchYearMonth"));
        titles.add("");
        titles.add("");
        model.put("titles", titles); //titles List

        List<String> headers = new ArrayList<String>();
        headers.add("소속");
        headers.add("지역");
        headers.add("담당자");
        headers.add("(전체)처수");
        headers.add("(전체)금액");
        headers.add("(A등급)처수");
        headers.add("(A등급)금액");
        headers.add("(B등급)처수");
        headers.add("(B등급)금액");
        headers.add("(C등급)처수");
        headers.add("(C등급)금액");
        headers.add("(D등급)처수");
        headers.add("(D등급)금액");
        headers.add("(E등급)처수");
        headers.add("(E등급)금액");
        model.put("headers", headers); //headers List

        List<List<String>> bodyList = new ArrayList<List<String>>();
            for(Map<String, Object> cp : pageList) {

                List<String> body = new ArrayList<String>();

                // 전체합계, 소계
                if (Integer.parseInt(cp.get("num").toString()) == 9999) {
                    // 전체합계
                    if (Integer.parseInt(cp.get("teamOrders").toString()) == 7777) {
                        body.add("전체합계");
                        body.add("전체합계");
                        body.add("전체합계");
                        body.add(CommonUtils.Comma_won(cp.get("totalCnt").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalTotal").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("aCnt").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("aTotal").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("bCnt").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("bTotal").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("cCnt").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("cTotal").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("dCnt").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("dTotal").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("eCnt").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("eTotal").toString()));
                        // 소계
                    } else {
                        body.add(cp.get("teamNm").toString());
                        body.add("소계");
                        body.add("소계");
                        body.add(CommonUtils.Comma_won(cp.get("totalCnt").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalTotal").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("aCnt").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("aTotal").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("bCnt").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("bTotal").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("cCnt").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("cTotal").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("dCnt").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("dTotal").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("eCnt").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("eTotal").toString()));
                    }
                } else {
                    body.add(cp.get("teamNm").toString());
                    body.add(cp.get("areaNm").toString());
                    body.add(cp.get("name").toString());
                    body.add(CommonUtils.Comma_won(cp.get("totalCnt").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalTotal").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("aCnt").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("aTotal").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("bCnt").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("bTotal").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("cCnt").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("cTotal").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("dCnt").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("dTotal").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("eCnt").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("eTotal").toString()));
                }
                bodyList.add(body);
            }

        model.put("body", bodyList); //body List

        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=accountStatusList.xlsx" );
        return new ModelAndView(new ExcelWriteXlsx(), model);
    }

    /**
     * 담당별/품목별 매출 현황 엑셀 다운로드
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/admin/itemSalesStatusListExcelCount.ajax")
    public ResponseEntity<?> adminItemSalesStatusListExcelCountAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {

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

        List<Map<String, Object>> pageList = itemSalesStatusListMapper.selectItemSalesStatusList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("cnt", pageList.size());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 담당별/품목별 매출 현황 엑셀 다운로드
     * @param ajaxDTO
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping(value="/admin/itemSalesStatusListExcel")
    public ModelAndView adminItemSalesStatusListExcel(@RequestParam Map<String, String> ajaxDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
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

        List<Map<String, Object>> pageList = itemSalesStatusListMapper.selectItemSalesStatusList(ajaxDTO);

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("sheetname", "담당별_품목별매출현황"+ajaxDTO.get("searchYearMonth")); //Sheet Name

        List<String> titles = new ArrayList<String>();
        titles.add("담당별_품목별매출현황"+ajaxDTO.get("searchYearMonth"));
        titles.add("");
        titles.add("");
        model.put("titles", titles); //titles List

        List<String> headers = new ArrayList<String>();
        headers.add("소속");
        headers.add("지역");
        headers.add("담당자");
        headers.add("골드");
        headers.add("골드 세트(130포)");
        headers.add("골드 본품(60포)-리뉴얼");
        headers.add("골드 본품(60포)");
        headers.add("베이비");
        headers.add("베이비 세트(120포)");
        headers.add("베이비 본품(60포)");
        headers.add("얌얌");
        headers.add("케어");
        headers.add("케어 본품(60C)-리뉴얼");
        headers.add("케어 본품(60C)");
        headers.add("에이티피");
        headers.add("에이티피 본품(70포)");
        headers.add("에이티피 세트(150포)");
        headers.add("아이비에스");
        headers.add("아이비에스_신(30C)");
        headers.add("아이비에스_신(60C)");
        headers.add("듀오락스탑");
        headers.add("듀오자임 플러스");
        headers.add("듀오락12");
        headers.add("키즈");
        model.put("headers", headers); //headers List

        List<List<String>> bodyList = new ArrayList<List<String>>();
            for(Map<String, Object> cp : pageList) {

                List<String> body = new ArrayList<String>();

                // 전체합계, 소계
                if (Integer.parseInt(cp.get("num").toString()) == 9999) {
                    // 전체합계
                    if (Integer.parseInt(cp.get("teamOrders").toString()) == 7777) {
                        body.add("전체합계");
                        body.add("전체합계");
                        body.add("전체합계");
                        body.add(CommonUtils.Comma_won(cp.get("totalGold").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalJJJ").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalKKK").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalLLL").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalBaby").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalMMM").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalNNN").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalOOO").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalCare").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalPPP").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalQQQ").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalAtp").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalIII").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalHHH").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalIbs").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalGGG").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalFFF").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalEEE").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalBBB").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalCCC").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalDDD").toString()));
                        // 소계
                    } else {
                        body.add(cp.get("teamNm").toString());
                        body.add("소계");
                        body.add("소계");
                        body.add(CommonUtils.Comma_won(cp.get("totalGold").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalJJJ").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalKKK").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalLLL").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalBaby").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalMMM").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalNNN").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalOOO").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalCare").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalPPP").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalQQQ").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalAtp").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalIII").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalHHH").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalIbs").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalGGG").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalFFF").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalEEE").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalBBB").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalCCC").toString()));
                        body.add(CommonUtils.Comma_won(cp.get("totalDDD").toString()));
                    }
                } else {
                    body.add(cp.get("teamNm").toString());
                    body.add(cp.get("areaNm").toString());
                    body.add(cp.get("name").toString());
                    body.add(CommonUtils.Comma_won(cp.get("totalGold").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalJJJ").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalKKK").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalLLL").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalBaby").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalMMM").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalNNN").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalOOO").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalCare").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalPPP").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalQQQ").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalAtp").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalIII").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalHHH").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalIbs").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalGGG").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalFFF").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalEEE").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalBBB").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalCCC").toString()));
                    body.add(CommonUtils.Comma_won(cp.get("totalDDD").toString()));
                }
                bodyList.add(body);
            }

        model.put("body", bodyList); //body List

        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=itemSalesStatusList.xlsx" );
        return new ModelAndView(new ExcelWriteXlsx(), model);
    }

    /**
     * 지역별 약국 거래율 현황 엑셀 다운로드
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/admin/areaStatusListExcelCount.ajax")
    public ResponseEntity<?> adminAreaStatusListExcelCountAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {

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

        int pageCnt = areaStatusListMapper.selectAreaStatusListCount(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("cnt", pageCnt);
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 지역별 약국 거래율 현황 엑셀 다운로드
     * @param ajaxDTO
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping(value="/admin/areaStatusListExcel")
    public ModelAndView adminAreaStatusListExcel(@RequestParam Map<String, String> ajaxDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
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

        List<Map<String, Object>> pageList = areaStatusListMapper.selectAreaStatusList(ajaxDTO);
        Map<String, Object> totalInfo = areaStatusListMapper.selectAreaStatusListTotal(ajaxDTO);

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("sheetname", "지역별약국거래율현황"+ajaxDTO.get("searchYearMonth")); //Sheet Name

        List<String> titles = new ArrayList<String>();
        titles.add("지역별약국거래율현황"+ajaxDTO.get("searchYearMonth"));
        titles.add("");
        titles.add("");
        model.put("titles", titles); //titles List

        List<String> headers = new ArrayList<String>();
        headers.add("팀");
        headers.add("영업소");
        headers.add("담당자");
        headers.add("행정구역");
        headers.add("전체거래처수");
        headers.add("실거래처");
        headers.add("매출");
        model.put("headers", headers); //headers List

        List<List<String>> bodyList = new ArrayList<List<String>>();
        List<String> totalInfoBody = new ArrayList<String>();
        totalInfoBody.add("합계");
        totalInfoBody.add("합계");
        totalInfoBody.add("합계");
        totalInfoBody.add("합계");
        totalInfoBody.add(CommonUtils.Comma_won(totalInfo.get("total1").toString()));
        totalInfoBody.add(CommonUtils.Comma_won(totalInfo.get("total").toString()));
        totalInfoBody.add(CommonUtils.Comma_won(totalInfo.get("totalAmt").toString()));
        bodyList.add(totalInfoBody);
        for(Map<String, Object> cp : pageList) {

            List<String> body = new ArrayList<String>();

            body.add(cp.get("TEAM_NM").toString());
            body.add(cp.get("AREA_NM").toString());
            body.add(cp.get("name").toString());
            body.add(cp.get("adName").toString());
            body.add(CommonUtils.Comma_won(cp.get("total1").toString()));
            body.add(CommonUtils.Comma_won(cp.get("total").toString()));
            body.add(CommonUtils.Comma_won(cp.get("totalAmt").toString()));

            bodyList.add(body);
        }

        model.put("body", bodyList); //body List

        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=areaStatusList.xlsx" );
        return new ModelAndView(new ExcelWriteXlsx(), model);
    }

    @PostMapping(value = "/admin/orderListExcelCount.ajax")
    public ResponseEntity<?> adminOrderListExcelCountAjax(@RequestParam Map<String, String> ajaxDTO) throws ParseException {

        System.out.println("COUNT :: "+orderListRepository.count());

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("cnt", orderListRepository.count());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    @GetMapping(value="/admin/orderListExcel")
    public ModelAndView adminOrderListExcel(@RequestParam Map<String, String> ajaxDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<OrderList> list = null;
        if (!ajaxDTO.get("searchYearMonth2").isEmpty()) {

        } else {

        }

        if (!ajaxDTO.get("searchYearMonth2").isEmpty()) {
            String fromDate = ajaxDTO.get("searchYearMonth2") + "-01";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String toDate = ajaxDTO.get("searchYearMonth2") + "-" + DateUtils.getEndDay(fromDate);
            list = orderListRepository.findAllByIdOrderNumContainingAndAccountNameContainingAndAccountCodeContainingAndOrderDateBetween(ajaxDTO.get("orderNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"), df.parse(fromDate), df.parse(toDate));
        } else {
            list = orderListRepository.findAllByIdOrderNumContainingAndAccountNameContainingAndAccountCodeContaining(ajaxDTO.get("orderNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"));
        }

        System.out.println("searchYearMonth2 :: "+ajaxDTO.get("searchYearMonth2"));

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("sheetname", "수주리스트"); //Sheet Name

        List<String> titles = new ArrayList<String>();
        titles.add("수주리스트");
        titles.add("");
        titles.add("");
        model.put("titles", titles); //titles List

        List<String> headers = new ArrayList<String>();
        headers.add("수주번호");
        headers.add("NO");
        headers.add("거래처코드");
        headers.add("거래처");
        headers.add("영업그룹코드");
        headers.add("영업그룹");
        headers.add("품목코드");
        headers.add("품목");
        headers.add("수주일자");
        headers.add("규격");
        headers.add("수주담당자");
        headers.add("수량");
        headers.add("단가");
        headers.add("금액");
        headers.add("부가세");
        headers.add("합계");
        headers.add("비고");
        headers.add("영업담당자코드");
        headers.add("영업담당자");
        model.put("headers", headers); //headers List

        List<List<String>> bodyList = new ArrayList<List<String>>();
            for(OrderList item : list) {

                List<String> body = new ArrayList<String>();

                body.add(item.getId().getOrderNum());
                body.add(item.getId().getOrderSeq());
                body.add(item.getAccountCode());
                body.add(item.getAccountName());
                body.add(item.getSalesGroupCode());
                body.add(item.getSalesGroupName());
                body.add(item.getItemCode());
                body.add(item.getItemName());
                body.add(item.getOrderDate());
                body.add(item.getStandardInfo());
                body.add(item.getOrderManager());
                body.add(CommonUtils.Comma_won(item.getQty().toString()));
                body.add(CommonUtils.Comma_won(item.getUnitPrice().toString()));
                body.add(CommonUtils.Comma_won(item.getAmount().toString()));
                body.add(CommonUtils.Comma_won(item.getSuperTax().toString()));
                body.add(CommonUtils.Comma_won(item.getSumAmount().toString()));
                body.add(item.getRemarks());
                bodyList.add(body);
            }

        model.put("body", bodyList); //body List
        System.out.println(bodyList.size());

        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=orderlist.xlsx" );
        return new ModelAndView(new ExcelWriteXlsx2(), model);
    }
    @PostMapping(value = "/admin/salesDeadlineListExcelCount.ajax")
    public ResponseEntity<?> adminSalesDeadlineListExcelCountAjax(@RequestParam Map<String, String> ajaxDTO) throws ParseException {

        System.out.println("COUNT :: "+salesDeadlineListRepository.count());

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("cnt", salesDeadlineListRepository.count());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    @GetMapping(value="/admin/salesDeadlineListExcel")
    public ModelAndView adminSalesDeadlineListExcel(@RequestParam Map<String, String> ajaxDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<SalesDeadlineList> list = null;
        Date from = null;
        Date to = null;
        if (!ajaxDTO.get("searchYearMonth2").isEmpty()) {
            String fromDate = ajaxDTO.get("searchYearMonth2") + "-01";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String toDate = ajaxDTO.get("searchYearMonth2") + "-" + DateUtils.getEndDay(fromDate);
            from = df.parse(fromDate);
            to = df.parse(toDate);
            list = salesDeadlineListRepository.findAllByIdSalesNumContainingAndAccountNameContainingAndAccountCodeContainingAndItemNameContainingAndIdItemCodeContainingAndSalesDateBetween(ajaxDTO.get("salesNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"), ajaxDTO.get("itemName"), ajaxDTO.get("itemCode"), from, to);
        } else {

            list = salesDeadlineListRepository.findAllByIdSalesNumContainingAndAccountNameContainingAndAccountCodeContainingAndItemNameContainingAndIdItemCodeContaining(ajaxDTO.get("salesNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"), ajaxDTO.get("itemName"), ajaxDTO.get("itemCode"));
        }

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("sheetname", "매출마감리스트"); //Sheet Name

        List<String> titles = new ArrayList<String>();
        titles.add("매출마감리스트");
        titles.add("");
        titles.add("");
        model.put("titles", titles); //titles List

        List<String> headers = new ArrayList<String>();
        headers.add("매출번호");
        headers.add("전표번호");
        headers.add("품목코드");
        headers.add("매출일자");
        headers.add("품목");
        headers.add("수량");
        headers.add("단가");
        headers.add("금액");
        headers.add("부가세");
        headers.add("합계");
        headers.add("거래처코드");
        headers.add("거래처");
        headers.add("반품여부");
        model.put("headers", headers); //headers List

        List<List<String>> bodyList = new ArrayList<List<String>>();
            for(SalesDeadlineList item : list) {

                List<String> body = new ArrayList<String>();

                body.add(item.getId().getSalesNum());
                body.add(item.getId().getDocNum());
                body.add(item.getId().getItemCode());
                body.add(item.getSalesDate());
                body.add(item.getItemName());
                body.add(CommonUtils.Comma_won(item.getQty().toString()));
                body.add(CommonUtils.Comma_won(item.getUnitPrice().toString()));
                body.add(CommonUtils.Comma_won(item.getAmount().toString()));
                body.add(CommonUtils.Comma_won(item.getSuperTax().toString()));
                body.add(CommonUtils.Comma_won(item.getSumAmount().toString()));
                body.add(item.getAccountCode());
                body.add(item.getAccountName());
                body.add(item.getReturnGb());

                bodyList.add(body);
            }

        model.put("body", bodyList); //body List
        System.out.println(bodyList.size());

        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=salesDeadlineList.xlsx" );
        return new ModelAndView(new ExcelWriteXlsx2(), model);
    }

    @PostMapping(value = "/admin/collectionListExcelCount.ajax")
    public ResponseEntity<?> adminCollectionListExcelCountAjax(@RequestParam Map<String, String> ajaxDTO) throws ParseException {

        System.out.println("COUNT :: "+collectionListRepository.count());

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("cnt", collectionListRepository.count());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    @GetMapping(value="/admin/collectionListExcel")
    public ModelAndView adminCollectionListExcel(@RequestParam Map<String, String> ajaxDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<CollectionList> list = null;
        if (!ajaxDTO.get("searchYearMonth2").isEmpty()) {
            String fromDate = ajaxDTO.get("searchYearMonth2") + "-01";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String toDate = ajaxDTO.get("searchYearMonth2") + "-" + DateUtils.getEndDay(fromDate);
            list = collectionListRepository.findAllByCollectionNumContainingAndCollectionPlaceNameContainingAndCollectionPlaceCodeContainingAndCollectionDateBetween(ajaxDTO.get("collectionNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"), df.parse(fromDate), df.parse(toDate));
        } else {
            list = collectionListRepository.findAllByCollectionNumContainingAndCollectionPlaceNameContainingAndCollectionPlaceCodeContaining(ajaxDTO.get("collectionNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"));
        }

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("sheetname", "수금리스트"); //Sheet Name

        List<String> headers = new ArrayList<String>();
        headers.add("수금번호");
        headers.add("수금일");
        headers.add("수금형태코드");
        headers.add("수금형태명");
        headers.add("수금처코드");
        headers.add("수금처명");
        headers.add("영업그룹코드");
        headers.add("영업그룹명");
        headers.add("수금담당자");
        headers.add("수금담당자명");
        headers.add("수금액(원화)");
        headers.add("금융기관");
        headers.add("금융기관명");
        headers.add("영업담당자코드");
        headers.add("영업담당자");
        model.put("headers", headers); //headers List

        List<List<String>> bodyList = new ArrayList<List<String>>();
            for(CollectionList item : list) {

                List<String> body = new ArrayList<String>();

                body.add(item.getCollectionNum());
                body.add(item.getCollectionDate());
                body.add(item.getCollectionTypeCode());
                body.add(item.getCollectionTypeName());
                body.add(item.getCollectionPlaceCode());
                body.add(item.getCollectionPlaceName());
                body.add(item.getSalesGroupCode());
                body.add(item.getSalesGroupName());
                body.add(item.getCollectionManagerCode());
                body.add(item.getCollectionManagerName());
                body.add(CommonUtils.Comma_won(item.getCollectionAmount().toString()));
                body.add(item.getCollectionPlaceName());
                body.add(item.getCollectionPlaceName());

                bodyList.add(body);
            }

        model.put("body", bodyList); //body List
        System.out.println(bodyList.size());

        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=collectionList.xlsx" );
        return new ModelAndView(new ExcelWriteXlsx2(), model);
    }

    @PostMapping(value = "/admin/deliveryRequestListExcelCount.ajax")
    public ResponseEntity<?> adminDeliveryRequestListExcelCountAjax(@RequestParam Map<String, String> ajaxDTO) throws ParseException {

        System.out.println("COUNT :: "+deliveryRequestListRepository.count());

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("cnt", deliveryRequestListRepository.count());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    @GetMapping(value="/admin/deliveryRequestListExcel")
    public ModelAndView adminDeliveryRequestListExcel(@RequestParam Map<String, String> ajaxDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<DeliveryRequestList> list = null;
        if (!ajaxDTO.get("searchYearMonth2").isEmpty()) {
            String fromDate = ajaxDTO.get("searchYearMonth2") + "-01";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String toDate = ajaxDTO.get("searchYearMonth2") + "-" + DateUtils.getEndDay(fromDate);
            list = deliveryRequestListRepository.findAllByIdRequestNumContainingAndAccountNameContainingAndAccountCodeContainingAndRequestDateBetween(ajaxDTO.get("requestNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"), df.parse(fromDate), df.parse(toDate));
        } else {
            list = deliveryRequestListRepository.findAllByIdRequestNumContainingAndAccountNameContainingAndAccountCodeContaining(ajaxDTO.get("requestNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"));
        }

        Map<String, Object> model = new HashMap<>();

        model.put("sheetname", "납품의뢰리스트"); //Sheet Name

        List<String> headers = new ArrayList<>();
        headers.add("의뢰번호");
        headers.add("의뢰일자");
        headers.add("거래처코드");
        headers.add("거래처");
        headers.add("품목");
        headers.add("품목명");
        headers.add("의뢰수량");
        headers.add("출하수량");
        headers.add("미출하수량");
        headers.add("단가");
        headers.add("금액");
        headers.add("원화금액");
        headers.add("출하창고");
        headers.add("출고구분");
        headers.add("라인비고");
        model.put("headers", headers); //headers List

        List<List<String>> bodyList = new ArrayList<>();
            for(DeliveryRequestList item : list) {

                List<String> body = new ArrayList<>();

                body.add(item.getId().getRequestNum());
                body.add(item.getRequestDate());
                body.add(item.getAccountCode());
                body.add(item.getAccountName());
                body.add(item.getId().getItemCode());
                body.add(item.getItemName());
                body.add(CommonUtils.Comma_won(item.getRequestQty().toString()));
                body.add(CommonUtils.Comma_won(item.getShippingQty().toString()));
                body.add(CommonUtils.Comma_won(item.getNotShippingQty().toString()));
                body.add(CommonUtils.Comma_won(item.getUnitPrice().toString()));
                body.add(CommonUtils.Comma_won(item.getPrice().toString()));
                body.add(CommonUtils.Comma_won(item.getAmount().toString()));
                body.add(item.getShippingWarehouse());
                body.add(item.getGoodsIssue());
                body.add(item.getLineRemarks());
                bodyList.add(body);
            }

        model.put("body", bodyList); //body List
        System.out.println(bodyList.size());

        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=deliveryRequestList.xlsx" );
        return new ModelAndView(new ExcelWriteXlsx2(), model);
    }

    @PostMapping(value = "/admin/accountInfoListExcelCount.ajax")
    public ResponseEntity<?> adminAccountInfoListExcelCountAjax(@RequestParam Map<String, String> ajaxDTO) throws ParseException {

        System.out.println("COUNT :: "+accountInfoListRepository.count());

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("cnt", accountInfoListRepository.count());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    @GetMapping(value="/admin/accountInfoListExcel")
    public ModelAndView adminAccountInfoListExcel(@RequestParam Map<String, String> ajaxDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<AccountInfoList> list = null;

        list = accountInfoListRepository.findAllByAccountNameContainingAndAccountCodeContaining(ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"));

        Map<String, Object> model = new HashMap<>();

        model.put("sheetname", "거래처정보리스트"); //Sheet Name

        List<String> headers = new ArrayList<>();
        headers.add("거래처코드");
        headers.add("거래처명");
        headers.add("거래처명(약칭)");
        headers.add("사업자등록번호");
        headers.add("대표자명");
        headers.add("주소");
        headers.add("상세주소");
        model.put("headers", headers); //headers List

        List<List<String>> bodyList = new ArrayList<>();
            for(AccountInfoList item : list) {

                List<String> body = new ArrayList<>();

                body.add(item.getAccountCode());
                body.add(item.getAccountName());
                body.add(item.getAccountShortName());
                body.add(item.getCorpRegNum());
                body.add(item.getRepresentativeName());
                body.add(item.getAddress());
                body.add(item.getDetailAddress());
                bodyList.add(body);
            }

        model.put("body", bodyList); //body List
        System.out.println(bodyList.size());

        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=accountInfoList.xlsx" );
        return new ModelAndView(new ExcelWriteXlsx2(), model);
    }


    @PostMapping(value = "/ima/itemListExcelCount.ajax")
    public ResponseEntity<?> imaItemListExcelCountAjax(@RequestParam Map<String, String> ajaxDTO) throws ParseException {

        System.out.println("COUNT :: "+itemListRepository.count());

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("cnt", itemListRepository.count());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    @GetMapping(value="/ima/itemListExcel")
    public ModelAndView imaItemListExcel(@RequestParam Map<String, String> ajaxDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<ItemList> list = null;

        list = itemListRepository.findAllByItemCodeContainingAndItemNameContainingAndUseYnContaining(ajaxDTO.get("itemCode"), ajaxDTO.get("itemName"), ajaxDTO.get("useYn"));

        Map<String, Object> model = new HashMap<>();

        model.put("sheetname", "품목리스트"); //Sheet Name

        List<String> headers = new ArrayList<>();
        headers.add("품목코드");
        headers.add("품목명");
        headers.add("규격");
        headers.add("재고단위");
        headers.add("재고관리여부");
        model.put("headers", headers); //headers List

        List<List<String>> bodyList = new ArrayList<>();
            for(ItemList item : list) {

                List<String> body = new ArrayList<>();

                body.add(item.getItemCode());
                body.add(item.getItemName());
                body.add(item.getStandardInfo());
                body.add(item.getUnit());
                body.add(item.getUseYn());
                bodyList.add(body);
            }

        model.put("body", bodyList); //body List
        System.out.println(bodyList.size());

        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=itemList.xlsx" );
        return new ModelAndView(new ExcelWriteXlsx2(), model);
    }

    @PostMapping(value = "/ima/itemHistoryListExcelCount.ajax")
    public ResponseEntity<?> imaItemHistoryListExcelCountAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {

        System.out.println("COUNT :: "+itemHistoryListMapper.selectItemHistoryListCount(ajaxDTO));

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("cnt", itemHistoryListMapper.selectItemHistoryListCount(ajaxDTO));
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    @GetMapping(value="/ima/itemHistoryListExcel")
    public ModelAndView imaItemHistoryListExcel(@RequestParam Map<String, String> ajaxDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Map<String, String>> list = itemHistoryListMapper.selectItemHistoryList(ajaxDTO);

        Map<String, Object> model = new HashMap<>();

        model.put("sheetname", "품목재고현황리스트"); //Sheet Name

        List<String> headers = new ArrayList<>();
        headers.add("품목코드");
        headers.add("품목명");
        headers.add("해당년월");
        headers.add("기초재고");
        headers.add("입고");
        headers.add("출고");
        headers.add("기말재고");
        model.put("headers", headers); //headers List

        List<List<String>> bodyList = new ArrayList<>();

        for(Map<String, String> itemHistory : list) {

            List<String> body = new ArrayList<String>();

            body.add(itemHistory.get("itemCode").toString());
            body.add(itemHistory.get("itemName").toString());
            body.add(itemHistory.get("updateDate").toString());
            body.add(CommonUtils.Comma_won(String.valueOf(itemHistory.get("basicStock"))));
            body.add(CommonUtils.Comma_won(String.valueOf(itemHistory.get("inItem"))));
            body.add(CommonUtils.Comma_won(String.valueOf(itemHistory.get("outItem"))));
            body.add(CommonUtils.Comma_won(String.valueOf(itemHistory.get("closingStock"))));

            bodyList.add(body);
        }

        model.put("body", bodyList); //body List
        System.out.println(bodyList.size());

        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=itemHistoryList.xlsx" );
        return new ModelAndView(new ExcelWriteXlsx2(), model);
    }


    @PostMapping(value = "/ima/prodListExcelCount.ajax")
    public ResponseEntity<?> imaProdListExcelCountAjax(@RequestParam Map<String, String> ajaxDTO) throws ParseException {

        System.out.println("COUNT :: "+prodListRepository.count());

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("cnt", prodListRepository.count());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    @GetMapping(value="/ima/prodListExcel")
    public ModelAndView imaProdListExcel(@RequestParam Map<String, String> ajaxDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<ProdList> list = prodListRepository.findAllByIdProdCodeContainingAndProdNameContainingAndProdTypeContainingAndIdMallSiteContainingAndUseYnContaining(ajaxDTO.get("prodCode"),ajaxDTO.get("prodName"), ajaxDTO.get("prodType"),ajaxDTO.get("mallSite"), ajaxDTO.get("useYn"));

        Map<String, Object> model = new HashMap<>();

        model.put("sheetname", "상품리스트"); //Sheet Name

        List<String> headers = new ArrayList<>();
        headers.add("상품코드");
        headers.add("상품명");
        headers.add("상품구분");
        headers.add("사이트");
        headers.add("사용여부");
        model.put("headers", headers); //headers List

        List<List<String>> bodyList = new ArrayList<>();
        for(ProdList item : list) {

            List<String> body = new ArrayList<>();

            body.add(item.getId().getProdCode());
            body.add(item.getProdName());
            body.add(item.getProdType());
            body.add(item.getId().getMallSite());
            body.add(item.getUseYn());
            bodyList.add(body);
        }

        model.put("body", bodyList); //body List
        System.out.println(bodyList.size());

        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=prodList.xlsx" );
        return new ModelAndView(new ExcelWriteXlsx2(), model);
    }

    /**
     * 자사몰 판매 현황 - 엑셀 카운트 다운로드
     * @param ajaxDTO
     * @return
     * @throws ParseException
     */
    @PostMapping(value = "/ima/duolacListExcelCount.ajax")
    public ResponseEntity<?> imaDuolacListExcelCountAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {

        System.out.println("COUNT :: "+duolacListMapper.selectDuolacListCount(ajaxDTO));

        ajaxDTO.put("prodTypeGsp", "G");
        Map<String, Object> rtn = new HashMap<>();
        rtn.put("cnt", duolacListMapper.selectDuolacListCount(ajaxDTO));
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 자사몰 판매 현황 - 엑셀 다운로드
     * @param ajaxDTO
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping(value="/ima/duolacListExcel")
    public ModelAndView imaDuolacListExcel(@RequestParam Map<String, String> ajaxDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ajaxDTO.put("prodTypeGsp", "G");
        ajaxDTO.put("searchDateNoDash", ajaxDTO.get("searchDateInput").replaceAll("-", ""));
        List<Map<String, String>> list = duolacListMapper.selectDuolacList(ajaxDTO);

        Map<String, Object> model = new HashMap<>();

        model.put("sheetname", "자사몰 판매 현황"); //Sheet Name

        List<String> titles = new ArrayList<String>();
        titles.add("자사몰 판매 현황"+ajaxDTO.get("searchDateInput"));
        titles.add("");
        titles.add("");
        model.put("titles", titles); //titles List

        List<String> headers = new ArrayList<>();
        headers.add("");
        headers.add("제품명");
        headers.add("1차");
        headers.add("2차");
        headers.add("3차");
        headers.add("반품");
        headers.add("합계");
        headers.add("외부몰");
        headers.add("기타");
        headers.add("재고");
        model.put("headers", headers); //headers List

        List<String> headers2 = new ArrayList<>();
        headers2.add("");
        headers2.add("샘플명");
        headers2.add("1차");
        headers2.add("2차");
        headers2.add("3차");
        headers2.add("반품");
        headers2.add("합계");
        headers2.add("전일(재고)");
        headers2.add("재고");
        model.put("headers2", headers2); //headers List

        List<List<String>> bodyList = new ArrayList<>();
        for(Map<String, String> item : list) {

            List<String> body = new ArrayList<>();
            if (item.get("priority") != null) {
                body.add(String.valueOf(item.get("priority"))); //
                body.add(item.get("itemName")); // 제품명
            } else {
                body.add(""); //
                body.add("합계"); // 제품명
            }
//            body.add(String.valueOf(item.get("oneTime"))); // 1차
            body.add(CommonUtils.getDecimal2String(String.valueOf(item.get("oneTime")),5)); // 1차
            body.add(CommonUtils.getDecimal2String(String.valueOf(item.get("twoTime")),5)); // 2차
            body.add(CommonUtils.getDecimal2String(String.valueOf(item.get("threeTime")),5)); // 3차
            body.add(CommonUtils.getDecimal2String(String.valueOf(item.get("prodQtyReturn")),5)); // 반품
            body.add(CommonUtils.getDecimal2String(String.valueOf(item.get("prodQty")),5)); // 합계
            body.add(CommonUtils.getDecimal2String(String.valueOf(item.get("exmallQty")),0)); // 외부몰
            body.add(item.get("기타")); // 기타
            if (item.get("priority") != null) {
                body.add(CommonUtils.getDecimal2String(String.valueOf(item.get("qtGoodInv")), 5)); // 재고
            } else {
                body.add(""); // 재고
            }
            bodyList.add(body);
        }

        model.put("body", bodyList); //body List

        ajaxDTO.put("prodTypeGsp", "P");
        List<Map<String, String>> pointList = duolacListMapper.selectDuolacList(ajaxDTO);
        List<List<String>> bodyList2 = new ArrayList<>();
        for(Map<String, String> item : pointList) {

            List<String> body = new ArrayList<>();
            if (item.get("priority") != null) {
                body.add(String.valueOf(item.get("priority"))); //
                body.add(item.get("itemName")); // 제품명
            } else {
                body.add(""); //
                body.add("합계"); // 제품명
            }
//            body.add(String.valueOf(item.get("oneTime"))); // 1차
            body.add(CommonUtils.getDecimal2String(String.valueOf(item.get("oneTime")),5)); // 1차
            body.add(CommonUtils.getDecimal2String(String.valueOf(item.get("twoTime")),5)); // 2차
            body.add(CommonUtils.getDecimal2String(String.valueOf(item.get("threeTime")),5)); // 3차
            body.add(CommonUtils.getDecimal2String(String.valueOf(item.get("prodQtyReturn")),5)); // 반품
            body.add(CommonUtils.getDecimal2String(String.valueOf(item.get("prodQty")),5)); // 합계
            body.add(item.get("전일(재고)")); // 기타
            if (item.get("priority") != null) {
                body.add(CommonUtils.getDecimal2String(String.valueOf(item.get("qtGoodInv")), 5)); // 재고
            } else {
                body.add(""); // 재고 qtGoodInv

            }
            bodyList2.add(body);
        }

        model.put("body2", bodyList2); //body List
        System.out.println(bodyList.size());

        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=duolacList.xlsx" );
        return new ModelAndView(new ExcelWriteXlsx(), model);
    }

    @PostMapping(value = "/ima/prodReleaseListExcelCount.ajax")
    public ResponseEntity<?> imaProdReleaseListExcelCountAjax(@RequestParam Map<String, String> ajaxDTO) throws ParseException {

        System.out.println("COUNT :: "+prodReleaseListRepository.count());

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("cnt", prodReleaseListRepository.count());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    @GetMapping(value="/ima/prodReleaseListExcel")
    public ModelAndView imaProdReleaseListExcel(@RequestParam Map<String, String> ajaxDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<ProdReleaseList> list = null;

        if (!ajaxDTO.get("searchYearMonth2").isEmpty()) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String updateDate = ajaxDTO.get("searchYearMonth2") + "-01";
            String toDate = ajaxDTO.get("searchYearMonth2") + "-" + DateUtils.getEndDay(updateDate);
            list = prodReleaseListRepository.findAllByIdUpdateDateBetweenAndIdProdCodeContainingAndProdNameContainingAndIdMallCodeContainingAndMallSiteContaining(df.parse(updateDate), df.parse(toDate), ajaxDTO.get("prodCode"), ajaxDTO.get("prodName"), ajaxDTO.get("mallCode"), ajaxDTO.get("mallSite"));
        } else {
            list = prodReleaseListRepository.findAllByIdProdCodeContainingAndProdNameContainingAndIdMallCodeContainingAndMallSiteContaining(ajaxDTO.get("prodCode"), ajaxDTO.get("prodName"), ajaxDTO.get("mallCode"), ajaxDTO.get("mallSite"));
        }
        Map<String, Object> model = new HashMap<>();

        model.put("sheetname", "상품판매현황리스트"); //Sheet Name

        List<String> headers = new ArrayList<>();
        headers.add("판매일");
        headers.add("상품코드");
        headers.add("상품명");
        headers.add("수량");
        headers.add("단가");
        headers.add("쇼핑몰");
        headers.add("사이트");
        model.put("headers", headers); //headers List

        List<List<String>> bodyList = new ArrayList<>();
        for(ProdReleaseList item : list) {

            List<String> body = new ArrayList<>();

            body.add(String.valueOf(item.getId().getUpdateDate()));
            body.add(item.getId().getProdCode());
            body.add(item.getProdName());
            body.add(CommonUtils.Comma_won(String.valueOf(item.getProdQty())));
            if (item.getUnitPrice() != null) {
                body.add(CommonUtils.Comma_won(String.valueOf(item.getUnitPrice())));
            } else {
                body.add("");
            }
            body.add(item.getId().getExmallCode());
            body.add(item.getMallSite());
            bodyList.add(body);
        }

        model.put("body", bodyList); //body List
        System.out.println(bodyList.size());

        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=prodReleaseList.xlsx" );
        return new ModelAndView(new ExcelWriteXlsx2(), model);
    }
}
