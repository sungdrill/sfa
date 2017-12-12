package com.cellbiotech.model.sfa;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 재고관리 - 품목 관련 jpa model
 */
@Entity
@Table(name = "I_ITEM_MASTER_LIST")
public class ItemList {

    @Id
    @Column(name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "STANDARD_INFO")
    private String standardInfo;

    @Column(name = "UNIT")
    private String unit;

    @Column(name = "USE_YN")
    private String useYn;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStandardInfo() {
        return standardInfo;
    }

    public void setStandardInfo(String standardInfo) {
        this.standardInfo = standardInfo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
