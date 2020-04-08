package org.ht.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.ht.dao.NoticeDao;
import org.ht.pojo.Notice;
import org.ht.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Website message announcement control layer
 */
@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {

	@Resource
	private NoticeDao noticeDao;

	@Override
	public List<Notice> noticelist(String ids) {
		// TODO Auto-generated method stub
		return noticeDao.noticelist(ids);
	}

	public Notice noticeget(Integer ids) {

		return noticeDao.noticeget(ids);
	}

	// ______________________________
	@Override
	public List<Notice> noticetop5() {
		// TODO Auto-generated method stub
		return noticeDao.noticetop5();
	}

	@Override
	public List<Notice> noticetop5meiti() {
		// TODO Auto-generated method stub
		return noticeDao.noticetop5meiti();
	}

	@Override
	public List<Notice> noticetop5sy() {
		// TODO Auto-generated method stub
		return noticeDao.noticetop5sy();
	}

	// ______________________________

	@Override
	public void noticeadd(Notice notice) {
		noticeDao.noticeadd(notice);

	}

	@Override
	public void noticeupd(Notice notice) {
		noticeDao.noticeupd(notice);

	}

	@Override
	public void noticedel(Integer ids) {
		noticeDao.noticedel(ids);
	}

	@Override
	public void noticeupds(Notice notice) {
		// TODO Auto-generated method stub
		noticeDao.noticeupd(notice);

	}

	@Override
	public void noticshiji(Integer ids) {
		noticeDao.noticshiji(ids);
	}

}
