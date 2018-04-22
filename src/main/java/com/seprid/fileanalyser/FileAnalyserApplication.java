package com.seprid.fileanalyser;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FileAnalyserApplication{

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		MainLogic logic = context.getBean("mainLogic", MainLogic.class);
	}

}
