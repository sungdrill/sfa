package com.cellbiotech.model.sfa;

import javax.persistence.*;
import java.util.Date;

/**
 * 행정구역 정보 관련 jpa model
 */
@Entity
@Table(name = "B_ERROR_LOG_LIST")
public class ErrorLogList {

    @Id
    @Column(name = "ERROR_SEQ")
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long errorSeq;

    @Column(name = "ERROR_IP", nullable = true)
    private String errorIp = "";

    @Column(name = "ERROR_CODE", nullable = true)
    private String errorCode = "";

    @Column(name = "ERROR_CONTENT", nullable = true)
    private String errorContent = "";

    @Column(name = "ERROR_DATE", nullable = true)
    private Date errorDate;

    public Long getErrorSeq() {
        return errorSeq;
    }

    public void setErrorSeq(Long errorSeq) {
        this.errorSeq = errorSeq;
    }

    public String getErrorIp() {
        return errorIp;
    }

    public void setErrorIp(String errorIp) {
        this.errorIp = errorIp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorContent() {
        return errorContent;
    }

    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent;
    }

    public Date getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(Date errorDate) {
        this.errorDate = errorDate;
    }
}
