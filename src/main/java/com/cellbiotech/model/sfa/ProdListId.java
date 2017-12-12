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
public class ProdListId implements Serializable {

    @Column(name = "PROD_CODE")
    private String prodCode;

    @Column(name = "MALL_SITE")
    private String mallSite = "";

    public ProdListId() {

    }

    public ProdListId(String prodCode, String mallSite) {
        this.prodCode = prodCode;
        this.mallSite = mallSite;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getMallSite() {
        return mallSite;
    }

    public void setMallSite(String mallSite) {
        this.mallSite = mallSite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProdListId that = (ProdListId) o;

        if (prodCode != null ? !prodCode.equals(that.prodCode) : that.prodCode != null) return false;
        return mallSite != null ? mallSite.equals(that.mallSite) : that.mallSite == null;
    }

    @Override
    public int hashCode() {
        int result = prodCode != null ? prodCode.hashCode() : 0;
        result = 31 * result + (mallSite != null ? mallSite.hashCode() : 0);
        return result;
    }
}
