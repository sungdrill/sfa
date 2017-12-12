package com.cellbiotech.model.sfa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 공통코드 코드 PK 관련 jpa model
 */
@Embeddable
public class CodeListId implements Serializable {

    @Column(name = "CODE_ID")
    private String codeId;

    @Column(name = "GROUP_CODE_ID")
    private String groupCodeId;

    public CodeListId() {}

    public CodeListId(String codeId, String groupCodeId) {
        this.codeId = codeId;
        this.groupCodeId = groupCodeId;
    }

    public String getGroupCodeId() {
        return groupCodeId;
    }

    public void setGroupCodeId(String groupCodeId) {
        this.groupCodeId = groupCodeId;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeListId that = (CodeListId) o;

        if (groupCodeId != null ? !groupCodeId.equals(that.groupCodeId) : that.groupCodeId != null) return false;
        return codeId != null ? codeId.equals(that.codeId) : that.codeId == null;
    }

    @Override
    public int hashCode() {
        int result = groupCodeId != null ? groupCodeId.hashCode() : 0;
        result = 31 * result + (codeId != null ? codeId.hashCode() : 0);
        return result;
    }
}
