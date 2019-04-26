package com.crawler.abroad.mapdata.processor.impl;

import java.util.Map;

import com.crawler.abroad.mapdata.processor.IProcessor;

/**map数据处理类
 * Created By giser on 2019-04-24
 */
public class DataProcess {
	
	public Map<String,String> process(IProcessor processor) {
		return processor.process();
	}
	
}
