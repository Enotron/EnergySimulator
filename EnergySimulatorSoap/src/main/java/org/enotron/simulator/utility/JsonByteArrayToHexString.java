package org.enotron.simulator.utility;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Json converter for byte[] that gives HexString 
 * Use as @JsonSerialize(using=JsonByteArrayToHexString.class)
 * 
 * @author Sean Condon
 * @date 18-Dec-2014
 */
public class JsonByteArrayToHexString extends JsonSerializer<byte[]>{

	@Override
	public void serialize(byte[] byteArray, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		

		String hexString = ByteArrayHelper.toHexString(byteArray);
		jgen.writeRaw(hexString);
	}


}
