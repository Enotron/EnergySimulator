package org.enotron.simulator.utility;

import static org.junit.Assert.*;

import org.enotron.simulator.api.SimulatorException;
import org.enotron.simulator.api.ValuesMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A junit test for the Values Mode helper utility class
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
