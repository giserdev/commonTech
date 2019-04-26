package com.crawler.abroad.mapdata.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author wb-hyg545156
 *
 */
public class PropertyUtils {
	private static Properties properties = new Properties();

	public static Map<String, String> PROPERTY_MAP = new HashMap<String, String>();

	static {
		InputStream resourceAsStream = PropertyUtils.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			properties.load(resourceAsStream);
			initPropMap(PROPERTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取配置信息
	 * 
	 * @param propKey
	 * @return
	 */
	public static String getStringByKey(String propKey, String defaultValue) {
		if (properties.containsKey(propKey)) {
			return properties.get(propKey).toString();
		}
		return defaultValue;
	}

	public static String getStringByKey(String propKey) {
		if (properties.containsKey(propKey)) {
			return properties.get(propKey).toString();
		}
		return null;
	}

	private static void initPropMap(Map<String, String> pROPERTY_MAP2) {
		Enumeration<Object> enumKeys = properties.keys();

		String curKey = null;
		while (enumKeys.hasMoreElements()) {
			curKey = enumKeys.nextElement().toString();
			PROPERTY_MAP.put(curKey, properties.getProperty(curKey));
		}
	}

}
