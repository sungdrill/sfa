package com.cellbiotech.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.cellbiotech.mapper.neoe.NeoeListMapper;
import com.cellbiotech.mapper.sfa.DuolacListMapper;
import com.cellbiotech.mapper.sfa.ProdReleaseListMapper;
import com.cellbiotech.util.CommonUtils;
import com.cellbiotech.util.PagingUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cellbiotech.dao.sfa.CodeListRepository;
import com.cellbiotech.dao.sfa.OrderListRepository;
import com.cellbiotech.model.sfa.CodeList;
import com.cellbiotech.model.sfa.GroupCodeList;
import com.cellbiotech.model.sfa.User;
import com.cellbiotech.service.UserService;

/**
 * 메뉴 호출 컨트롤러
 * GET 호출
 */
@Controller
public class MenuController {

    @Autowired
    private UserService userService;

    @Autowired
    CodeListRepository codeListRepository;

    @Autowired
    private DuolacListMapper duolacListMapper;

    @Autowired
    private NeoeListMapper neoeListMapper;

    @Autowired
    private ProdReleaseListMapper prodReleaseListMapper;

    @Value("${page.size}")
    private int LIST_PAGE_SIZE;

    final static Map<String, String> RADIO_ITEMS =
            Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
                {
                    put("ADMIN", "ADMIN");
                    put("USER", "USER");
                    put("MANAGER", "MANAGER");
                    put("IMA", "IMA");
                }
            });

    final static Map<String, String> SELECT_ITEMS =
            Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
                {
                    put("Y", "Y");
                    put("N", "N");
                }
            });

    final static Map<String, String> SELECT_GODOMALL =
            Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
                {
                    put("전문가몰가입유무", "");
                    put("미가입처", "미가입처");
                    put("전문가몰", "전문가몰");
                }
            });

    @GetMapping(value="/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.addObject("radioItems", RADIO_ITEMS);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @GetMapping(value = "/admin/userList")
    public ModelAndView adminUserList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "userList");
        modelAndView.setViewName("admin/userList");
        return modelAndView;
    }

    @GetMapping("/admin/upload")
    public ModelAndView adminUpload(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserById(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getId() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.addObject("module", "upload");
        modelAndView.setViewName("/admin/upload");
        return modelAndView;
    }

    @GetMapping("/admin/orderList")
    public ModelAndView adminOrderList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "orderList");
        modelAndView.setViewName("/admin/orderList");
        return modelAndView;
    }

    @GetMapping("/admin/salesDeadlineList")
    public ModelAndView adminSalesDeadlineList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "salesDeadlineList");
        modelAndView.setViewName("/admin/salesDeadlineList");
        return modelAndView;
    }

    @GetMapping("/admin/collectionList")
    public ModelAndView adminCollectionList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "collectionList");
        modelAndView.setViewName("/admin/collectionList");
        return modelAndView;
    }

    @GetMapping("/admin/deliveryRequestList")
    public ModelAndView adminDeliveryRequestList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "deliveryRequestList");
        modelAndView.setViewName("/admin/deliveryRequestList");
        return modelAndView;
    }

    @GetMapping("/admin/accountInfoList")
    public ModelAndView adminAccountInfoList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "accountInfoList");
        modelAndView.setViewName("/admin/accountInfoList");
        return modelAndView;
    }

    @GetMapping("/admin/memberList")
    public ModelAndView adminMemberList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "memberList");
        modelAndView.setViewName("/admin/memberList");
        return modelAndView;
    }

    @GetMapping("/admin/groupCodeList")
    public ModelAndView adminGroupCodeList(){
        ModelAndView modelAndView = new ModelAndView();
        GroupCodeList groupCodeList = new GroupCodeList();
        modelAndView.addObject("groupCodeList", groupCodeList);
        modelAndView.addObject("module", "groupCodeList");
        modelAndView.addObject("selectItems", SELECT_ITEMS);
        modelAndView.setViewName("/admin/groupCodeList");
        return modelAndView;
    }

    @GetMapping("/admin/codeListEdit")
    public ModelAndView adminCodeListEdit(){
        ModelAndView modelAndView = new ModelAndView();
        CodeList codeList = new CodeList();
        modelAndView.addObject("codeList", codeList);
        modelAndView.setViewName("/admin/codeListEdit");
        return modelAndView;
    }

    @GetMapping("/admin/goalList")
    public ModelAndView adminGoalList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "goalList");
        modelAndView.setViewName("/admin/goalList");
        return modelAndView;
    }

    @GetMapping("/admin/uploadList")
    public ModelAndView adminUploadList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "uploadList");
        modelAndView.setViewName("/admin/uploadList");
        return modelAndView;
    }

    @GetMapping("/admin/cpList")
    public ModelAndView adminCpList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "cpList");
        modelAndView.setViewName("/admin/cpList");
        return modelAndView;
    }

    /**
     * 담당자별 종합실적(callnote)
     * @return
     */
    @GetMapping("/cpList")
    public ModelAndView cpList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "cpList");
        modelAndView.setViewName("/cpList");
        return modelAndView;
    }

    /**
     * 담당자별 종합실적(sfa)
     * @return
     */
    @GetMapping("/cpList2")
    public ModelAndView cpList2(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "cpList2");
        modelAndView.setViewName("/cpList2");
        return modelAndView;
    }

    @GetMapping("/admin/memberAccountInfoList")
    public ModelAndView adminMemberAccountInfoList(){
        ModelAndView modelAndView = new ModelAndView();
        GroupCodeList groupCodeList = new GroupCodeList();
        modelAndView.addObject("groupCodeList", groupCodeList);
        modelAndView.addObject("module", "memberAccountInfoList");
        modelAndView.addObject("selectItems", SELECT_ITEMS);
        modelAndView.setViewName("/admin/memberAccountInfoList");
        return modelAndView;
    }

    /**
     * 영업담당자(sfa) 페이지
     * @return
     */
    @GetMapping("/admin/memberList2")
    public ModelAndView adminMemberList2(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "memberList2");
        // TODO 팀코드, 지역코드, 직급코드 콤보박스 가져오기

        modelAndView.addObject("selectTeam", getCodeList("TEAM", "팀코드"));
        modelAndView.addObject("selectArea", getCodeList("AREA", "지역코드"));
        modelAndView.addObject("selectRank", getCodeList("RANK", "직급코드"));
        modelAndView.setViewName("/admin/memberList2");
        return modelAndView;
    }

    /**
     * 등급별 거래처 현황 페이지
     * @return
     */
    @GetMapping("/itemSalesStatusList")
    public ModelAndView itemSalesStatusList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "itemSalesStatusList");
        modelAndView.setViewName("/itemSalesStatusList");
        return modelAndView;
    }

    /**
     * 담당별/품목별 매출 현황 페이지
     * @return
     */
    @GetMapping("/accountStatusList")
    public ModelAndView accountStatusList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "accountStatusList");
        modelAndView.setViewName("/accountStatusList");
        return modelAndView;
    }

    /**
     * 지역별 약국 거래율 현황 페이지
     * @return
     */
    @GetMapping("/areaStatusList")
    public ModelAndView areaStatusList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "areaStatusList");
        modelAndView.setViewName("/areaStatusList");
        return modelAndView;
    }


    /**
     * 행정구역 관리 페이지
     * @return
     */
    @GetMapping("/admin/adminDistrictList")
    public ModelAndView adminAdminDistrictList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "adminDistrictList");
        modelAndView.setViewName("/admin/adminDistrictList");
        return modelAndView;
    }

    /**
     * 행정구역 거래처정보 관리 페이지
     * @return
     */
    @GetMapping("/admin/zipAccountList")
    public ModelAndView adminAdminDistrictAccountList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "zipAccountList");
        modelAndView.setViewName("/admin/zipAccountList");
        return modelAndView;
    }

    /**
     * 거래처별 등급 관리 페이지
     * @return
     */
    @GetMapping("/admin/accountGradeList")
    public ModelAndView adminAccountGradeList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "accountGradeList");
        modelAndView.addObject("selectGrade", getCodeList("GRADE", "등급"));
        modelAndView.addObject("selectGodomoll", SELECT_GODOMALL);
        modelAndView.setViewName("/admin/accountGradeList");
        return modelAndView;
    }

    /**
     * 재고관리 - 품목 관리
     * @return
     */
    @GetMapping("/ima/itemList")
    public ModelAndView imaItemList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "itemList");
        modelAndView.addObject("selectUseYn", getCodeList("USE_YN", "전체"));
        modelAndView.setViewName("/ima/itemList");
        return modelAndView;
    }

    /**
     * 재고관리 - 품목 재고현황 관리 페이지
     * @return
     */
    @GetMapping("/ima/itemHistoryList")
    public ModelAndView imaItemHistoryList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "itemHistoryList");
        modelAndView.setViewName("/ima/itemHistoryList");
        return modelAndView;
    }

    /**
     * 재고관리 - 상품 판매현황 관리 페이지
     * @return
     */
    @GetMapping("/ima/prodReleaseList")
    public ModelAndView prodReleaseList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "prodReleaseList");
        modelAndView.addObject("selectSite", getCodeList("SITE", "전체"));
        modelAndView.setViewName("/ima/prodReleaseList");
        return modelAndView;
    }

    /**
     * 재고관리 - 상품 재고현황 관리 페이지
     * @return
     */
    @GetMapping("/ima/prodHistoryList")
    public ModelAndView prodHistoryList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "prodHistoryList");
        modelAndView.setViewName("/ima/prodHistoryList");
        return modelAndView;
    }

    /**
     * 재고관리 - 상품 관리 페이지
     * @return
     */
    @GetMapping("/ima/prodList")
    public ModelAndView prodList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "prodList");
        modelAndView.addObject("selectSite", getCodeList("SITE", "전체"));
        modelAndView.addObject("selectProdType", getCodeList("PROD_TYPE", "전체"));
        modelAndView.addObject("selectUseYn", getCodeList("USE_YN2", "전체"));
        modelAndView.setViewName("/ima/prodList");
        return modelAndView;
    }

    /**
     * 재고관리 - 품목-상품 관리 페이지
     * @return
     */
    @GetMapping("/ima/itemProdList")
    public ModelAndView itemProdList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "itemProdList");
        modelAndView.addObject("selectUseYn", getCodeList("USE_YN", "전체"));
        modelAndView.setViewName("/ima/itemProdList");
        return modelAndView;
    }


    /**
     * 재고관리 - 재고현황 페이지
     * @return
     */
    @GetMapping("/ima/imaList")
    public ModelAndView imaImaList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "imaList");
        modelAndView.setViewName("/ima/imaList");
        return modelAndView;
    }


    /**
     * 재고관리 - 더존ERP 재고현황 페이지
     * @return
     */
    @GetMapping("/neoe/neoeList")
    public ModelAndView neoeIma2List(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "neoeList");
        modelAndView.setViewName("/neoe/neoeList");
        return modelAndView;
    }


    /**
     * 판매현황 - 자사몰 판매 현황 페이지
     * @return
     */
    @GetMapping("/ima/duolacList")
    public ModelAndView imaDuolacList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "duolacList");
        modelAndView.setViewName("/ima/duolacList");
        return modelAndView;
    }


    /**
     * 재고관리 - 기초재고 페이지
     * @return
     */
    @GetMapping("/ima/basicStockList")
    public ModelAndView imaBasicStockList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "basicStockList");
        modelAndView.setViewName("/ima/basicStockList");
        return modelAndView;
    }


    /**
     * 재고관리 - 외부몰 페이지
     * @return
     */
    @GetMapping("/exmall/orderList")
    public ModelAndView exmallOrderList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "orderList");
        modelAndView.setViewName("/exmall/orderList");
        return modelAndView;
    }


    /**
     * 재고관리 - 대시보드 페이지
     * @return
     */
    @GetMapping("/ima/dashboard")
    public ModelAndView imaDashboard() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("module", "dashboard");

        Map<String, String> ajaxDTO = new HashMap<>();

        String strDate = "";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            strDate = df.format(today);

        ajaxDTO.put("searchDateInput", strDate);
        ajaxDTO.put("searchDateNoDash", strDate.replaceAll("-", ""));
        ajaxDTO.put("prodTypeGsp", "G");

        int totalCnt = duolacListMapper.selectDuolacListCount(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, 1);
        pageInfo.setPageSize(200);//재고현황은 한번에 보이도록 작성
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, Object>> pageList = duolacListMapper.selectDuolacDashboardList(ajaxDTO);

        ajaxDTO.put("searchDateInput", strDate.replaceAll("-", ""));
        int totalCnt2 = neoeListMapper.selectNeoeListCount(ajaxDTO);

        PagingUtil pageInfo2 = new PagingUtil(totalCnt2, 1);
        pageInfo2.setPageSize(200);//
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo2.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo2.getEndPageNum()));

        ajaxDTO.put("multiCdSl", "DOK");
        ajaxDTO.put("ynUseQtInv", "Y");
        List<Map<String, String>> neoeList = neoeListMapper.selectNeoeList(ajaxDTO);

        ajaxDTO.put("itemTypeGsp", "G");
        String searchDate = strDate.replaceAll("-", "");
        String compareDate = searchDate.substring(0,4);
        ajaxDTO.put("compareDate", compareDate);
        List<Map<String, String>> duolacSalesList = prodReleaseListMapper.selectProdReleaseDashboardList(ajaxDTO);
        List<Map<String, String>> quarterList = prodReleaseListMapper.selectProdReleaseDashBoardQuarterList(ajaxDTO);
        List<Map<String, String>> quarterXaxisList = prodReleaseListMapper.selectProdReleaseDashBoardQuarterxAxisList(ajaxDTO);
        ajaxDTO.put("itemExmallYn", "Y");
        List<Map<String, String>> exmallSalesList = prodReleaseListMapper.selectProdReleaseDashboardList(ajaxDTO);

        List<String> itemNameList = new ArrayList<String>();
        List<Integer> warehouseQtyList = new ArrayList<Integer>();
        List<Integer> deliveryQtyList = new ArrayList<Integer>();
        List<Integer> duolacQtyList = new ArrayList<Integer>();
        List<Integer> exmallQtyList = new ArrayList<Integer>();
        List<Map<String, Object>> duolacSalesChartList = new ArrayList<>();
        List<Map<String, Object>> exmallSalesChartList = new ArrayList<>();

        List<String> itemNameBySiteList = new ArrayList<String>();
        List<Integer> duolacQtyBySiteList = new ArrayList<Integer>();
        List<Integer> exmallQtyBySiteList = new ArrayList<Integer>();

        List<String> quarterXaxisChartList = new ArrayList<String>();
        List<Map<String, Object>> quarterChartList = new ArrayList<>();

        List<Map<String, Object>> quarterExmallChartList = new ArrayList<>();


        for(Map<String, Object> info : pageList) {
            // 제품명 가져오기
            itemNameList.add(info.get("itemShorName").toString());
            // 배송실 재고 수량 가져오기
            deliveryQtyList.add(Integer.parseInt(CommonUtils.getDecimal2StringZero(String.valueOf(info.get("qtGoodInv")), 2))); // .2
            // 자사몰 판매량 가져오기
            duolacQtyList.add(Integer.parseInt(CommonUtils.getDecimal2StringZero(String.valueOf(info.get("prodQty")), 5))); // .2345
            // 외부몰 판매량 가져오기
            exmallQtyList.add(Integer.parseInt(CommonUtils.getDecimal2StringZero(String.valueOf(info.get("exmallQty")), 5)));   // .2345

            for(Map<String, String> neoeInfo : neoeList) {
                if (info.get("itemCode").toString().equals(neoeInfo.get("CD_ITEM"))) {
                    warehouseQtyList.add(Integer.parseInt(CommonUtils.getDecimal2StringZero(String.valueOf(neoeInfo.get("QT")), 5)));
                }
            }
        }

        for(Map<String, String> info : duolacSalesList) {
            // 자사몰 제품 판매량 비중
            Map<String, Object> duolacSales = new HashMap<>();
            duolacSales.put("name", info.get("itemShorName").toString());
            duolacSales.put("y", Integer.parseInt(CommonUtils.getDecimal2StringZero(String.valueOf(info.get("dQty")), 5)));
            duolacSalesChartList.add(duolacSales);
        }

        for(Map<String, String> info : exmallSalesList) {
            // 외부몰 제품 판매량 비중
            Map<String, Object> exmallSales = new HashMap<>();
            exmallSales.put("name", info.get("itemShorName").toString());
            exmallSales.put("y", Integer.parseInt(CommonUtils.getDecimal2StringZero(String.valueOf(info.get("eQty")), 5)));
            exmallSalesChartList.add(exmallSales);

            // 사이트별 제품 판매량
            itemNameBySiteList.add(info.get("itemShorName").toString());
            // 자사몰 판매량 가져오기
            duolacQtyBySiteList.add(Integer.parseInt(CommonUtils.getDecimal2StringZero(String.valueOf(info.get("dQty")), 5))); // .2345
            // 외부몰 판매량 가져오기
            exmallQtyBySiteList.add(Integer.parseInt(CommonUtils.getDecimal2StringZero(String.valueOf(info.get("eQty")), 5)));   // .2345
        }


        for(Map<String, String> infoXaxis : quarterXaxisList) {
            if (infoXaxis.get("balancedate").compareTo("201609") > 0) {
                quarterXaxisChartList.add(infoXaxis.get("balancedate").toString());
            }

        }

        for(Map<String, Object> infoName : pageList) {
            List<Integer> tempData = new ArrayList<Integer>();
            for(Map<String, String> info : quarterList) {
                if (info.get("DT").compareTo("201609") > 0) {
                    if (infoName.get("itemShorName").toString().equals(info.get("itemShorName").toString())) {
                        tempData.add(Integer.parseInt(CommonUtils.getDecimal2StringZero(String.valueOf(info.get("dQty")), 5)));
                    }
                }
            }
            Map<String, Object> quarter = new HashMap<>();
            quarter.put("name", infoName.get("itemShorName").toString());
            quarter.put("data", tempData);
            // TODO 디비에 컬럼 하나 추가 해서 디비 플래그를 확인해서 처리
            if (infoName.get("priority").toString().equals("1")
                    || infoName.get("priority").toString().equals("2")
                    || infoName.get("priority").toString().equals("4")
                    || infoName.get("priority").toString().equals("9")
                    || infoName.get("priority").toString().equals("11")) {

            } else {
                quarter.put("visible", false);

            }
            quarterChartList.add(quarter);
        }

        for(Map<String, Object> infoName : pageList) {
            List<Integer> tempData = new ArrayList<Integer>();

            if (infoName.get("itemExmallYn").toString().equals("Y")) {
                for (Map<String, String> info : quarterList) {
                    if (info.get("DT").compareTo("201609") > 0) {
                        if (infoName.get("itemShorName").toString().equals(info.get("itemShorName").toString())) {
                            tempData.add(Integer.parseInt(CommonUtils.getDecimal2StringZero(String.valueOf(info.get("eQty")), 5)));
                        }
                    }
                }
                Map<String, Object> quarter = new HashMap<>();
                quarter.put("name", "("+infoName.get("itemShorName").toString()+")");
                quarter.put("data", tempData);
                quarterExmallChartList.add(quarter);
//                quarter.put("type", "spline");
                // TODO 디비에 컬럼 하나 추가 해서 디비 플래그를 확인해서 처리
                if (infoName.get("priority").toString().equals("13")) {

                } else {
                    quarter.put("visible", false);

                }
                quarterChartList.add(quarter);
            }
        }

        String itemName = new Gson().toJson(itemNameList );
        String deliveryQty = new Gson().toJson(deliveryQtyList );
        String duolacQty = new Gson().toJson(duolacQtyList );
        String exmallQty = new Gson().toJson(exmallQtyList );
        String warehouseQty = new Gson().toJson(warehouseQtyList );
        String duolacSalesChart = new Gson().toJson(duolacSalesChartList );
        String exmallSalesChart = new Gson().toJson(exmallSalesChartList );
        String itemNameBySite = new Gson().toJson(itemNameBySiteList );
        String duolacQtyBySite = new Gson().toJson(duolacQtyBySiteList );
        String exmallQtyBySite = new Gson().toJson(exmallQtyBySiteList );

        String quarterXaxisChart = new Gson().toJson(quarterXaxisChartList );
        String quarterChart = new Gson().toJson(quarterChartList );
        String quarterExmallChart = new Gson().toJson(quarterExmallChartList );

        modelAndView.addObject("itemName", itemName);
        modelAndView.addObject("deliveryQty", deliveryQty);
        modelAndView.addObject("duolacQty", duolacQty);
        modelAndView.addObject("exmallQty", exmallQty);
        modelAndView.addObject("warehouseQty", warehouseQty);
        modelAndView.addObject("duolacSalesChart", duolacSalesChart);
        modelAndView.addObject("exmallSalesChart", exmallSalesChart);
        modelAndView.addObject("itemNameBySite", itemNameBySite);
        modelAndView.addObject("duolacQtyBySite", duolacQtyBySite);
        modelAndView.addObject("exmallQtyBySite", exmallQtyBySite);

        modelAndView.addObject("quarterXaxisChart", quarterXaxisChart);
        modelAndView.addObject("quarterChart", quarterChart);
        modelAndView.addObject("quarterExmallChart", quarterExmallChart);

        modelAndView.setViewName("/ima/dashboard");
        return modelAndView;
    }


    /**
     * 공통코드 가져오기
     * @param groupCodeId
     * @param selectName
     * @return
     */
    private Map<String, String> getCodeList(String groupCodeId, final String selectName) {
        final List<CodeList> codeList = codeListRepository.findAllByIdGroupCodeId(groupCodeId, new Sort(Sort.Direction.ASC, new String[]{"sort"}));
        Map<String, String> SELECT_ITEM =
                Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
                    {
                        put(selectName, "");
                        for (CodeList code : codeList) {
                            put(code.getCodeName(), code.getId().getCodeId());
                        }
                    }
                });
        return SELECT_ITEM;
    }

}
