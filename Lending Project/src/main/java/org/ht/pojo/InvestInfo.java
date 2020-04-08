package org.ht.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class InvestInfo extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int getInid() {
		return inid;
	}

	public void setInid(int inid) {
		this.inid = inid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getBrrowid() {
		return brrowid;
	}

	public void setBrrowid(int brrowid) {
		this.brrowid = brrowid;
	}

	public BigDecimal getInmoney() {
		return inmoney;
	}

	public void setInmoney(BigDecimal inmoney) {
		this.inmoney = inmoney;
	}

	public String getInstatus() {
		return instatus;
	}

	public void setInstatus(String instatus) {
		this.instatus = instatus;
	}

	public String getInstyle() {
		return instyle;
	}

	public void setInstyle(String instyle) {
		this.instyle = instyle;
	}

	public String getBrrowstatus() {
		return brrowstatus;
	}

	public void setBrrowstatus(String brrowstatus) {
		this.brrowstatus = brrowstatus;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getProfitmodel() {
		return profitmodel;
	}

	public void setProfitmodel(String profitmodel) {
		this.profitmodel = profitmodel;
	}

	public BigDecimal getProfitmoney() {
		return profitmoney;
	}

	public void setProfitmoney(BigDecimal profitmoney) {
		this.profitmoney = profitmoney;
	}

	public Timestamp getIndate() {
		return indate;
	}

	public void setIndate(Timestamp indate) {
		this.indate = indate;
	}

	public String getReplaydate() {
		return replaydate;
	}

	public void setReplaydate(String replaydate) {
		this.replaydate = replaydate;
	}

	public int getMarkstatus() {
		return markstatus;
	}

	public void setMarkstatus(int markstatus) {
		this.markstatus = markstatus;
	}

	public String toString() {
		return "InvestInfo [inid=" + inid + ", userid=" + userid + ", brrowid=" + brrowid + ", inmoney=" + inmoney
				+ ", instatus=" + instatus + ", instyle=" + instyle + ", brrowstatus=" + brrowstatus + ", interest="
				+ interest + ", profitmodel=" + profitmodel + ", profitmoney=" + profitmoney + ", indate=" + indate
				+ ", replaydate=" + replaydate + ", markstatus=" + markstatus + "]";
	}

	private int inid; // 'Investment Information Table Primary Key',
	private int userid; // 'Investment user primary key',
	private int brrowid; // 'Primary key of bid',
	private BigDecimal inmoney; // 'investment amount',
	private String instatus; // 'Investment status 0 Investment in earnings 1 Completed investment',
	private String instyle; // 'Type of investment',
	private String brrowstatus;// 'Whether the loan status is completed',
	private String interest; // 'Investment rate',
	private String profitmodel; // 'Profit method such as equal principal',
	private BigDecimal profitmoney; // 'Profit amount',
	private Timestamp indate; // 'Investment time can be empty'

	private String replaydate; //
	private int markstatus; // 'Bid status 0 The default bid is in progress 1 The bid passes (winning) 2 The bid fails (losing the bid)';
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String title;
	private String type;
	private String uname;
	
	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	private Integer startPage;
	private Integer pageSize;

	public Integer getStartPage() {
		return startPage;
	}

	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
