package com.cellbiotech.model.sfa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 거래처 정보 리스트 관련 jpa model
 */
@Entity
@Table(name = "E_ACCOUNT_INFO_LIST")
public class AccountInfoList {

    @Id
    @Column(name = "ACCOUNT_CODE")
    private String accountCode;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "ACCOUNT_SHORT_NAME")
    private String accountShortName;

    @Column(name = "CORP_REG_NUM")
    private String corpRegNum;

    @Column(name = "REPRESENTATIVE_NAME")
    private String representativeName;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DETAIL_ADDRESS")
    private String detailAddress;

    @Column(name = "SALES_MANAGER_NAME")
    private String salesManagerName;

    @Column(name = "BUSINESS_CONDITIONS")
    private String businessConditions;

    @Column(name = "BUSINESS_TYPE")
    private String businessType;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountShortName() {
        return accountShortName;
    }

    public void setAccountShortName(String accountShortName) {
        this.accountShortName = accountShortName;
    }

    public String getCorpRegNum() {
        return corpRegNum;
    }

    public void setCorpRegNum(String corpRegNum) {
        this.corpRegNum = corpRegNum;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getSalesManagerName() {
        return salesManagerName;
    }

    public void setSalesManagerName(String salesManagerName) {
        this.salesManagerName = salesManagerName;
    }

    public String getBusinessConditions() {
        return businessConditions;
    }

    public void setBusinessConditions(String businessConditions) {
        this.businessConditions = businessConditions;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}