package com.cellbiotech.model.sfa;

import javax.persistence.*;

/**
 * 거래처별 등급 관리 관련 jpa model
 */
@Entity
@Table(name = "G_ACCOUNT_GRADE_LIST")
public class AccountGradeList {

    @Id
    @Column(name = "ACCOUNT_CODE")
    private String accountCode;

    @Column(name = "GRADE_CODE")
    private String gradeCode;

    @Column(name = "GODOMOLL_YN")
    private String godomollYn;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getGodomollYn() {
        return godomollYn;
    }

    public void setGodomollYn(String godomollYn) {
        this.godomollYn = godomollYn;
    }
}
