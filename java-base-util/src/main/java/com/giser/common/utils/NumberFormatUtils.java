package com.giser.common.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @Location com.giser.common.utils
 * @Notes {数字处理工具类}
 * @author giserDev
 * @Date 2019-04-20 10:22:37
 */
public class NumberFormatUtils {
	
	
	
	public static void main(String[] args) {
		NumberFormat decimalFormat = DecimalFormat.getInstance();
		
		System.out.println(decimalFormat.format("2.35648"));
	}
	
}
