package com.crawler.abroad.mapdata.processor.impl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.crawler.abroad.mapdata.constants.MapConstants;
import com.crawler.abroad.mapdata.processor.IProcessor;

/**
 * Mapbox数据处理器 
 * Created By giser on 2019-04-23
 */
public class MapboxDataProcessImpl implements IProcessor {
	private String jsonMapData;

	public MapboxDataProcessImpl(String jsonMapData) {
		this.jsonMapData = jsonMapData;
	}

	@Override
	public Map<String, String> process() {
		return processUniqKey(jsonMapData);
	}
	
	private Map<String,String> processUniqKey(String jsonMapData) {
		System.out.println("Mapbox请求返回原始数据：" + jsonMapData);
		
		Map<String,String> resultMap = new HashMap<String,String>();
		
		resultMap.put(MapConstants.LELMapDataKey.LEL_EXTRACT_DATA_KEY, "");
		resultMap.put(MapConstants.LELMapDataKey.LEL_UNIQ_KEY, "");
		
		if(jsonMapData.isEmpty()) {
			return resultMap;
		}
		
		try {
			org.json.JSONObject result = new org.json.JSONObject(jsonMapData).getJSONObject("raw_resp");

			if ((!result.has("features")) || result.getJSONArray("features").length() == 0
					|| (!result.getJSONArray("features").getJSONObject(0).has("context"))) {
				return resultMap;
			}
			
			JSONObject parentObj = new JSONObject();
			JSONArray subContextArr = new JSONArray();
			
			JSONObject firstJSONObj = result.getJSONArray("features").getJSONObject(0);
			JSONArray ctxArray = result.getJSONArray("features").getJSONObject(0).getJSONArray("context");
			for (int index = 0; index < ctxArray.length(); index++) {
				JSONObject jsonObject = ctxArray.getJSONObject(index);
				JSONObject subJsonObj = new JSONObject();
				subJsonObj.put("id", jsonObject.getString("id"));
				subJsonObj.put("text", jsonObject.getString("text"));
				subContextArr.put(subJsonObj);
			}
			parentObj.put("context", subContextArr);
			parentObj.put("place_name", firstJSONObj.get("place_name"));
			parentObj.put("id", firstJSONObj.get("id"));
			parentObj.put("text", firstJSONObj.get("text"));
			
			StringBuilder uniqKey = new StringBuilder();
			for (String curKey : parentObj.keySet()) {
				if("context".equals(curKey)) {
					extractContext(uniqKey,parentObj);
				}else {
					uniqKey.append(parentObj.get(curKey)).append("#");
				}
			}
			uniqKey = new StringBuilder(uniqKey.substring(0, uniqKey.length()-1));
			uniqKey.append("\n");
			System.out.println(uniqKey.toString());
			
			resultMap.put(MapConstants.LELMapDataKey.LEL_EXTRACT_DATA_KEY, parentObj.toString());
			resultMap.put(MapConstants.LELMapDataKey.LEL_UNIQ_KEY, uniqKey.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return resultMap;
	}

	/**抽取context
	 * Created By giser on 2019-04-24
	 */
	private void extractContext(StringBuilder uniqKey, JSONObject parentObj) {
		JSONArray jsonArr = parentObj.getJSONArray("context");
		for(int k=0; k<jsonArr.length(); k++) {
			uniqKey.append(jsonArr.getJSONObject(k).get("id")).append("#");
			uniqKey.append(jsonArr.getJSONObject(k).get("text")).append("#");
		}
	}

}
