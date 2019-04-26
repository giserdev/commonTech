package com.crawler.abroad.mapdata.thread;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.StringUtils;

import com.crawler.abroad.mapdata.constants.MapConstants;
import com.crawler.abroad.mapdata.entity.GeoEncodeNode;
import com.crawler.abroad.mapdata.processor.impl.DataProcess;
import com.crawler.abroad.mapdata.processor.impl.HereDataProcessImpl;
import com.crawler.abroad.mapdata.processor.impl.MapboxDataProcessImpl;
import com.crawler.abroad.mapdata.utils.AMapFileUtils;
import com.crawler.abroad.mapdata.utils.HttpClientUtils;

/**
 * Created By giser on 2019-04-24
 */
public class ExtractMapDataThread implements Runnable {

	private List<GeoEncodeNode> searchList;
	private Integer lelMapDataProvider;
	private String  target;
	
	public ExtractMapDataThread(List<GeoEncodeNode> searchList, Integer lelMapDataProvider, String  target) {
		this.searchList = searchList;
		this.lelMapDataProvider = lelMapDataProvider;
		this.target = target;
	}

	@Override
	public void run() {
		extractMapData(searchList, lelMapDataProvider, target);
	}
	
	/**
	 * 抽取数据 
	 * Created By giser on 2019-04-23
	 * @param target 
	 * @param lelMapbox 
	 */
	private void extractMapData(List<GeoEncodeNode> searchList, int providerType, String target) {
		String requestUrl = null;
		String responseContent = null;
		String formatResponseContent = null;
		for (int index = 0; index < searchList.size(); index++) {
			requestUrl = getRequestUrl(searchList.get(index),providerType,target);
			System.out.println("requestUrl:" + requestUrl);
			responseContent = HttpClientUtils.doGetTimeOut(requestUrl,
					MapConstants.HttpConnectTimeOut.CONNECT_TIMEOUT,
					MapConstants.HttpConnectTimeOut.CONNECT_TIMEOUT_INCREMENT);

			formatResponseContent = AMapFileUtils.formatResponseContent(requestUrl, responseContent);
			
			String filePath = null;
			if(MapConstants.LELMapDataProvider.LEL_HERE == providerType) {
				filePath = AMapFileUtils.logFileMap.get(new StringBuilder(MapConstants.LELMapDataPrefix.LEL_HERE_PRE).append(target).append("_rawData").toString());
			}else if(MapConstants.LELMapDataProvider.LEL_MAPBOX == providerType) {
				filePath = AMapFileUtils.logFileMap.get(new StringBuilder(MapConstants.LELMapDataPrefix.LEL_MAPBOX_PRE).append(target).append("_rawData").toString());
			}
//			System.out.println(filePath);
			AMapFileUtils.writeToFile(new File(filePath),formatResponseContent, "UTF-8", true);
			
			//唯一化处理
			uniqRespContent(responseContent,providerType,target,requestUrl);
			
		}
		
	}

	/**获取唯一值
	 * Created By giser on 2019-04-23
	 * @param providerType 数据供应商
	 * @param target 目标国
	 * @param requestUrl 请求Path
	 */
	private String uniqRespContent(String responseContent, int providerType, String target, String requestUrl) {
		if(StringUtils.isEmpty(responseContent)) {
			return null;
		}
		
		Map<String, String> resultMap = null;//当前数据处理的唯一值结果
		String uniqLogFile = null;//日志路径
		String reqRespLogFile = null;//源数据和抓取数据日志
		String uniqTargetKey = null;//唯一值map key
		
		DataProcess processor = new DataProcess();//数据处理器
		
		if(MapConstants.LELMapDataProvider.LEL_HERE == providerType) {
			uniqLogFile = AMapFileUtils.logFileMap.get(new StringBuilder(MapConstants.LELMapDataPrefix.LEL_HERE_PRE).append(target).append("_uniqData").toString());
			reqRespLogFile = AMapFileUtils.logFileMap.get(new StringBuilder(MapConstants.LELMapDataPrefix.LEL_HERE_PRE).append(target).append("_reqRespData").toString());
			uniqTargetKey = MapConstants.LELMapDataProvider.LEL_HERE + "#" + target;
			resultMap = processor.process(new HereDataProcessImpl(responseContent));
		}else if(MapConstants.LELMapDataProvider.LEL_MAPBOX == providerType){
			uniqLogFile = AMapFileUtils.logFileMap.get(new StringBuilder(MapConstants.LELMapDataPrefix.LEL_MAPBOX_PRE).append(target).append("_uniqData").toString());
			reqRespLogFile = AMapFileUtils.logFileMap.get(new StringBuilder(MapConstants.LELMapDataPrefix.LEL_MAPBOX_PRE).append(target).append("_reqRespData").toString());
			uniqTargetKey = MapConstants.LELMapDataProvider.LEL_MAPBOX + "#" + target;
			resultMap = processor.process(new MapboxDataProcessImpl(responseContent));
		}
		
		String uniqKey = resultMap.get(MapConstants.LELMapDataKey.LEL_UNIQ_KEY);//唯一值
		if(AMapFileUtils.uniqValueMap.containsKey(uniqTargetKey)) {
			if(!AMapFileUtils.uniqValueMap.get(uniqTargetKey).containsKey(uniqKey)) {
				AMapFileUtils.uniqValueMap.get(uniqTargetKey).put(uniqKey, uniqKey);
			}
		}else {
			AMapFileUtils.uniqValueMap.put(uniqTargetKey, new HashMap<String,String>());
			AMapFileUtils.uniqValueMap.get(uniqTargetKey).put(uniqKey, uniqKey);
		}
		
		AMapFileUtils.writeToFile(new File(uniqLogFile), new StringBuilder(uniqKey), "UTF-8", true);//将唯一值写入文件
		
		//将源数据Path###抓取的数据存到文件中
		String crawlerData = resultMap.get(MapConstants.LELMapDataKey.LEL_EXTRACT_DATA_KEY);
		if(!StringUtils.isEmpty(crawlerData)) {
			AMapFileUtils.writeToFile(new File(reqRespLogFile), new StringBuilder(requestUrl).append("###").append(crawlerData).append("\n"), "UTF-8", true);
		}
		
		return uniqKey;
	}

	/**获取请求url
	 * Created By giser on 2019-04-23
	 */
	private String getRequestUrl(GeoEncodeNode geoEncodeNode, int providerType, String target) {
		StringBuilder requestUrl = new StringBuilder();
		StringBuilder params = new StringBuilder();
		if(MapConstants.LELMapDataProvider.LEL_HERE == providerType) {
			requestUrl.append(AMapFileUtils.HERE_URL_PREFIX);
			requestUrl.append("searchtext=");
			params.append(geoEncodeNode.getCountryName());
			params.append(",");
			
			if(target.equals("ประเทศไทย_ Thailand")){
				//只取泰文作为输入
				String[] fourAddr = geoEncodeNode.getNodePath().split(",");
				for (int i = 0; i < fourAddr.length; i++) {
					params.append(fourAddr[i].split("//")[0]);
				}
			}else {
				params.append(geoEncodeNode.getNodePath());
			}
			
			try {
				requestUrl.append(URLEncoder.encode(params.toString(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			requestUrl.append(AMapFileUtils.HERE_URL_POST);
		}else if(MapConstants.LELMapDataProvider.LEL_MAPBOX == providerType){
			requestUrl.append(AMapFileUtils.MAPBOX_URL_PREFIX);
			
			requestUrl.append("country=");
			switch(target) {
				case "Viet Nam":
					requestUrl.append("vn");
					break;
				case "Indonesia":
					requestUrl.append("id");
					break;
				case "Malaysia":
					requestUrl.append("my");
					break;
				case "Philippines":
					requestUrl.append("ph");
					break;
				case "Singapore":
					requestUrl.append("sg");
					break;
				case "ประเทศไทย_ Thailand":
					requestUrl.append("th");
					break;
				default:
					requestUrl.append("en");
			}
			
			requestUrl.append("&address=");
			
			params.append(target).append(",");
			if(target.equals("Singapore")) {
				String[] fourAddr = geoEncodeNode.getNodePath().split(",");
				params.append(fourAddr[2]).append(",").append(fourAddr[3]);
			}else if(target.equals("ประเทศไทย_ Thailand")){
				//只取泰文作为输入
				String[] fourAddr = geoEncodeNode.getNodePath().split(",");
				for (int i = 0; i < fourAddr.length; i++) {
					params.append(fourAddr[i].split("/")[0]).append(",");
				}
			}else {
				params.append(geoEncodeNode.getNodePath());
			}
			
			try {
				requestUrl.append(URLEncoder.encode(params.toString(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			requestUrl.append("&language=");
			switch(target) {
				case "Viet Nam":
					requestUrl.append("vi");
					break;
				case "Indonesia":
					requestUrl.append("en");
					break;
				case "Malaysia":
					requestUrl.append("ms");
					break;
				case "Philippines":
					requestUrl.append("en");
					break;
				case "Singapore":
					requestUrl.append("en");
					break;
				case "ประเทศไทย_ Thailand":
					requestUrl.append("th");
					break;
				default:
					requestUrl.append("en");
			}
			
			requestUrl.append(AMapFileUtils.MAPBOX_URL_POST);
			
		}
		
		return requestUrl.toString();
	}

}
