package com.cellbiotech.model.sfa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 수주리스트 PK 관련 jpa model
 */
@Embeddable
public class OrderListId implements Serializable {

    @Column(name = "ORDER_NUM")
    private String orderNum;

    @Column(name = "ORDER_SEQ")
    private String orderSeq;

    public OrderListId() {

    }

    public OrderListId(String orderNum, String orderSeq) {
        this.orderNum = orderNum;
        this.orderSeq = orderSeq;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderListId that = (OrderListId) o;

        if (orderNum != null ? !orderNum.equals(that.orderNum) : that.orderNum != null) return false;
        return orderSeq != null ? orderSeq.equals(that.orderSeq) : that.orderSeq == null;
    }

    @Override
    public int hashCode() {
        int result = orderNum != null ? orderNum.hashCode() : 0;
        result = 31 * result + (orderSeq != null ? orderSeq.hashCode() : 0);
        return result;
    }
}
