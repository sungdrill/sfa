package com.cellbiotech.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.cellbiotech.mapper.neoe.PartnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cellbiotech.dao.callnote.MemberListRepository;
import com.cellbiotech.dao.sfa.AccountGradeListRepository;
import com.cellbiotech.dao.sfa.AccountInfoListRepository;
import com.cellbiotech.dao.sfa.AdminDistrictListRepository;
import com.cellbiotech.dao.sfa.CodeListRepository;
import com.cellbiotech.dao.sfa.CollectionListRepository;
import com.cellbiotech.dao.sfa.DeliveryRequestListRepository;
import com.cellbiotech.dao.sfa.GroupCodeListRepository;
import com.cellbiotech.dao.sfa.MemberAccountInfoManagerListRepository;
import com.cellbiotech.dao.sfa.MemberList2Repository;
import com.cellbiotech.dao.sfa.OrderListRepository;
import com.cellbiotech.dao.sfa.SalesDeadlineListRepository;
import com.cellbiotech.dao.sfa.UploadListRepository;
import com.cellbiotech.dao.sfa.UserRepository;
import com.cellbiotech.mapper.callnote.MemberMapper;
import com.cellbiotech.mapper.sfa.AccountGradeListMapper;
import com.cellbiotech.mapper.sfa.CpListMapper;
import com.cellbiotech.mapper.sfa.GoalListMapper;
import com.cellbiotech.mapper.sfa.MemberAccountInfoListMapper;
import com.cellbiotech.mapper.sfa.MemberList2Mapper;
import com.cellbiotech.mapper.sfa.UploadListMapper;
import com.cellbiotech.mapper.sfa.ZipAccountListMapper;
import com.cellbiotech.model.callnote.MemberVO;
import com.cellbiotech.model.sfa.AccountGradeList;
import com.cellbiotech.model.sfa.AccountInfoList;
import com.cellbiotech.model.sfa.AdminDistrictList;
import com.cellbiotech.model.sfa.CodeList;
import com.cellbiotech.model.sfa.CodeListId;
import com.cellbiotech.model.sfa.CollectionList;
import com.cellbiotech.model.sfa.DeliveryRequestList;
import com.cellbiotech.model.sfa.GoalListVO;
import com.cellbiotech.model.sfa.GroupCodeList;
import com.cellbiotech.model.sfa.MemberAccountInfoManagerList;
import com.cellbiotech.model.sfa.MemberAccountInfoManagerListId;
import com.cellbiotech.model.sfa.MemberList2;
import com.cellbiotech.model.sfa.OrderList;
import com.cellbiotech.model.sfa.SalesDeadlineList;
import com.cellbiotech.model.sfa.User;
import com.cellbiotech.service.UserService;
import com.cellbiotech.util.DateUtils;
import com.cellbiotech.util.PagingUtil;

/**
 * ADMIN 권한 관련 컨트롤러
 * POST, AJAX 맵핑관련
 */
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderListRepository orderListRepository;

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
    private GroupCodeListRepository groupCodeListRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private GoalListMapper goalListMapper;

    @Autowired
    private UploadListMapper uploadListMapper;

    @Autowired
    private CpListMapper cpListMapper;

    @Autowired
    private MemberList2Mapper memberList2Mapper;

    @Autowired
    private ZipAccountListMapper zipAccountListMapper;

    @Autowired
    private MemberAccountInfoListMapper memberAccountInfoListMapper;

    @Autowired
    private UploadListRepository uploadListRepository;

    @Autowired
    private MemberAccountInfoManagerListRepository memberAccountInfoManagerListRepository;

    @Autowired
    private AdminDistrictListRepository adminDistrictListRepository;

    @Autowired
    private MemberList2Repository memberList2Repository;

    @Autowired
    private AccountGradeListMapper accountGradeListMapper;

    @Autowired
    private AccountGradeListRepository accountGradeListRepository;

    @Autowired
    private PartnerMapper partnerMapper;

    @Value("${page.size}")
    private int LIST_PAGE_SIZE;

    /**
     * 유저등록화면 - 권한 라디오버튼
     */
    final static Map<String, String> RADIO_ITEMS =
            Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
                {
                    put("ADMIN", "ADMIN");
                    put("USER", "USER");
                    put("MANAGER", "MANAGER");
                    put("IMA", "IMA");
                }
            });

    /**
     *
     */
    final static Map<String, String> SELECT_ITEMS =
            Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
                {
                    put("Y", "Y");
                    put("N", "N");
                }
            });

    /**
     * 영업관리시스템 사용자 등록
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserById(user.getId());
        if (userExists != null) {
            bindingResult
                    .rejectValue("id", "error.user",
                            "There is already a user registered with the id provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("radioItems", RADIO_ITEMS);
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user); // USER ADMIN MANGER
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.addObject("radioItems", RADIO_ITEMS);
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    /**
     * 회원정보 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/admin/userList.ajax")
    public ResponseEntity<?>  adminUserListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        PageRequest pageRequest = new PageRequest(pageNumber-1, LIST_PAGE_SIZE, new Sort(Sort.Direction.ASC, new String[]{"role"}));

        Page<User> pageList = null;

        if (!ajaxDTO.get("id").isEmpty()) {
            if (!ajaxDTO.get("role").isEmpty()) {
                pageList = userRepository.findAllByIdContaining(ajaxDTO.get("id"), pageRequest);
            } else {

                pageList = userRepository.findAllByIdContainingAndRoleContaining(ajaxDTO.get("id"), ajaxDTO.get("role"), pageRequest);
            }
        } else if (!ajaxDTO.get("role").isEmpty()) {
            pageList = userRepository.findAllByRoleContaining(ajaxDTO.get("role"), pageRequest);
        } else {
            pageList = userRepository.findAll(pageRequest);
        }

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList.getContent());
        rtn.put("totalPageCount", pageList.getTotalPages());

        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 수주리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws ParseException
     */
    @PostMapping(value="/admin/orderList.ajax")
    public ResponseEntity<?> adminOrderListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"orderDate"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"id.orderNum"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"id.orderSeq"));
        PageRequest pageRequest = new PageRequest(pageNumber-1, LIST_PAGE_SIZE, new Sort(orders));

        Page<OrderList> pageList = null;

        if (!ajaxDTO.get("searchYearMonth2").isEmpty()) {
            String fromDate = ajaxDTO.get("searchYearMonth2") + "-01";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String toDate = ajaxDTO.get("searchYearMonth2") + "-" + DateUtils.getEndDay(fromDate);
            pageList = orderListRepository.findAllByIdOrderNumContainingAndAccountNameContainingAndAccountCodeContainingAndOrderDateBetween(ajaxDTO.get("orderNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"), df.parse(fromDate), df.parse(toDate), pageRequest);
        } else {
            pageList = orderListRepository.findAllByIdOrderNumContainingAndAccountNameContainingAndAccountCodeContaining(ajaxDTO.get("orderNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"), pageRequest);
        }

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList.getContent());
        rtn.put("totalCount", pageList.getTotalElements());
        rtn.put("totalPageCount", pageList.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 매출마감리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws ParseException
     */
    @PostMapping(value="/admin/salesDeadlineList.ajax")
    public ResponseEntity<?> adminSalesDeadlineListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        PageRequest pageRequest = new PageRequest(pageNumber-1, LIST_PAGE_SIZE, new Sort(Sort.Direction.ASC, new String[]{"salesDate", "id.salesNum"}));

        Page<SalesDeadlineList> pageList = null;

        Date from = null;
        Date to = null;
        if (!ajaxDTO.get("searchYearMonth2").isEmpty()) {
            String fromDate = ajaxDTO.get("searchYearMonth2") + "-01";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String toDate = ajaxDTO.get("searchYearMonth2") + "-" + DateUtils.getEndDay(fromDate);
            from = df.parse(fromDate);
            to = df.parse(toDate);
            pageList = salesDeadlineListRepository.findAllByIdSalesNumContainingAndAccountNameContainingAndAccountCodeContainingAndItemNameContainingAndIdItemCodeContainingAndSalesDateBetween(ajaxDTO.get("salesNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"), ajaxDTO.get("itemName"), ajaxDTO.get("itemCode"), from, to, pageRequest);
        } else {

            pageList = salesDeadlineListRepository.findAllByIdSalesNumContainingAndAccountNameContainingAndAccountCodeContainingAndItemNameContainingAndIdItemCodeContaining(ajaxDTO.get("salesNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"), ajaxDTO.get("itemName"), ajaxDTO.get("itemCode"), pageRequest);
        }

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList.getContent());
        rtn.put("totalCount", pageList.getTotalElements());
        rtn.put("totalPageCount", pageList.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 수금리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws ParseException
     */
    @PostMapping(value="/admin/collectionList.ajax")
    public ResponseEntity<?> adminCollectionListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        PageRequest pageRequest = new PageRequest(pageNumber-1, LIST_PAGE_SIZE, new Sort(Sort.Direction.ASC, new String[]{"collectionDate", "collectionNum"}));

        Page<CollectionList> pageList = null;

        if (!ajaxDTO.get("searchYearMonth2").isEmpty()) {
            String fromDate = ajaxDTO.get("searchYearMonth2") + "-01";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String toDate = ajaxDTO.get("searchYearMonth2") + "-" + DateUtils.getEndDay(fromDate);
            pageList = collectionListRepository.findAllByCollectionNumContainingAndCollectionPlaceNameContainingAndCollectionPlaceCodeContainingAndCollectionDateBetween(ajaxDTO.get("collectionNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"), df.parse(fromDate), df.parse(toDate), pageRequest);
        } else {
            pageList = collectionListRepository.findAllByCollectionNumContainingAndCollectionPlaceNameContainingAndCollectionPlaceCodeContaining(ajaxDTO.get("collectionNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"), pageRequest);
        }

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList.getContent());
        rtn.put("totalCount", pageList.getTotalElements());
        rtn.put("totalPageCount", pageList.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 납품의뢰리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws ParseException
     */
    @PostMapping(value="/admin/deliveryRequestList.ajax")
    public ResponseEntity<?> adminDeliveryRequestListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        PageRequest pageRequest = new PageRequest(pageNumber-1, LIST_PAGE_SIZE, new Sort(Sort.Direction.ASC, new String[]{"requestDate", "id.requestNum"}));

        Page<DeliveryRequestList> pageList = null;

        if (!ajaxDTO.get("searchYearMonth2").isEmpty()) {
            String fromDate = ajaxDTO.get("searchYearMonth2") + "-01";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String toDate = ajaxDTO.get("searchYearMonth2") + "-" + DateUtils.getEndDay(fromDate);
            pageList = deliveryRequestListRepository.findAllByIdRequestNumContainingAndAccountNameContainingAndAccountCodeContainingAndRequestDateBetween(ajaxDTO.get("requestNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"), df.parse(fromDate), df.parse(toDate), pageRequest);
        } else {
            pageList = deliveryRequestListRepository.findAllByIdRequestNumContainingAndAccountNameContainingAndAccountCodeContaining(ajaxDTO.get("requestNum"), ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"), pageRequest);
        }

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList.getContent());
        rtn.put("totalCount", pageList.getTotalElements());
        rtn.put("totalPageCount", pageList.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 거래처정보리스트 가져오기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value="/admin/accountInfoList.ajax")
    public ResponseEntity<?> adminAccountInfoList2Ajax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        PageRequest pageRequest = new PageRequest(pageNumber-1, LIST_PAGE_SIZE, new Sort(Sort.Direction.ASC, new String[]{"accountCode"}));

        Page<AccountInfoList> pageList = null;

        pageList = accountInfoListRepository.findAllByAccountNameContainingAndAccountCodeContaining(ajaxDTO.get("accountName"), ajaxDTO.get("accountCode"), pageRequest);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList.getContent());
        rtn.put("totalCount", pageList.getTotalElements());
        rtn.put("totalPageCount", pageList.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 거래처정보리스트 가져오기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value="/admin/accountInfoList2.ajax")
    public ResponseEntity<?> adminAccountInfoListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        int totalCnt = partnerMapper.selectPartnerListCount(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(LIST_PAGE_SIZE);
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, Object>> memberVOList = partnerMapper.selectPartnerList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", memberVOList);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 영업담당자(callnote) 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/admin/memberList.ajax")
    public ResponseEntity<?> adminMemberListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        int totalCnt = memberMapper.selectMemberListCount(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(LIST_PAGE_SIZE);
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<MemberVO> memberVOList = memberMapper.selectMemberList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", memberVOList);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 공통코드 가져오기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value="/admin/groupCodeList.ajax")
    public ResponseEntity<?> adminGroupCodeListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"creaDate"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"groupCodeId"));
        PageRequest pageRequest = new PageRequest(pageNumber-1, LIST_PAGE_SIZE, new Sort(orders));

        Page<GroupCodeList> pageList = groupCodeListRepository.findAll(pageRequest);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList.getContent());
        rtn.put("totalCount", pageList.getTotalElements());
        rtn.put("totalPageCount", pageList.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }


    @PostMapping(value="/admin/codeListEdit/save.ajax")
    public ResponseEntity<?> adminCodeListSaveAjax(@Valid CodeList codeList,BindingResult bindingResult) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        codeListRepository.save(codeList);
        Map<String, Object> rtn = new HashMap<>();
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 그룹코드 등록 여부 체크
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/admin/groupCodeListSaveCheck.ajax")
    public ResponseEntity<?> adminGroupCodeListSaveCheckAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());

        List<GroupCodeList> groupCodeList = groupCodeListRepository.findAllByGroupCodeId(ajaxDTO.get("groupCodeId").toUpperCase());

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("saveCheck", groupCodeList.size());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 그룹코드 등록/수정 하기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/admin/groupCodeListSave.ajax")
    public ResponseEntity<?> adminGroupCodeListSaveAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());

        GroupCodeList groupCodeList = new GroupCodeList();
        // 그룹코드, 그룹명, 그룹설명, 사용여부
        System.out.println("groupCodeId :: "+ajaxDTO.get("groupCodeId"));
        System.out.println("groupCodeName :: "+ajaxDTO.get("groupCodeName"));
        System.out.println("groupCodeExp :: "+ajaxDTO.get("groupCodeExp"));
        System.out.println("useYn :: "+ajaxDTO.get("useYn"));
        groupCodeList.setGroupCodeId(ajaxDTO.get("groupCodeId").toUpperCase());
        groupCodeList.setGroupCodeName(ajaxDTO.get("groupCodeName"));
        groupCodeList.setGroupCodeExp(ajaxDTO.get("groupCodeExp"));
        groupCodeList.setUseYn(ajaxDTO.get("useYn"));
        groupCodeList.setCreaId(auth.getName());
        groupCodeList.setCreaDate(new Date());
        groupCodeListRepository.save(groupCodeList);

        Map<String, Object> rtn = new HashMap<>();
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 코드 리스트 가져오기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value="/admin/codeList.ajax")
    public ResponseEntity<?> adminCodeListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        PageRequest pageRequest = new PageRequest(pageNumber-1, 7, new Sort(Sort.Direction.ASC, new String[]{"sort"}));

        Page<CodeList> pageList = codeListRepository.findAllByIdGroupCodeId(ajaxDTO.get("groupCodeId"), pageRequest);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList.getContent());
        rtn.put("totalCount", pageList.getTotalElements());
        rtn.put("totalPageCount", pageList.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 코드 등록 여부 체크 하기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/admin/codeListSaveCheck.ajax")
    public ResponseEntity<?> adminCodeListSaveCheckAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());

        List<CodeList> codeList = codeListRepository.findAllByIdGroupCodeIdAndIdCodeId(ajaxDTO.get("groupCodeId2"), ajaxDTO.get("codeId").toUpperCase());

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("saveCheck", codeList.size());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 코드 등록/수정 하기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/admin/codeListSave.ajax")
    public ResponseEntity<?> adminCodeListSaveAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());

        CodeList codeList = new CodeList();
        // 그룹코드, 그룹명, 그룹설명, 사용여부
        System.out.println("groupCodeId :: "+ajaxDTO.get("groupCodeId"));
        System.out.println("groupCodeName :: "+ajaxDTO.get("groupCodeName"));
        System.out.println("groupCodeExp :: "+ajaxDTO.get("groupCodeExp"));
        System.out.println("useYn :: "+ajaxDTO.get("useYn"));
        codeList.setId(new CodeListId(ajaxDTO.get("codeId").toUpperCase(), ajaxDTO.get("groupCodeId2")));
        codeList.setCodeName(ajaxDTO.get("codeName"));
        if (ajaxDTO.get("sort").length() > 0) codeList.setSort(Integer.parseInt(ajaxDTO.get("sort")));
        codeList.setUseYn(ajaxDTO.get("useYn2"));
        codeList.setCreaId(auth.getName());
        codeList.setCreaDate(new Date());
        codeListRepository.save(codeList);

        Map<String, Object> rtn = new HashMap<>();
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 담당자 목표관리 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/admin/goalList.ajax")
    public ResponseEntity<?> adminGoalListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        int totalCnt = goalListMapper.selectGoalListCount(ajaxDTO);
        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(LIST_PAGE_SIZE);
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<GoalListVO> pageList = null;
        try {
            pageList = goalListMapper.selectGoalList(ajaxDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList);
        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 담당자목표관리 개별 저장하기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value="/admin/goalList/save.ajax")
    public ResponseEntity<?> adminGoalListSaveAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        ajaxDTO.put("user", auth.getName());
        try {
            goalListMapper.mergeGoalInfo(ajaxDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Map<String, Object> rtn = new HashMap<>();
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 엑셀업로드확인 리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/admin/uploadList.ajax")
    public ResponseEntity<?> adminUploadListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        int totalCnt = uploadListMapper.selectUploadListCount(ajaxDTO);
        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(LIST_PAGE_SIZE);
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, Object>> pageList = null;
        try {
            pageList = uploadListMapper.selectUploadList(ajaxDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList);
        rtn.put("totalCount", totalCnt);
        rtn.put("totalPageCount",  pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 담당자별 종합실적(callnote) 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/admin/cpList.ajax")
    public ResponseEntity<?> adminCpListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
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

        List<Map<String, Object>> pageList = cpListMapper.selectCpList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList);
        rtn.put("totalPageCount", 1);
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 영업담당자 거래처정보 등록
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/admin/memberAccountInfoListSave.ajax")
    public ResponseEntity<?> adminMemberAccountInfoListSaveAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());

        MemberAccountInfoManagerList memberAccountInfoManagerList = new MemberAccountInfoManagerList();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (ajaxDTO.get("managerStartDate").length() > 0) {
            String managerStartDate = ajaxDTO.get("managerStartDate") + "-01";
            memberAccountInfoManagerList.setId(new MemberAccountInfoManagerListId(ajaxDTO.get("accountCode").toUpperCase(), ajaxDTO.get("salesManagerCode").toUpperCase(), df.parse(managerStartDate)));
        }
        if (ajaxDTO.get("managerEndDate").length() > 0) {
            String endDay = DateUtils.getEndDay(ajaxDTO.get("managerEndDate")+"-01");
            memberAccountInfoManagerList.setManagerEndDate(df.parse(ajaxDTO.get("managerEndDate")+"-"+endDay));
        }

        memberAccountInfoManagerList.setCreaDate(new Date());
        memberAccountInfoManagerList.setCreaId(auth.getName());
        memberAccountInfoManagerListRepository.save(memberAccountInfoManagerList);

        Map<String, Object> rtn = new HashMap<>();
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 영업담당자 거래처정보 관리 리스트 가져오기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value="/admin/memberAccountInfoList.ajax")
    public ResponseEntity<?> adminMemberAccountInfoListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        if (ajaxDTO.get("managerSearchStartYearMonth").length() > 0) {
            ajaxDTO.put("managerSearchStartYearMonth", ajaxDTO.get("managerSearchStartYearMonth")+"-01");
        }
        if (ajaxDTO.get("managerSearchEndYearMonth").length() > 0) {
            ajaxDTO.put("managerSearchEndYearMonth", ajaxDTO.get("managerSearchEndYearMonth")+"-"+DateUtils.getEndDay(ajaxDTO.get("managerSearchEndYearMonth")+"-01"));
        }
        int totalCnt = memberAccountInfoListMapper.selectMemberAccountInfoListCount(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(LIST_PAGE_SIZE);
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, String>> memberList = memberAccountInfoListMapper.selectMemberAccountInfoList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", memberList);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }


    /**
     * 영업담당자 거래처정보 삭제
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/admin/memberAccountInfoListDelete.ajax")
    public ResponseEntity<?> adminMemberAccountInfoListDeleteAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());

        MemberAccountInfoManagerList memberAccountInfoManagerList = new MemberAccountInfoManagerList();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (ajaxDTO.get("managerStartDate").length() > 0) {
            String managerStartDate = ajaxDTO.get("managerStartDate") + "-01";
            memberAccountInfoManagerList.setId(new MemberAccountInfoManagerListId(ajaxDTO.get("accountCode").toUpperCase(), ajaxDTO.get("salesManagerCode").toUpperCase(), df.parse(managerStartDate)));
        }
        memberAccountInfoManagerListRepository.delete(memberAccountInfoManagerList);

        Map<String, Object> rtn = new HashMap<>();
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 팝업 거래처정보리스트 가져오기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value="/admin/popupAccountInfoModal.ajax")
    public ResponseEntity<?> adminPopupAccountInfoModalAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber2") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber2"));
        }

        PageRequest pageRequest = new PageRequest(pageNumber-1, 5, new Sort(Sort.Direction.ASC, new String[]{"accountCode"}));

        Page<AccountInfoList> pageList = null;

        if (!ajaxDTO.get("accountName2").isEmpty()) {
            pageList = accountInfoListRepository.findAllByAccountNameContaining(ajaxDTO.get("accountName2"), pageRequest);
        } else {
            pageList = accountInfoListRepository.findAll(pageRequest);
        }

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList.getContent());
        rtn.put("totalCount", pageList.getTotalElements());
        rtn.put("totalPageCount", pageList.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 팝업 영업담당자 리스트 가져오기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value="/admin/popupMemberListModal.ajax")
    public ResponseEntity<?> adminPopupMemberListModalAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber3") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber3"));
        }

        int totalCnt = memberList2Mapper.selectMemberList2Count(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(7);
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, String>> memberList = memberList2Mapper.selectMemberList2(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", memberList);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());

        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 영업담당자 등록 여부 체크
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/admin/memberList2SaveCheck.ajax")
    public ResponseEntity<?> adminMemberList2SaveCheckAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());

        List<MemberList2> memberList2 = memberList2Repository.findAllBySalesManagerCode(ajaxDTO.get("salesManagerCode"));

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("saveCheck", memberList2.size());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }
    
    /**
     * 영업담당자 등록
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/admin/memberList2Save.ajax")
    public ResponseEntity<?> adminMemberList2SaveAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	System.out.println("auth :: getName() :: "+auth.getName());
    	
    	MemberList2 memberList2 = new MemberList2();
    	
    	memberList2.setSalesManagerCode(ajaxDTO.get("salesManagerCode").toUpperCase());
    	if (ajaxDTO.get("salesManagerName").length() > 0) memberList2.setSalesManagerName(ajaxDTO.get("salesManagerName"));
    	if (ajaxDTO.get("teamCode").length() > 0)memberList2.setTeamCode(ajaxDTO.get("teamCode"));
    	if (ajaxDTO.get("areaCode").length() > 0)memberList2.setAreaCode(ajaxDTO.get("areaCode"));
    	if (ajaxDTO.get("rankCode").length() > 0)memberList2.setRankCode(ajaxDTO.get("rankCode"));
    	if (ajaxDTO.get("phoneNumber").length() > 0)memberList2.setPhoneNumber(ajaxDTO.get("phoneNumber"));
    	if (ajaxDTO.get("cellPhoneNumber").length() > 0)memberList2.setCellPhoneNumber(ajaxDTO.get("cellPhoneNumber"));
    	if (ajaxDTO.get("zipCode").length() > 0)memberList2.setZipCode(ajaxDTO.get("zipCode"));
    	if (ajaxDTO.get("address").length() > 0)memberList2.setAddress(ajaxDTO.get("address"));
    	if (ajaxDTO.get("detailAddress").length() > 0)memberList2.setDetailAddress(ajaxDTO.get("detailAddress"));
    	if (ajaxDTO.get("remarks").length() > 0)memberList2.setRemarks(ajaxDTO.get("remarks"));
    	memberList2.setCreaDate(new Date());
    	memberList2.setCreaId(auth.getName());
    	memberList2Repository.save(memberList2);
    	
    	Map<String, Object> rtn = new HashMap<>();
    	return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 영업담당자 삭제
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/admin/memberList2Delete.ajax")
    public ResponseEntity<?> adminMemberList2DeleteAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());

        MemberList2 memberList2 = new MemberList2();
        memberList2.setSalesManagerCode(ajaxDTO.get("salesManagerCode"));

        memberList2Repository.delete(memberList2);

        Map<String, Object> rtn = new HashMap<>();
        return new ResponseEntity(rtn, HttpStatus.OK);
    }
    
    /**
     * 영업담당자 리스트 가져오기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value="/admin/memberList2.ajax")
    public ResponseEntity<?> adminMemberList2Ajax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        int totalCnt = memberList2Mapper.selectMemberList2Count(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(LIST_PAGE_SIZE);
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, String>> memberList = memberList2Mapper.selectMemberList2(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", memberList);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());

        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 영업담당자 거래처정보 관리 리스트 가져오기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value="/admin/adminDistrictList.ajax")
    public ResponseEntity<?> adminAdminDistrictListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        PageRequest pageRequest = new PageRequest(pageNumber-1, LIST_PAGE_SIZE, new Sort(Sort.Direction.ASC, new String[]{"administrativeDistrictName"}));

        Page<AdminDistrictList> pageList = null;

            if (!ajaxDTO.get("administrativeDistrictCode").isEmpty()) {
            pageList = adminDistrictListRepository.findAllByAdministrativeDistrictNameContaining(ajaxDTO.get("administrativeDistrictCode"), pageRequest);
        } else {
            pageList = adminDistrictListRepository.findAll(pageRequest);
        }

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList.getContent());
        rtn.put("totalCount", pageList.getTotalElements());
        rtn.put("totalPageCount", pageList.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 행정구역 거래처정보 관리 리스트 가져오기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value="/admin/zipAccountList.ajax")
    public ResponseEntity<?> adminZipAccountListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        int totalCnt = zipAccountListMapper.selectZipAccountListCount(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(LIST_PAGE_SIZE);
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, String>> list = zipAccountListMapper.selectZipAccountList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", list);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 거래처정보 관리 리스트 가져오기
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value="/admin/getAccountGradeList.ajax")
    public ResponseEntity<?> adminGetAccountGradeListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        int totalCnt = accountGradeListMapper.selectAccountGradeListCount(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(LIST_PAGE_SIZE);
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, String>> list = accountGradeListMapper.selectAccountGradeList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", list);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 거래처별 등급 등록 여부 체크
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/admin/accountGradeInfoSaveCheck.ajax")
    public ResponseEntity<?> adminAccountGradeInfoSaveCheckAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());

        List<AccountGradeList> accountGradeList = accountGradeListRepository.findAllByAccountCode(ajaxDTO.get("accountCode"));

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("saveCheck", accountGradeList.size());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }
    
    /**
     * 거래처별 등급 등록
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/admin/accountGradeInfoSave.ajax")
    public ResponseEntity<?> adminAccountGradeInfoSaveAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	System.out.println("auth :: getName() :: "+auth.getName());
    	
    	AccountGradeList accountGradeList = new AccountGradeList();
    	
    	accountGradeList.setAccountCode(ajaxDTO.get("accountCode"));
    	accountGradeList.setGradeCode(ajaxDTO.get("gradeCode"));
    	System.out.println(ajaxDTO.get("godomollYn"));
    	if (ajaxDTO.get("godomollYn").length() == 0) {
    		accountGradeList.setGodomollYn("미가입처");
    	} else {
    		accountGradeList.setGodomollYn(ajaxDTO.get("godomollYn"));
    	}
    	accountGradeListRepository.save(accountGradeList);
    	
    	Map<String, Object> rtn = new HashMap<>();
    	return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 거래처별 등급 삭제
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/admin/accountGradeInfoDelete.ajax")
    public ResponseEntity<?> adminAccountGradeInfoDeleteAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());

        AccountGradeList accountGradeList = new AccountGradeList();
        accountGradeList.setAccountCode(ajaxDTO.get("accountCode"));

        accountGradeListRepository.delete(accountGradeList);

        Map<String, Object> rtn = new HashMap<>();
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

}
