package com.crawler.abroad.mapdata.service;

import java.util.Map;

import com.crawler.abroad.mapdata.entity.Coordination;

/**
 * @author wb-hyg545156
 *	数据抽取
 */
public interface IMapDataExtractor {
	
	/**根据策略获取下一个坐标数据
	 * @param currentCoordination 当前坐标
	 * @param strategyType 策略
	 * @return 下一个坐标
	 */
	Coordination updateCoord(Coordination currentCoordination, int strategyType);
	
	/**利用逆地理团队基于TomTom封装的服务，判断当前坐标点是否落在预期国家内
	 * @param currentCoordiantion
	 * @return
	 */
	Boolean isTargetNation(Coordination currentCoordination);
	
	/**获取Mapbox的结果数据，每条结果都记录到raw_data.log文件中
	 * @param currentCoordination
	 * @param curDictMap 
	 * @return
	 */
	String callMapbox(Coordination currentCoordination, Map<String, String> curDictMap);
	
}
