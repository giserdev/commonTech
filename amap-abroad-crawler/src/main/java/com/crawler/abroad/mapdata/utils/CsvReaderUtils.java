package com.crawler.abroad.mapdata.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.crawler.abroad.mapdata.entity.GeoEncodeNode;

/**
 * read initial location address 
 * Created By giser on 2019-04-22
 */
public class CsvReaderUtils {
	public static String CSV_FILE_PATH_SOURCE = null;//原始文件路径
	public static String CSV_FILE_PATH_BAK = null;//备份路径
	public static String CSV_FILE_SUFFIX = null;
	private static Integer CSV_PROCESS_LEVEL = 4;
	private static String CSV_FILE_SEPERATOR = ",";
	private static String CSV_FILE_LINE_SEPERATOR = "\\\n";

	static {
		CSV_FILE_PATH_SOURCE = PropertyUtils.PROPERTY_MAP.get("location.csv.basepath.source");
		CSV_FILE_PATH_BAK = PropertyUtils.PROPERTY_MAP.get("location.csv.basepath.bak");
		CSV_FILE_SUFFIX = PropertyUtils.PROPERTY_MAP.get("location.csv.suffix");
		CSV_PROCESS_LEVEL = Integer.valueOf(PropertyUtils.getStringByKey("location.csv.processlevel", CSV_PROCESS_LEVEL.toString()));
	}

	/**
	 * 将读取的行数据转换为实体，并存入集合容器
	 * @param map  结果存放容器
	 * @param fileName 当前处理的文件名
	 * @param ignoreFirstLine 是否忽略首行  注意第一行为标题行，舍弃存储 
	 * Created By giser on 2019-04-22
	 */
	public static void convertToMap(Map<String, List<GeoEncodeNode>> map, String readLines, String fileName, Boolean ignoreFirstLine) {
		String entryKey = fileName.substring(0,fileName.indexOf("_addresses"));
		GeoEncodeNode geoEncodeNode = null;
		List<GeoEncodeNode> list = new ArrayList<GeoEncodeNode>();

		String[] lines = readLines.split(CSV_FILE_LINE_SEPERATOR);
		String fourAddr = null;
		
		//标题行，分隔获取当前文件的原始列数
		int originColumnNum = lines[0].split(CSV_FILE_SEPERATOR).length;
		//根据当前处理的地址级别，得到处理的行分割后的数组长度
		int splitArrLength = originColumnNum + CSV_PROCESS_LEVEL - 1;
		
		// 除去首行标题
		int index = 0;
		if(ignoreFirstLine) {
			index = 1;
		}
		
		for (; index < lines.length; index++) {
			String[] cntArr = lines[index].split(CSV_FILE_SEPERATOR, splitArrLength);
			int nodeLevel = Integer.valueOf(cntArr[cntArr.length - 5]);

			// 只处理四级
			if (CSV_PROCESS_LEVEL != nodeLevel) {
				continue;
			}

			// 拼接path数据，nodeLevel有多少就需要拼接几个
			StringBuilder nodePath = new StringBuilder();
			for (int j = 4; j < CSV_PROCESS_LEVEL + nodeLevel; j++) {
				nodePath.append(cntArr[j]);
				nodePath.append(",");
			}
			fourAddr = nodePath.toString().substring(1, nodePath.toString().length() - 2);

			geoEncodeNode = new GeoEncodeNode(entryKey, cntArr[0], cntArr[1], cntArr[2], cntArr[3], fourAddr, nodeLevel,
					cntArr[9], cntArr[10], cntArr[11], cntArr[12]);

			list.add(geoEncodeNode);
		}

		map.put(entryKey, list);
	}

}
