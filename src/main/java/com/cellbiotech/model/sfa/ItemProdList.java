package com.cellbiotech.model.sfa;

import javax.persistence.*;
import java.util.Date;

/**
 * 재고관리 - 품목-상품 jpa model
 */
@Entity
@Table(name = "I_ITEM_PROD_LIST")
public class ItemProdList {

    @EmbeddedId
    private ItemProdListId id;

    @Column(name = "MAPPING_DATE", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date mappingDate;

    public ItemProdListId getId() {
        return id;
    }

    public void setId(ItemProdListId id) {
        this.id = id;
    }

    public Date getMappingDate() {
        return mappingDate;
    }

    public void setMappingDate(Date mappingDate) {
        this.mappingDate = mappingDate;
    }
}
