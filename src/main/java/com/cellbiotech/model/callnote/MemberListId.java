package com.cellbiotech.model.callnote;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * callnote 데이터베이스 영업담당자 PK 관련 JPA model
 */
@Embeddable
public class MemberListId implements Serializable {

    @Column(name = "SEQ")
    private int seq;

    @Column(name = "ID")
    private String id;

    public MemberListId() {
    }

    public MemberListId(int seq, String id) {
        this.seq = seq;
        this.id = id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberListId memberId = (MemberListId) o;

        if (seq != memberId.seq) return false;
        return id != null ? id.equals(memberId.id) : memberId.id == null;
    }

    @Override
    public int hashCode() {
        int result = seq;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
