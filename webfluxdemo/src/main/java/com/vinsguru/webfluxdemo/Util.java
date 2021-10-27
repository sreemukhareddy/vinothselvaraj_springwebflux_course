package com.vinsguru.webfluxdemo;

public class Util {
	
	public static void sleep(int sec) {
		try {
			Thread.sleep(1000*sec);
		} catch (InterruptedException e) {
			
		}
	}

}
