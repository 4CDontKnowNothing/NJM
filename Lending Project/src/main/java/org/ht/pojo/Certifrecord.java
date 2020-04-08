package org.ht.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:Certification application record bean
 */
public class Certifrecord implements Serializable {
	private Integer crid;//id
	private Integer cruserid;//user id
	private String crusername;//user name
	private Integer craiid;//Certification item number
	private String crainame;//Authentication item name
	private String craitype;//Authentication item type
	private String crispass;//Certification item status
	private Date crdate;//Review time
	private String crauditor;//auditor follow up
	private Integer crintegral;//credit
	private String crviewpoint;//viewpoint
	private String crimg;//credit image
	private Date crupldate;//upload time
	
	
	private Integer checkpend;//Pending review
	
	public Certifrecord() {

	}

	
	
	public Certifrecord(Integer crid, Integer cruserid, String crusername, Integer craiid, String crainame,
			String craitype, String crispass, Date crdate, String crauditor, Integer crintegral, String crviewpoint,
			String crimg, Date crupldate) {
		
		this.crid = crid;
		this.cruserid = cruserid;
		this.crusername = crusername;
		this.craiid = craiid;
		this.crainame = crainame;
		this.craitype = craitype;
		this.crispass = crispass;
		this.crdate = crdate;
		this.crauditor = crauditor;
		this.crintegral = crintegral;
		this.crviewpoint = crviewpoint;
		this.crimg = crimg;
		this.crupldate = crupldate;
	}



	public Integer getCrid() {
		return crid;
	}

	public void setCrid(Integer crid) {
		this.crid = crid;
	}

	public Integer getCruserid() {
		return cruserid;
	}

	public void setCruserid(Integer cruserid) {
		this.cruserid = cruserid;
	}

	public String getCrusername() {
		return crusername;
	}

	public void setCrusername(String crusername) {
		this.crusername = crusername;
	}

	public Integer getCraiid() {
		return craiid;
	}

	public void setCraiid(Integer craiid) {
		this.craiid = craiid;
	}

	public String getCrainame() {
		return crainame;
	}

	public void setCrainame(String crainame) {
		this.crainame = crainame;
	}

	public String getCraitype() {
		return craitype;
	}

	public void setCraitype(String craitype) {
		this.craitype = craitype;
	}

	public String getCrispass() {
		return crispass;
	}

	public void setCrispass(String crispass) {
		this.crispass = crispass;
	}

	public Date getCrdate() {
		return crdate;
	}

	public void setCrdate(Date crdate) {
		this.crdate = crdate;
	}

	public String getCrauditor() {
		return crauditor;
	}

	public void setCrauditor(String crauditor) {
		this.crauditor = crauditor;
	}

	public Integer getCrintegral() {
		return crintegral;
	}

	public void setCrintegral(Integer crintegral) {
		this.crintegral = crintegral;
	}

	public String getCrviewpoint() {
		return crviewpoint;
	}

	public void setCrviewpoint(String crviewpoint) {
		this.crviewpoint = crviewpoint;
	}

	public String getCrimg() {
		return crimg;
	}

	public void setCrimg(String crimg) {
		this.crimg = crimg;
	}

	public Date getCrupldate() {
		return crupldate;
	}

	public void setCrupldate(Date crupldate) {
		this.crupldate = crupldate;
	}

	public Integer getCheckpend() {
		return checkpend;
	}

	public void setCheckpend(Integer checkpend) {
		this.checkpend = checkpend;
	}

}
