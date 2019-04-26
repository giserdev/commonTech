package com.crawler.abroad.mapdata.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.crawler.abroad.mapdata.constants.MapConstants;
import com.crawler.abroad.mapdata.entity.GeoEncodeNode;
import com.crawler.abroad.mapdata.service.ICSVMapDataExtractor;
import com.crawler.abroad.mapdata.thread.ExtractMapDataThread;
import com.crawler.abroad.mapdata.thread.ThreadContainer;
import com.crawler.abroad.mapdata.utils.AMapFileUtils;
import com.crawler.abroad.mapdata.utils.CsvReaderUtils;

public class CSVMapDataExtractorImpl implements ICSVMapDataExtractor {

	@Override
	public Map<String, List<GeoEncodeNode>> readFile(Collection<File> fileList) {
		Map<String, List<GeoEncodeNode>> map = new HashMap<String, List<GeoEncodeNode>>();
		map.clear();

		try {
			String content = null;
			for (File file : fileList) {
				content = FileUtils.readFileToString(file, "UTF-8");
				// 解析存入map中，忽略首行标题
				CsvReaderUtils.convertToMap(map, content, file.getName(), true);

				// 移动到备份路径，避免重复扫描
				FileUtils.copyFileToDirectory(file,new File(new StringBuilder(CsvReaderUtils.CSV_FILE_PATH_BAK).toString()));
				if(file.exists()) {
					FileUtils.forceDelete(file);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		AMapFileUtils.initLogFileMap(map);

		return map;
	}

	@Override
	public void extractCSVMapData(Map<String, List<GeoEncodeNode>> csvMapData) {
		Set<String> county = csvMapData.keySet();

		Iterator<String> iterator = county.iterator();
		String searchText = null;
		while (iterator.hasNext()) {
			searchText = iterator.next();// 国家名
			List<GeoEncodeNode> searchList = csvMapData.get(searchText);
			String target = searchList.get(0).getCountryName();
			
			//获取Here数据
			if(ThreadContainer.canExecute()) {
				ThreadContainer.execute(new ExtractMapDataThread(searchList, MapConstants.LELMapDataProvider.LEL_HERE, target));
			}
			
			//获取Mapbox数据
			if(ThreadContainer.canExecute()) {
				ThreadContainer.execute(new ExtractMapDataThread(searchList, MapConstants.LELMapDataProvider.LEL_MAPBOX, target));
			}

		}

	}

}
