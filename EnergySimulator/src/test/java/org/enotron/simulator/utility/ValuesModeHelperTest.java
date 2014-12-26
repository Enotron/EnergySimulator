package org.enotron.simulator.utility;

import static org.junit.Assert.*;

import org.enotron.simulator.api.SimulatorException;
import org.enotron.simulator.api.ValuesMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ValuesModeHelperTest {
	double[] testData;
	
	@Before
	public void setUp() throws Exception {
		
		testData = new double[]{1,2,3,4};
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetProcessedValuesAverage() {
		
		try {
			double[] results = 
					ValuesModeHelper.getProcessedValues(
							ValuesMode.AVERAGE, testData);
			
			assertArrayEquals(new double[]{2.5}, results, 0.01);
			
		} catch (SimulatorException e) {
			e.printStackTrace();
			fail("Exception!! " + e.getMessage());
			
		}
		
	}

	@Test
	public void testGetProcessedValuesSamples() {
		
		try {
			double[] results = 
					ValuesModeHelper.getProcessedValues(
							ValuesMode.SAMPLES, testData);

			assertArrayEquals(testData, results, 0.01);
			
		} catch (SimulatorException e) {
			e.printStackTrace();
			fail("Exception!! " + e.getMessage());
			
		}
	}
	
	@Test
	public void testGetProcessedValuesSingle() {
		
		try {
			double[] results = 
					ValuesModeHelper.getProcessedValues(
							ValuesMode.SINGLE, testData);

			assertArrayEquals(new double[]{2.5}, results, 1.5);
			
		} catch (SimulatorException e) {
			e.printStackTrace();
			fail("Exception!! " + e.getMessage());
			
		}
	}
	
	@Test
	public void testGetProcessedValuesStats() {
		
		try {
			double[] results = 
					ValuesModeHelper.getProcessedValues(
							ValuesMode.MIN_MEAN_MAX_STDDEV, testData);

			assertArrayEquals(new double[]{1,2.5,4,1.29}, results, 0.01);
			
		} catch (SimulatorException e) {
			e.printStackTrace();
			fail("Exception!! " + e.getMessage());
			
		}
	}
}
