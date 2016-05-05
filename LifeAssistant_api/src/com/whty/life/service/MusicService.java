package com.whty.life.service;

import java.util.List;
import java.util.Map;

import com.whty.life.model.Music;

public interface MusicService {
	public List<Music> selectAllMusic(Map<String, Object> paramMap);
}
