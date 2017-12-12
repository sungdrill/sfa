package com.cellbiotech.model.sfa;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * 영업담당자 거래처정보 PK 관련 jpa model
 */
@Embeddable
public class MemberAccountInfoManagerListId implements Serializable {

    @Column(name = "ACCOUNT_CODE")
    private String accountCode;

    @Column(name = "SALES_MANAGER_CODE")
    private String salesManagerCode;

    @Column(name = "MANAGER_START_DATE")
    @Temporal(TemporalType.DATE)
    private Date managerStartDate;

    public MemberAccountInfoManagerListId() {
    }

    public MemberAccountInfoManagerListId(String accountCode, String salesManagerCode, Date managerStartDate) {
        this.accountCode = accountCode;
        this.salesManagerCode = salesManagerCode;
        this.managerStartDate = managerStartDate;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getSalesManagerCode() {
        return salesManagerCode;
    }

    public void setSalesManagerCode(String salesManagerCode) {
        this.salesManagerCode = salesManagerCode;
    }

    public Date getManagerStartDate() {
        return managerStartDate;
    }

    public void setManagerStartDate(Date managerStartDate) {
        this.managerStartDate = managerStartDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberAccountInfoManagerListId that = (MemberAccountInfoManagerListId) o;

        if (accountCode != null ? !accountCode.equals(that.accountCode) : that.accountCode != null) return false;
        if (salesManagerCode != null ? !salesManagerCode.equals(that.salesManagerCode) : that.salesManagerCode != null)
            return false;
        return managerStartDate != null ? managerStartDate.equals(that.managerStartDate) : that.managerStartDate == null;
    }

    @Override
    public int hashCode() {
        int result = accountCode != null ? accountCode.hashCode() : 0;
        result = 31 * result + (salesManagerCode != null ? salesManagerCode.hashCode() : 0);
        result = 31 * result + (managerStartDate != null ? managerStartDate.hashCode() : 0);
        return result;
    }
}