package com.cellbiotech.controller;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    OrderListRepository orderListRepository;

    @Autowired
    CodeListRepository codeListRepository;

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
