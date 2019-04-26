package com.giser.java.base.polymorphism.processor;

import com.giser.java.base.polymorphism.processor.service.IProcessor;

public class DataProcessor {
	public void process(IProcessor processor) {
		processor.process();
	}
}
