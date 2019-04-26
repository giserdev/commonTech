package com.crawler.abroad.mapdata.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {

	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(), "utf-8");

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static String doGet(String url) {
		// 创建Httpclient对象
		HttpClientBuilder builder = HttpClients.custom();
		builder.setUserAgent(
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");
		final CloseableHttpClient httpClient = builder.build();

		CloseableHttpResponse response = null;

		try {
			// 创建Http Post请求
			HttpGet httpGet = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
					.setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
			httpGet.setConfig(requestConfig);
			// 执行http请求
			response = httpClient.execute(httpGet);
			String result = EntityUtils.toString(response.getEntity(), "utf-8");

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
	
	public static String doGetTimeOut(String url, int connectTimeout, int connectTimeoutIncrement) {
		HttpClientBuilder builder = HttpClients.custom();
		builder.setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");
		final CloseableHttpClient httpClient = builder.build();

		CloseableHttpResponse response = null;
		
		String result = null;
		try {
//			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
					.setConnectionRequestTimeout(connectTimeout).setSocketTimeout(connectTimeout).build();
			
			HttpGet httpGet = new HttpGet(url);
			
			httpGet.setConfig(requestConfig);
			// 执行http请求
			response = httpClient.execute(httpGet);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			result = null;
			System.out.println("Exception info:" + e.getMessage());
		} finally {
			if(response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		/**
		 * 重试机制：获取数据
		 */
		while(StringUtils.isEmpty(result)) {
			System.out.println("craw data time out , retrying...");
			connectTimeout = connectTimeout + connectTimeoutIncrement;
			doGetTimeOut(url, connectTimeout, connectTimeoutIncrement);
		}
		return result;
	}

}
