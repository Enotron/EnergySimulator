package org.enotron.simulator.utility;

import java.util.Random;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.enotron.simulator.api.SimulatorException;
import org.enotron.simulator.api.ValuesMode;

/**
 * A complex class for processing the calculation of raw values
 * in to the statistical summary requested.
 * 
 * See the corresponding Unit test for an example of how this works
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
public class ValuesModeHelper {

	private static StandardDeviation sdEvaluator = new StandardDeviation();
	private static Random rand = new Random();

	
	public static double[] getProcessedValues(final ValuesMode valuesMode, 
			final double[] values) throws SimulatorException {
		
		if (valuesMode.equals(ValuesMode.AVERAGE)) {
			return getAverageOfValues(values);
			
		} else if (valuesMode.equals(ValuesMode.SAMPLES)) {
			return getSamples(values);
			
		} else if (valuesMode.equals(ValuesMode.SINGLE)) {
			return getSingle(values);
			
		} else if (valuesMode.equals(ValuesMode.MIN_MEAN_MAX_STDDEV)) {
			return getStatsOfValues(values);
			
		} else {
			throw new SimulatorException(
					"Invalid option for ValuesMode" + valuesMode, 128);
		}
		
	}
	
	
	private static double[] getAverageOfValues(double[] values) {
		if (values == null || values.length == 0) {
			return new double[]{0.0f};
		}
		
		double sum = 0;
		for (int i=0;i<values.length;i++) {
			sum += values[i];
		}
		
		return new double[]{sum/values.length};
	}
	
	
	private static double[] getStatsOfValues(double[] values) {
		if (values == null || values.length == 0) {
			return new double[]{0.0d};
		}
		
		double sum = 0;
		double min = (float) 1e6;
		double max = (float) -1e6;
		
		for (int i=0;i<values.length;i++) {
			sum += values[i];
			if (values[i] < min) min = values[i];
			if (values[i] > max) max = values[i];
		}
		
		return new double[]{
				min,
				sum/values.length,
				max,
				(float)sdEvaluator.evaluate(values)};
	}
	
	private static double[] getSamples(double[] values) {
		return values;
	}
	
	private static double[] getSingle(double[] values) {
		
		return new double[]{
				values[rand.nextInt(values.length)]};
		
	}
}
