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
}
