package org.ht.pojo;

import java.io.Serializable;

/**
 * @Name: Approveitem
 * @Description:CertifiedJavaBeans
 */
public class Approveitem implements Serializable {

	private Integer aiid;// Certification item number
	private String ainame;// Authentication item name
	private String aitype;// Types of authentication items 1. Basic authentication items; 2. Optional authentication items
	private String aistate;// Certification item status 0. Deprecated; 1. Not deprecated

	public Approveitem() {

	}

	public Approveitem(Integer aiid, String ainame, String aitype, String aistate) {

		this.aiid = aiid;
		this.ainame = ainame;
		this.aitype = aitype;
		this.aistate = aistate;
	}

	public Integer getAiid() {
		return aiid;
	}

	public void setAiid(Integer aiid) {
		this.aiid = aiid;
	}

	public String getAiname() {
		return ainame;
	}

	public void setAiname(String ainame) {
		this.ainame = ainame;
	}

	public String getAitype() {
		return aitype;
	}

	public void setAitype(String aitype) {
		this.aitype = aitype;
	}

	public String getAistate() {
		return aistate;
	}

	public void setAistate(String aistate) {
		this.aistate = aistate;
	}

	/**
	 * 
	 * @return All values ​​of this object
	 */
	public String getApproveitemInfo() {

		return "Info aiid==" + this.getAiid() + "  ainame==" + this.getAiname() + "  aitype==" + this.getAitype()
				+ "  aistate==" + this.getAistate();
	}

}
