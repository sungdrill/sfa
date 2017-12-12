package com.cellbiotech.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.cellbiotech.dao.sfa.*;
import com.cellbiotech.model.sfa.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cellbiotech.mapper.sfa.GoalListMapper;
import com.cellbiotech.mapper.sfa.MemberAccountInfoListMapper;
import com.cellbiotech.service.UserService;
import com.cellbiotech.util.CommonUtils;
import com.cellbiotech.util.DateUtils;
import com.cellbiotech.util.excel.ExcelRead;
import com.cellbiotech.util.excel.ExcelReadOption;

/**
 * 엑셀 업로드 및 데이터베이스 저장
 */
@RestController
public class RestUploadController {

    private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);

    @Value("${upload.path}")
    private String UPLOADED_FOLDER;
    @Value("${link.path}")
    private String LINKED_FOLDER;

    @Autowired
    DataSource dataSource;

    @Autowired
    private FileUploadRepository fileUploadRepository;

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
    private MemberAccountInfoManagerListRepository memberAccountInfoManagerListRepository;

    @Autowired
    private MemberAccountInfoListMapper memberAccountInfoListMapper;

    @Autowired
    private AdminDistrictListRepository adminDistrictListRepository;

    @Autowired
    private AccountGradeListRepository accountGradeListRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GoalListMapper goalListMapper;

    @Autowired
    private ItemListRepository itemListRepository;

    @Autowired
    private ItemHistoryListRepository itemHistoryListRepository;

    @Autowired
    private ProdListRepository prodListRepository;

    @Autowired
    private ItemProdListRepository itemProdListRepository;

    @PostMapping("/api/upload")
    public ResponseEntity<?> apiUpload(@RequestParam("file") MultipartFile uploadfile, @RequestParam("boardIdx") String boardIdx) {

        System.out.println("boardIdx :: " + boardIdx);
        logger.debug("Single file upload!");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserById(auth.getName());

        FileUpload fileUpload = new FileUpload();
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {

            List<FileUpload> fileUploadList = saveUploadedFiles(Arrays.asList(uploadfile));
            if (fileUploadList.size() > 0) {
                for(FileUpload fileUpload2 : fileUploadList) {
                    fileUpload2.setBoardIdx(Long.parseLong(boardIdx));
                    fileUpload2.setCreaId(user.getId());
                    fileUpload = fileUploadRepository.save(fileUpload2);
                }
            }

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<FileUpload>(fileUpload, new HttpHeaders(), HttpStatus.OK);

    }

    // maps html form to a Model

    /**
     * 수주리스트 엑셀업로드
     * @param model
     * @param response
     * @return
     */
    @PostMapping("/api/insert/orderList")
    public ResponseEntity<?> apiInsertOrderList(@ModelAttribute FileUpload model, HttpServletResponse response) {

        int i = 2;
        try {
            model.setInsertStartTime(new Date());   // TODO
            File destFile = new File(model.getUploadPath());
            ExcelReadOption excelReadOption = new ExcelReadOption();
            excelReadOption.setFilePath(destFile.getAbsolutePath());
            logger.info("destFile.getAbsolutePath() :: "+destFile.getAbsolutePath());
            excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S");
            excelReadOption.setStartRow(2);
            List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

            OrderList orderList = new OrderList();
            for (Map<String, String> article: excelContent) {
                orderList.setId(new OrderListId(article.get("A"), article.get("B")));   // 수주번호, NO
                orderList.setAccountCode(article.get("C"));                             // 거래처코드
                orderList.setAccountName(article.get("D"));                             // 거래처명
                orderList.setSalesGroupCode(article.get("E"));                          // 영업그룹코드
                orderList.setSalesGroupName(article.get("F"));                          // 영업그룹명
                orderList.setItemCode(article.get("G"));                                // 품목
                orderList.setItemName(article.get("H"));                                // 품목명
                orderList.setOrderDate(DateUtils.getStringtoDate(article.get("I")));    // 수주일자
                orderList.setStandardInfo(article.get("J"));                            // 규격
                orderList.setOrderManager(article.get("K"));                            // 수주담당자
                orderList.setQty(new BigDecimal(article.get("L")));                   // 수량
                orderList.setUnitPrice( new BigDecimal(article.get("M").replaceAll(",", "")));  // 단가
                orderList.setAmount(new BigDecimal(article.get("N").replaceAll(",","")));       // 금액
                orderList.setSuperTax(new BigDecimal(article.get("O").replaceAll(",","")));     // 원화금액
                orderList.setSumAmount(new BigDecimal(article.get("P").replaceAll(",","")));    // 부가세
                orderList.setRemarks(article.get("Q"));                                 //

                orderListRepository.save(orderList);
                i++;

            }

            model.setInsertFinishTime(new Date());
            model.setInsertGb("Y");
            System.out.println(":::::::::::"+model.getIdx());
            String insertTableNameAndCnt = model.getInsertTable() + "[cnt:"+i+"]";
            model.setInsertTable(insertTableNameAndCnt);
            fileUploadRepository.save(model);
        } catch (Exception e) {
            model.setInsertGb("N");
            model.setInsertTable("Excel error line :: " + i);
            fileUploadRepository.save(model);

            Cookie cookie = new Cookie("fileUpload", "true");
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return new ResponseEntity<FileUpload>(model, HttpStatus.OK);

    }
    // maps html form to a Model

    /**
     * 매출마감리스트 엑셀업로드
     * @param model
     * @param response
     * @return
     */
    @PostMapping("/api/insert/salesDeadlineList")
    public ResponseEntity<?> apiInsertSalesDeadlineList(@ModelAttribute FileUpload model, HttpServletResponse response) {

        int i = 2;
        try {
            model.setInsertStartTime(new Date());   // TODO
            File destFile = new File(model.getUploadPath());
            ExcelReadOption excelReadOption = new ExcelReadOption();
            excelReadOption.setFilePath(destFile.getAbsolutePath());
            logger.info("destFile.getAbsolutePath() :: "+destFile.getAbsolutePath());
            excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N");
            excelReadOption.setStartRow(2);
            List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

            SalesDeadlineList salesDeadlineList = new SalesDeadlineList();
            for (Map<String, String> article: excelContent) {
                salesDeadlineList.setId(new SalesDeadlineListId(article.get("A"), article.get("B"), article.get("D"), article.get("N")));   // 매출번호, 전표번호, 품목
                salesDeadlineList.setSalesDate(DateUtils.getStringtoDate(article.get("C")));                              // 매출일자
                salesDeadlineList.setItemName(article.get("E"));                                                          // 품목명
                salesDeadlineList.setQty(new BigDecimal(article.get("F").replaceAll(",","")));        // 수량
                salesDeadlineList.setUnitPrice(new BigDecimal(article.get("G").replaceAll(",","")));    // 단가
                salesDeadlineList.setAmount(new BigDecimal(article.get("H").replaceAll(",","")));       // 금액
                salesDeadlineList.setSuperTax(new BigDecimal(article.get("I").replaceAll(",","")));     // 부가세
                salesDeadlineList.setSum(new BigDecimal(article.get("J").replaceAll(",","")));          // 합계
                salesDeadlineList.setAccountCode(article.get("K"));                                                      // 거래처
                salesDeadlineList.setAccountName(article.get("L"));                                                      // 거래처명
                salesDeadlineList.setReturnGb(article.get("M"));                                                         // 반품여부

                salesDeadlineListRepository.save(salesDeadlineList);
                i++;

            }

            // TODO 2.
            model.setInsertFinishTime(new Date());
            model.setInsertGb("Y");
            System.out.println(":::::::::::"+model.getIdx());
            String insertTableNameAndCnt = model.getInsertTable() + "[cnt:"+i+"]";
            model.setInsertTable(insertTableNameAndCnt);
            fileUploadRepository.save(model);

        } catch (Exception e) {
            logger.debug("debug ::::::::::::::::::::::: "+e.getMessage());
            logger.info("info ::::::::::::::::::::::: "+e.getMessage());
            logger.error("error ::::::::::::::::::::::: "+e.getMessage());
            System.out.println("sout ::::::::::::::::: "+e.getMessage());
            model.setInsertGb("N");
            model.setInsertTable("Error line :: " + i);
            fileUploadRepository.save(model);

            Cookie cookie = new Cookie("fileUpload", "true");
            cookie.setPath("/");
            response.addCookie(cookie);
        }


        return new ResponseEntity<FileUpload>(model, HttpStatus.OK);

    }

    /**
     * 수금리스트 엑셀업로드
     * @param model
     * @param response
     * @return
     * @throws ParseException
     */
    @PostMapping("/api/insert/collectionList")
    public ResponseEntity<?> apiInsertCollectionList(@ModelAttribute FileUpload model, HttpServletResponse response) throws ParseException {

        int i = 2;
        try {
        model.setInsertStartTime(new Date());   // TODO
        File destFile = new File(model.getUploadPath());
        ExcelReadOption excelReadOption = new ExcelReadOption();
        excelReadOption.setFilePath(destFile.getAbsolutePath());
        logger.info("destFile.getAbsolutePath() :: "+destFile.getAbsolutePath());
        excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M");
        excelReadOption.setStartRow(2);
        List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

        CollectionList collectionList = new CollectionList();
        for (Map<String, String> article: excelContent) {
            collectionList.setCollectionNum(article.get("A"));                              //
            collectionList.setCollectionDate(DateUtils.getStringtoDate(article.get("B")));  //
            collectionList.setCollectionTypeCode(article.get("C"));                         //
            collectionList.setCollectionTypeName(article.get("D"));                         //
            collectionList.setCollectionPlaceCode(article.get("E"));                        //
            collectionList.setCollectionPlaceName(article.get("F"));                        //
            collectionList.setSalesGroupCode(article.get("G"));                             //
            collectionList.setSalesGroupName(article.get("H"));                             //
            collectionList.setCollectionManagerCode(article.get("I"));                      //
            collectionList.setCollectionManagerName(article.get("J"));                      //
            collectionList.setCollectionAmount(new BigDecimal(article.get("K").replaceAll(",",""))); //
            collectionList.setFinancialInstitutionCode(article.get("L"));                   //
            collectionList.setFinancialInstitutionName(article.get("M"));                   //

            collectionListRepository.save(collectionList);
            i++;
        }

        // TODO 2.
        model.setInsertFinishTime(new Date());
        model.setInsertGb("Y");
        System.out.println(":::::::::::"+model.getIdx());
            String insertTableNameAndCnt = model.getInsertTable() + "[cnt:"+i+"]";
            model.setInsertTable(insertTableNameAndCnt);
        fileUploadRepository.save(model);

        } catch (Exception e) {
            logger.debug("debug ::::::::::::::::::::::: "+e.getMessage());
            logger.info("info ::::::::::::::::::::::: "+e.getMessage());
            logger.error("error ::::::::::::::::::::::: "+e.getMessage());
            System.out.println("sout ::::::::::::::::: "+e.getMessage());
            model.setInsertGb("N");
            model.setInsertTable("Excel error line :: " + i);
            fileUploadRepository.save(model);

            Cookie cookie = new Cookie("fileUpload", "true");
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return new ResponseEntity<FileUpload>(model, HttpStatus.OK);

    }

    // maps html form to a Model

    /**
     * 납품의뢰리스트 엑셀업로드
     * @param model
     * @return
     * @throws ParseException
     */
    @PostMapping("/api/insert/deliveryRequestList")
    public ResponseEntity<?> apiInsertDeliveryRequestList(@ModelAttribute FileUpload model, HttpServletResponse response) throws ParseException {

        int i = 2;
        try {
        model.setInsertStartTime(new Date());
        File destFile = new File(model.getUploadPath());
        ExcelReadOption excelReadOption = new ExcelReadOption();
        excelReadOption.setFilePath(destFile.getAbsolutePath());
        logger.info("destFile.getAbsolutePath() :: "+destFile.getAbsolutePath());
        excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O");
        excelReadOption.setStartRow(2);
        List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

        DeliveryRequestList deliveryRequestList = new DeliveryRequestList();
        for (Map<String, String> article: excelContent) {
            deliveryRequestList.setId(new DeliveryRequestListId(article.get("A"), article.get("E")));   //
            deliveryRequestList.setRequestDate(DateUtils.getStringtoDate(article.get("B")));            //
            deliveryRequestList.setAccountCode(article.get("C"));                                       //
            deliveryRequestList.setAccountName(article.get("D"));                                       //
            deliveryRequestList.setItemName(article.get("F"));                                          //
            deliveryRequestList.setRequestQty(new BigDecimal(article.get("G")));                      //
            deliveryRequestList.setShippingQty(new BigDecimal(article.get("H")));                     //
            deliveryRequestList.setNotShippingQty(new BigDecimal(article.get("I")));                  //
            deliveryRequestList.setUnitPrice(new BigDecimal(article.get("J").replaceAll(",","")));//
            deliveryRequestList.setPrice(new BigDecimal(article.get("K").replaceAll(",","")));    //
            deliveryRequestList.setAmount(new BigDecimal(article.get("L").replaceAll(",","")));   //
            deliveryRequestList.setShippingWarehouse(article.get("M"));                                 //
            deliveryRequestList.setGoodsIssue(article.get("N"));                                        //
            deliveryRequestList.setLineRemarks(article.get("O"));                                       //

            deliveryRequestListRepository.save(deliveryRequestList);
            i++;

        }

        model.setInsertFinishTime(new Date());
        model.setInsertGb("Y");
        System.out.println(":::::::::::"+model.getIdx());
            String insertTableNameAndCnt = model.getInsertTable() + "[cnt:"+i+"]";
            model.setInsertTable(insertTableNameAndCnt);
        fileUploadRepository.save(model);

        } catch (Exception e) {
            logger.debug("debug ::::::::::::::::::::::: "+e.getMessage());
            logger.info("info ::::::::::::::::::::::: "+e.getMessage());
            logger.error("error ::::::::::::::::::::::: "+e.getMessage());
            System.out.println("sout ::::::::::::::::: "+e.getMessage());
            model.setInsertGb("N");
            model.setInsertTable("Excel error line :: " + i);
            fileUploadRepository.save(model);

            Cookie cookie = new Cookie("fileUpload", "true");
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return new ResponseEntity<FileUpload>(model, HttpStatus.OK);

    }

    // maps html form to a Model

    /**
     * 거래처정보리스트 엑셀업로드드
    * @param model
     * @return
     * @throws ParseException
     */
    @PostMapping("/api/insert/accountInfoList")
    public ResponseEntity<?> apiInsertAccountInfoList(@ModelAttribute FileUpload model, HttpServletResponse response) throws ParseException {

        int i = 2;
        try {
        model.setInsertStartTime(new Date());   // TODO
        File destFile = new File(model.getUploadPath());
        ExcelReadOption excelReadOption = new ExcelReadOption();
        excelReadOption.setFilePath(destFile.getAbsolutePath());
        logger.info("destFile.getAbsolutePath() :: "+destFile.getAbsolutePath());
        excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K");
        excelReadOption.setStartRow(2);
        List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

        AccountInfoList accountInfoList = new AccountInfoList();
        for (Map<String, String> article: excelContent) {
            accountInfoList.setAccountCode(article.get("A"));           // 거래처코드
            accountInfoList.setAccountName(article.get("B"));           // 거래처
            accountInfoList.setAccountShortName(article.get("C"));      // 거래처(약칭)
            accountInfoList.setCorpRegNum(article.get("D"));            // 사업자등록번호
            accountInfoList.setRepresentativeName(article.get("E"));    // 대표자명
            accountInfoList.setZipCode(article.get("F"));               // 우편번호
            accountInfoList.setAddress(article.get("G"));               // 주소
            accountInfoList.setDetailAddress(article.get("H"));         // 상세주소
            accountInfoList.setSalesManagerName(article.get("I"));      // 영업담당자명
            accountInfoList.setBusinessConditions(article.get("J"));    // 업태
            accountInfoList.setBusinessType(article.get("K"));          // 업종

            accountInfoListRepository.save(accountInfoList);
            i++;

        }

        model.setInsertFinishTime(new Date());
        model.setInsertGb("Y");
        System.out.println(":::::::::::"+model.getIdx());
            String insertTableNameAndCnt = model.getInsertTable() + "[cnt:"+i+"]";
            model.setInsertTable(insertTableNameAndCnt);
        fileUploadRepository.save(model);

        } catch (Exception e) {
            logger.debug("debug ::::::::::::::::::::::: "+e.getMessage());
            logger.info("info ::::::::::::::::::::::: "+e.getMessage());
            logger.error("error ::::::::::::::::::::::: "+e.getMessage());
            model.setInsertGb("N");
            model.setInsertTable("Excel error line :: " + i);
            fileUploadRepository.save(model);

            Cookie cookie = new Cookie("fileUpload", "true");
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return new ResponseEntity<FileUpload>(model, HttpStatus.OK);

    }

    // maps html form to a Model

    /**
     * 담당자목표관리 엑셀업로드
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/api/insert/goalList")
    public ResponseEntity<?> apiInsertGoalList(@ModelAttribute FileUpload model, HttpServletResponse response) throws Exception {

        int i = 2;
        try {
        model.setInsertStartTime(new Date());   // TODO
        File destFile = new File(model.getUploadPath());
        ExcelReadOption excelReadOption = new ExcelReadOption();
        excelReadOption.setFilePath(destFile.getAbsolutePath());
        logger.info("destFile.getAbsolutePath() :: "+destFile.getAbsolutePath());
        excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N");
        excelReadOption.setStartRow(2);
        List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (Map<String, String> article: excelContent) {
            Map<String, String> goalInfo = new HashMap<>();
            goalInfo.put("user", auth.getName());
            goalInfo.put("id", article.get("A"));
            goalInfo.put("searchYear", article.get("B"));
            goalInfo.put("goal1", CommonUtils.null2zero(article.get("C")));
            goalInfo.put("goal2", CommonUtils.null2zero(article.get("D")));
            goalInfo.put("goal3", CommonUtils.null2zero(article.get("E")));
            goalInfo.put("goal4", CommonUtils.null2zero(article.get("F")));
            goalInfo.put("goal5", CommonUtils.null2zero(article.get("G")));
            goalInfo.put("goal6", CommonUtils.null2zero(article.get("H")));
            goalInfo.put("goal7", CommonUtils.null2zero(article.get("I")));
            goalInfo.put("goal8", CommonUtils.null2zero(article.get("J")));
            goalInfo.put("goal9", CommonUtils.null2zero(article.get("K")));
            goalInfo.put("goal10", CommonUtils.null2zero(article.get("L")));
            goalInfo.put("goal11", CommonUtils.null2zero(article.get("M")));
            goalInfo.put("goal12", CommonUtils.null2zero(article.get("N")));

                goalListMapper.mergeGoalInfo(goalInfo);
                i++;

        }

        model.setInsertFinishTime(new Date());
        model.setInsertGb("Y");
        System.out.println(":::::::::::"+model.getIdx());
            String insertTableNameAndCnt = model.getInsertTable() + "[cnt:"+i+"]";
            model.setInsertTable(insertTableNameAndCnt);
        fileUploadRepository.save(model);
        } catch (Exception e) {
            logger.debug("debug ::::::::::::::::::::::: "+e.getMessage());
            logger.info("info ::::::::::::::::::::::: "+e.getMessage());
            logger.error("error ::::::::::::::::::::::: "+e.getMessage());
            System.out.println("sout ::::::::::::::::: "+e.getMessage());
            model.setInsertGb("N");
            model.setInsertTable("Excel error line :: " + i);
            fileUploadRepository.save(model);

            Cookie cookie = new Cookie("fileUpload", "true");
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return new ResponseEntity<FileUpload>(model, HttpStatus.OK);

    }

    // maps html form to a Model

    /**
     * 영업담당자 거래처정보 관리 엑셀 업로드
     * @param model
     * @param response
     * @return
     */
    @PostMapping("/api/insert/memberAccountInfoList")
    public ResponseEntity<?> apiInsertMemberAccountInfoList(@ModelAttribute FileUpload model, HttpServletResponse response) {

        int i = 2;
        try {
            model.setInsertStartTime(new Date());   // TODO
            File destFile = new File(model.getUploadPath());
            ExcelReadOption excelReadOption = new ExcelReadOption();
            excelReadOption.setFilePath(destFile.getAbsolutePath());
            logger.info("destFile.getAbsolutePath() :: "+destFile.getAbsolutePath());
            excelReadOption.setOutputColumns("A", "B", "C", "D");
            excelReadOption.setStartRow(2);
            List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MemberAccountInfoManagerList uploadList = new MemberAccountInfoManagerList();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            for (Map<String, String> article: excelContent) {
                System.out.println("accountCode :: "+article.get("A"));
                String temp = "";
                if (article.get("A").length() < 5) {
                    temp = "000000"+article.get("A");
                    temp = temp.substring(temp.length()-5);
                } else {
                    temp = article.get("A");
                }
                if (article.get("B").length() == 8) {
                } else {
                    continue;
                }
                if (article.get("C") != null && article.get("C").length() > 0) {
                    String managerStartDate = article.get("C").replaceAll("/", "-") + "-01";
                    uploadList.setId(new MemberAccountInfoManagerListId(temp, article.get("B"), df.parse(managerStartDate)));
                } else {
                    continue;
                }

                if (article.get("D") != null && article.get("D").length() > 0) {
                    String endDay = DateUtils.getEndDay(article.get("D").replaceAll("/", "-")+"-01");
                    uploadList.setManagerEndDate(df.parse(article.get("D").replaceAll("/", "-")+"-"+endDay));
                }
                uploadList.setCreaId(auth.getName());
                uploadList.setCreaDate(new Date());
                memberAccountInfoManagerListRepository.save(uploadList);
                i++;

            }

            model.setInsertFinishTime(new Date());
            model.setInsertGb("Y");
            System.out.println(":::::::::::"+model.getIdx());
            String insertTableNameAndCnt = model.getInsertTable() + "[cnt:"+i+"]";
            model.setInsertTable(insertTableNameAndCnt);
            fileUploadRepository.save(model);
        } catch (Exception e) {
            logger.debug("debug ::::::::::::::::::::::: "+e.getMessage());
            logger.info("info ::::::::::::::::::::::: "+e.getMessage());
            logger.error("error ::::::::::::::::::::::: "+e.getMessage());
            System.out.println("sout ::::::::::::::::: "+e.getMessage());
            model.setInsertGb("N");
            model.setInsertTable("Excel error line :: " + i);
            fileUploadRepository.save(model);

            Cookie cookie = new Cookie("fileUpload", "true");
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return new ResponseEntity<FileUpload>(model, HttpStatus.OK);

    }

    /**
     * 행정구역 정보 관리 엑셀 업로드
     * @param model
     * @param response
     * @return
     */
    @PostMapping("/api/insert/adminDistrictList")
    public ResponseEntity<?> apiInsertAdminDistrictList(@ModelAttribute FileUpload model, HttpServletResponse response) {

        int i = 2;
        try {
            model.setInsertStartTime(new Date());   // TODO
            File destFile = new File(model.getUploadPath());
            ExcelReadOption excelReadOption = new ExcelReadOption();
            excelReadOption.setFilePath(destFile.getAbsolutePath());
            logger.info("destFile.getAbsolutePath() :: "+destFile.getAbsolutePath());
            excelReadOption.setOutputColumns("A", "B", "C", "D", "E");
            excelReadOption.setStartRow(2);
            List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            AdminDistrictList uploadList = new AdminDistrictList();
            for (Map<String, String> article: excelContent) {
                uploadList.setAdministrativeDistrictCode(article.get("A"));
                if(article.get("B") != null) uploadList.setAdministrativeDistrictName(article.get("B"));
                if(article.get("C") != null) uploadList.setPharmacyNumber(article.get("C"));
                if(article.get("D") != null) uploadList.setDealYn(article.get("D"));
                if(article.get("E") != null) uploadList.setUseYn(article.get("E"));
                adminDistrictListRepository.save(uploadList);
                i++;

            }

            model.setInsertFinishTime(new Date());
            model.setInsertGb("Y");
            System.out.println(":::::::::::"+model.getIdx());
            String insertTableNameAndCnt = model.getInsertTable() + "[cnt:"+i+"]";
            model.setInsertTable(insertTableNameAndCnt);
            fileUploadRepository.save(model);
        } catch (Exception e) {
            logger.debug("debug ::::::::::::::::::::::: "+e.getMessage());
            logger.info("info ::::::::::::::::::::::: "+e.getMessage());
            logger.error("error ::::::::::::::::::::::: "+e.getMessage());
            model.setInsertGb("N");
            model.setInsertTable("Excel error line :: " + i);
            fileUploadRepository.save(model);

            Cookie cookie = new Cookie("fileUpload", "true");
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return new ResponseEntity<FileUpload>(model, HttpStatus.OK);

    }

    /**
     * 거래처별 등급 관리 엑셀 업로드
     * @param model
     * @param response
     * @return
     */
    @PostMapping("/api/insert/accountGradeList")
    public ResponseEntity<?> apiInsertAccountGradeList(@ModelAttribute FileUpload model, HttpServletResponse response) {

        int i = 2;
        try {
            model.setInsertStartTime(new Date());   // TODO
            File destFile = new File(model.getUploadPath());
            ExcelReadOption excelReadOption = new ExcelReadOption();
            excelReadOption.setFilePath(destFile.getAbsolutePath());
            logger.info("destFile.getAbsolutePath() :: "+destFile.getAbsolutePath());
            excelReadOption.setOutputColumns("A", "B", "C");
            excelReadOption.setStartRow(2);
            List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            AccountGradeList uploadList = new AccountGradeList();
            for (Map<String, String> article: excelContent) {
                if (article.get("A").length() < 5) {
                    String temp = "000000"+article.get("A");
                    uploadList.setAccountCode(temp.substring(temp.length()-5));
                } else {

                    uploadList.setAccountCode(article.get("A"));
                }
                if(article.get("B") != null) uploadList.setGradeCode(article.get("B"));
                if(article.get("C") != null) uploadList.setGodomollYn(article.get("C"));
                accountGradeListRepository.save(uploadList);
                i++;

            }

            model.setInsertFinishTime(new Date());
            model.setInsertGb("Y");
            System.out.println(":::::::::::"+model.getIdx());
            String insertTableNameAndCnt = model.getInsertTable() + "[cnt:"+i+"]";
            model.setInsertTable(insertTableNameAndCnt);
            fileUploadRepository.save(model);
        } catch (Exception e) {
            logger.debug("debug ::::::::::::::::::::::: "+e.getMessage());
            logger.info("info ::::::::::::::::::::::: "+e.getMessage());
            logger.error("error ::::::::::::::::::::::: "+e.getMessage());
            model.setInsertGb("N");
            model.setInsertTable("Excel error line :: " + i);
            fileUploadRepository.save(model);

            Cookie cookie = new Cookie("fileUpload", "true");
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return new ResponseEntity<FileUpload>(model, HttpStatus.OK);

    }

    /**
     * 재고관리 - 품목 재고현황 엑셀 업로드
     * @param model
     * @param response
     * @return
     */
    @PostMapping("/api/insert/itemHistoryList")
    public ResponseEntity<?> apiInsertItemHistoryList(@ModelAttribute FileUpload model, HttpServletResponse response) {

        int i = 2;
        try {
            model.setInsertStartTime(new Date());   // TODO
            File destFile = new File(model.getUploadPath());
            ExcelReadOption excelReadOption = new ExcelReadOption();
            excelReadOption.setFilePath(destFile.getAbsolutePath());
            logger.info("destFile.getAbsolutePath() :: "+destFile.getAbsolutePath());
            excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F");
            excelReadOption.setStartRow(2);
            List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            ItemHistoryList uploadList = new ItemHistoryList();
            for (Map<String, String> article: excelContent) {
                System.out.println(":::::::::::::::"+article.get("B"));
                uploadList.setId(new ItemHistoryListId(article.get("A"), DateUtils.getStringtoDate(article.get("B")+"/01", "/")));
                if(article.get("C") != null) uploadList.setBasicStock(new BigDecimal(article.get("C").replaceAll(",", "")));
                if(article.get("D") != null) uploadList.setInItem(new BigDecimal(article.get("D").replaceAll(",", "")));
                if(article.get("E") != null) uploadList.setOutItem(new BigDecimal (article.get("E").replaceAll(",", "")));
                if(article.get("F") != null) uploadList.setClosingStock(new BigDecimal(article.get("F").replaceAll(",", "")));
                itemHistoryListRepository.save(uploadList);
                i++;

            }

            model.setInsertFinishTime(new Date());
            model.setInsertGb("Y");
            System.out.println(":::::::::::"+model.getIdx());
            String insertTableNameAndCnt = model.getInsertTable() + "[cnt:"+i+"]";
            model.setInsertTable(insertTableNameAndCnt);
            fileUploadRepository.save(model);
        } catch (Exception e) {
            logger.info("info ::::::::::::::::::::::: "+e.getMessage());
            logger.debug("debug ::::::::::::::::::::::: "+e.getMessage());
            logger.error("error ::::::::::::::::::::::: "+e.getMessage());
            model.setInsertGb("N");
            model.setInsertTable("Excel error line :: " + i);
            fileUploadRepository.save(model);

            Cookie cookie = new Cookie("fileUpload", "true");
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return new ResponseEntity<FileUpload>(model, HttpStatus.OK);

    }

    /**
     * 재고관리 - 품목관리 엑셀 업로드
     * @param model
     * @param response
     * @return
     */
    @PostMapping("/api/insert/itemList")
    public ResponseEntity<?> apiInsertItemList(@ModelAttribute FileUpload model, HttpServletResponse response) {

        int i = 2;
        try {
            model.setInsertStartTime(new Date());   // TODO
            File destFile = new File(model.getUploadPath());
            ExcelReadOption excelReadOption = new ExcelReadOption();
            excelReadOption.setFilePath(destFile.getAbsolutePath());
            logger.info("destFile.getAbsolutePath() :: "+destFile.getAbsolutePath());
            excelReadOption.setOutputColumns("A", "B", "C", "D", "E");
            excelReadOption.setStartRow(2);
            List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            ItemList uploadList = new ItemList();
            for (Map<String, String> article: excelContent) {
                uploadList.setItemCode(article.get("A"));
                if(article.get("B") != null) uploadList.setItemName(article.get("B"));
                if(article.get("C") != null) uploadList.setStandardInfo(article.get("C"));
                if(article.get("D") != null) uploadList.setUnit(article.get("D"));
                if(article.get("E") != null) uploadList.setUseYn(article.get("E"));
                itemListRepository.save(uploadList);
                i++;

            }

            model.setInsertFinishTime(new Date());
            model.setInsertGb("Y");
            System.out.println(":::::::::::"+model.getIdx());
            String insertTableNameAndCnt = model.getInsertTable() + "[cnt:"+i+"]";
            model.setInsertTable(insertTableNameAndCnt);
            fileUploadRepository.save(model);
        } catch (Exception e) {
            logger.info("info ::::::::::::::::::::::: "+e.getMessage());
            logger.debug("debug ::::::::::::::::::::::: "+e.getMessage());
            logger.error("error ::::::::::::::::::::::: "+e.getMessage());
            model.setInsertGb("N");
            model.setInsertTable("Excel error line :: " + i);
            fileUploadRepository.save(model);

            Cookie cookie = new Cookie("fileUpload", "true");
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return new ResponseEntity<FileUpload>(model, HttpStatus.OK);

    }

    /**
     * 재고관리 - 상품관리 엑셀 업로드
     * @param model
     * @param response
     * @return
     */
    @PostMapping("/api/insert/prodList")
    public ResponseEntity<?> apiInsertProdList(@ModelAttribute FileUpload model, HttpServletResponse response) {

        int i = 2;
        try {
            model.setInsertStartTime(new Date());   // TODO
            File destFile = new File(model.getUploadPath());
            ExcelReadOption excelReadOption = new ExcelReadOption();
            excelReadOption.setFilePath(destFile.getAbsolutePath());
            logger.info("destFile.getAbsolutePath() :: "+destFile.getAbsolutePath());
            excelReadOption.setOutputColumns("A", "B", "C", "D", "E");
            excelReadOption.setStartRow(2);
            List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            ProdList uploadList = new ProdList();
            for (Map<String, String> article: excelContent) {
                uploadList.setId(new ProdListId(article.get("A"),article.get("B")));
                if(article.get("C") != null) uploadList.setProdName(article.get("C"));
                if(article.get("D") != null) uploadList.setProdType(article.get("D"));
                if(article.get("E") != null) uploadList.setUseYn(article.get("E"));
                prodListRepository.save(uploadList);
                i++;

            }

            model.setInsertFinishTime(new Date());
            model.setInsertGb("Y");
            System.out.println(":::::::::::"+model.getIdx());
            String insertTableNameAndCnt = model.getInsertTable() + "[cnt:"+i+"]";
            model.setInsertTable(insertTableNameAndCnt);
            fileUploadRepository.save(model);
        } catch (Exception e) {
            logger.info("info ::::::::::::::::::::::: "+e.getMessage());
            logger.debug("debug ::::::::::::::::::::::: "+e.getMessage());
            logger.error("error ::::::::::::::::::::::: "+e.getMessage());
            model.setInsertGb("N");
            model.setInsertTable("Excel error line :: " + i);
            fileUploadRepository.save(model);

            Cookie cookie = new Cookie("fileUpload", "true");
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return new ResponseEntity<FileUpload>(model, HttpStatus.OK);

    }

    /**
     * 재고관리 - 품목-상품관리 엑셀 업로드
     * @param model
     * @param response
     * @return
     */
    @PostMapping("/api/insert/itemProdList")
    public ResponseEntity<?> apiInsertItemProdList(@ModelAttribute FileUpload model, HttpServletResponse response) {

        int i = 2;
        try {
            model.setInsertStartTime(new Date());   // TODO
            File destFile = new File(model.getUploadPath());
            ExcelReadOption excelReadOption = new ExcelReadOption();
            excelReadOption.setFilePath(destFile.getAbsolutePath());
            logger.info("destFile.getAbsolutePath() :: "+destFile.getAbsolutePath());
            excelReadOption.setOutputColumns("A", "B", "C");
            excelReadOption.setStartRow(2);
            List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            ItemProdList uploadList = new ItemProdList();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            for (Map<String, String> article: excelContent) {
                uploadList.setId(new ItemProdListId(article.get("A"), article.get("B")));
                String mappingDate = article.get("C").replaceAll("/", "-") + "-01";
                if(article.get("C") != null) uploadList.setMappingDate(df.parse(mappingDate));
                itemProdListRepository.save(uploadList);
                i++;

            }

            model.setInsertFinishTime(new Date());
            model.setInsertGb("Y");
            System.out.println(":::::::::::"+model.getIdx());
            String insertTableNameAndCnt = model.getInsertTable() + "[cnt:"+i+"]";
            model.setInsertTable(insertTableNameAndCnt);
            fileUploadRepository.save(model);
        } catch (Exception e) {
            logger.info("info ::::::::::::::::::::::: "+e.getMessage());
            logger.debug("debug ::::::::::::::::::::::: "+e.getMessage());
            logger.error("error ::::::::::::::::::::::: "+e.getMessage());
            model.setInsertGb("N");
            model.setInsertTable("Excel error line :: " + i);
            fileUploadRepository.save(model);

            Cookie cookie = new Cookie("fileUpload", "true");
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return new ResponseEntity<FileUpload>(model, HttpStatus.OK);

    }



    // Multiple file upload
    @PostMapping("/api/upload/multi")
    public ResponseEntity<?> uploadFileMulti(
            @RequestParam("extraField") String extraField,
            @RequestParam("files") MultipartFile[] uploadfiles) {

        logger.debug("Multiple file upload!");

        String uploadedFileName = "";

        for (MultipartFile multiFile : uploadfiles) {
            if (multiFile.getSize() > 0) {

                uploadedFileName += multiFile.getOriginalFilename();
            }

        }

        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadfiles));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - "
                + uploadedFileName, HttpStatus.OK);

    }

    // maps html form to a Model
    @PostMapping("/api/upload/multi/model")
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadModel model) {

        logger.debug("Multiple file upload! With UploadModel");

        try {

            saveUploadedFiles(Arrays.asList(model.getFiles()));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded!", HttpStatus.OK);

    }

    //save file
    private List<FileUpload> saveUploadedFiles(List<MultipartFile> files) throws IOException {

        List<FileUpload> fileUploadList = new ArrayList<FileUpload>();

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();
            String fileNm = file.getOriginalFilename();
            String originalFileExtension = fileNm.substring(fileNm.lastIndexOf("."));
            String realFileNm = CommonUtils.getRandomString() + originalFileExtension;

            String filePath = UPLOADED_FOLDER + DateUtils.getYearFilePath() + "/" + DateUtils.getMonthFilePath();
            String linkPath = LINKED_FOLDER + DateUtils.getYearFilePath() + "/" + DateUtils.getMonthFilePath();
            File isfile = new File(filePath);
            if (isfile.exists() == false) {
                isfile.mkdirs();
            }

            Path path = Paths.get(filePath + "/" + realFileNm);
            Files.write(path, bytes);

            FileUpload fileUpload = new FileUpload();

            fileUpload.setOriginalFileName(fileNm);

            fileUpload.setStoredFileName(realFileNm);
            fileUpload.setUploadPath(filePath + "/" + realFileNm);
            fileUpload.setLinkPath(linkPath + "/" + realFileNm);
            fileUpload.setFileSize(file.getSize());

            fileUploadList.add(fileUpload);
        }

        return fileUploadList;

    }
}
