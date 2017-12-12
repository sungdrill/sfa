package com.cellbiotech.model.sfa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 매출마감리스트 PK 관련 jpa model
 */
@Embeddable
public class SalesDeadlineListId implements Serializable {

    @Column(name = "SALES_NUM")
    private String salesNum;

    @Column(name = "DOC_NUM")
    private String docNum;

    @Column(name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "ISSUE_NUM")
    private String issueNum;

    public SalesDeadlineListId() {
    }

    public SalesDeadlineListId(String salesNum, String docNum, String itemCode, String issueNum) {
        this.salesNum = salesNum;
        this.docNum = docNum;
        this.itemCode = itemCode;
        this.issueNum = issueNum;
    }

    public String getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(String salesNum) {
        this.salesNum = salesNum;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getIssueNum() {
        return issueNum;
    }

    public void setIssueNum(String issueNum) {
        this.issueNum = issueNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalesDeadlineListId that = (SalesDeadlineListId) o;

        if (salesNum != null ? !salesNum.equals(that.salesNum) : that.salesNum != null) return false;
        if (docNum != null ? !docNum.equals(that.docNum) : that.docNum != null) return false;
        if (itemCode != null ? !itemCode.equals(that.itemCode) : that.itemCode != null) return false;
        return issueNum != null ? issueNum.equals(that.issueNum) : that.issueNum == null;
    }

    @Override
    public int hashCode() {
        int result = salesNum != null ? salesNum.hashCode() : 0;
        result = 31 * result + (docNum != null ? docNum.hashCode() : 0);
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        result = 31 * result + (issueNum != null ? issueNum.hashCode() : 0);
        return result;
    }
}
