package com.crawler.abroad.mapdata.processor.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.crawler.abroad.mapdata.constants.MapConstants;
import com.crawler.abroad.mapdata.processor.IProcessor;

/**
 * Here数据处理器 
 * Created By giser on 2019-04-23
 */
public class HereDataProcessImpl implements IProcessor {
	private String jsonMapData;

	public HereDataProcessImpl(String jsonMapData) {
		this.jsonMapData = jsonMapData;
	}

	@Override
	public Map<String,String> process() {
		return processUniqKey(jsonMapData);
	}
	
	
	private Map<String,String> processUniqKey(String jsonMapData) {
		System.out.println("Here请求返回原始数据：" + jsonMapData);
		
		Map<String,String> resultMap = new HashMap<String,String>();
		
		resultMap.put(MapConstants.LELMapDataKey.LEL_EXTRACT_DATA_KEY, "");
		resultMap.put(MapConstants.LELMapDataKey.LEL_UNIQ_KEY, "");
		
		if(jsonMapData.isEmpty()) {
			return resultMap;
		}
		
		try {
			org.json.JSONObject result = new org.json.JSONObject(jsonMapData).getJSONObject("Response");

			if ((!result.has("View")) 
					|| result.getJSONArray("View").length() == 0
					|| (!result.getJSONArray("View").getJSONObject(0).has("Result"))
					|| result.getJSONArray("View").getJSONObject(0).getJSONArray("Result").length() == 0
					|| (!result.getJSONArray("View").getJSONObject(0).getJSONArray("Result").getJSONObject(0).has("Location"))
					|| (!result.getJSONArray("View").getJSONObject(0).getJSONArray("Result").getJSONObject(0).getJSONObject("Location").has("Address"))) {
				return resultMap;
			}
			
			Map<String, String> dictMap = new HashMap<String, String>();
			JSONArray resultArray = result.getJSONArray("View").getJSONObject(0).getJSONArray("Result");
			
			JSONObject addrObj = resultArray.getJSONObject(0).getJSONObject("Location").getJSONObject("Address");
			
			for (int index = 0; index < resultArray.length(); index++) {
				addrObj = resultArray.getJSONObject(index).getJSONObject("Location").getJSONObject("Address");
				
				Set<String> keySet = addrObj.keySet();
				for (String curKey : keySet) {
					if("AdditionalData".equals(curKey)) {
						continue;
					}
					dictMap.put(curKey, addrObj.getString(curKey));
				}
				
			}
			
			StringBuilder uniqKey = new StringBuilder();
			for (String curKey : dictMap.keySet()) {
				uniqKey.append(dictMap.get(curKey)).append("#");
			}
			uniqKey = new StringBuilder(uniqKey.substring(0, uniqKey.length()-1));
			uniqKey.append("\n");
			System.out.println(uniqKey.toString());
			
			resultMap.put(MapConstants.LELMapDataKey.LEL_EXTRACT_DATA_KEY, JSON.toJSONString(dictMap));
			resultMap.put(MapConstants.LELMapDataKey.LEL_UNIQ_KEY, uniqKey.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return resultMap;
	}

}
