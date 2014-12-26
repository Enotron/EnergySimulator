package org.enotron.simulator.utility;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

public class ByteArrayHelperTest {

	private byte[] abcBa = "abc".getBytes();
	private String testHexString = "616263";

	
	@Test
	public void testToHexString() {
		String hexString = ByteArrayHelper.toHexString(abcBa);
		
		assertEquals(testHexString, hexString);
	}

	@Test
	public void testFromHexString() {
		
		byte[] testBa = ByteArrayHelper.fromHexString(testHexString);
		System.out.println(
				"testFromHexString() Expected: " + 
				abcBa + " Actual " + testBa);
		
		assertArrayEquals(abcBa, testBa);
		
	}
	
	@Test
	public void testFromRandomLong() {
		
		Random rand = new Random();
		long randLong = rand.nextLong();
		BigInteger randBi = BigInteger.valueOf(randLong);
		
		byte[] ba1 = randBi.toByteArray();
		String hexString = Long.toHexString(randLong);
		
		byte[] calculatedBa = ByteArrayHelper.fromHexString(hexString);
		System.out.println(
				"testFromRandomLong() Expected: " + 
						ba1 + " Actual " + calculatedBa);

		assertArrayEquals(ba1, calculatedBa);
		
	}

}
