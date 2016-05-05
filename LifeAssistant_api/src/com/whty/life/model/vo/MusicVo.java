package com.whty.life.model.vo;

import java.util.List;

import com.whty.life.model.Music;

/**
 * Music response返回体结构
 * @author zhangming
 */
public class MusicVo extends BaseEntity{
	private List<Music> data; //json数据

	public List<Music> getData() {
		return data;
	}

	public void setData(List<Music> data) {
		this.data = data;
	}
}
