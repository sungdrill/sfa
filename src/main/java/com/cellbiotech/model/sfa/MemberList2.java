package com.cellbiotech.model.sfa;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 영업담당자(sfa) 관련 jpa model
 */
@Entity
@Table(name = "G_MEMBER_LIST")
public class MemberList2 {

    @Id
    @Column(name = "SALES_MANAGER_CODE")
    private String salesManagerCode;

    @Column(name = "SALES_MANAGER_NAME", nullable = true)
    private String salesManagerName = "";

    @Column(name = "TEAM_CODE", nullable = true)
    private String teamCode = "";

    @Column(name = "AREA_CODE", nullable = true)
    private String areaCode = "";

    @Column(name = "RANK_CODE", nullable = true)
    private String rankCode = "";

    @Column(name = "PHONE_NUMBER", nullable = true)
    private String phoneNumber = "";

    @Column(name = "CELL_PHONE_NUMBER", nullable = true)
    private String cellPhoneNumber = "";

    @Column(name = "ZIP_CODE", nullable = true)
    private String zipCode = "";

    @Column(name = "ADDRESS", nullable = true)
    private String address = "";

    @Column(name = "DETAIL_ADDRESS", nullable = true)
    private String detailAddress = "";

    @Column(name = "REMARKS", nullable = true)
    private String remarks = "";

    @Column(name = "CREA_ID")
    private String creaId;

    @Column(name = "CREA_DATE")
    private Date creaDate;

    public String getSalesManagerCode() {
        return salesManagerCode;
    }

    public void setSalesManagerCode(String salesManagerCode) {
        this.salesManagerCode = salesManagerCode;
    }

    public String getSalesManagerName() {
        return salesManagerName;
    }

    public void setSalesManagerName(String salesManagerName) {
        this.salesManagerName = salesManagerName;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getRankCode() {
        return rankCode;
    }

    public void setRankCode(String rankCode) {
        this.rankCode = rankCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreaDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(creaDate);
    }

    public void setCreaDate(Date creaDate) {
        this.creaDate = creaDate;
    }

    public String getCreaId() {
        return creaId;
    }

    public void setCreaId(String creaId) {
        this.creaId = creaId;
    }
}
