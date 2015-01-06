package org.enotron.simulator.api;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A unit test for the MeasurementImpl class
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
public class MeasurementImplTest {

	private MeasurementImpl meas1, meas2;
	private long nowMs = System.currentTimeMillis();
	private Float[] floatArray4 = new Float[]{10.1f, 11.2f,13.3f, 14.4f};
	
	@Before
	public void setUp() throws Exception {
		
		meas1 = new MeasurementImpl();
		meas1.setName("Name1");
		meas1.setUnit("V");
		meas1.setDurationSecs(10);
		meas1.setTimestamp(new Date(nowMs));
		meas1.setValueCode(ValuesMode.AVERAGE);
		meas1.setValues(floatArray4);
		
		meas2 = new MeasurementImpl();
	}

	@After
	public void tearDown() throws Exception {
		
		meas1 = null;
		
		meas2 = null;
	}

	@Test
	public void testGetName() {
		assertEquals("Name1", meas1.getName());
	}

	@Test
	public void testSetName() {
		meas2.setName("Name2");
		
		assertEquals("Name2", meas2.getName());
	}

	@Test
	public void testGetUnit() {
		assertEquals("V", meas1.getUnit());
	}

	@Test
	public void testSetUnit() {
		meas2.setUnit("W");
		
		assertEquals("W", meas2.getUnit());
	}

	@Test
	public void testGetTimestamp() {
		assertEquals(new Date(nowMs), meas1.getTimestamp());
	}

	@Test
	public void testSetTimestamp() {
		Date nowPlus1Sec = new Date(nowMs+1000l);
		meas2.setTimestamp(nowPlus1Sec);
		
		assertEquals(nowPlus1Sec, meas2.getTimestamp());
	}

	@Test
	public void testGetDurationSecs() {
		assertEquals(10, meas1.getDurationSecs());
	}

	@Test
	public void testSetDurationSecs() {
		meas2.setDurationSecs(5);
		
		assertEquals(5, meas2.getDurationSecs());
	}

	@Test
	public void testGetValueCode() {
		assertEquals(ValuesMode.AVERAGE, meas1.getValueCode());
	}

	@Test
	public void testSetValueCode() {
		meas2.setValueCode(ValuesMode.SAMPLES);
		
		assertEquals(ValuesMode.SAMPLES, meas2.getValueCode());
	}

	@Test
	public void testGetValues() {
		assertArrayEquals(floatArray4, meas1.getValues());
		
	}

	@Test
	public void testSetValues() {
		Float[] floatArray = new Float[]{1.1f, 2.2f,3.3f, 4.4f, 5.5f};
		
		meas2.setValues(floatArray);
		
		assertArrayEquals(floatArray, meas2.getValues());
	}
}
