package com.cellbiotech.model.sfa;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * 재고관리 - 듀오락 상품 매출 관련 jpa model
 */
@Embeddable
public class ProdReleaseListId implements Serializable {

    @Column(name = "UPDATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @Column(name = "PROD_CODE")
    private String prodCode;

    @Column(name = "MALL_CODE")
    private String mallCode = "";

    public ProdReleaseListId() {

    }

    public ProdReleaseListId(Date updateDate, String prodCode, String mallCode) {
        this.updateDate = updateDate;
        this.prodCode = prodCode;
        this.mallCode = mallCode;
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
        return mallCode;
    }

    public void setExmallCode(String exmallCode) {
        this.mallCode = exmallCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProdReleaseListId that = (ProdReleaseListId) o;

        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (prodCode != null ? !prodCode.equals(that.prodCode) : that.prodCode != null) return false;
        return mallCode != null ? mallCode.equals(that.mallCode) : that.mallCode == null;
    }

    @Override
    public int hashCode() {
        int result = updateDate != null ? updateDate.hashCode() : 0;
        result = 31 * result + (prodCode != null ? prodCode.hashCode() : 0);
        result = 31 * result + (mallCode != null ? mallCode.hashCode() : 0);
        return result;
    }
}
