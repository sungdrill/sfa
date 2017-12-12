package com.cellbiotech.model.sfa;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 상품 기초 재고 현황
 */
@Entity
@Table(name = "I_PROD_BASIC_STOCK")
public class ProdBasicStock {

    @EmbeddedId
    private ProdBasicStockId id;

    @Column(name = "QT_GOOD_INV", nullable = true)
    private BigDecimal qtGoodInv;

    public ProdBasicStockId getId() {
        return id;
    }

    public void setId(ProdBasicStockId id) {
        this.id = id;
    }

    public BigDecimal getQtGoodInv() {
        return qtGoodInv;
    }

    public void setQtGoodInv(BigDecimal qtGoodInv) {
        this.qtGoodInv = qtGoodInv;
    }
}
