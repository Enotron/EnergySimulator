package org.enotron.simulator.utility;

import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarHelper {
	public static GregorianCalendar now(){
		return new GregorianCalendar();
	}
	
	public static GregorianCalendar forTimeMs(long timeMs) {
		
		GregorianCalendar gc = now();
		gc.setTime(new Date(timeMs));
		return gc;
	}
	
	public static GregorianCalendar forDate(Date date) {
		GregorianCalendar gc = now();
		gc.setTime(date);
		return gc;
	}
}
