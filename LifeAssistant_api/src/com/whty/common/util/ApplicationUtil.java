package com.whty.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取Spring的Bean工具
 * 
 */
public class ApplicationUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ApplicationUtil.applicationContext = applicationContext;  //将spring上下文对象注入进行接收
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}
	
	public static Object getBean(String name,Class<?> clz){
		return applicationContext.getBean(name, clz);
	}
}
