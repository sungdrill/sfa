package com.cellbiotech.model.sfa;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * 재고관리 - 외부몰 관련 jpa model
 */
@Embeddable
public class ExmallListId implements Serializable {

    @Column(name = "UPDATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @Column(name = "PROD_CODE")
    private String prodCode;

    @Column(name = "EXMALL_CODE", nullable = true)
    private String exmallCode = "";

    public ExmallListId() {

    }

    public ExmallListId(Date updateDate, String prodCode, String exmallCode) {
        this.updateDate = updateDate;
        this.prodCode = prodCode;
        this.exmallCode = exmallCode;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getExmallCode() {
        return exmallCode;
    }

    public void setExmallCode(String exmallCode) {
        this.exmallCode = exmallCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExmallListId that = (ExmallListId) o;

        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (prodCode != null ? !prodCode.equals(that.prodCode) : that.prodCode != null) return false;
        return exmallCode != null ? exmallCode.equals(that.exmallCode) : that.exmallCode == null;
    }

    @Override
    public int hashCode() {
        int result = updateDate != null ? updateDate.hashCode() : 0;
        result = 31 * result + (prodCode != null ? prodCode.hashCode() : 0);
        result = 31 * result + (exmallCode != null ? exmallCode.hashCode() : 0);
        return result;
    }
}
