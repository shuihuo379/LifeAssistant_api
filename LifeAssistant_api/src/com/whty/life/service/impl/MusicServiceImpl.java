package com.whty.life.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.whty.common.dao.SqlDao;
import com.whty.life.model.Music;
import com.whty.life.service.MusicService;

public class MusicServiceImpl implements MusicService{
	private static Logger logger = Logger.getLogger(MusicServiceImpl.class);
	private SqlDao sqlDao;
	
	@Override
	public List<Music> selectAllMusic(Map<String, Object> paramMap) {
		logger.info("执行语句：whty.life.selectMusic");
		return sqlDao.list("whty.life.selectMusic",paramMap);
	}

	public SqlDao getSqlDao() {
		return sqlDao;
	}

	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}
}
