package com.cellbiotech.model.sfa;

import javax.persistence.*;
import java.util.Date;

/**
 * 재고관리 - 품목-상품 jpa model
 */
@Entity
@Table(name = "I_DUZON_DELIVERY_QT_LIST")
public class DuzonDeliveryQtList {

    @EmbeddedId
    private DuzonDeliveryQtListId id;

    @Column(name = "QT_DELIVERY", nullable = true)
    private int qtDelivery;

    public DuzonDeliveryQtListId getId() {
        return id;
    }

    public void setId(DuzonDeliveryQtListId id) {
        this.id = id;
    }

    public int getQtDelivery() {
        return qtDelivery;
    }

    public void setQtDelivery(int qtDelivery) {
        this.qtDelivery = qtDelivery;
    }
}
