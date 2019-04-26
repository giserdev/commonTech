package com.crawler.abroad.mapdata.main;

import java.util.HashMap;
import java.util.Map;

import com.crawler.abroad.mapdata.constants.MapConstants;
import com.crawler.abroad.mapdata.entity.Coordination;
import com.crawler.abroad.mapdata.entity.CoordinationConfig;
import com.crawler.abroad.mapdata.service.IMapDataExtractor;
import com.crawler.abroad.mapdata.service.impl.MapDataExtractorImpl;

public class MapDataExtractMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CoordinationConfig coordinationConfig = new CoordinationConfig();
		Coordination preCoordination = new Coordination(coordinationConfig.getStartCoordLng(), coordinationConfig.getStartCoordLat());
		Coordination currentCoordination = new Coordination(preCoordination,coordinationConfig.getStartCoordLng(),coordinationConfig.getStartCoordLat(),coordinationConfig);
		
		Integer quota = coordinationConfig.getQuota();
//		四级地址数据，已去重
		Map<String, String> curDictMap = new HashMap<String, String>();

		IMapDataExtractor mapDataExtractor = new MapDataExtractorImpl();
		
		while (currentCoordination.getLatitude() < coordinationConfig.getMaxStep() && 0 < quota) {
			System.out.println("当前第" + quota + "次获取数据");
			if (!mapDataExtractor.isTargetNation(currentCoordination)) {
				currentCoordination = mapDataExtractor.updateCoord(currentCoordination,
						MapConstants.StrategyConfig.STRATEGY_CONFIG_OUTER);
			} else {
				--quota;
				String curContent = mapDataExtractor.callMapbox(currentCoordination, curDictMap);
				if (currentCoordination.getCoordinationConfig().getPreContent().equals(curContent)) {
					currentCoordination = mapDataExtractor.updateCoord(currentCoordination,
							MapConstants.StrategyConfig.STRATEGY_CONFIG_SAME);
				} else {
					currentCoordination = mapDataExtractor.updateCoord(currentCoordination,
							MapConstants.StrategyConfig.STRATEGY_CONFIG_DIFF);
					if(curContent != null) {
						currentCoordination.getCoordinationConfig().setPreContent(curContent);
					}
				}
			}
		}
	}

}
