package com.cellbiotech.model.sfa;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 수금리스트 관련 jpa model
 */
@Entity
@Table(name = "E_COLLECTION_LIST")
public class CollectionList {


    @Id
    @Column(name = "COLLECTION_NUM")
    private String collectionNum;

    @Column(name = "COLLECTION_DATE")
    @Temporal(TemporalType.DATE)
    private Date collectionDate;

    @Column(name = "COLLECTION_TYPE_CODE")
    private String collectionTypeCode;

    @Column(name = "COLLECTION_TYPE_NAME")
    private String collectionTypeName;

    @Column(name = "COLLECTION_PLACE_CODE")
    private String collectionPlaceCode;

    @Column(name = "COLLECTION_PLACE_NAME")
    private String collectionPlaceName;

    @Column(name = "SALES_GROUP_CODE")
    private String salesGroupCode;

    @Column(name = "SALES_GROUP_NAME")
    private String salesGroupName;

    @Column(name = "COLLECTION_MANAGER_CODE")
    private String collectionManagerCode;

    @Column(name = "COLLECTION_MANAGER_NAME")
    private String collectionManagerName;

    @Column(name = "COLLECTION_AMOUNT")
    private BigDecimal collectionAmount;

    @Column(name = "FINANCIAL_INSTITUTION_CODE")
    private String financialInstitutionCode;

    @Column(name = "FINANCIAL_INSTITUTION_NAME")
    private String financialInstitutionName;

    public String getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(String collectionNum) {
        this.collectionNum = collectionNum;
    }

    public String getCollectionDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(collectionDate);
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String getCollectionTypeCode() {
        return collectionTypeCode;
    }

    public void setCollectionTypeCode(String collectionTypeCode) {
        this.collectionTypeCode = collectionTypeCode;
    }

    public String getCollectionTypeName() {
        return collectionTypeName;
    }

    public void setCollectionTypeName(String collectionTypeName) {
        this.collectionTypeName = collectionTypeName;
    }

    public String getCollectionPlaceCode() {
        return collectionPlaceCode;
    }

    public void setCollectionPlaceCode(String collectionPlaceCode) {
        this.collectionPlaceCode = collectionPlaceCode;
    }

    public String getCollectionPlaceName() {
        return collectionPlaceName;
    }

    public void setCollectionPlaceName(String collectionPlaceName) {
        this.collectionPlaceName = collectionPlaceName;
    }

    public String getSalesGroupCode() {
        return salesGroupCode;
    }

    public void setSalesGroupCode(String salesGroupCode) {
        this.salesGroupCode = salesGroupCode;
    }

    public String getSalesGroupName() {
        return salesGroupName;
    }

    public void setSalesGroupName(String salesGroupName) {
        this.salesGroupName = salesGroupName;
    }

    public String getCollectionManagerCode() {
        return collectionManagerCode;
    }

    public void setCollectionManagerCode(String collectionManagerCode) {
        this.collectionManagerCode = collectionManagerCode;
    }

    public String getCollectionManagerName() {
        return collectionManagerName;
    }

    public void setCollectionManagerName(String collectionManagerName) {
        this.collectionManagerName = collectionManagerName;
    }

    public BigDecimal getCollectionAmount() {
        return collectionAmount;
    }

    public void setCollectionAmount(BigDecimal collectionAmount) {
        this.collectionAmount = collectionAmount;
    }

    public String getFinancialInstitutionCode() {
        return financialInstitutionCode;
    }

    public void setFinancialInstitutionCode(String financialInstitutionCode) {
        this.financialInstitutionCode = financialInstitutionCode;
    }

    public String getFinancialInstitutionName() {
        return financialInstitutionName;
    }

    public void setFinancialInstitutionName(String financialInstitutionName) {
        this.financialInstitutionName = financialInstitutionName;
    }

}
