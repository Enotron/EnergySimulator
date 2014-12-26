package org.enotron.simulator.utility;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Map;

import org.enotron.simulator.api.GroupByTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GroupByTimeHelperTest {
	private static final long MINUTE_MS = 60*1000L;
	private static final long HOUR_MS = 60*60*1000L;
	private double[] test1, test2, test3;

	@Before
	public void setUp() throws Exception {
		test1 = new double[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
		test2 = new double[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		test3 = new double[]{0,1,2,3,4,5,6,7};
		
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Group these minute values in to 10 minute groups. They start at the 
	 * beginning of the last hour.
	 * We expect to get 2 groups 1) with 10 values and the second with 10 
	 */
	@Test
	public void testGroupValuesByTime() {
		
		long nowMs = System.currentTimeMillis();
		long lastHourMs = nowMs - nowMs%HOUR_MS;
		
		Map<Long, GroupedValues> result = 
				GroupByTimeHelper.groupValuesByTime(
						test1, lastHourMs, MINUTE_MS, GroupByTime.TENMINUTE);
		
		assertNotNull(result);
		

		assertEquals(2, result.size());
		
		assertNotNull(result.get(new Long(lastHourMs + 0*MINUTE_MS)));
		assertNotNull(result.get(new Long(lastHourMs + 10*MINUTE_MS)));
		
		assertArrayEquals(new double[]{0,1,2,3,4,5,6,7,8,9}, result.get(new Long(lastHourMs + 0*MINUTE_MS)).getValues(), 0.01d);
		assertArrayEquals(new double[]{10,11,12,13,14,15,16,17,18,19}, result.get(new Long(lastHourMs + 10*MINUTE_MS)).getValues(), 0.01d);
	
	}

	
	@Test
	public void testGroupValuesByTimeShortSet() {
		
		long nowMs = System.currentTimeMillis();
		long lastHourMs = nowMs - nowMs%HOUR_MS;
		
		Map<Long, GroupedValues> result = 
				GroupByTimeHelper.groupValuesByTime(
						test2, lastHourMs, MINUTE_MS, GroupByTime.TENMINUTE);
		
		assertNotNull(result);
		
		assertEquals(2, result.size());
		
		System.out.println("Expecting " + new Date(lastHourMs) + " and " + new Date(lastHourMs+10*MINUTE_MS));
		assertNotNull(result.get(new Long(lastHourMs + 0*MINUTE_MS)));
		assertNotNull(result.get(new Long(lastHourMs + 10*MINUTE_MS)));
		
		assertArrayEquals(new double[]{0,1,2,3,4,5,6,7,8,9}, result.get(new Long(lastHourMs + 0*MINUTE_MS)).getValues(), 0.01d);
		assertArrayEquals(new double[]{10,11,12,13,14,15}, result.get(new Long(lastHourMs + 10*MINUTE_MS)).getValues(), 0.01d);
	
	}
	
	@Test
	public void testGroupValuesByTimeUltraShortSet() {
		
		long nowMs = System.currentTimeMillis();
		long lastHourMs = nowMs - nowMs%HOUR_MS;
		
		Map<Long, GroupedValues> result = 
				GroupByTimeHelper.groupValuesByTime(
						test3, lastHourMs+2*MINUTE_MS, MINUTE_MS, GroupByTime.TENMINUTE);
		
		assertNotNull(result);
		
		assertEquals(1, result.size());
		
		System.out.println("Expecting " + new Date(lastHourMs));
		assertNotNull(result.get(new Long(lastHourMs + 0*MINUTE_MS)));
		
		assertArrayEquals(test3, result.get(new Long(lastHourMs + 0*MINUTE_MS)).getValues(), 0.01d);
	
	}

	
	@Test
	public void testGroupValuesByTimeOffset() {
		
		long nowMs = System.currentTimeMillis();
		long lastHourMs = nowMs - nowMs%HOUR_MS;
		
		Map<Long, GroupedValues> result = 
				GroupByTimeHelper.groupValuesByTime(
						test1, lastHourMs+3*MINUTE_MS, MINUTE_MS, GroupByTime.TENMINUTE);
		
		assertNotNull(result);
		
		assertEquals(3, result.size());
		
		assertNotNull(result.get(new Long(lastHourMs + 0*MINUTE_MS)));
		assertNotNull(result.get(new Long(lastHourMs + 10*MINUTE_MS)));
		assertNotNull(result.get(new Long(lastHourMs + 20*MINUTE_MS)));
		
		assertArrayEquals(new double[]{0,1,2,3,4,5,6}, result.get(new Long(lastHourMs + 0*MINUTE_MS)).getValues(), 0.01d);
		assertArrayEquals(new double[]{7,8,9,10,11,12,13,14,15,16}, result.get(new Long(lastHourMs + 10*MINUTE_MS)).getValues(), 0.01d);
		assertArrayEquals(new double[]{17,18,19}, result.get(new Long(lastHourMs + 20*MINUTE_MS)).getValues(), 0.01d);
	
	}

	
}
