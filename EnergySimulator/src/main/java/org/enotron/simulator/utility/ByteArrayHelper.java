package org.enotron.simulator.utility;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import javax.xml.bind.DatatypeConverter;

public class ByteArrayHelper {
	
	
	public static String toHexString(byte[] byteArray) {
		//Note: Long.hexstring will convert to unsigned
		return Long.toHexString(new BigInteger(byteArray).longValue()).toUpperCase();
	}
	
	public static byte[] fromHexString(String hexString) {
		
//		Requires Java 8 -  
		ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
		bb.putLong(Long.parseUnsignedLong(hexString, 16));
		return bb.array(); 
	}
	
	public static byte[] fromBase64String(String base64String) {
		return DatatypeConverter.parseBase64Binary(base64String);
	}
	
}
