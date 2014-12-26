package org.enotron.simulator.utility;

import java.util.Random;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.enotron.simulator.api.SimulatorException;
import org.enotron.simulator.api.ValuesMode;

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
