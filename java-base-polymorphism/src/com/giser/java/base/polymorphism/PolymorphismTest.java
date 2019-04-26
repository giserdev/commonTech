package com.giser.java.base.polymorphism;

import com.giser.java.base.polymorphism.processor.DataProcessor;
import com.giser.java.base.polymorphism.processor.service.impl.BikeProcessor;
import com.giser.java.base.polymorphism.processor.service.impl.CarProcessor;

/**测试类
 * Created By giser on 2019-04-26
 */
public class PolymorphismTest {
	public static void main(String[] args) {
		DataProcessor dataProcessor = new DataProcessor();
		dataProcessor.process(new BikeProcessor());
		dataProcessor.process(new CarProcessor());
	}
}
