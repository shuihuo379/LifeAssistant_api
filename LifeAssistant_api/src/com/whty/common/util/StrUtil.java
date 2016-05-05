package com.whty.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {

	/**
	 * 判断对象是否为空
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNotNull(Object o) {
		if (o == null) {
			return false;
		} else if (o instanceof String && "".equals(((String) o).trim())) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 获得主键字符�?
	 * @return
	 */
	public static String getPKID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	/**
	 * 验证是否包含中文
	 * @param str
	 * @return
	 */
	public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
	
	/**
	 *  @category 时间转字符串 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String DateToString(Date date,String format){
		if(date==null){date = new Date();}
		if(format==null||format.equals("")){format="yyyy-MM-dd HH:mm:ss";}
		SimpleDateFormat dateformater = new SimpleDateFormat(format);
        String value = dateformater.format(date);
        return value;
	}
	/**
	 * @category 验证是否 中文
	 * @param cn
	 * @return
	 */
	public static boolean isChina(String cn){
			if(cn==null){
				return false;
			}
	        String chinese = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$"; 
	        return cn.matches(chinese);
	      
	}
	/**
	 *  @category 验证是否数字
	 * @param num
	 * @return
	 */
	public static boolean isNumber(String num){
		if(num==null){
			return false;
		}
		else{
			return num.matches("^([1-9]\\d*)|(0)$");
		}
	}
}
