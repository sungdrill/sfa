package com.cellbiotech.model.sfa;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 자사몰 판매현황 리스트 jpa model
 */
@Entity
@Table(name = "I_DUOLAC_RELEASE")
public class DuolacReleaseList {

    @EmbeddedId
    private DuolacReleaseListId id;

    @Column(name = "PROD_NAME", nullable = true)
    private String prodName = "";

    @Column(name = "PROD_QTY", nullable = true)
    private int prodQty = 0;

    @Column(name = "UNIT_PRICE", nullable = true)
    private BigDecimal unitPrice;

    @Column(name = "SALES_PRICE_SUM", nullable = true)
    private BigDecimal salesPriceSum;

    @Column(name = "ONE_TIME", nullable = true)
    private BigDecimal oneTime;

    @Column(name = "TWO_TIME", nullable = true)
    private BigDecimal twoTime;

    @Column(name = "THREE_TIME", nullable = true)
    private BigDecimal threeTime;

    @Column(name = "PROD_QTY_RETURN", nullable = true)
    private BigDecimal prodQtyReturn;

    @Column(name = "PROD_TYPE_GSP", nullable = true)
    private String prodTypeGsp;

    public DuolacReleaseListId getId() {
        return id;
    }

    public void setId(DuolacReleaseListId id) {
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

    public BigDecimal getOneTime() {
        return oneTime;
    }

    public void setOneTime(BigDecimal oneTime) {
        this.oneTime = oneTime;
    }

    public BigDecimal getTwoTime() {
        return twoTime;
    }

    public void setTwoTime(BigDecimal twoTime) {
        this.twoTime = twoTime;
    }

    public BigDecimal getThreeTime() {
        return threeTime;
    }

    public void setThreeTime(BigDecimal threeTime) {
        this.threeTime = threeTime;
    }

    public BigDecimal getProdQtyReturn() {
        return prodQtyReturn;
    }

    public void setProdQtyReturn(BigDecimal prodQtyReturn) {
        this.prodQtyReturn = prodQtyReturn;
    }

    public String getProdTypeGsp() {
        return prodTypeGsp;
    }

    public void setProdTypeGsp(String prodTypeGsp) {
        this.prodTypeGsp = prodTypeGsp;
    }
}
