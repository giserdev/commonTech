package com.giser.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Location com.giser.common.utils
 * @Notes {读取config配置信息}
 * @author giserDev
 * @Date 2019-04-20 09:18:49
 */
public class ConfigUtils {
	private static Properties property = new Properties();
	
	static {
		InputStream is = ConfigUtils.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			property.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Notes {获取字符串}
	 * @author giserDev
	 * @Date 2019-04-20 10:10:06
	 */
	public static String getValue(String key) {
		if(property.containsKey(key)) {
			return property.getProperty(key);
		}
		return null;
	}
	
	/**
	 * @Notes {获取所有的配置信息，存入Map中}
	 * @author giserDev
	 * @Date 2019-04-20 10:09:43
	 */
	public static Map<String,String> getMapValue(){
		Map<String, String> propMap = new HashMap<String, String>();

		Enumeration<Object> keyEnum = property.keys();
		while(keyEnum.hasMoreElements()) {
			propMap.put(keyEnum.nextElement().toString(), property.getProperty(keyEnum.nextElement().toString()));
		}
		
		return propMap;
	}
	
	public static void main(String[] args) {
//		System.out.println(getValue("redis.server.host"));
//		System.out.println(getValue("redis.server.port"));
//		System.out.println(getValue("redis.server.password"));
//		System.out.println(getMapValue());
	}
	
}
