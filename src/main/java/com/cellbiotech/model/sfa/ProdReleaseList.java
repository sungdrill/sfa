package com.cellbiotech.model.sfa;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 듀오락 매출 리스트 jpa model
 */
@Entity
@Table(name = "I_PROD_RELEASE_LIST")
public class ProdReleaseList {

    @EmbeddedId
    private ProdReleaseListId id;

    @Column(name = "PROD_NAME", nullable = true)
    private String prodName = "";

    @Column(name = "PROD_QTY", nullable = true)
    private int prodQty = 0;

    @Column(name = "MALL_SITE", nullable = true)
    private String mallSite = "";

    @Column(name = "UNIT_PRICE", nullable = true)
    private BigDecimal unitPrice;

    @Column(name = "SALES_PRICE_SUM", nullable = true)
    private BigDecimal salesPriceSum;

    public ProdReleaseListId getId() {
        return id;
    }

    public void setId(ProdReleaseListId id) {
        this.id = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public int getProdQty() {
        return prodQty;
    }

    public void setProdQty(int prodQty) {
        this.prodQty = prodQty;
    }

    public String getMallSite() {
        return mallSite;
    }

    public void setMallSite(String mallSite) {
        this.mallSite = mallSite;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSalesPriceSum() {
        return salesPriceSum;
    }

    public void setSalesPriceSum(BigDecimal salesPriceSum) {
        this.salesPriceSum = salesPriceSum;
    }
}
