package com.cellbiotech.model.sfa;

import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 공통코드 코드 관련 jpa model
 */
@Entity
@Table(name = "G_CODE_LIST")
public class CodeList {

    @EmbeddedId
    private CodeListId id;

    @Column(name = "CODE_NAME", nullable = true)
    private String codeName;

    @Column(name = "SORT", nullable = true)
    private int sort;

    @Column(name = "USE_YN", nullable = true)
    private String useYn;

    @Column(name = "CREA_ID", nullable = true)
    private String creaId;

    @Column(name = "CREA_DATE", nullable = true)
    private Date creaDate;

    public CodeListId getId() {
        return id;
    }

    public void setId(CodeListId id) {
        this.id = id;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getCreaDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(creaDate);
    }

    public void setCreaDate(Date creaDate) {
        this.creaDate = creaDate;
    }

    public String getCreaId() {
        return creaId;
    }

    public void setCreaId(String creaId) {
        this.creaId = creaId;
    }
}
