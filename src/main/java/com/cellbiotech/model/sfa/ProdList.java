package com.cellbiotech.model.sfa;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 듀오락 매출 리스트 jpa model
 */
@Entity
@Table(name = "I_PROD_MASTER_LIST")
public class ProdList {

    @EmbeddedId
    private ProdListId id;

    @Column(name = "PROD_NAME", nullable = true)
    private String prodName = "";

    @Column(name = "PROD_TYPE", nullable = true)
    private String prodType = "";

    @Column(name = "USE_YN", nullable = true)
    private String useYn = "";

    public ProdListId getId() {
        return id;
    }

    public void setId(ProdListId id) {
        this.id = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
