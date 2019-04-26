package com.crawler.abroad.mapdata.constants;

/**常量
 * @author wb-hyg545156
 *
 */
public class MapConstants {
	
	/**
	 * 步长处理策略
	 */
	public interface StrategyConfig{
		int STRATEGY_CONFIG_SAME = 1;
		int STRATEGY_CONFIG_DIFF = 2;
		int STRATEGY_CONFIG_OUTER = 3;
	}
	
	/**
	 * 日志文件类型
	 */
	public interface DataStorageType{
		int RAWDATA_STORAGE = 1;
		int UNIQDATA_STORAGE = 2;
	}
	
	/**
	 * http请求超时设定
	 */
	public interface HttpConnectTimeOut{
		int CONNECT_TIMEOUT = 10000;
		int CONNECT_TIMEOUT_INCREMENT = 5000;
	}
	
	/**csv数据供应方
	 * Created By giser on 2019-04-23
	 */
	public interface LELMapDataProvider{
		int LEL_HERE = 1;
		int LEL_MAPBOX = 2;
	}
	
	/**文件保存前缀
	 * Created By giser on 2019-04-24
	 */
	public interface LELMapDataPrefix{
		String LEL_HERE_PRE = "LEL_HERE_";
		String LEL_MAPBOX_PRE = "LEL_MAPBOX_";
	}
	
	/**唯一值key和抽取的数据存放key
	 * Created By giser on 2019-04-24
	 */
	public interface LELMapDataKey{
		String LEL_UNIQ_KEY = "LEL_UNIQ_KEY";
		String LEL_EXTRACT_DATA_KEY = "LEL_EXTRACT_DATA_KEY";
	}

}
