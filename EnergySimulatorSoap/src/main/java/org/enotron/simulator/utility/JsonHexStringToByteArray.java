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
public class JsonHexStringToByteArray extends JsonDeserializer<byte[]>{

	@Override
	public byte[] deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

        JsonNode node = jp.getCodec().readTree(jp);
        String hexString = ((TextNode)node.get("simulatorUid")).asText();
		
		return ByteArrayHelper.fromHexString(hexString);
	}



}
