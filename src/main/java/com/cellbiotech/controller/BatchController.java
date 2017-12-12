package com.cellbiotech.controller;

import com.cellbiotech.dao.sfa.*;
import com.cellbiotech.mapper.callnote.DuolacMallMapper;
import com.cellbiotech.mapper.exmall.OrderListMapper;
import com.cellbiotech.mapper.neoe.CbtisBatchListMapper;
import com.cellbiotech.mapper.neoe.DuzonDeliveryQtBatchMapper;
import com.cellbiotech.mapper.sfa.ProdBasicStockBatchListMapper;
import com.cellbiotech.mapper.sfa.ProdInvBatchListMapper;
import com.cellbiotech.model.sfa.*;
import com.cellbiotech.util.CommonUtils;
import com.cellbiotech.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * 배치 호출 컨트롤러
 * GET 호출
 */
@Controller
public class BatchController {

    @Autowired
    private OrderListMapper orderListMapper;

    @Autowired
    private DuolacMallMapper duolacMallMapper;

    @Autowired
    private ProdReleaseListRepository prodReleaseListRepository;

    @Autowired
    private DuolacReleaseRepository duolacReleaseRepository;

    @Autowired
    private DuzonDeliveryQtBatchMapper duzonDeliveryQtBatchMapper;

    @Autowired
    private ProdInvRepository prodInvRepository;

    @Autowired
    private ProdInvBatchListMapper prodInvBatchListMapper;

    @Autowired
    private ProdBasicStockBatchListMapper prodBasicStockBatchListMapper;

    @Autowired
    private ProdBasicStockRepository prodBasicStockRepository;

    @Autowired
    private DuzonDeliveryQtListRepository duzonDeliveryQtListRepository;

    /**
     * 재고관리 - 배치 - 외부몰
     * @return
     */
    @GetMapping("/exmallBatch")
    public ModelAndView exmallBatch(@RequestParam Map<String, Object> ajaxDTO) throws Exception {
        System.out.println("::::::::::::::::::::::::::exmall Batch:::::::::::::::::::::::::::::::::::::::::::::::::");

        // 특정 형태의 날짜로 값을 뽑기
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = df.format(cal.getTime());

        // 날짜 더하기
        Calendar cal = Calendar.getInstance();
        if (ajaxDTO.get("date") == null ) {
//            inputDate = "2017-07-16";
            cal.setTime(new Date());
        } else {
            String inputDate = (String)ajaxDTO.get("date");
            Date date = df.parse(inputDate);
            cal.setTime(date);
        }
//        cal.setTime(new Date());
//        Date date = df.parse("2017-10-17");
//        cal.setTime(date);
        // 특정 형태의 날짜로 값을 뽑기
        String strDate = df.format(cal.getTime());

        System.err.println(df.format(cal.getTime()));

//        Map<String, Object> map = new HashMap<>();

        ajaxDTO.put("regDate", strDate);
        System.out.println("regDate :: "+ strDate);
        List<Map<String, Object>> list = orderListMapper.selectOrderList(ajaxDTO);

        System.out.println("list.size() :: "+list.size());
        List<ProdReleaseList> prodlList = new ArrayList<>();

        for (Map orderInfo : list) {
            ProdReleaseList prodInfo = new ProdReleaseList();
            prodInfo.setId(new ProdReleaseListId(DateUtils.getStringtoDate((String) orderInfo.get("uploadDate"), "-"), (String) orderInfo.get("prodno"), (String) orderInfo.get("omDesc")));
            prodInfo.setProdName((String) orderInfo.get("prodnm"));
            prodInfo.setMallSite("EXMALL");
            prodInfo.setProdQty(Integer.parseInt(orderInfo.get("sqty").toString()));
            prodlList.add(prodInfo);
        }

        System.out.println("exmallList.size() :: "+prodlList.size());
        if (prodlList.size() > 0) {
            prodReleaseListRepository.save(prodlList);
        }

        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("module", "orderList");
        modelAndView.setViewName("/login");
        return modelAndView;
    }

    /**
     * 재고관리 - 배치 - 외부몰
     * @return
     */
    @GetMapping("/exmallBatch2")
    public ModelAndView exmallBatch2(@RequestParam Map<String, Object> ajaxDTO) throws Exception {
        System.out.println("::::::::::::::::::::::::::exmall Batch:::::::::::::::::::::::::::::::::::::::::::::::::");

        // 특정 형태의 날짜로 값을 뽑기
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = df.format(cal.getTime());


        String inputDate = null;
//
//        if (ajaxDTO.get("date") == null ) {
//            inputDate = "2017-07-16";
//        } else {
//            inputDate = (String)ajaxDTO.get("date");
//            System.out.println("new Date() :::::::::::: "+new Date());
//        }
        // 날짜 더하기
        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
        if (ajaxDTO.get("date") == null ) {
            inputDate = "2017-08-01";
            cal.setTime(new Date());
        } else {
            inputDate = (String)ajaxDTO.get("date");
            Date date = df.parse(inputDate);
            cal.setTime(date);
        }
        Date date = df.parse(inputDate);
        int cnt = 0;
        if (ajaxDTO.get("cnt") == null) {
            cnt = 31;
        } else {
            cnt = Integer.parseInt(ajaxDTO.get("cnt").toString());
        }
        for(int i=0; i<cnt; i++) {
            cal.setTime(date);
            cal.add(Calendar.DATE, i);
            String strDate = df.format(cal.getTime());

            System.err.println(df.format(cal.getTime()));

//        Map<String, Object> map = new HashMap<>();

            ajaxDTO.put("regDate", strDate);
            System.out.println("regDate :: " + strDate);
            List<Map<String, Object>> list = orderListMapper.selectOrderList(ajaxDTO);

            System.out.println("list.size() :: " + list.size());
            List<ProdReleaseList> prodlList = new ArrayList<>();

            for (Map orderInfo : list) {
                ProdReleaseList prodInfo = new ProdReleaseList();
                prodInfo.setId(new ProdReleaseListId(DateUtils.getStringtoDate((String) orderInfo.get("uploadDate"), "-"), (String) orderInfo.get("prodno"), (String) orderInfo.get("omDesc")));
                prodInfo.setProdName((String) orderInfo.get("prodnm"));
                prodInfo.setMallSite("EXMALL");
                prodInfo.setProdQty(Integer.parseInt(orderInfo.get("sqty").toString()));
                prodlList.add(prodInfo);
            }

            System.out.println("exmallList.size() :: " + prodlList.size());
            if (prodlList.size() > 0) {
                prodReleaseListRepository.save(prodlList);
            }
        }

        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("module", "orderList");
        modelAndView.setViewName("/login");
        return modelAndView;
    }

    /**
     * 재고관리 - 배치 - 듀오락몰
     * @return
     */
    @GetMapping("/duolacBatch")
    public ModelAndView batch(@RequestParam Map<String, Object> ajaxDTO) throws Exception {
        System.out.println("::::::::::::::::::::::::::duolac batch:::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("date :::::::::::::::::::: "+ajaxDTO.get("date"));

        // 특정 형태의 날짜로 값을 뽑기
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = df.format(cal.getTime());


//        String inputDate = null;
//
//        if (ajaxDTO.get("date") == null ) {
//            inputDate = "2017-07-16";
//        } else {
//            inputDate = (String)ajaxDTO.get("date");
//            System.out.println("new Date() :::::::::::: "+new Date());
//        }
        // 날짜 더하기
        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
        if (ajaxDTO.get("date") == null ) {
//            inputDate = "2017-07-16";
            cal.setTime(new Date());
        } else {
            String inputDate = (String)ajaxDTO.get("date");
            Date date = df.parse(inputDate);
            cal.setTime(date);
        }
//        Date date = df.parse(inputDate);
//        for(int i=0; i<300; i++) {
//            cal.setTime(date);
//            cal.add(Calendar.DATE, i);
            // 특정 형태의 날짜로 값을 뽑기
            String strDate = df.format(cal.getTime());

            System.err.println(df.format(cal.getTime()));

//            Map<String, Object> ajaxDTO = new HashMap<>();

            ajaxDTO.put("regDate", strDate);
            System.out.println("regDate :: "+ strDate);
            List<Map<String, Object>> list = duolacMallMapper.selectDuolacList(ajaxDTO);

            System.out.println("list.size() :: "+list.size());
            List<DuolacReleaseList> duolacReleaseList = new ArrayList<>();

            for (Map orderInfo : list) {
                DuolacReleaseList duolacInfo = new DuolacReleaseList();
                duolacInfo.setId(new DuolacReleaseListId(DateUtils.getStringtoDate((String) orderInfo.get("targetDate"), "-"), (String) orderInfo.get("prodCd")));
                duolacInfo.setProdName((String) orderInfo.get("prodNm"));
                duolacInfo.setProdQty(Integer.parseInt(orderInfo.get("prodQty").toString()));
                if (orderInfo.get("realProdPrice") != null) duolacInfo.setSalesPriceSum(new BigDecimal(orderInfo.get("realProdPrice").toString()));
                if (orderInfo.get("unitPrice") != null) duolacInfo.setUnitPrice(new BigDecimal(orderInfo.get("unitPrice").toString()));
                duolacInfo.setOneTime(new BigDecimal(orderInfo.get("oneTime").toString()));
                duolacInfo.setTwoTime(new BigDecimal(orderInfo.get("twoTime").toString()));
                duolacInfo.setThreeTime(new BigDecimal(orderInfo.get("threeTime").toString()));
                duolacInfo.setProdQtyReturn(new BigDecimal(orderInfo.get("prodQtyReturn").toString()));
                duolacInfo.setProdTypeGsp(orderInfo.get("prodTypeGsp").toString());
                duolacReleaseList.add(duolacInfo);
            }

            System.out.println("duolacList.size() :: "+ duolacReleaseList.size());
            if (duolacReleaseList.size() > 0) {
                duolacReleaseRepository.save(duolacReleaseList);
            }
//        }


        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("module", "orderList");
        modelAndView.setViewName("/login");
        return modelAndView;
    }


    /**
     * 재고관리 - 배치 - 듀오락몰
     * @return
     */
    @GetMapping("/duolacBatch2")
    public ModelAndView batch2(@RequestParam Map<String, Object> ajaxDTO) throws Exception {
        System.out.println("::::::::::::::::::::::::::duolac batch:::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("date :::::::::::::::::::: "+ajaxDTO.get("date"));

        // 특정 형태의 날짜로 값을 뽑기
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = df.format(cal.getTime());


        String inputDate = null;
//
//        if (ajaxDTO.get("date") == null ) {
//            inputDate = "2017-07-16";
//        } else {
//            inputDate = (String)ajaxDTO.get("date");
//            System.out.println("new Date() :::::::::::: "+new Date());
//        }
        // 날짜 더하기
        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
        if (ajaxDTO.get("date") == null ) {
            inputDate = "2017-07-16";
            cal.setTime(new Date());
        } else {
            inputDate = (String)ajaxDTO.get("date");
            Date date = df.parse(inputDate);
            cal.setTime(date);
        }
        Date date = df.parse(inputDate);
        int cnt = 0;
        if (ajaxDTO.get("cnt") == null) {
            cnt = 31;
        } else {
            cnt = Integer.parseInt(ajaxDTO.get("cnt").toString());
        }
        for(int i=0; i<cnt; i++) {
            cal.setTime(date);
            cal.add(Calendar.DATE, i);
            // 특정 형태의 날짜로 값을 뽑기
            String strDate = df.format(cal.getTime());

            System.err.println(df.format(cal.getTime()));

//            Map<String, Object> ajaxDTO = new HashMap<>();

            ajaxDTO.put("regDate", strDate);
            System.out.println("regDate :: "+ strDate);
            List<Map<String, Object>> list = duolacMallMapper.selectDuolacList(ajaxDTO);

            System.out.println("list.size() :: "+list.size());
            List<DuolacReleaseList> duolacReleaseList = new ArrayList<>();

            for (Map orderInfo : list) {
                DuolacReleaseList duolacInfo = new DuolacReleaseList();
                duolacInfo.setId(new DuolacReleaseListId(DateUtils.getStringtoDate((String) orderInfo.get("targetDate"), "-"), (String) orderInfo.get("prodCd")));
                duolacInfo.setProdName((String) orderInfo.get("prodNm"));
                duolacInfo.setProdQty(Integer.parseInt(orderInfo.get("prodQty").toString()));
                if (orderInfo.get("realProdPrice") != null) duolacInfo.setSalesPriceSum(new BigDecimal(orderInfo.get("realProdPrice").toString()));
                if (orderInfo.get("unitPrice") != null) duolacInfo.setUnitPrice(new BigDecimal(orderInfo.get("unitPrice").toString()));
                duolacInfo.setOneTime(new BigDecimal(orderInfo.get("oneTime").toString()));
                duolacInfo.setTwoTime(new BigDecimal(orderInfo.get("twoTime").toString()));
                duolacInfo.setThreeTime(new BigDecimal(orderInfo.get("threeTime").toString()));
                duolacInfo.setProdQtyReturn(new BigDecimal(orderInfo.get("prodQtyReturn").toString()));
                duolacInfo.setProdTypeGsp(orderInfo.get("prodTypeGsp").toString());
                duolacReleaseList.add(duolacInfo);
            }

            System.out.println("duolacList.size() :: "+ duolacReleaseList.size());
            if (duolacReleaseList.size() > 0) {
                duolacReleaseRepository.save(duolacReleaseList);
            }
        }


        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("module", "orderList");
        modelAndView.setViewName("/login");
        return modelAndView;
    }

    /**
     * 상품 입출고 현황 - 배송실 입고 현황
     * @return
     */
    @GetMapping("/duzonDeliveryQtBatch")
    public ModelAndView duzonDeliveryQtBatch(@RequestParam Map<String, Object> ajaxDTO) throws Exception {
        System.out.println("::::::::::::::::::::::::::prodInvCBTISBatch:::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("date :::::::::::::::::::: "+ajaxDTO.get("date"));

        // 특정 형태의 날짜로 값을 뽑기
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        if (ajaxDTO.get("date") == null ) {
            cal.setTime(new Date());
        } else {
            String inputDate = (String)ajaxDTO.get("date");
            Date date = df.parse(inputDate);
            cal.setTime(date);
        }

        String strDate = df.format(cal.getTime());

        System.err.println(df.format(cal.getTime()));

        ajaxDTO.put("regDate", strDate.replaceAll("-", ""));
        System.out.println("regDate :: "+ strDate);
        List<Map<String, Object>> list = duzonDeliveryQtBatchMapper.selectDuzonDeliveryBatch(ajaxDTO);

        System.out.println("list.size() :: "+list.size());
        List<DuzonDeliveryQtList> duzonDeliveryQtLists = new ArrayList<>();

        for (Map duzonInfo : list) {
            DuzonDeliveryQtList duzonDeliveryQtList = new DuzonDeliveryQtList();
            duzonDeliveryQtList.setId(new DuzonDeliveryQtListId(duzonInfo.get("itemCode").toString(), duzonInfo.get("dtSo").toString(), duzonInfo.get("deliveryType").toString(), duzonInfo.get("noSo").toString(), Integer.parseInt(duzonInfo.get("seqSo").toString())));
            duzonDeliveryQtList.setQtDelivery(Integer.parseInt(CommonUtils.getDecimal2String(duzonInfo.get("qtDelivery").toString(), 5)));
            duzonDeliveryQtLists.add(duzonDeliveryQtList);
        }

        System.out.println("prodInvList.size() :: "+ duzonDeliveryQtLists.size());
        if (duzonDeliveryQtLists.size() > 0) {
            duzonDeliveryQtListRepository.save(duzonDeliveryQtLists);
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/home");
        return modelAndView;
    }


    /**
     * 상품 입출고 현황 - 배송실 입고 현황
     * @return
     */
    @GetMapping("/duzonDeliveryQtBatchByCnt")
    public ModelAndView duzonDeliveryQtBatchByCnt(@RequestParam Map<String, Object> ajaxDTO) throws Exception {
        System.out.println("::::::::::::::::::::::::::prodInvCBTISBatchByCnt:::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("date :::::::::::::::::::: "+ajaxDTO.get("date"));

        // 특정 형태의 날짜로 값을 뽑기
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String inputDate = null;
        // 날짜 더하기
        Calendar cal = Calendar.getInstance();
        if (ajaxDTO.get("date") == null ) {
            inputDate = "2017-07-16";
            cal.setTime(new Date());
        } else {
            inputDate = (String)ajaxDTO.get("date");
            Date date = df.parse(inputDate);
            cal.setTime(date);
        }

        Date date = df.parse(inputDate);
        int cnt = 0;
        if (ajaxDTO.get("cnt") == null) {
            cnt = 31;
        } else {
            cnt = Integer.parseInt(ajaxDTO.get("cnt").toString());
        }

        for(int i=0; i<cnt; i++) {
            cal.setTime(date);
            cal.add(Calendar.DATE, i);
            // 특정 형태의 날짜로 값을 뽑기
            String strDate = df.format(cal.getTime());

            System.err.println(df.format(cal.getTime()));

            ajaxDTO.put("regDate", strDate.replaceAll("-", ""));
            System.out.println("regDate :: "+ strDate);
            List<Map<String, Object>> list = duzonDeliveryQtBatchMapper.selectDuzonDeliveryBatch(ajaxDTO);

            System.out.println("list.size() :: "+list.size());
            List<DuzonDeliveryQtList> duzonDeliveryQtLists = new ArrayList<>();

            for (Map duzonInfo : list) {
                DuzonDeliveryQtList duzonDeliveryQtList = new DuzonDeliveryQtList();
                duzonDeliveryQtList.setId(new DuzonDeliveryQtListId(duzonInfo.get("itemCode").toString(), duzonInfo.get("dtSo").toString(), duzonInfo.get("deliveryType").toString(), duzonInfo.get("noSo").toString(), Integer.parseInt(duzonInfo.get("seqSo").toString())));
                duzonDeliveryQtList.setQtDelivery(Integer.parseInt(CommonUtils.getDecimal2String(duzonInfo.get("qtDelivery").toString(), 5)));
                duzonDeliveryQtLists.add(duzonDeliveryQtList);
            }

            System.out.println("prodInvList.size() :: "+ duzonDeliveryQtLists.size());
            if (duzonDeliveryQtLists.size() > 0) {
                duzonDeliveryQtListRepository.save(duzonDeliveryQtLists);
            }
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/home");
        return modelAndView;
    }

    /**
     * 상품 입출고 현황 - 자사몰,외부몰 판매 현황
     * @return
     */
    @GetMapping("/sfaBatch")
    public ModelAndView sfaBatch(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        System.out.println("::::::::::::::::::::::::::sfaBatch:::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("date :::::::::::::::::::: "+ajaxDTO.get("date"));

        // 특정 형태의 날짜로 값을 뽑기
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        if (ajaxDTO.get("date") == null ) {
            cal.setTime(new Date());
        } else {
            String inputDate = (String)ajaxDTO.get("date");
            Date date = df.parse(inputDate);
            cal.setTime(date);
        }

        String strDate = df.format(cal.getTime());

        System.err.println(df.format(cal.getTime()));

        ajaxDTO.put("regDate", strDate.replaceAll("-", ""));
        System.out.println("regDate :: "+ strDate);
        List<Map<String, String>> list = prodInvBatchListMapper.selectProdInvBatchList(ajaxDTO);

        System.out.println("list.size() :: "+list.size());
        List<ProdInv> prodInvList = new ArrayList<>();

        for (Map cbtisInfo : list) {
            ProdInv prodInv = new ProdInv();
            prodInv.setId(new ProdInvId(cbtisInfo.get("cdPlant").toString(), cbtisInfo.get("cdItem").toString(), cbtisInfo.get("yyInv").toString(), cbtisInfo.get("ymIo").toString()));
            prodInv.setQtDelivery(new BigDecimal(cbtisInfo.get("qtDelivery").toString()));
            prodInv.setQtDuolac(new BigDecimal(cbtisInfo.get("qtDuolac").toString()));
            prodInv.setQtExmall(new BigDecimal(cbtisInfo.get("qtExmall").toString()));
            prodInvList.add(prodInv);
        }

        System.out.println("prodInvList.size() :: "+ prodInvList.size());
        if (prodInvList.size() > 0) {
            prodInvRepository.save(prodInvList);
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/home");
        return modelAndView;
    }


    /**
     * 상품 입출고 현황 - 자사몰,외부몰 판매 현황
     * @return
     */
    @GetMapping("/sfaBatchByCnt")
    public ModelAndView sfaBatchByCnt(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        System.out.println("::::::::::::::::::::::::::sfaBatchByCnt:::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("date :::::::::::::::::::: "+ajaxDTO.get("date"));

        // 특정 형태의 날짜로 값을 뽑기
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String inputDate = null;
        // 날짜 더하기
        Calendar cal = Calendar.getInstance();
        if (ajaxDTO.get("date") == null ) {
            inputDate = "2017-07-16";
            cal.setTime(new Date());
        } else {
            inputDate = (String)ajaxDTO.get("date");
            Date date = df.parse(inputDate);
            cal.setTime(date);
        }

        Date date = df.parse(inputDate);
        int cnt = 0;
        if (ajaxDTO.get("cnt") == null) {
            cnt = 31;
        } else {
            cnt = Integer.parseInt(ajaxDTO.get("cnt").toString());
        }

        for(int i=0; i<cnt; i++) {
            cal.setTime(date);
            cal.add(Calendar.DATE, i);
            // 특정 형태의 날짜로 값을 뽑기
            String strDate = df.format(cal.getTime());

            System.err.println(df.format(cal.getTime()));

            ajaxDTO.put("regDate", strDate.replaceAll("-", ""));
            System.out.println("regDate :: "+ strDate);
            List<Map<String, String>> list = prodInvBatchListMapper.selectProdInvBatchList(ajaxDTO);

            System.out.println("list.size() :: "+list.size());
            List<ProdInv> prodInvList = new ArrayList<>();

            for (Map cbtisInfo : list) {
                ProdInv prodInv = new ProdInv();
                prodInv.setId(new ProdInvId(cbtisInfo.get("cdPlant").toString(), cbtisInfo.get("cdItem").toString(), cbtisInfo.get("yyInv").toString(), cbtisInfo.get("ymIo").toString()));
                prodInv.setQtDelivery(new BigDecimal(cbtisInfo.get("qtDelivery").toString()));
                prodInv.setQtDuolac(new BigDecimal(cbtisInfo.get("qtDuolac").toString()));
                prodInv.setQtExmall(new BigDecimal(cbtisInfo.get("qtExmall").toString()));
                prodInvList.add(prodInv);
            }

            System.out.println("prodInvList.size() :: "+ prodInvList.size());
            if (prodInvList.size() > 0) {
                prodInvRepository.save(prodInvList);
            }
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/home");
        return modelAndView;
    }

    /**
     * 배송실 - 기초재고 생성
     * @return
     */
    @GetMapping("/prodBasicStockBatch")
    public ModelAndView prodBasicStockBatch(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        System.out.println("::::::::::::::::::::::::::sfaBatch:::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("date :::::::::::::::::::: "+ajaxDTO.get("date"));

        // 특정 형태의 날짜로 값을 뽑기
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        if (ajaxDTO.get("date") == null ) {
            cal.setTime(new Date());
        } else {
            String inputDate = (String)ajaxDTO.get("date");
            Date date = df.parse(inputDate);
            cal.setTime(date);
        }

        String strDate = df.format(cal.getTime()).replaceAll("-", "");

        System.err.println(df.format(cal.getTime()));

        ajaxDTO.put("regDate", strDate);
        System.out.println("regDate :: "+ strDate);
        List<Map<String, String>> list = prodBasicStockBatchListMapper.selectProdBasicStockBatchList(ajaxDTO);

        System.out.println("list.size() :: "+list.size());
        List<ProdBasicStock> prodBasicStockList = new ArrayList<>();

        for (Map info : list) {
            ProdBasicStock prodBasicStock = new ProdBasicStock();
            prodBasicStock.setId(new ProdBasicStockId(info.get("cdItem").toString(), info.get("cdPlant").toString(), info.get("ymStandard").toString()));
            prodBasicStock.setQtGoodInv(new BigDecimal(info.get("qtGoodInv").toString()));
            prodBasicStockList.add(prodBasicStock);
        }

        System.out.println("prodBasicStockList.size() :: "+ prodBasicStockList.size());
        if (prodBasicStockList.size() > 0) {
            prodBasicStockRepository.save(prodBasicStockList);
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/home");
        return modelAndView;
    }


    /**
     * 배송실 - 기초재고 생성
     * @return
     */
    @GetMapping("/prodBasicStockBatchByCnt")
    public ModelAndView prodBasicStockBatchByCnt(@RequestParam Map<String, String> ajaxDTO) throws Exception {
        System.out.println("::::::::::::::::::::::::::sfaBatchByCnt:::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("date :::::::::::::::::::: "+ajaxDTO.get("date"));

        // 특정 형태의 날짜로 값을 뽑기
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String inputDate = null;
        // 날짜 더하기
        Calendar cal = Calendar.getInstance();
        if (ajaxDTO.get("date") == null ) {
            inputDate = "2017-07-16";
            cal.setTime(new Date());
        } else {
            inputDate = (String)ajaxDTO.get("date");
            Date date = df.parse(inputDate);
            cal.setTime(date);
        }

        Date date = df.parse(inputDate);
        int cnt = 0;
        if (ajaxDTO.get("cnt") == null) {
            cnt = 31;
        } else {
            cnt = Integer.parseInt(ajaxDTO.get("cnt").toString());
        }

        for(int i=0; i<cnt; i++) {
            cal.setTime(date);
            cal.add(Calendar.DATE, i);
            // 특정 형태의 날짜로 값을 뽑기
            String strDate = df.format(cal.getTime());

            System.err.println(df.format(cal.getTime()));

            ajaxDTO.put("regDate", strDate.replaceAll("-", ""));
            System.out.println("regDate :: "+ strDate);
            List<Map<String, String>> list = prodBasicStockBatchListMapper.selectProdBasicStockBatchList(ajaxDTO);

            System.out.println("list.size() :: "+list.size());
            List<ProdBasicStock> prodBasicStockList = new ArrayList<>();

            for (Map info : list) {
                ProdBasicStock prodBasicStock = new ProdBasicStock();
                prodBasicStock.setId(new ProdBasicStockId(info.get("cdItem").toString(), info.get("cdPlant").toString(), info.get("ymStandard").toString()));
                prodBasicStock.setQtGoodInv(new BigDecimal(info.get("qtGoodInv").toString()));
                prodBasicStockList.add(prodBasicStock);
            }

            System.out.println("prodBasicStockList.size() :: "+ prodBasicStockList.size());
            if (prodBasicStockList.size() > 0) {
                prodBasicStockRepository.save(prodBasicStockList);
            }
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/home");
        return modelAndView;
    }

    /**
     * 재고관리 - 배치 - 듀오락몰
     * @return
     */
    @PostMapping("/syncList.ajax")
    public ResponseEntity<?> syncListAjax(@RequestParam Map<String, Object> ajaxDTO) throws Exception {
        System.out.println("::::::::::::::::::::::::::duolac batch:::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("date :::::::::::::::::::: "+ajaxDTO.get("date"));

        // 특정 형태의 날짜로 값을 뽑기
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // 날짜 더하기
        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
        if (ajaxDTO.get("date") == null ) {
            cal.setTime(new Date());
        } else {
            String inputDate = (String)ajaxDTO.get("date");
            Date date = df.parse(inputDate);
            cal.setTime(date);
        }
        // 특정 형태의 날짜로 값을 뽑기
        String strDate = df.format(cal.getTime());

        System.err.println(df.format(cal.getTime()));

        ajaxDTO.put("regDate", strDate);
        System.out.println("regDate :: "+ strDate);
        List<Map<String, Object>> list = duolacMallMapper.selectDuolacList(ajaxDTO);

        System.out.println("list.size() :: "+list.size());
        List<DuolacReleaseList> duolacReleaseList = new ArrayList<>();

        for (Map orderInfo : list) {
            DuolacReleaseList duolacInfo = new DuolacReleaseList();
            duolacInfo.setId(new DuolacReleaseListId(DateUtils.getStringtoDate((String) orderInfo.get("targetDate"), "-"), (String) orderInfo.get("prodCd")));
            duolacInfo.setProdName((String) orderInfo.get("prodNm"));
            duolacInfo.setProdQty(Integer.parseInt(orderInfo.get("prodQty").toString()));
            if (orderInfo.get("realProdPrice") != null) duolacInfo.setSalesPriceSum(new BigDecimal(orderInfo.get("realProdPrice").toString()));
            if (orderInfo.get("unitPrice") != null) duolacInfo.setUnitPrice(new BigDecimal(orderInfo.get("unitPrice").toString()));
            duolacInfo.setOneTime(new BigDecimal(orderInfo.get("oneTime").toString()));
            duolacInfo.setTwoTime(new BigDecimal(orderInfo.get("twoTime").toString()));
            duolacInfo.setThreeTime(new BigDecimal(orderInfo.get("threeTime").toString()));
            duolacInfo.setProdQtyReturn(new BigDecimal(orderInfo.get("prodQtyReturn").toString()));
            duolacInfo.setProdTypeGsp(orderInfo.get("prodTypeGsp").toString());
            duolacReleaseList.add(duolacInfo);
        }

        System.out.println("duolacList.size() :: "+ duolacReleaseList.size());
        if (duolacReleaseList.size() > 0) {
            duolacReleaseRepository.save(duolacReleaseList);
        }


        List<Map<String, Object>> orderList = orderListMapper.selectOrderList(ajaxDTO);

        System.out.println("orderList.size() :: "+orderList.size());
        List<ProdReleaseList> prodlList = new ArrayList<>();

        for (Map orderInfo : orderList) {
            ProdReleaseList prodInfo = new ProdReleaseList();
            prodInfo.setId(new ProdReleaseListId(DateUtils.getStringtoDate((String) orderInfo.get("uploadDate"), "-"), (String) orderInfo.get("prodno"), (String) orderInfo.get("omDesc")));
            prodInfo.setProdName((String) orderInfo.get("prodnm"));
            prodInfo.setMallSite("EXMALL");
            prodInfo.setProdQty(Integer.parseInt(orderInfo.get("sqty").toString()));
            prodlList.add(prodInfo);
        }

        System.out.println("exmallList.size() :: "+prodlList.size());
        if (prodlList.size() > 0) {
            prodReleaseListRepository.save(prodlList);
        }

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("duolacCnt", duolacReleaseList.size());
        rtn.put("exmallCnt", prodlList.size());
//        rtn.put("totalCount", pageInfo.getTotalCount());
//        rtn.put("totalPageCount", pageInfo.getTotalPages());
        return new ResponseEntity(rtn, HttpStatus.OK);
    }

    /**
     * 재고관리 - 배치 - 듀오락몰 - 재고수량용
     * @return
     */
    @PostMapping("/syncSfaBatch.ajax")
    public ResponseEntity<?> syncSfaBatchAjax(@RequestParam Map<String, String> ajaxDTO)  {

        Map<String, Object> rtn = new HashMap<>();

        // 특정 형태의 날짜로 값을 뽑기
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        try {
            if (ajaxDTO.get("date") == null ) {
                cal.setTime(new Date());
            } else {
                String inputDate = (String)ajaxDTO.get("date");
                Date date = df.parse(inputDate);
                cal.setTime(date);
            }

            String strDate = df.format(cal.getTime());

            System.err.println(df.format(cal.getTime()));

            ajaxDTO.put("regDate", strDate.replaceAll("-", ""));
            System.out.println("regDate :: "+ strDate);
            List<Map<String, String>> list = prodInvBatchListMapper.selectProdInvBatchList(ajaxDTO);

            System.out.println("list.size() :: "+list.size());
            List<ProdInv> prodInvList = new ArrayList<>();

            for (Map cbtisInfo : list) {
                ProdInv prodInv = new ProdInv();
                prodInv.setId(new ProdInvId(cbtisInfo.get("cdPlant").toString(), cbtisInfo.get("cdItem").toString(), cbtisInfo.get("yyInv").toString(), cbtisInfo.get("ymIo").toString()));
                prodInv.setQtDelivery(new BigDecimal(cbtisInfo.get("qtDelivery").toString()));
                prodInv.setQtDuolac(new BigDecimal(cbtisInfo.get("qtDuolac").toString()));
                prodInv.setQtExmall(new BigDecimal(cbtisInfo.get("qtExmall").toString()));
                prodInvList.add(prodInv);
            }

            System.out.println("prodInvList.size() :: "+ prodInvList.size());
            if (prodInvList.size() > 0) {
                prodInvRepository.save(prodInvList);
            }

            rtn.put("isSuccess", true);
        } catch (Exception e) {
            rtn.put("isSuccess", false);

        }

        return new ResponseEntity(rtn, HttpStatus.OK);
    }
}
