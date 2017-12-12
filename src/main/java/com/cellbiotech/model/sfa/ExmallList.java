package com.cellbiotech.model.sfa;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 외부몰리스트 jpa model
 */
@Entity
@Table(name = "I_EXMALL_RELEASE_LIST")
public class ExmallList {

    @EmbeddedId
    private ExmallListId id;

    @Column(name = "PROD_NAME", nullable = true)
    private String prodName = "";

    @Column(name = "PROD_QTY", nullable = true)
    private int prodQty = 0;

    @Column(name = "EXMALL_NAME", nullable = true)
    private String exmallName = "";

    @Column(name = "UNIT_PRICE", nullable = true)
    private BigDecimal unitPrice;

    @Column(name = "SALES_PRICE_SUM", nullable = true)
    private BigDecimal salesPriceSum;

    public ExmallListId getId() {
        return id;
    }

    public void setId(ExmallListId id) {
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

    public String getExmallName() {
        return exmallName;
    }

    public void setExmallName(String exmallName) {
        this.exmallName = exmallName;
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
