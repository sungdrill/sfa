package com.cellbiotech.model.sfa;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 재고관리 - 품목 재고현황 관련 jpa model
 */
@Embeddable
public class DuzonDeliveryQtListId implements Serializable {

    @Column(name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "DT_SO")
    private String dtSo;

    @Column(name = "DELIVER_TYPE")
    private String deliverType;

    @Column(name = "NO_SO")
    private String noSo;

    @Column(name = "SEQ_SO")
    private int seqSo;

    public DuzonDeliveryQtListId() {
    }

    public DuzonDeliveryQtListId(String itemCode, String dtSo, String deliverType, String noSo, int seqSo) {
        this.itemCode = itemCode;
        this.dtSo = dtSo;
        this.deliverType = deliverType;
        this.noSo = noSo;
        this.seqSo = seqSo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DuzonDeliveryQtListId that = (DuzonDeliveryQtListId) o;

        if (seqSo != that.seqSo) return false;
        if (itemCode != null ? !itemCode.equals(that.itemCode) : that.itemCode != null) return false;
        if (dtSo != null ? !dtSo.equals(that.dtSo) : that.dtSo != null) return false;
        if (deliverType != null ? !deliverType.equals(that.deliverType) : that.deliverType != null) return false;
        return noSo != null ? noSo.equals(that.noSo) : that.noSo == null;
    }

    @Override
    public int hashCode() {
        int result = itemCode != null ? itemCode.hashCode() : 0;
        result = 31 * result + (dtSo != null ? dtSo.hashCode() : 0);
        result = 31 * result + (deliverType != null ? deliverType.hashCode() : 0);
        result = 31 * result + (noSo != null ? noSo.hashCode() : 0);
        result = 31 * result + seqSo;
        return result;
    }
}
