package org.enotron.simulator.utility;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import javax.xml.bind.DatatypeConverter;

/**
 * A utility class that exposes static methods for handling byte 
 * array identifiers in to Hex and Base 64 strings
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
public class ByteArrayHelper {
	
	
	public static String toHexString(byte[] byteArray) {
		//Note: Long.hexstring will convert to unsigned
		return Long.toHexString(new BigInteger(byteArray).longValue()).toUpperCase();
	}
	
	public static byte[] fromHexString(String hexString) {
		
//		Requires Java 8 - not widespread yet on 01/01/'15 :-(
//		ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
//		bb.putLong(Long.parseUnsignedLong(hexString, 16));
		
		
		//For older versions of java thanks to Olathe who posted 
		// http://stackoverflow.com/questions/5723579/java-parse-and-unsigned-hex-string-into-a-signed-long
	    long value = 0;

	    final int hexLength = hexString.length();
	    if (hexLength == 0) throw new NumberFormatException("For input string: \"\"");
	    for (int i = Math.max(0, hexLength - 16); i < hexLength; i++) {
	      final char ch = hexString.charAt(i);

	      if      (ch >= '0' && ch <= '9') value = (value << 4) | (ch - '0'         );
	      else if (ch >= 'A' && ch <= 'F') value = (value << 4) | (ch - ('A' - 0xaL));
	      else if (ch >= 'a' && ch <= 'f') value = (value << 4) | (ch - ('a' - 0xaL));
	      else
	    	  throw new NumberFormatException("For input string: \"" + hexString + "\"");
	    }
		
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.putLong(value);
		return bb.array(); 
	}
	
	public static byte[] fromBase64String(String base64String) {
		return DatatypeConverter.parseBase64Binary(base64String);
	}
	
}
