package com.whty.life.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.whty.common.action.BaseAction;
import com.whty.life.model.Music;
import com.whty.life.model.vo.MusicVo;
import com.whty.life.service.MusicService;

public class MusicAction extends BaseAction{
	private static final long serialVersionUID = 1L;

	private MusicService musicService;
	
	private final static SerializerFeature[] featuresNoNull = {
		SerializerFeature.SortField,
		SerializerFeature.DisableCircularReferenceDetect,
		SerializerFeature.WriteDateUseDateFormat
	};
	
	/**
	 * GET请求方式
	 * @throws IOException
	 */
	public void musicList() throws IOException{
		MusicVo musicVo = new MusicVo();
		if("GET".equals(getMethod())){
			musicVo.setStatus("success");
			musicVo.setMessage("");
			Map<String,Object> paramMap = new HashMap<String,Object>();
			List<Music> musicList = musicService.selectAllMusic(paramMap);
			musicVo.setData(musicList);
		}else{
			musicVo.setStatus("failed");
			musicVo.setMessage("请求方法错误");
		}
		String json= JSONObject.toJSONString(musicVo,featuresNoNull); //成功返回,null值不输出
		printWriter(getResponse(),json);
	}

	public MusicService getMusicService() {
		return musicService;
	}

	public void setMusicService(MusicService musicService) {
		this.musicService = musicService;
	}
}
