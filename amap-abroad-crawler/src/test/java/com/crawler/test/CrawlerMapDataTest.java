package com.crawler.test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.junit.Test;

public class CrawlerMapDataTest {
	@Test
	public void test() throws IOException {
//		Collection<File> listFiles = FileUtils.listFiles(new File("D:\\datasource\\source"),
//				FileFilterUtils.suffixFileFilter(".csv"), TrueFileFilter.INSTANCE);
//		System.out.println(listFiles);
//		String[] extensions = new String[] {"txt"};
//		IOFileFilter filter = new SuffixFileFilter(extensions, IOCase.INSENSITIVE);
//		Iterator iter = FileUtils.iterateFiles(new File("D:/datasource/111/"), filter, DirectoryFileFilter.DIRECTORY);
		
		Iterator<File> iterateFiles = FileUtils.iterateFiles(new File("D:/datasource/111/"), FileFilterUtils.suffixFileFilter(".txt"), DirectoryFileFilter.DIRECTORY);
		while(iterateFiles.hasNext()) {
			File next = iterateFiles.next();
			System.out.println(next.getName());
		}
	}

}
