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
public class ItemHistoryListId implements Serializable {

    @Column(name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "UPDATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date updateDate;


    public ItemHistoryListId() {

    }

    public ItemHistoryListId(String itemCode, Date updateDate) {
        this.itemCode = itemCode;
        this.updateDate = updateDate;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemHistoryListId that = (ItemHistoryListId) o;

        if (itemCode != null ? !itemCode.equals(that.itemCode) : that.itemCode != null) return false;
        return updateDate != null ? updateDate.equals(that.updateDate) : that.updateDate == null;
    }

    @Override
    public int hashCode() {
        int result = itemCode != null ? itemCode.hashCode() : 0;
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}
