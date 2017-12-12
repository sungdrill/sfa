package com.cellbiotech.model.sfa;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

/**
 * 파일 업로드 관련 jpa model
 */
@Entity
@Table(name = "U_UPLOAD_FILE_INFO")
public class UploadList {

    @Id
    @Column(name = "IDX")
    private int idx;

    @Column(name = "BOARD_IDX")
    private int boardIdx;

    @Column(name = "ORIGINAL_FILE_NAME")
    private String originalFileName;

    @Column(name = "STORED_FILE_NAME")
    private String storedFileName;

    @Column(name = "UPLOAD_PATH")
    private String uploadPath;

    @Column(name = "LINK_PATH")
    private String linkPath;

    @Column(name = "FILE_SIZE")
    private int fileSize;

    @Column(name = "CREA_DTM")
    private Date creaDtm;

    @Column(name = "CREA_ID")
    private String creaId;

    @Column(name = "DEL_GB")
    private String delGb;

    @Column(name = "INSERT_START_TIME")
    private Date insertStartTime;

    @Column(name = "INSERT_FINISH_TIME")
    private Date insertFinishTime;

    @Column(name = "INSERT_TABLE")
    private String insertTable;

    @Column(name = "INSERT_GB")
    private String insertGb;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getBoardIdx() {
        return boardIdx;
    }

    public void setBoardIdx(int boardIdx) {
        this.boardIdx = boardIdx;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getLinkPath() {
        return linkPath;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getCreaDtm() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(creaDtm);
    }

    public void setCreaDtm(Date creaDtm) {
        this.creaDtm = creaDtm;
    }

    public String getCreaId() {
        return creaId;
    }

    public void setCreaId(String creaId) {
        this.creaId = creaId;
    }

    public String getDelGb() {
        return delGb;
    }

    public void setDelGb(String delGb) {
        this.delGb = delGb;
    }

    public String getInsertStartTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(insertStartTime);
    }

    public void setInsertStartTime(Date insertStartTime) {
        this.insertStartTime = insertStartTime;
    }

    public String getInsertFinishTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(insertFinishTime);
    }

    public void setInsertFinishTime(Date insertFinishTime) {
        this.insertFinishTime = insertFinishTime;
    }

    public String getInsertTable() {
        return insertTable;
    }

    public void setInsertTable(String insertTable) {
        this.insertTable = insertTable;
    }

    public String getInsertGb() {
        return insertGb;
    }

    public void setInsertGb(String insertGb) {
        this.insertGb = insertGb;
    }
}
