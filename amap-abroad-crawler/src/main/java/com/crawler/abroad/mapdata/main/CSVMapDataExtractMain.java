package com.crawler.abroad.mapdata.main;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.crawler.abroad.mapdata.entity.GeoEncodeNode;
import com.crawler.abroad.mapdata.service.ICSVMapDataExtractor;
import com.crawler.abroad.mapdata.service.impl.CSVMapDataExtractorImpl;
import com.crawler.abroad.mapdata.utils.AMapFileUtils;
import com.crawler.abroad.mapdata.utils.CsvReaderUtils;

/**
 * 通过CSV文件获取坐标数据信息 
 * Created By giser on 2019-04-23
 */
public class CSVMapDataExtractMain {

	public static void main(String[] args) {
		AMapFileUtils.uniqValueMap.clear();

		ICSVMapDataExtractor csvMapDataExtrator = new CSVMapDataExtractorImpl();

		while (true) {
			// 获取路径下的所有csv文件
			Collection<File> listFiles = FileUtils.listFiles(new File(CsvReaderUtils.CSV_FILE_PATH_SOURCE),
					FileFilterUtils.suffixFileFilter(CsvReaderUtils.CSV_FILE_SUFFIX), TrueFileFilter.INSTANCE);
			
			if (!listFiles.isEmpty()) {
				Map<String, List<GeoEncodeNode>> csvMapData = csvMapDataExtrator.readFile(listFiles);
				csvMapDataExtrator.extractCSVMapData(csvMapData);
			}else {
				try {
					Thread.sleep(10000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
