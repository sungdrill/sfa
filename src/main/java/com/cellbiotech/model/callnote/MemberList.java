package com.cellbiotech.model.callnote;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

/**
 * callnote 데이터베이스 영업담당자 관련 jpa model
 */
@Entity
@Table(name = "M_MEMBER")
public class MemberList {

    @EmbeddedId
    private MemberListId id;

    @Column(name = "PW")
    private String pw;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PHONE", nullable = true)
    private String phone;
    @Column(name = "MPHONE")
    private String mphone;
    @Column(name = "ZIPCODE", nullable = true)
    private String zipcode;
    @Column(name = "ADDR", nullable = true)
    private String addr;
    @Column(name = "ADDR_DT", nullable = true)
    private String addrDt;
    @Column(name = "AREA")
    private String area;
    @Column(name = "DEPT")
    private String dept;
    @Column(name = "AUTH")
    private String auth;
    @Column(name = "SEX", nullable = true)
    private String sex;
    @Column(name = "AGE", nullable = true)
    private Integer age;
    @Column(name = "MEMO", nullable = true)
    private String memo;
    @Column(name = "REGDATE")
    private Date regdate;
    @Column(name = "MODDATE", nullable = true)
    private Date moddate;
    @Column(name = "TEAM_CD", nullable = true)
    private String teamCd;
    @Column(name = "USE_YN", nullable = true)
    private String useYn;

    public MemberListId getId() {
        return id;
    }

    public void setId(MemberListId id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        if (phone != null) {
            return phone;
        } else {
            return "";
        }
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getZipcode() {
        if (zipcode != null) {
            return zipcode;
        } else {
            return "";
        }
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddr() {
        if (addr != null) {
            return addr;
        } else {
            return "";
        }
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddrDt() {
        if (addrDt != null) {
            return addrDt;
        } else {
            return "";
        }
    }

    public void setAddrDt(String addrDt) {
        this.addrDt = addrDt;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getSex() {
        if (sex != null) {
            return sex;
        } else {
            return "";
        }
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        if (age != null) {
            return Integer.toString(age);
        } else {

            return "";
        }
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMemo() {
        if (memo != null) {
            return memo;
        } else {
            return "";
        }
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRegdate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(regdate);
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public String getModdate() {
        if (moddate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(moddate);
        } else {
            return "";
        }
    }

    public void setModdate(Date moddate) {
        this.moddate = moddate;
    }

    public String getTeamCd() {
        if (teamCd != null) {
            return teamCd;
        } else {
            return "";
        }
    }

    public void setTeamCd(String teamCd) {
        this.teamCd = teamCd;
    }

    public String getUseYn() {
        if (useYn != null) {
            return useYn;
        } else {
            return "";
        }
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
