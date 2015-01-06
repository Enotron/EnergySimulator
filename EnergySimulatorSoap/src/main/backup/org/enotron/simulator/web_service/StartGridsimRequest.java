//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.14 at 05:29:59 PM GMT 
//


package org.enotron.simulator.web_service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.enotron.simulator.utility.JsonHexStringToByteArray;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


/**
 * Copy this file from src/main/backup in to the org.enotron.simulator.web_service folder
 * if the class there has been regenerated
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="callContext" type="{http://simulator.enotron.org/web-service}callContext"/>
 *         &lt;element name="simulatorid" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 *  @copyright 2015 Enotron Ltd.
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "callContext",
    "simulatorid"
})
@XmlRootElement(name = "startGridsimRequest")
public class StartGridsimRequest {

    @XmlElement(required = true)
    protected CallContext callContext;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    @JsonDeserialize(using=JsonHexStringToByteArray.class)
    protected byte[] simulatorid;

    /**
     * Gets the value of the callContext property.
     * 
     * @return
     *     possible object is
     *     {@link CallContext }
     *     
     */
    public CallContext getCallContext() {
        return callContext;
    }

    /**
     * Sets the value of the callContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link CallContext }
     *     
     */
    public void setCallContext(CallContext value) {
        this.callContext = value;
    }

    /**
     * Gets the value of the simulatorid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getSimulatorid() {
        return simulatorid;
    }

    /**
     * Sets the value of the simulatorid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSimulatorid(byte[] value) {
        this.simulatorid = value;
    }

}
