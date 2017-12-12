package com.cellbiotech.model.sfa;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 재고관리 - 듀오락 상품 매출 관련 jpa model
 */
@Embeddable
public class ProdBasicStockId implements Serializable {

    @Column(name = "CD_ITEM")
    private String cdItem = "";

    @Column(name = "CD_PLANT")
    private String cdPlant;

    @Column(name = "YM_STANDARD")
    private String ymStandard = "";

    public ProdBasicStockId() {
    }

    public ProdBasicStockId(String cdItem, String cdPlant, String ymStandard) {
        this.cdItem = cdItem;
        this.cdPlant = cdPlant;
        this.ymStandard = ymStandard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProdBasicStockId that = (ProdBasicStockId) o;

        if (cdItem != null ? !cdItem.equals(that.cdItem) : that.cdItem != null) return false;
        if (cdPlant != null ? !cdPlant.equals(that.cdPlant) : that.cdPlant != null) return false;
        return ymStandard != null ? ymStandard.equals(that.ymStandard) : that.ymStandard == null;
    }

    @Override
    public int hashCode() {
        int result = cdItem != null ? cdItem.hashCode() : 0;
        result = 31 * result + (cdPlant != null ? cdPlant.hashCode() : 0);
        result = 31 * result + (ymStandard != null ? ymStandard.hashCode() : 0);
        return result;
    }
}
