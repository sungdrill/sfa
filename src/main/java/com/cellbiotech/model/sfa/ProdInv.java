package com.cellbiotech.model.sfa;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 상품 입출고 현황
 */
@Entity
@Table(name = "I_PROD_INV")
public class ProdInv {

    @EmbeddedId
    private ProdInvId id;

    @Column(name = "QT_DELIVERY", nullable = true)
    private BigDecimal qtDelivery;

    @Column(name = "QT_DUOLAC", nullable = true)
    private BigDecimal qtDuolac;

    @Column(name = "QT_EXMALL", nullable = true)
    private BigDecimal qtExmall;

    public ProdInvId getId() {
        return id;
    }

    public void setId(ProdInvId id) {
        this.id = id;
    }

    public BigDecimal getQtDelivery() {
        return qtDelivery;
    }

    public void setQtDelivery(BigDecimal qtDelivery) {
        this.qtDelivery = qtDelivery;
    }

    public BigDecimal getQtDuolac() {
        return qtDuolac;
    }

    public void setQtDuolac(BigDecimal qtDuolac) {
        this.qtDuolac = qtDuolac;
    }

    public BigDecimal getQtExmall() {
        return qtExmall;
    }

    public void setQtExmall(BigDecimal qtExmall) {
        this.qtExmall = qtExmall;
    }
}
