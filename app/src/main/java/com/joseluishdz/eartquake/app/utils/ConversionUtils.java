package com.joseluishdz.eartquake.app.utils;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;

import java.util.Calendar;

@SuppressLint("SimpleDateFormat")
public class ConversionUtils {
	public static String getCompleteDateFromTimestamp(long timeStamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeStamp);
		String date = DateFormat.format("yyyy-MM-dd h:mm a", cal).toString();
		return date;
	}

	public static String getHour(long timeStamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeStamp);
		String date = DateFormat.format("h:mm a", cal).toString();
		return date;
	}
}
