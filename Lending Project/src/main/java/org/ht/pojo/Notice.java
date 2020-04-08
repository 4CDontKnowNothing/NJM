package org.ht.pojo;

import java.util.Date;

public class Notice {
	// noticeid int(11) NOT NULL AUTO_INCREMENT COMMENT 'notice form id',
	// noticetitle varchar(255) DEFAULT NULL COMMENT 'notice form title',
	// noticetype varchar(255) DEFAULT NULL COMMENT 'notice type',
	// noticepicture varchar(255) DEFAULT NULL COMMENT 'notice image',
	// noticecontent varchar(255) DEFAULT NULL COMMENT 'notice content',
	// noticelasttime datetime DEFAULT NULL COMMENT 'notice's last modification time, release time',
	// noticelastmodifier int(11) DEFAULT NULL COMMENT 'notice's last operator, operator.',

	private int noticeid;
	private String noticetitle;
	private String noticetype;
	private String noticepicture;
	private String noticecontent;
	private Date noticelasttime;
	private int noticelastmodifier;

	public Notice() {
		// TODO Auto-generated constructor stub
	}

	public Notice(int noticeid, String noticetitle, String noticetype, String noticepicture, String noticecontent,
			Date noticelasttime, int noticelastmodifier) {
		super();
		this.noticeid = noticeid;
		this.noticetitle = noticetitle;
		this.noticetype = noticetype;
		this.noticepicture = noticepicture;
		this.noticecontent = noticecontent;
		this.noticelasttime = noticelasttime;
		this.noticelastmodifier = noticelastmodifier;
	}

	public int getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(int noticeid) {
		this.noticeid = noticeid;
	}

	public String getNoticetitle() {
		return noticetitle;
	}

	public void setNoticetitle(String noticetitle) {
		this.noticetitle = noticetitle;
	}

	public String getNoticetype() {
		return noticetype;
	}

	public void setNoticetype(String noticetype) {
		this.noticetype = noticetype;
	}

	public String getNoticepicture() {
		return noticepicture;
	}

	public void setNoticepicture(String noticepicture) {
		this.noticepicture = noticepicture;
	}

	public String getNoticecontent() {
		return noticecontent;
	}

	public void setNoticecontent(String noticecontent) {
		this.noticecontent = noticecontent;
	}

	public Date getNoticelasttime() {
		return noticelasttime;
	}

	public void setNoticelasttime(Date noticelasttime) {
		this.noticelasttime = noticelasttime;
	}

	public int getNoticelastmodifier() {
		return noticelastmodifier;
	}

	public void setNoticelastmodifier(int noticelastmodifier) {
		this.noticelastmodifier = noticelastmodifier;
	}

}
