package com.cellbiotech.model.sfa;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 재고관리 - 듀오락 상품 매출 관련 jpa model
 */
@Embeddable
public class ProdInvId implements Serializable {

    @Column(name = "CD_PLANT")
    private String cdPlant;

    @Column(name = "CD_ITEM")
    private String cdItem = "";

    @Column(name = "YY_INV")
    private String yyInv = "";

    @Column(name = "YM_IO")
    private String ymIo = "";

    public ProdInvId() {
    }

    public ProdInvId(String cdPlant, String cdItem, String yyInv, String ymIo) {
        this.cdPlant = cdPlant;
        this.cdItem = cdItem;
        this.yyInv = yyInv;
        this.ymIo = ymIo;
    }

    public String getCdPlant() {
        return cdPlant;
    }

    public void setCdPlant(String cdPlant) {
        this.cdPlant = cdPlant;
    }

    public String getCdItem() {
        return cdItem;
    }

    public void setCdItem(String cdItem) {
        this.cdItem = cdItem;
    }

    public String getYyInv() {
        return yyInv;
    }

    public void setYyInv(String yyInv) {
        this.yyInv = yyInv;
    }

    public String getYmIo() {
        return ymIo;
    }

    public void setYmIo(String ymIo) {
        this.ymIo = ymIo;
    }
}
