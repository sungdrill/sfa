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
public class DuolacReleaseListId implements Serializable {

    @Column(name = "RELEASE_DATE")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @Column(name = "PROD_CODE")
    private String prodCode;

    public DuolacReleaseListId() {

    }

    public DuolacReleaseListId(Date releaseDate, String prodCode) {
        this.releaseDate = releaseDate;
        this.prodCode = prodCode;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DuolacReleaseListId that = (DuolacReleaseListId) o;

        if (releaseDate != null ? !releaseDate.equals(that.releaseDate) : that.releaseDate != null) return false;
        return prodCode != null ? prodCode.equals(that.prodCode) : that.prodCode == null;
    }

    @Override
    public int hashCode() {
        int result = releaseDate != null ? releaseDate.hashCode() : 0;
        result = 31 * result + (prodCode != null ? prodCode.hashCode() : 0);
        return result;
    }
}
