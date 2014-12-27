package org.enotron.simulator.utility;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * Json converter for HexString that gives byte[] 
 * Use as @JsonDeserialize(using=JsonHexStringToByteArray.class)
 * 
 * @author Sean Condon
 * @date 18-Dec-2014
 */
public class JsonHexStringToByteArray extends JsonDeserializer<byte[]>{

	@Override
	public byte[] deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

        JsonNode node = jp.getCodec().readTree(jp);
        String hexString = ((TextNode)node.get("simulatorUid")).asText();
		
		return ByteArrayHelper.fromHexString(hexString);
	}



}
