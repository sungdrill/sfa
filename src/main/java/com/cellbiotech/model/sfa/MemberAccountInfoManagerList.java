package com.cellbiotech.model.sfa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 영업담당자 거래처 정보 관련 jpa model
 */
@Entity
@Table(name = "G_MEMBER_ACCOUNT_INFO_MANAGER_LIST")
public class MemberAccountInfoManagerList {

    @EmbeddedId
    private MemberAccountInfoManagerListId id;

    @Column(name = "MANAGER_END_DATE", nullable = true, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date managerEndDate;

    @Column(name = "CREA_ID")
    private String creaId;

    @Column(name = "CREA_DATE")
    private Date creaDate;

    public MemberAccountInfoManagerListId getId() {
        return id;
    }

    public void setId(MemberAccountInfoManagerListId id) {
        this.id = id;
    }

    public Date getManagerEndDate() {
        return managerEndDate;
    }

    public void setManagerEndDate(Date managerEndDate) {
        this.managerEndDate = managerEndDate;
    }

    public String getCreaDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
