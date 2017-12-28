package com.cellbiotech.model.sfa;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 재고관리 - 상품 재고현황 관련 jpa model
 */
@Entity
@Table(name = "I_PROD_HISTORY_LIST")
public class ProdHistoryList {

    @EmbeddedId
    private ProdHistoryListId id;

    @Column(name = "BASIC_STOCK")
    private BigDecimal basicStock;

    @Column(name = "IN_ITEM")
    private BigDecimal inItem;

    @Column(name = "OUT_ITEM")
    private BigDecimal outItem;

    @Column(name = "CLOSING_STOCK")
    private BigDecimal closingStock;

    @Column(name = "INPUT_TYPE")
    private String inputType;

    @Column(name = "PROD_NAME", updatable = false)
    private String prodName;

    @Column(name = "DEL_YN")
    private String delYn;

    @Column(name = "REG_ID", updatable = false)
    private String regId;

    @Column(name = "REG_DATE", updatable = false)
    @Temporal(TemporalType.DATE)
    private Date regDate;

    @Column(name = "MOD_ID")
    private String modId;

    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.DATE)
    private Date modDate;

    public ProdHistoryListId getId() {
        return id;
    }

    public void setId(ProdHistoryListId id) {
        this.id = id;
    }

    public BigDecimal getBasicStock() {
        return basicStock;
    }

    public void setBasicStock(BigDecimal basicStock) {
        this.basicStock = basicStock;
    }

    public BigDecimal getInItem() {
        return inItem;
    }

    public void setInItem(BigDecimal inItem) {
        this.inItem = inItem;
    }

    public BigDecimal getOutItem() {
        return outItem;
    }

    public void setOutItem(BigDecimal outItem) {
        this.outItem = outItem;
    }

    public BigDecimal getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(BigDecimal closingStock) {
        this.closingStock = closingStock;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }
}
