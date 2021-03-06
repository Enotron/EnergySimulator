package org.enotron.simulator.utility;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A utility class wraps simple static methods for handling calendars
 * 
 * Waiting to use Java 8 to make this much simpler
 * 
 * @author scondon
 * @copyright 2015 Enotron Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 */
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
