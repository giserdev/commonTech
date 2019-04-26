package com.crawler.abroad.mapdata.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import com.crawler.abroad.mapdata.constants.MapConstants;
import com.crawler.abroad.mapdata.entity.Coordination;
import com.crawler.abroad.mapdata.service.IMapDataExtractor;
import com.crawler.abroad.mapdata.utils.AMapFileUtils;
import com.crawler.abroad.mapdata.utils.HttpClientUtils;
import com.crawler.abroad.mapdata.utils.PropertyUtils;

public class MapDataExtractorImpl implements IMapDataExtractor {

	@Override
	public Coordination updateCoord(Coordination currentCoordination, int strategyType) {

		if (MapConstants.StrategyConfig.STRATEGY_CONFIG_SAME == strategyType) {
			// 当前点与上一点一样
			currentCoordination.getCoordinationConfig().setCurrentStep(currentCoordination.getCoordinationConfig().getCurrentStep() * 2);// 步长加倍

			if (currentCoordination.getCoordinationConfig().getCurrentStep() > currentCoordination
					.getCoordinationConfig().getInitMaxStep()) {
				currentCoordination.getCoordinationConfig().setCurrentStep(currentCoordination.getCoordinationConfig().getInitMaxStep());
			}

			// 调整上一步记录
			currentCoordination.getPreCoordination().setLongitude(currentCoordination.getLongitude());
			currentCoordination.getPreCoordination().setLatitude(currentCoordination.getLatitude());

		} else if (MapConstants.StrategyConfig.STRATEGY_CONFIG_DIFF == strategyType) {
			// 当前点与上一点不同，回退至上一步，步长缩至最小
			currentCoordination.setLongitude(currentCoordination.getPreCoordination().getLongitude());
			currentCoordination.setLatitude(currentCoordination.getPreCoordination().getLatitude());
			currentCoordination.getCoordinationConfig().setCurrentStep(currentCoordination.getCoordinationConfig().getInitMinStep());// 最小步长
		} else {
			// 如果当前点落在目标国以外，使用最大步长，同时设置preCoord
			currentCoordination.getCoordinationConfig().setCurrentStep(currentCoordination.getCoordinationConfig().getInitMaxStep());// 最大步长
			currentCoordination.getPreCoordination().setLongitude(currentCoordination.getLongitude() + currentCoordination.getCoordinationConfig().getInitMaxStep());
		}
		
		//使用当前步长更新当前纬度坐标
		currentCoordination.setLongitude(currentCoordination.getLongitude() + currentCoordination.getCoordinationConfig().getCurrentStep());

		// 判断坐标是否越界
		if (currentCoordination.getLongitude() >= currentCoordination.getCoordinationConfig().getMaxLng()) {
			currentCoordination.setLongitude(currentCoordination.getCoordinationConfig().getMinLng());
			currentCoordination.setLatitude(currentCoordination.getLatitude() + currentCoordination.getCoordinationConfig().getConstLatStep());
			currentCoordination.getPreCoordination().setLatitude(currentCoordination.getLatitude());
			currentCoordination.getPreCoordination().setLongitude(currentCoordination.getLongitude());
		}

		System.out.println("pre：" + currentCoordination.getPreCoordination());
		System.out.println("cur：" + currentCoordination);
		return currentCoordination;
	}

	@Override
	public Boolean isTargetNation(Coordination currentCoordination) {
		String serviceUrl = PropertyUtils.getStringByKey("TomTomPreUrl");

		Map<String, String> param = new HashMap<String, String>();
		param.put("x", currentCoordination.getLongitude().toString());
		param.put("y", currentCoordination.getLatitude().toString());

		String respStr = HttpClientUtils.doPost(serviceUrl, param);
		
		if(!StringUtils.isEmpty(respStr) && respStr.contains("district")){
			org.json.JSONObject xmlJSONObj = XML.toJSONObject(respStr);
			if (xmlJSONObj != null && xmlJSONObj.has("Result")) {
				xmlJSONObj = xmlJSONObj.getJSONObject("Result");
				if (xmlJSONObj != null && xmlJSONObj.has("district")) {
					if (xmlJSONObj.getJSONObject("district").has("level_0")) {
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public String callMapbox(Coordination currentCoordination, Map<String, String> curDictMap) {
		String serviceUrl = new StringBuilder(PropertyUtils.getStringByKey("MapboxPrefix"))
				.append(currentCoordination.getLongitude()).append(",").append(currentCoordination.getLatitude())
				.append(PropertyUtils.getStringByKey("MapboxPostfix")).toString();

//		String respStr = HttpClientUtils.doGet(serviceUrl);
		String respStr = HttpClientUtils.doGetTimeOut(serviceUrl, MapConstants.HttpConnectTimeOut.CONNECT_TIMEOUT, MapConstants.HttpConnectTimeOut.CONNECT_TIMEOUT_INCREMENT);
		
//		System.out.println("serviceUrl: " + serviceUrl);
//		System.out.println("callMapbox:" + respStr);

//		String respContents = new StringBuilder(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
//				.append(".000###RAWREQ:").append(serviceUrl).append("###RAWRESP:").append(respStr).append("\n")
//				.toString();
		
		writeToFile(currentCoordination, AMapFileUtils.formatResponseContent(serviceUrl, respStr), 1);
		
		return logUniqAddr(currentCoordination, respStr, curDictMap);
	}

	/**
	 * 将数据写入文件
	 * @param currentCoordination 当前坐标
	 * @param destData 写入的数据
	 * @param dataType 数据类型
	 */
	private static void writeToFile(Coordination currentCoordination, String destData, int dataType) {
		// 将数据写入文件
		String storageFile = null;
		if(MapConstants.DataStorageType.UNIQDATA_STORAGE == dataType) {
			storageFile = currentCoordination.getCoordinationConfig().getUniqDataStorageFile();
		}else if(MapConstants.DataStorageType.RAWDATA_STORAGE == dataType) {
			storageFile = currentCoordination.getCoordinationConfig().getRawDataStorageFile();
		}
		AMapFileUtils.writeToFile(new File(storageFile), destData, "UTF-8", true);
	}

	/**将四级地址去重处理
	 * @param currentCoordination 当前坐标
	 * @param respStr 抽取数据
	 * @param curDictMap 当前已抽取数据字典
	 * @return
	 */
	private String logUniqAddr(Coordination currentCoordination, String respStr, Map<String, String> curDictMap) {
		if(StringUtils.isEmpty(respStr)) {
			return null;
		}
		
		org.json.JSONObject result = new org.json.JSONObject(respStr);

		if ((!result.has("features")) || result.getJSONArray("features").length() == 0
				|| (!result.getJSONArray("features").getJSONObject(0).has("context"))
				|| result.getJSONArray("features").getJSONObject(0).getJSONArray("context").length() == 0) {
			return null;
		}

		// 记录去重地址
		Map<String, String> dictMap = new HashMap<String, String>();
		org.json.JSONArray featuresArray = result.getJSONArray("features");

		org.json.JSONArray contextArray = featuresArray.getJSONObject(0).getJSONArray("context");
		org.json.JSONObject firstJSONObj = featuresArray.getJSONObject(0);
		// 优先判断context外部是否有postcode信息，否则从context中抽取
		if (firstJSONObj.has("id") && firstJSONObj.getString("id").contains("postcode")) {
			dictMap.put("postalcode", firstJSONObj.getString("text"));
			dictMap.put("postalId", firstJSONObj.getString("id"));
		} else {
			// 从context中抽取postcode
			extractPostcode(contextArray, dictMap);
		}

		if (!dictMap.containsKey("postalcode")) {
			dictMap.put("postalcode", "-");
			dictMap.put("postalId", "-");
		}

		if (firstJSONObj.has("center")) {
			// 抽取center
			extractCenter(firstJSONObj, dictMap);
		} else {
			dictMap.put("center", "-");
		}

		int contextLength = contextArray.length();
		dictMap.put("country", contextArray.getJSONObject(contextLength - 1).getString("text"));
		dictMap.put("countryAl", contextArray.getJSONObject(contextLength - 1).getString("short_code"));
		dictMap.put("countryId", contextArray.getJSONObject(contextLength - 1).getString("id"));

		if (contextLength >= 2) {
			dictMap.put("county", contextArray.getJSONObject(contextLength - 2).getString("text"));
			dictMap.put("countyId", contextArray.getJSONObject(contextLength - 2).getString("id"));

			if (contextArray.getJSONObject(contextLength - 2).has("short_code")) {
				dictMap.put("countyAl", contextArray.getJSONObject(contextLength - 2).getString("short_code"));
			} else {
				dictMap.put("countyAl", "-");
			}
		} else {
			dictMap.put("county", "-");
			dictMap.put("countyId", "-");
		}

		if (contextLength >= 3) {
			dictMap.put("city", contextArray.getJSONObject(contextLength - 3).getString("text"));
			dictMap.put("cityId", contextArray.getJSONObject(contextLength - 3).getString("id"));
		} else {
			dictMap.put("city", "-");
			dictMap.put("cityId", "-");
		}

		if (contextLength >= 4) {
			dictMap.put("district", contextArray.getJSONObject(contextLength - 4).getString("text"));
			dictMap.put("districtId", contextArray.getJSONObject(contextLength - 4).getString("id"));
		} else {
			dictMap.put("district", "-");
			dictMap.put("districtId", "-");
		}

		if (contextLength >= 5) {
			dictMap.put("subdistrict", contextArray.getJSONObject(contextLength - 5).getString("text"));
			dictMap.put("subdistrictId", contextArray.getJSONObject(contextLength - 5).getString("id"));
		} else {
			dictMap.put("subdistrict", "-");
			dictMap.put("subdistrictId", "-");
		}

		String uniqKey = new StringBuilder(dictMap.get("country")).append("#").append(dictMap.get("county")).append("#")
				.append(dictMap.get("city")).append("#").append(dictMap.get("district")).append("#")
				.append(dictMap.get("subdistrict")).append("#").append(dictMap.get("postalcode")).toString();

		if (!curDictMap.containsKey(uniqKey)) {
			curDictMap.put(uniqKey, "1");
//			writeToFile(currentCoordination, JSON.toJSONString(uniqKey + "\n"), 2);
			writeToFile(currentCoordination, uniqKey + "\n", 2);
		}

		return uniqKey;
	}

	/**抽取center
	 * @param featuresObject 待处理的features
	 * @param dictMap 字典
	 */
	private void extractCenter(JSONObject featuresObject, Map<String, String> dictMap) {
		JSONArray jsonArray = featuresObject.getJSONArray("center");
		if (jsonArray.length() != 2) {
			dictMap.put("center", "-");
		} else {
			dictMap.put("center", jsonArray.getDouble(0) + "," + jsonArray.getDouble(1));
		}
	}

	/**抽取postcode
	 * @param contextArray context数组
	 * @param dictMap 字典
	 */
	private void extractPostcode(JSONArray contextArray, Map<String, String> dictMap) {
		for (int i = 0; i < contextArray.length(); i++) {
			org.json.JSONObject jsonObj = contextArray.getJSONObject(i);
			if (jsonObj.has("id") && jsonObj.getString("id").startsWith("postcode")) {
				dictMap.put("postalcode", jsonObj.getString("text"));
				dictMap.put("postalId", jsonObj.getString("id"));
			}
		}
	}

}
