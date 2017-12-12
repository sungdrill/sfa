package com.cellbiotech.model.sfa;

import java.util.Date;

import javax.persistence.*;

/**
 * 파일 업로드 관련 jpa model
 */
@Entity
@Table(name="U_UPLOAD_FILE_INFO")
public class FileUpload {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
    Long idx;
	
	@Column(name="BOARD_IDX", updatable = false)
	Long boardIdx;

	@Column(name="ORIGINAL_FILE_NAME", updatable = false)
    String originalFileName;

	@Column(name="STORED_FILE_NAME", updatable = false)
	String storedFileName;

	@Column(name="UPLOAD_PATH", updatable = false)
	String uploadPath;

	@Column(name="LINK_PATH", updatable = false)
	String linkPath;

	@Column(name="FILE_SIZE", updatable = false)
	Long fileSize;

	@Column(name="CREA_DTM", updatable = false)
    Date date;
    
	@Column(name="CREA_ID", updatable = false)
	String creaId;
	
	@Column(name="DEL_GB", updatable = false)
	String delGb;

	@Column(name="INSERT_START_TIME")
	Date insertStartTime;

	@Column(name="INSERT_FINISH_TIME")
	Date insertFinishTime;

	@Column(name="INSERT_TABLE")
	String insertTable;

	@Column(name="INSERT_GB")
	String insertGb;

	public FileUpload(Long boardIdx, String originalFileName, String storedFileName, String uploadPath, String linkPath, Long fileSize, Date date, String creaId, String delGb, Date insertStartTime, Date insertFinishTime, String insertTable, String insertGb) {
		this.boardIdx = boardIdx;
		this.originalFileName = originalFileName;
		this.storedFileName = storedFileName;
		this.uploadPath = uploadPath;
		this.linkPath = linkPath;
		this.fileSize = fileSize;
		this.date = date;
		this.creaId = creaId;
		this.delGb = delGb;
		this.insertStartTime = insertStartTime;
		this.insertFinishTime = insertFinishTime;
		this.insertTable = insertTable;
		this.insertGb = insertGb;
	}

	public FileUpload() {
    }

	@PrePersist
	void preInsert() {
		delGb = "N";
		date = new Date();
		insertGb = "N";
	}

	@Override
	public String toString() {
		return "FileUpload{" +
				"idx=" + idx +
				", boardIdx=" + boardIdx +
				", originalFileName='" + originalFileName + '\'' +
				", storedFileName='" + storedFileName + '\'' +
				", uploadPath='" + uploadPath + '\'' +
				", linkPath='" + linkPath + '\'' +
				", fileSize=" + fileSize +
				", date=" + date +
				", creaId='" + creaId + '\'' +
				", delGb='" + delGb + '\'' +
				", insertStartTime=" + insertStartTime +
				", insertFinishTime=" + insertFinishTime +
				", insertTable='" + insertTable + '\'' +
				", insertGb='" + insertGb + '\'' +
				'}';
	}

	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public Long getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(Long boardIdx) {
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

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Date getInsertStartTime() {
		return insertStartTime;
	}

	public void setInsertStartTime(Date insertStartTime) {
		this.insertStartTime = insertStartTime;
	}

	public Date getInsertFinishTime() {
		return insertFinishTime;
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