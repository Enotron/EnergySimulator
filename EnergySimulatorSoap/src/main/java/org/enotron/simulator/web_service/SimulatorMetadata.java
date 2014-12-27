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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for simulatorMetadata complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="simulatorMetadata">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="simulatorType" type="{http://simulator.enotron.org/web-service}simulatorType"/>
 *         &lt;element name="simulatorClass" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="parameterList" type="{http://simulator.enotron.org/web-service}parameter" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simulatorMetadata", propOrder = {
    "simulatorType",
    "simulatorClass",
    "parameterList"
})
public class SimulatorMetadata {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String simulatorType;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String simulatorClass;
    protected List<Parameter> parameterList;

    /**
     * Gets the value of the simulatorType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSimulatorType() {
        return simulatorType;
    }

    /**
     * Sets the value of the simulatorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSimulatorType(String value) {
        this.simulatorType = value;
    }

    /**
     * Gets the value of the simulatorClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSimulatorClass() {
        return simulatorClass;
    }

    /**
     * Sets the value of the simulatorClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSimulatorClass(String value) {
        this.simulatorClass = value;
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
     * {@link Parameter }
     * 
     * 
     */
    public List<Parameter> getParameterList() {
        if (parameterList == null) {
            parameterList = new ArrayList<Parameter>();
        }
        return this.parameterList;
    }

}
