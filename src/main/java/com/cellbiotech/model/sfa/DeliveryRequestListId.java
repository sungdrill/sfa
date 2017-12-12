package com.cellbiotech.model.sfa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 납품의뢰리스트 PK 관련 jpa model
 */
@Embeddable
public class DeliveryRequestListId implements Serializable {

    @Column(name = "REQUEST_NUM")
    private String requestNum;

    @Column(name = "ITEM_CODE")
    private String itemCode;

    public DeliveryRequestListId() {
    }

    public DeliveryRequestListId(String requestNum, String itemCode) {
        this.requestNum = requestNum;
        this.itemCode = itemCode;
    }

    public String getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(String requestNum) {
        this.requestNum = requestNum;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryRequestListId that = (DeliveryRequestListId) o;

        if (requestNum != null ? !requestNum.equals(that.requestNum) : that.requestNum != null) return false;
        return itemCode != null ? itemCode.equals(that.itemCode) : that.itemCode == null;
    }

    @Override
    public int hashCode() {
        int result = requestNum != null ? requestNum.hashCode() : 0;
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        return result;
    }
}
