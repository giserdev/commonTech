package com.crawler.abroad.mapdata.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.crawler.abroad.mapdata.constants.MapConstants;
import com.crawler.abroad.mapdata.entity.GeoEncodeNode;

/**文件操作工具类
 * Created By giser on 2019-04-23
 */
public class AMapFileUtils {
	
	public static String HERE_URL_PREFIX = PropertyUtils.getStringByKey("herePrefix"); 
	public static String HERE_URL_POST = PropertyUtils.getStringByKey("herePostfix"); 
	public static String MAPBOX_URL_PREFIX = PropertyUtils.getStringByKey("mapBoxPre"); 
	public static String MAPBOX_URL_POST = PropertyUtils.getStringByKey("mapBoxPost"); 
	
	//用于存储日志文件路径
	public static Map<String, String> logFileMap = new HashMap<String,String>();
	//唯一值
	public static final Map<String, Map<String,String>> uniqValueMap = new HashMap<String, Map<String,String>>();
	
	/**将数据写入文件
	 * @param file  文件
	 * @param data  写入的数据
	 * @param encoding 编码格式
	 * @param append 写入模式 true追加  false覆盖
	 * Created By giser on 2019-04-23
	 */
	public static void writeToFile(File file, CharSequence data, String encoding, boolean append){
		try {
			FileUtils.write(file, data, encoding, append);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**获取写入文件
	 * @param basePath 文件根路径
	 * @param target 目标国
	 * @param suffix 文件后缀名
	 * Created By giser on 2019-04-23
	 */
	public static String getResultFile(String basePath, String target, String suffix) {
		return new StringBuilder(basePath).append(target).append(System.currentTimeMillis()).append(suffix).toString();
	}
	
	/**格式化结果
	 * @param serviceUrl 请求路径
	 * @param responseContent 写入的响应数据
	 * Created By giser on 2019-04-23
	 */
	public static String formatResponseContent(String serviceUrl, String responseContent) {
		return new StringBuilder(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
				.append(".000###RAWREQ:").append(serviceUrl).append("###RAWRESP:").append(responseContent).append("\n")
				.toString();
	}
	
	/**创建日志文件
	 * @param 读取的原始数据
	 * Created By giser on 2019-04-24
	 */
	public static void initLogFileMap(Map<String, List<GeoEncodeNode>> map) {
		Set<String> keySet = map.keySet();
		
		Iterator<String> iterator = keySet.iterator();
		String target = null;
		while(iterator.hasNext()) {
			target = iterator.next();
			//设定数据保存路径
			AMapFileUtils.logFileMap.put(new StringBuilder(MapConstants.LELMapDataPrefix.LEL_HERE_PRE).append(target).append("_rawData").toString(), new StringBuilder("./result/Here_result/rawData_Mapbox_").append(MapConstants.LELMapDataPrefix.LEL_HERE_PRE).append(target).append(System.currentTimeMillis()).append(".log").toString());
			AMapFileUtils.logFileMap.put(new StringBuilder(MapConstants.LELMapDataPrefix.LEL_HERE_PRE).append(target).append("_uniqData").toString(), new StringBuilder("./result/Here_result/uniqData_Mapbox_").append(MapConstants.LELMapDataPrefix.LEL_HERE_PRE).append(target).append(System.currentTimeMillis()).append(".log").toString());
			AMapFileUtils.logFileMap.put(new StringBuilder(MapConstants.LELMapDataPrefix.LEL_HERE_PRE).append(target).append("_reqRespData").toString(), new StringBuilder("./result/Here_result/reqRespData_Mapbox_").append(MapConstants.LELMapDataPrefix.LEL_HERE_PRE).append(target).append(System.currentTimeMillis()).append(".log").toString());
			
			AMapFileUtils.logFileMap.put(new StringBuilder(MapConstants.LELMapDataPrefix.LEL_MAPBOX_PRE).append(target).append("_rawData").toString(), new StringBuilder("./result/Mapbox_result/rawData_Mapbox_").append(MapConstants.LELMapDataPrefix.LEL_MAPBOX_PRE).append(target).append(System.currentTimeMillis()).append(".log").toString());
			AMapFileUtils.logFileMap.put(new StringBuilder(MapConstants.LELMapDataPrefix.LEL_MAPBOX_PRE).append(target).append("_uniqData").toString(), new StringBuilder("./result/Mapbox_result/uniqData_Mapbox_").append(MapConstants.LELMapDataPrefix.LEL_MAPBOX_PRE).append(target).append(System.currentTimeMillis()).append(".log").toString());
			AMapFileUtils.logFileMap.put(new StringBuilder(MapConstants.LELMapDataPrefix.LEL_MAPBOX_PRE).append(target).append("_reqRespData").toString(), new StringBuilder("./result/Mapbox_result/reqRespData_Mapbox_").append(MapConstants.LELMapDataPrefix.LEL_MAPBOX_PRE).append(target).append(System.currentTimeMillis()).append(".log").toString());
		}
		
	}
}
