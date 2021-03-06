//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.14 at 05:29:59 PM GMT 
//


package org.enotron.simulator.web_service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

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
 *         &lt;element name="simulatorid" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/>
 *         &lt;element name="parameterList" type="{http://www.w3.org/2001/XMLSchema}token" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="valuesMode" type="{http://simulator.enotron.org/web-service}valuesMode"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="groupBy" type="{http://simulator.enotron.org/web-service}timeScope" minOccurs="0"/>
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
    "simulatorid",
    "parameterList",
    "valuesMode",
    "startTime",
    "endTime",
    "groupBy"
})
@XmlRootElement(name = "getDataRequest")
public class GetDataRequest {

    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    @JsonDeserialize(using=JsonHexStringToByteArray.class)
    protected byte[] simulatorid;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected List<String> parameterList;
    @XmlElement(required = true)
    protected ValuesMode valuesMode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTime;
    protected TimeScope groupBy;

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

    /**
     * Gets the value of the parameterList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameterList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameterList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getParameterList() {
        if (parameterList == null) {
            parameterList = new ArrayList<String>();
        }
        return this.parameterList;
    }

    /**
     * Gets the value of the valuesMode property.
     * 
     * @return
     *     possible object is
     *     {@link ValuesMode }
     *     
     */
    public ValuesMode getValuesMode() {
        return valuesMode;
    }

    /**
     * Sets the value of the valuesMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValuesMode }
     *     
     */
    public void setValuesMode(ValuesMode value) {
        this.valuesMode = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndTime(XMLGregorianCalendar value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the groupBy property.
     * 
     * @return
     *     possible object is
     *     {@link TimeScope }
     *     
     */
    public TimeScope getGroupBy() {
        return groupBy;
    }

    /**
     * Sets the value of the groupBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeScope }
     *     
     */
    public void setGroupBy(TimeScope value) {
        this.groupBy = value;
    }

}
