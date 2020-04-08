package org.ht.dao;

import java.util.List;

import org.ht.pojo.Notice;

/**
 * Website News Notice Module
 */
public interface NoticeDao {
	/**
	 * Query website notification form list
	 * 
	 * @return
	 */
	public List<Notice> noticelist(String ids);

	/**
	 * Query website notification form Notification details
	 * 
	 * @return
	 */
	public Notice noticeget(Integer ids);

	/**
	 * Add website notification form Notification details
	 * 
	 * @return
	 */
	public void noticeadd(Notice notice);

	/**
	 * Modify website notification form Notification details
	 * 
	 * @return
	 */
	public void noticeupd(Notice notice);
	
	
	public void noticeupds(Notice notice);

	public void noticshiji(Integer ids);
	/**
	 * Modify website notification form Notification details
	 * 
	 * @return
	 */
	public void noticedel(Integer ids);
	
	
	public List<Notice> noticetop5();
	
	public List<Notice> noticetop5meiti();
	
	public List<Notice> noticetop5sy();

}
