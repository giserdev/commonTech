package com.crawler.abroad.mapdata.service;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.crawler.abroad.mapdata.entity.GeoEncodeNode;

/**通过csv文件获取坐标数据
 * Created By giser on 2019-04-23
 */
public interface ICSVMapDataExtractor {
	
	/**读取特定路径下的特定类型的文件数据
	 * @param fileList  文件集合
	 * 
	 * Created By giser on 2019-04-23
	 */
	public Map<String, List<GeoEncodeNode>> readFile(Collection<File> fileList);

	/**抽取数据
	 * @param csvMapData 读取的特定级别的数据
	 * Created By giser on 2019-04-23
	 */
	public void extractCSVMapData(Map<String, List<GeoEncodeNode>> csvMapData);
	
}
