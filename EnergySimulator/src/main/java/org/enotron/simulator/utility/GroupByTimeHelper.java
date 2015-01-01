package org.enotron.simulator.utility;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.enotron.simulator.api.GroupByTime;

/**
 * Complex mechanism for grouping raw time series data by time unit
 * on the fly - see the unit test for an example of how this works
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
public class GroupByTimeHelper {
	protected static Logger _logger = Logger.getLogger(GroupByTimeHelper.class.getName());

	private GroupByTimeHelper() {
		//Prevent instantiation
	}
	
	public static Map<Long, GroupedValues> groupValuesByTime(
			final double[] values,
			final long startTimeMs,
			final long durationMs,
			final GroupByTime groupByTime) {
		
		//First partition up the times
		long nominalPeriodStartMs = 
			startTimeMs - 
			startTimeMs%groupByTime.getMilliseconds();
		
		//Round up if necessary
		int numberGroupedPeriods = 
				(int) Math.ceil(
						((double)values.length * durationMs + startTimeMs - nominalPeriodStartMs)
						/groupByTime.getMilliseconds());
		
		//Round if necessary
		final int groupSize = (int) (groupByTime.getMilliseconds() / durationMs);

		final int firstPeriodOffset = (int) ((startTimeMs-nominalPeriodStartMs)/durationMs);
		
		final int lastPeriodLength = (values.length + firstPeriodOffset) % groupSize;

		_logger.info("Grouping measurements by time:" +
				"\nValue Count:\t" + values.length +
				"\nGroup By:\t" + groupByTime.name() +
				"\nPeriod(sec):\t" + durationMs/1000l +
				"\nNumber Groups:\t" + numberGroupedPeriods +
				"\nGroup size:\t" + groupSize +
				"\nQuery Start:\t" + new Date(startTimeMs) +
				"\nNominal Start:\t" + new Date(nominalPeriodStartMs) +
				"\nFP Offset:\t" + firstPeriodOffset +
				"\nLP Length:\t" + lastPeriodLength);
		
		Map<Long, GroupedValues> groupedValuesMap = new HashMap<Long,GroupedValues>();
		for (int i=0;i<numberGroupedPeriods;i++) {
			long nominalGroupedValueStartMs = 
					nominalPeriodStartMs + 
					groupByTime.getMilliseconds() * i;
			
			GroupedValues groupedValues = null;
			
			int lastPeriodTruncate = groupSize;
			if (i == numberGroupedPeriods-1 && lastPeriodLength != 0) {
				lastPeriodTruncate = lastPeriodLength;
			}

			double[] subset;
			int startIndex, endIndex;
			long actualStartTimeMs;
			if (i == 0 && nominalPeriodStartMs < startTimeMs) {
				startIndex = i*groupSize;
				endIndex = i*groupSize+groupSize - firstPeriodOffset;
				actualStartTimeMs = startTimeMs;
			} else {
				startIndex = i*groupSize-firstPeriodOffset;
				endIndex = i*groupSize+groupSize - firstPeriodOffset;
				actualStartTimeMs = nominalGroupedValueStartMs;
			}
			
			if (lastPeriodTruncate < endIndex-startIndex) {
				endIndex = startIndex+lastPeriodTruncate;
			}
			subset = Arrays.copyOfRange(values, startIndex, endIndex);
			groupedValues = new GroupedValues(subset);
			groupedValues.setActualStart(actualStartTimeMs);
			

			_logger.info("Adding grouped value " + i +
					"\n\tRange:\t[" + String.valueOf((i*groupSize-firstPeriodOffset)<0?0:(i*groupSize-firstPeriodOffset)) + 
						"-" + String.valueOf(i*groupSize+groupSize-firstPeriodOffset) + "]" +
					"\n\tSubset Len:\t" + subset.length +
					"\n\tStart:\t" + new Date(nominalGroupedValueStartMs));

			groupedValues.setNumberOfPeriods(subset.length);
			groupedValues.setDurationMs(subset.length * groupByTime.getMilliseconds());
			
			groupedValuesMap.put(
					nominalGroupedValueStartMs, 
					groupedValues);
		}
		
		
		return groupedValuesMap;
		
	}

}
