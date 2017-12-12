package com.cellbiotech.model.sfa;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * 재고관리 - 품목 재고현황 관련 jpa model
 */
@Embeddable
public class ItemProdListId implements Serializable {

    @Column(name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "PROD_CODE")
    private String prodCode;

    public ItemProdListId() {
    }

    public ItemProdListId(String itemCode, String prodCode) {
        this.itemCode = itemCode;
        this.prodCode = prodCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemProdListId that = (ItemProdListId) o;

        if (itemCode != null ? !itemCode.equals(that.itemCode) : that.itemCode != null) return false;
        return prodCode != null ? prodCode.equals(that.prodCode) : that.prodCode == null;
    }

    @Override
    public int hashCode() {
        int result = itemCode != null ? itemCode.hashCode() : 0;
        result = 31 * result + (prodCode != null ? prodCode.hashCode() : 0);
        return result;
    }
}
