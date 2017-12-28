package com.cellbiotech.controller;

import com.cellbiotech.dao.sfa.*;
import com.cellbiotech.mapper.exmall.OrderListMapper;
import com.cellbiotech.mapper.sfa.*;
import com.cellbiotech.model.sfa.*;
import com.cellbiotech.util.DateUtils;
import com.cellbiotech.util.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 재고관리 관련 컨트롤러
 * POST, AJAX 맵핑관련
 */
@Controller
public class ImaController {

    @Autowired
    private ItemListRepository itemListRepository;


    @Autowired
    private ItemHistoryListRepository itemHistoryListRepository;

    @Autowired
    private ItemHistoryListMapper itemHistoryListMapper;

    @Autowired
    private ProdHistoryListMapper prodHistoryListMapper;

    @Autowired
    private OrderListMapper orderListMapper;

    @Autowired
    private ProdReleaseListRepository prodReleaseListRepository;

    @Autowired
    private ProdHistoryListRepository prodHistoryListRepository;

    @Autowired
    private ProdListRepository prodListRepository;

    @Autowired
    private ItemProdListRepository itemProdListRepository;

    @Autowired
    private ItemProdListMapper itemProdListMapper;

    @Autowired
    private ImaListMapper imaListMapper;

    @Autowired
    private DuolacListMapper duolacListMapper;

    @Autowired
    private BasicStockListMapper basicStockListMapper;

    @Autowired
    private ProdBasicStockRepository prodBasicStockRepository;

    @Value("${page.size}")
    private int LIST_PAGE_SIZE;

    /**
     * 품목 리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/itemList.ajax")
    public ResponseEntity<?> imaItemListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());


        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        System.out.println(":::::::::::::::::: useYn :::::::::::::: "+ajaxDTO.get("useYn"));
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC,"itemCode"));
//        orders.add(new Sort.Order(Sort.Direction.ASC,"id.orderSeq"));
        PageRequest pageRequest = new PageRequest(pageNumber-1, LIST_PAGE_SIZE, new Sort(orders));

        Page<ItemList> pageList = null;

        pageList = itemListRepository.findAllByItemCodeContainingAndItemNameContainingAndUseYnContaining(ajaxDTO.get("itemCode"), ajaxDTO.get("itemName"), ajaxDTO.get("useYn"), pageRequest);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList.getContent());
        rtn.put("totalCount", pageList.getTotalElements());
        rtn.put("totalPageCount", pageList.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 품목 재고관리여부 설정하기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/itemUseYn.ajax")
    public ResponseEntity<?> imaItemUseYnAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());
        System.out.println("auth :: isAuthenticated() :: "+auth.isAuthenticated());


        System.out.println("::::::::::::::::: "+ajaxDTO.get("itemCodes").replaceAll("&#39;", ""));
        System.out.println("::::::::::::::::: "+ajaxDTO.get("useYn"));

        String [] itemCodeArr = ajaxDTO.get("itemCodes").replaceAll("&#39;", "").split(",");
        System.out.println(":::::::::::::::::: length :::::::::::::::: "+itemCodeArr.length);

        List<ItemList> list = new ArrayList<>();
        for(String itemCode : itemCodeArr) {
            ItemList itemList = itemListRepository.findOne(itemCode);
            itemList.setItemCode(itemCode);
            itemList.setUseYn(ajaxDTO.get("useYn"));
            System.out.println(":::::::::::"+itemCode);
            list.add(itemList);
        }
        List<ItemList> sucessList = itemListRepository.save(list);

        System.out.println("aaa :::::::::::::::::::: "+sucessList.size());

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("size", sucessList.size());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 품목 재고현황 리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/itemHistoryList.ajax")
    public ResponseEntity<?> imaItemHistoryListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        if (ajaxDTO.get("searchYearMonth2").length() > 0) {
            ajaxDTO.put("searchMappingDate", ajaxDTO.get("searchYearMonth2")+"-01");
        }
        int totalCnt = itemHistoryListMapper.selectItemHistoryListCount(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(LIST_PAGE_SIZE);
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, String>> pageList = itemHistoryListMapper.selectItemHistoryList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 품목코드에 따른 재고현황 리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/itemHistoryListByItemCode.ajax")
    public ResponseEntity<?> imaItemHistoryListByItemCodeAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        System.out.println("::::::::::::::::::year :::::::::::::::::::"+year);
//        int lastYear = year;
        int lastYear = year - Integer.parseInt(ajaxDTO.get("year"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String from = lastYear + "-01-" + "01";
        String to = year + "-12-" + "31";

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC,"id.updateDate"));
        List<ItemHistoryList> list = itemHistoryListRepository.findAllByIdItemCodeAndIdUpdateDateBetween(ajaxDTO.get("itemCode"), df.parse(from), df.parse(to), new Sort(orders));
        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", list);
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 상품 판매현황 리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/prodReleaseList.ajax")
    public ResponseEntity<?> imaProdReleaseListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"id.updateDate"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"id.prodCode"));
        PageRequest pageRequest = new PageRequest(pageNumber-1, LIST_PAGE_SIZE, new Sort(orders));

        Page<ProdReleaseList> pageList = null;

        if (!ajaxDTO.get("searchYearMonth2").isEmpty()) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String updateDate = ajaxDTO.get("searchYearMonth2") + "-01";
            String toDate = ajaxDTO.get("searchYearMonth2") + "-" + DateUtils.getEndDay(updateDate);
            pageList = prodReleaseListRepository.findAllByIdUpdateDateBetweenAndIdProdCodeContainingAndProdNameContainingAndIdMallCodeContainingAndMallSiteContaining(df.parse(updateDate), df.parse(toDate), ajaxDTO.get("prodCode"), ajaxDTO.get("prodName"), ajaxDTO.get("mallCode"), ajaxDTO.get("mallSite"), pageRequest);
        } else {
            pageList = prodReleaseListRepository.findAllByIdProdCodeContainingAndProdNameContainingAndIdMallCodeContainingAndMallSiteContaining(ajaxDTO.get("prodCode"), ajaxDTO.get("prodName"), ajaxDTO.get("mallCode"), ajaxDTO.get("mallSite"), pageRequest);
        }

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList.getContent());
        rtn.put("totalCount", pageList.getTotalElements());
        rtn.put("totalPageCount", pageList.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 상품 입출고 현황 리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/prodHistoryList.ajax")
    public ResponseEntity<?> imaProdHistoryListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"id.inputDate"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"id.prodCode"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"id.inputSeq"));
        PageRequest pageRequest = new PageRequest(pageNumber-1, LIST_PAGE_SIZE, new Sort(orders));

        Page<ProdHistoryList> pageList = null;

        if (ajaxDTO.get("searchYearMonth2").length() > 0) {
            String fromDate = ajaxDTO.get("searchYearMonth2") + "-01";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String toDate = ajaxDTO.get("searchYearMonth2") + "-" + DateUtils.getEndDay(fromDate);
            pageList = prodHistoryListRepository.findAllByIdProdCodeContainingAndDelYnAndProdNameContainingAndIdInputDateBetween(ajaxDTO.get("prodCode"), "N", ajaxDTO.get("prodName"), df.parse(fromDate), df.parse(toDate), pageRequest);
        } else {
            pageList = prodHistoryListRepository.findAllByIdProdCodeContainingAndDelYnAndProdNameContaining(ajaxDTO.get("prodCode"), "N", ajaxDTO.get("prodName"), pageRequest);

        }

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList.getContent());
        rtn.put("totalCount", pageList.getTotalElements());
        rtn.put("totalPageCount", pageList.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 상품 재고현황 저장하기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/saveProdHistory.ajax")
    public ResponseEntity<?> imaSaveProdHistoryAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<ProdHistoryList> list = prodHistoryListRepository.findAllByIdProdCodeAndIdInputDate(ajaxDTO.get("itemCode"), df.parse(ajaxDTO.get("searchDateInput")));
        ProdHistoryList prodHistoryList = new ProdHistoryList();

        prodHistoryList.setOutItem(new BigDecimal(ajaxDTO.get("prodQty")));
        prodHistoryList.setInputType(ajaxDTO.get("inputType"));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date javaDate=new Date();
        System.out.println("Java Date : "+javaDate);
        String msSqlDate=sdf.format(javaDate).trim();
        System.out.println(":::::::::::::::::::::: " + msSqlDate);
        if (ajaxDTO.get("inputSeq") != null && ajaxDTO.get("inputSeq").length() > 0) {
            prodHistoryList.setId(new ProdHistoryListId(ajaxDTO.get("itemCode"), Integer.parseInt(ajaxDTO.get("inputSeq")), df.parse(ajaxDTO.get("searchDateInput"))));
            prodHistoryList.setModDate(sdf.parse(msSqlDate));
            prodHistoryList.setModId(auth.getName());
        } else {
            prodHistoryList.setId(new ProdHistoryListId(ajaxDTO.get("itemCode"), list.size()+1, df.parse(ajaxDTO.get("searchDateInput"))));
            prodHistoryList.setRegDate(sdf.parse(msSqlDate));
            prodHistoryList.setRegId(auth.getName());
        }
        prodHistoryList.setDelYn("N");
        prodHistoryList.setProdName(ajaxDTO.get("itemName"));

        prodHistoryListRepository.save(prodHistoryList);

        Map<String, Object> rtn = new HashMap<>();
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 상품입출고 삭제
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/ima/deleteItemHistory.ajax")
    public ResponseEntity<?> imaDeleteItemHistoryAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ProdHistoryList prodHistoryList = new ProdHistoryList();
        prodHistoryList.setId(new ProdHistoryListId(ajaxDTO.get("itemCode"), Integer.parseInt(ajaxDTO.get("inputSeq")), df.parse(ajaxDTO.get("searchDateInput"))));
        prodHistoryList.setDelYn("Y");
//        prodHistoryListRepository.delete(prodHistoryList);
        prodHistoryList.setClosingStock(new BigDecimal(ajaxDTO.get("prodQty")));
        prodHistoryList.setOutItem(new BigDecimal(ajaxDTO.get("prodQty")));
        prodHistoryList.setInputType(ajaxDTO.get("inputType"));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date javaDate=new Date();
        System.out.println("Java Date : "+javaDate);
        String msSqlDate=sdf.format(javaDate).trim();
        prodHistoryList.setModDate(sdf.parse(msSqlDate));
        prodHistoryList.setModId(auth.getName());
        prodHistoryListRepository.save(prodHistoryList);

        Map<String, Object> rtn = new HashMap<>();
        return new ResponseEntity(rtn, HttpStatus.OK);
    }
    /**
     * 상품코드에 따른 재고현황 리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/prodHistoryListByProdCode.ajax")
    public ResponseEntity<?> imaProdHistoryListByProdCodeAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        System.out.println("::::::::::::::::::year :::::::::::::::::::"+year);
//        int lastYear = year;
        int lastYear = year - Integer.parseInt(ajaxDTO.get("year"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String from = lastYear + "-01-" + "01";
        String to = year + "-12-" + "31";

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC,"id.inputDate"));
        List<ProdHistoryList> list = prodHistoryListRepository.findAllByIdProdCodeAndIdInputDateBetween(ajaxDTO.get("prodCode"), df.parse(from), df.parse(to), new Sort(orders));
        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", list);
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 상품 재고현황 사용여부 설정하기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/prodUseYn.ajax")
    public ResponseEntity<?> imaProdUseYnAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();



        String [] idArr = ajaxDTO.get("prodCodes").replaceAll("&#39;", "").split(",");

        List<ProdList> list = new ArrayList<>();
        for(String ids : idArr) {
            String [] id = ids.split("-");
            System.out.println(":::::::::"+id[0]+":::::::::::::"+id[1]);
//            ProdListId prodListId = new ProdListId(id[0], id[1]);
            ProdList prodList = prodListRepository.findByIdProdCodeAndIdMallSite(id[0], id[1]);
            prodList.setUseYn(ajaxDTO.get("useYn"));
            list.add(prodList);
        }
        List<ProdList> sucessList = prodListRepository.save(list);

        System.out.println("aaa :::::::::::::::::::: "+sucessList.size());

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("size", sucessList.size());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 상품 리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/prodList.ajax")
    public ResponseEntity<?> imaProdListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC,"id.prodCode"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"id.mallSite"));
        PageRequest pageRequest = new PageRequest(pageNumber-1, LIST_PAGE_SIZE, new Sort(orders));

        Page<ProdList> pageList = null;

        pageList = prodListRepository.findAllByIdProdCodeContainingAndProdNameContainingAndProdTypeContainingAndIdMallSiteContainingAndUseYnContaining(ajaxDTO.get("prodCode"),ajaxDTO.get("prodName"), ajaxDTO.get("prodType"),ajaxDTO.get("mallSite"), ajaxDTO.get("useYn"),  pageRequest);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList.getContent());
        rtn.put("totalCount", pageList.getTotalElements());
        rtn.put("totalPageCount", pageList.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 품목-상품 리스트 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/itemProdList.ajax")
    public ResponseEntity<?> imaItemProdListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        if (ajaxDTO.get("searchMappingDate").length() > 0) {
            ajaxDTO.put("searchMappingDate", ajaxDTO.get("searchMappingDate")+"-01");
        }
        int totalCnt = itemProdListMapper.selectItemProdListCount(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(LIST_PAGE_SIZE);
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, String>> pageList = itemProdListMapper.selectItemProdList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 품목-상품 등록
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/ima/createItemProd.ajax")
    public ResponseEntity<?> imaCreateItemProdAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());

        ItemProdList itemProdList = new ItemProdList();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String mappingDate = ajaxDTO.get("mappingDate") + "-01";
        itemProdList.setId(new ItemProdListId(ajaxDTO.get("itemCode"), ajaxDTO.get("prodCode")));
        itemProdList.setMappingDate(df.parse(mappingDate));

        itemProdListRepository.save(itemProdList);

        Map<String, Object> rtn = new HashMap<>();
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 품목-상품 삭제
     * @param ajaxDTO
     * @return
     */
    @PostMapping(value = "/ima/deleteItemProd.ajax")
    public ResponseEntity<?> imaDeleteItemProdAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth :: getName() :: "+auth.getName());

        ItemProdList itemProdList = new ItemProdList();
        itemProdList.setId(new ItemProdListId(ajaxDTO.get("itemCode"), ajaxDTO.get("prodCode")));
        itemProdListRepository.delete(itemProdList);

        Map<String, Object> rtn = new HashMap<>();
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 재고현황 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/imaList.ajax")
    public ResponseEntity<?> imaImaListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        if (ajaxDTO.get("searchYearMonth2").length() > 0) {
            ajaxDTO.put("searchMappingDate", ajaxDTO.get("searchYearMonth2")+"-01");
        }
        int totalCnt = imaListMapper.selectImaListCount(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(200);//재고현황은 한번에 보이도록 작성
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, String>> pageList = imaListMapper.selectImaList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 판매현황 - 자사몰 판매현황 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/duolacList.ajax")
    public ResponseEntity<?> imaDuolacListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(" auth.isAuthenticated() :: " + auth.isAuthenticated());
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

        ajaxDTO.put("searchDateInput", strDate);
        ajaxDTO.put("searchDateNoDash", strDate.replaceAll("-", ""));
        ajaxDTO.put("prodTypeGsp", "G");

        int totalCnt = duolacListMapper.selectDuolacListCount(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(200);//재고현황은 한번에 보이도록 작성
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, String>> pageList = duolacListMapper.selectDuolacList(ajaxDTO);

        ajaxDTO.put("prodTypeGsp", "P");
        List<Map<String, String>> pointList = duolacListMapper.selectDuolacList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList);
        rtn.put("pointList", pointList);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 판매현황 - 자사몰 판매현황 가져오기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/duolacDashboardList.ajax")
    public ResponseEntity<?> imaDuolacDashBoardListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(" auth.isAuthenticated() :: " + auth.isAuthenticated());
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

        ajaxDTO.put("searchDateInput", strDate);
        ajaxDTO.put("searchDateNoDash", strDate.replaceAll("-", ""));
        ajaxDTO.put("prodTypeGsp", "G");

        int totalCnt = duolacListMapper.selectDuolacListCount(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(200);//재고현황은 한번에 보이도록 작성
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, Object>> pageList = duolacListMapper.selectDuolacDashboardList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("data", pageList);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }


    /**
     * 기초 재고 관리
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/basicStockList.ajax")
    public ResponseEntity<?> imaBasicStockListAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        int pageNumber = 1;
        if (ajaxDTO.get("pageNumber") == null) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(ajaxDTO.get("pageNumber"));
        }

        if (ajaxDTO.get("searchYearMonth2")!= null && ajaxDTO.get("searchYearMonth2").length() > 0) {
            ajaxDTO.put("searchMappingDate", ajaxDTO.get("searchYearMonth2")+"-01");
        }
        int totalCnt = basicStockListMapper.selectBasicStockListCount(ajaxDTO);

        PagingUtil pageInfo = new PagingUtil(totalCnt, pageNumber);
        pageInfo.setPageSize(LIST_PAGE_SIZE);
        ajaxDTO.put("startPageNum", Integer.toString(pageInfo.getStartPageNum()));
        ajaxDTO.put("endPageNum", Integer.toString(pageInfo.getEndPageNum()));

        List<Map<String, String>> pageList = basicStockListMapper.selectBasicStockList(ajaxDTO);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("list", pageList);
        rtn.put("totalCount", pageInfo.getTotalCount());
        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }


    /**
     * 기초 재고 저장하기
     * @param ajaxDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value="/ima/saveBasicStock.ajax")
    public ResponseEntity<?> imaSaveBasicStockAjax(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ProdBasicStock prodBasicStock = new ProdBasicStock();

        prodBasicStock.setId(new ProdBasicStockId(ajaxDTO.get("itemCode"), ajaxDTO.get("plant"), ajaxDTO.get("inputDate")));
        prodBasicStock.setQtGoodInv(new BigDecimal(ajaxDTO.get("qtGoodInv")));

        prodBasicStockRepository.save(prodBasicStock);

        Map<String, Object> rtn = new HashMap<>();
        return new ResponseEntity(rtn, HttpStatus.OK);
    }
}
