package com.cellbiotech.model.sfa;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 공통코드 그룹코드 관련 jpa model
 */
@Entity
@Table(name = "G_GROUP_CODE_LIST")
public class GroupCodeList {

    @Id
    @Column(name = "GROUP_CODE_ID")
    private String groupCodeId;

    @Column(name = "GROUP_CODE_NAME", nullable = true)
    private String groupCodeName;

    @Column(name = "GROUP_CODE_EXP", nullable = true)
    private String groupCodeExp;

    @Column(name = "USE_YN", nullable = true)
    private String useYn;

    @Column(name = "CREA_ID", nullable = true)
    private String creaId;

    @Column(name = "CREA_DATE", nullable = true)
    private Date creaDate;

    public String getGroupCodeId() {
        return groupCodeId;
    }

    public void setGroupCodeId(String groupCodeId) {
        this.groupCodeId = groupCodeId;
    }

    public String getGroupCodeName() {
        return groupCodeName;
    }

    public void setGroupCodeName(String groupCodeName) {
        this.groupCodeName = groupCodeName;
    }

    public String getGroupCodeExp() {
        return groupCodeExp;
    }

    public void setGroupCodeExp(String groupCodeExp) {
        this.groupCodeExp = groupCodeExp;
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
