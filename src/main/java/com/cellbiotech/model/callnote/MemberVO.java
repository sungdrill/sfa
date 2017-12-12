package com.cellbiotech.model.callnote;

import javax.persistence.Column;
import java.util.Date;

/**
 * callnote 데이터베이스 영업담당자 관련 model
 */
public class MemberVO {

    private String teamCd;
    private String teamNm;
    private int teamOrders;
    private String areaCd;
    private String areaNm;
    private int areaOrders;
    private String memberId;
    private String name;
    private String phone;
    private String mphone;
    private String zipcode;
    private String addr;
    private String addrDt;
    private String memo;
    private String posiCd;
    private String posiNm;
    private int posiOrders;

    public String getTeamCd() {
        return teamCd;
    }

    public void setTeamCd(String teamCd) {
        this.teamCd = teamCd;
    }

    public String getTeamNm() {
        return teamNm;
    }

    public void setTeamNm(String teamNm) {
        this.teamNm = teamNm;
    }

    public int getTeamOrders() {
        return teamOrders;
    }

    public void setTeamOrders(int teamOrders) {
        this.teamOrders = teamOrders;
    }

    public String getAreaCd() {
        return areaCd;
    }

    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }

    public String getAreaNm() {
        return areaNm;
    }

    public void setAreaNm(String areaNm) {
        this.areaNm = areaNm;
    }

    public int getAreaOrders() {
        return areaOrders;
    }

    public void setAreaOrders(int areaOrders) {
        this.areaOrders = areaOrders;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddrDt() {
        return addrDt;
    }

    public void setAddrDt(String addrDt) {
        this.addrDt = addrDt;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPosiCd() {
        return posiCd;
    }

    public void setPosiCd(String posiCd) {
        this.posiCd = posiCd;
    }

    public String getPosiNm() {
        return posiNm;
    }

    public void setPosiNm(String posiNm) {
        this.posiNm = posiNm;
    }

    public int getPosiOrders() {
        return posiOrders;
    }

    public void setPosiOrders(int posiOrders) {
        this.posiOrders = posiOrders;
    }
}
