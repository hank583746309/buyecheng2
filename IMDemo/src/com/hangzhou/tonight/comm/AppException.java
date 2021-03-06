package com.hangzhou.tonight.comm;

import android.util.Log;

import com.hangzhou.tonight.util.SLog;


public class AppException implements Thread.UncaughtExceptionHandler {
	
	private String tag = "AppException";
	   
	private static AppException instance;
	private AppException(){}
	public static AppException getInstance(){
		if(null == instance)
			instance = new AppException();
		return instance;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		SLog.e(tag, Log.getStackTraceString(ex));
		android.os.Process.killProcess(android.os.Process.myPid());
//		System.exit(1);
	}

}
