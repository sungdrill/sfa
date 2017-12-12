package com.cellbiotech.model.sfa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 재고관리 - 상품 재고현황 관련 jpa model
 */
@Embeddable
public class ProdHistoryListId implements Serializable {

    @Column(name = "PROD_CODE")
    private String prodCode;

    @Column(name = "INPUT_SEQ")
    private int inputSeq;

    @Column(name = "INPUT_DATE")
    @Temporal(TemporalType.DATE)
    private Date inputDate;


    public ProdHistoryListId() {

    }

    public ProdHistoryListId(String prodCode, int inputSeq,  Date inputDate) {
        this.prodCode = prodCode;
        this.inputSeq = inputSeq;
        this.inputDate = inputDate;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public int getInputSeq() {
        return inputSeq;
    }

    public void setInputSeq(int inputSeq) {
        this.inputSeq = inputSeq;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProdHistoryListId that = (ProdHistoryListId) o;

        if (inputSeq != that.inputSeq) return false;
        if (prodCode != null ? !prodCode.equals(that.prodCode) : that.prodCode != null) return false;
        return inputDate != null ? inputDate.equals(that.inputDate) : that.inputDate == null;
    }

    @Override
    public int hashCode() {
        int result = prodCode != null ? prodCode.hashCode() : 0;
        result = 31 * result + inputSeq;
        result = 31 * result + (inputDate != null ? inputDate.hashCode() : 0);
        return result;
    }
}
