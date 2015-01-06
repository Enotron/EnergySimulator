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
 * @date 18-Dec-2014 * 
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
public class JsonByteArrayToHexString extends JsonSerializer<byte[]>{

	@Override
	public void serialize(byte[] byteArray, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		

		String hexString = ByteArrayHelper.toHexString(byteArray);
		jgen.writeRaw(hexString);
	}


}
