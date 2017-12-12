package com.cellbiotech.model.sfa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 행정구역 정보 관련 jpa model
 */
@Entity
@Table(name = "B_ADMINISTRATIVE_DISTRICT_LIST")
public class AdminDistrictList {

    @Id
    @Column(name = "ADMINISTRATIVE_DISTRICT_CODE")
    private String administrativeDistrictCode;

    @Column(name = "ADMINISTRATIVE_DISTRICT_NAME", nullable = true)
    private String administrativeDistrictName = "";

    @Column(name = "PHARMACY_NUMBER", nullable = true)
    private String pharmacyNumber = "";

    @Column(name = "DEAL_YN", nullable = true)
    private String dealYn = "";

    @Column(name = "USE_YN", nullable = true)
    private String useYn = "";

    public String getAdministrativeDistrictCode() {
        return administrativeDistrictCode;
    }

    public void setAdministrativeDistrictCode(String administrativeDistrictCode) {
        this.administrativeDistrictCode = administrativeDistrictCode;
    }

    public String getAdministrativeDistrictName() {
        return administrativeDistrictName;
    }

    public void setAdministrativeDistrictName(String administrativeDistrictName) {
        this.administrativeDistrictName = administrativeDistrictName;
    }

    public String getPharmacyNumber() {
        return pharmacyNumber;
    }

    public void setPharmacyNumber(String pharmacyNumber) {
        this.pharmacyNumber = pharmacyNumber;
    }

    public String getDealYn() {
        return dealYn;
    }

    public void setDealYn(String dealYn) {
        this.dealYn = dealYn;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
