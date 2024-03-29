package com.eazybank.springsecurityeazybank.model;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "notice_details")
public class Notices {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "notice_id")
	private int noticeId;

	@Column(name = "notice_summary")
	private String noticeSummary;

	@Column(name = "notice_details")
	private String noticeDetails;

	@Column(name = "notic_beg_dt")
	private Date noticeBeginDate;
	
	@Column(name = "notic_end_dt")
	private Date noticeEndDate;
	
	@Column(name = "create_dt")
	private Date createDate;
	
	@Column(name = "update_dt")
	private Date updateDate;

	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeSummary() {
		return noticeSummary;
	}

	public void setNoticeSummary(String noticeSummary) {
		this.noticeSummary = noticeSummary;
	}

	public String getNoticeDetails() {
		return noticeDetails;
	}

	public void setNoticeDetails(String noticeDetails) {
		this.noticeDetails = noticeDetails;
	}

	public Date getNoticeBeginDate() {
		return noticeBeginDate;
	}

	public void setNoticeBeginDate(Date noticeBeginDate) {
		this.noticeBeginDate = noticeBeginDate;
	}

	public Date getNoticeEndDate() {
		return noticeEndDate;
	}

	public void setNoticeEndDate(Date noticeEndDate) {
		this.noticeEndDate = noticeEndDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}	
}
