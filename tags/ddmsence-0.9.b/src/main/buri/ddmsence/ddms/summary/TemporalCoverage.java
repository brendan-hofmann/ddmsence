/* Copyright 2010 by Brian Uri!
   
   This file is part of DDMSence.
   
   This library is free software; you can redistribute it and/or modify
   it under the terms of version 3.0 of the GNU Lesser General Public 
   License as published by the Free Software Foundation.
   
   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
   GNU Lesser General Public License for more details.
   
   You should have received a copy of the GNU Lesser General Public 
   License along with DDMSence. If not, see <http://www.gnu.org/licenses/>.

   You can contact the author at ddmsence@urizone.net. The DDMSence
   home page is located at http://ddmsence.urizone.net/
*/
package buri.ddmsence.ddms.summary;

import java.util.HashSet;
import java.util.Set;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import nu.xom.Element;
import buri.ddmsence.ddms.AbstractBaseComponent;
import buri.ddmsence.ddms.InvalidDDMSException;
import buri.ddmsence.ddms.security.SecurityAttributes;
import buri.ddmsence.util.Util;

/**
 * An immutable implementation of ddms:temporalCoverage.
 * 
 * <p>
 * A temporalCoverage element contains a locally defined TimePeriod construct. This TimePeriod construct is a container
 * for the name, start, and end values of a time period. It exists only inside of a ddms:temporalCoverage parent, so it
 * is not implemented as a Java object.
 * </p>
 * 
 * <p>
 * If not "Not Applicable" or Unknown.NAMe, date formats must adhere to one of: xs:dateTime, xs:date, xs:gYearMonth, or
 * xs:gYear, and the <code>XMLGregorianCalendar</code> class is used to enforce these restrictions.
 * </p>
 * 
 * <table class="info"><tr class="infoHeader"><th>Nested Elements</th></tr><tr><td class="infoBody">
 * <u>ddms:name</u>: An interval of time, which can be expressed as a named era (0-1 optional, default=Unknown). Zero or
 * 1 of these elements may appear.<br />
 * <u>ddms:start</u>: The start date of a period of time (exactly 1 required, default=Unknown).<br />
 * <u>ddms:end</u>: The end date of a period of time (exactly 1 required, default=Unknown).
 * </td></tr></table>
 * 
 * <table class="info"><tr class="infoHeader"><th>Attributes</th></tr><tr><td class="infoBody">
 * This class is decorated with ICISM {@link SecurityAttributes}. The classification and
 * ownerProducer attributes are optional.
 * </td></tr></table>
 * 
 * <table class="info"><tr class="infoHeader"><th>DDMS Information</th></tr><tr><td class="infoBody">
 * <u>Link</u>: https://metadata.dod.mil/mdr/irs/DDMS/ddms_categories.htm#TemporalCoverage<br />
 * <u>Description</u>: Subject matter coverage expressed in terms of one or more periods of time.<br />
 * <u>Obligation</u>: Optional<br />
 * <u>Schema Modification Date</u>: 2010-01-26<br />
 * </td></tr></table>
 * 
 * @author Brian Uri!
 * @since 0.9.b
 */
public final class TemporalCoverage extends AbstractBaseComponent {
	
	// Values are cached upon instantiation, so Calendars do not have to be built when calling getters.
	private String _cachedName = DEFAULT_VALUE;
	private String _cachedStartString;
	private String _cachedEndString;
	private XMLGregorianCalendar _cachedStart = null;
	private XMLGregorianCalendar _cachedEnd = null;
	private SecurityAttributes _cachedSecurityAttributes = null;
	
	private static final String DEFAULT_VALUE = "Unknown";
		
	private static Set<String> EXTENDED_DATE_TYPES = new HashSet<String>();
	static {
		EXTENDED_DATE_TYPES.add("Not Applicable");
		EXTENDED_DATE_TYPES.add("Unknown");
	}
	
	/** The element name of this component */
	public static final String NAME = "temporalCoverage";
	
	// The name of the TimePeriod element itself
	private static final String TIME_PERIOD_NAME = "TimePeriod";
	
	// The name of the "name" element nested in a TimePeriod 
	private static final String TIME_PERIOD_NAME_NAME = "name";
	
	private static final String START_NAME = "start";
	private static final String END_NAME = "end";
	
	/**
	 * Constructor for creating a component from a XOM Element
	 *  
	 * @param element the XOM element representing this 
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	public TemporalCoverage(Element element) throws InvalidDDMSException {
		Util.requireDDMSValue("temporalCoverage element", element);
		Element periodElement = element.getFirstChildElement(TIME_PERIOD_NAME, element.getNamespaceURI());
		if (periodElement != null) {
			Element nameElement = periodElement.getFirstChildElement(TIME_PERIOD_NAME_NAME, element.getNamespaceURI());
			if (nameElement != null && !Util.isEmpty(nameElement.getValue()))
				_cachedName = nameElement.getValue();
			Element startElement = periodElement.getFirstChildElement(START_NAME, element.getNamespaceURI());
			Element endElement = periodElement.getFirstChildElement("end", element.getNamespaceURI());
			String startString = (startElement == null ? "" : startElement.getValue());
			String endString = (endElement == null ? "" : endElement.getValue());
			loadDateCaches(startString, endString);
		}
		_cachedSecurityAttributes = new SecurityAttributes(element);
		setXOMElement(element, true);
	}
		
	/**
	 * Constructor for creating a component from raw data
	 *  
	 * @param timePeriodName	the time period name (optional) (if empty, defaults to Unknown)
	 * @param startString		a string representation of the date (required) (if empty, defaults to Unknown)
	 * @param endString 		a string representation of the end date (required) (if empty, defaults to Unknown)
	 * @param securityAttributes any security attributes (optional)
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	public TemporalCoverage(String timePeriodName, String startString, String endString, SecurityAttributes securityAttributes) throws InvalidDDMSException {
		Element periodElement = Util.buildDDMSElement(TIME_PERIOD_NAME, null);
		if (!Util.isEmpty(timePeriodName))
				_cachedName = timePeriodName;
		startString = (Util.isEmpty(startString) ? DEFAULT_VALUE : startString);
		endString = (Util.isEmpty(endString) ? DEFAULT_VALUE : endString);
		Util.addDDMSChildElement(periodElement, TIME_PERIOD_NAME_NAME, timePeriodName);
		periodElement.appendChild(Util.buildDDMSElement(START_NAME, startString));
		periodElement.appendChild(Util.buildDDMSElement(END_NAME, endString));
		loadDateCaches(startString, endString);
		
		Element element = Util.buildDDMSElement(TemporalCoverage.NAME, null);
		element.appendChild(periodElement);
		
		_cachedSecurityAttributes = (securityAttributes == null ? new SecurityAttributes(null, null, null) : securityAttributes);
		_cachedSecurityAttributes.addTo(element);
		setXOMElement(element, true);
	}
			
	/**
	 * Helper method to populate cached date variables.
	 * 
	 * @param startString the start string. Defaults to Unknown.NAMe if empty.
	 * @param endString the end string. Defaults to Unknown.NAMe if empty.
	 */
	private void loadDateCaches(String startString, String endString) {
		_cachedStartString = startString;
		try {
			_cachedStart = getFactory().newXMLGregorianCalendar(_cachedStartString);
		}
		catch (IllegalArgumentException e) {
			// Was not a valid date. validate() will catch this later.
		}
		_cachedEndString = endString;
		try {
			_cachedEnd = getFactory().newXMLGregorianCalendar(_cachedEndString);
		}
		catch (IllegalArgumentException e) {
			// Was not a valid date. validate() will catch this later.
		}
	}
	
	/**
	 * Asserts that an extended date is either "Not Applicable" or Unknown.NAMe.
	 * 
	 * @param dateString	the string to check
	 * @throws InvalidDDMSException if the value is null, empty or invalid.
	 */
	public static void validateExtendedDateType(String dateString) throws InvalidDDMSException {
		Util.requireDDMSValue("extended date type", dateString);
		if (!EXTENDED_DATE_TYPES.contains(dateString))
			throw new InvalidDDMSException("If no date is specified, the value must be one of " + EXTENDED_DATE_TYPES);
	}
	
	/**
	 * Validates the component.
	 * 
	 * <table class="info"><tr class="infoHeader"><th>Rules</th></tr><tr><td class="infoBody">
	 * <li>start is a valid date format.</li>
	 * <li>end is a valid date format.</li>
	 * <li>0-1 names, exactly 1 start, and exactly 1 end exist.</li>
	 * <li>The SecurityAttributes are valid.</li>
	 * </td></tr></table>
	 * 
	 * @see AbstractBaseComponent#validate()
	 * @throws InvalidDDMSException  if any required information is missing or malformed
	 */
	public void validate() throws InvalidDDMSException {
		super.validate();
		Element periodElement = getChild(TIME_PERIOD_NAME);
		Util.requireDDMSValue("TimePeriod element", periodElement);
		Util.requireBoundedDDMSChildCount(periodElement, TIME_PERIOD_NAME_NAME, 0, 1);
		Util.requireBoundedDDMSChildCount(periodElement, START_NAME, 1, 1);
		Util.requireBoundedDDMSChildCount(periodElement, END_NAME, 1, 1);
		if (getStart() != null)
			Util.requireDDMSDateFormat(getStart().getXMLSchemaType());
		else
			validateExtendedDateType(getStartString());
		if (getEnd() != null)
			Util.requireDDMSDateFormat(getEnd().getXMLSchemaType());
		else
			validateExtendedDateType(getEndString());
	}
	
	/**
	 * @see AbstractBaseComponent#toHTML()
	 */
	public String toHTML() {
		StringBuffer html = new StringBuffer();
		html.append(buildHTMLMeta("temporal.TimePeriod", getTimePeriodName(), false));
		html.append(buildHTMLMeta("temporal.DateStart", getStartString(), true));
		html.append(buildHTMLMeta("temporal.DateEnd", getEndString(), true));
		return (html.toString());
	}
		
	/**
	 * @see AbstractBaseComponent#toText()
	 */
	public String toText() {
		StringBuffer text = new StringBuffer();
		text.append(buildTextLine("Time Period", getTimePeriodName(), false));
		text.append(buildTextLine("Date Start", getStartString(), true));
		text.append(buildTextLine("Date End", getEndString(), true));
		text.append(getSecurityAttributes().toText("Time Period"));
		return (text.toString());
	}
	 
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object obj) {
		if (!super.equals(obj) || !(obj instanceof TemporalCoverage))
			return (false);
		TemporalCoverage test = (TemporalCoverage) obj;
		return (getTimePeriodName().equals(test.getTimePeriodName()) &&
			getStartString().equals(test.getStartString()) &&
			getEndString().equals(test.getEndString()) &&
			getSecurityAttributes().equals(test.getSecurityAttributes()));
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		int result = super.hashCode();
		result = 7 * result + getTimePeriodName().hashCode();
		result = 7 * result + getStartString().hashCode();
		result = 7 * result + getEndString().hashCode();
		result = 7 * result + getSecurityAttributes().hashCode();
		return (result);
	}
	
	/**
	 * Accessor for the TimePeriod name element child text. Note that the getName() accessor will
	 * return the local name of the temporal coverage element (temporalCoverage).
	 */
	public String getTimePeriodName() {
		return (_cachedName);
	}
	
	/**
	 * Accessor for the XML calendar representing the start date. If the start date is "Not Applicable" or Unknown.NAMe
	 * will return null. Use <code>getStartString</code> to retrieve the string representation.
	 */
	public XMLGregorianCalendar getStart() {
		return (_cachedStart == null ? null : getFactory().newXMLGregorianCalendar(_cachedStart.toXMLFormat())); 
	}

	/**
	 * Accessor for the start date as a string. If the value of start cannot be represented by an XML calendar, this will
	 * return "Not Applicable" or Unknown.NAMe. Use <code>getStart</code> to work with this value as a calendar date.
	 */
	public String getStartString() {
		if (getStart() != null)
			return (getStart().toXMLFormat());
		return (_cachedStartString);
	}
	
	/**
	 * Accessor for the XML calendar representing the end date. If the end date is "Not Applicable" or Unknown.NAMe
	 * will return null. Use <code>getEndString</code> to retrieve the string representation.
	 */
	public XMLGregorianCalendar getEnd() {
		return (_cachedEnd == null ? null : getFactory().newXMLGregorianCalendar(_cachedEnd.toXMLFormat()));
	}

	/**
	 * Accessor for the end date as a string. If the value of end cannot be represented by an XML calendar, this will
	 * return "Not Applicable" or Unknown.NAMe. Use <code>getEnd</code> to work with this value as a calendar date.
	 */
	public String getEndString() {
		if (getEnd() != null)
			return (getEnd().toXMLFormat());
		return (_cachedEndString);
	}
	
	/**
	 * Accessor for the Security Attributes. Will always be non-null even if the attributes are not set.
	 */
	public SecurityAttributes getSecurityAttributes() {
		return (_cachedSecurityAttributes);
	}
	
	/**
	 * Accesor for the datatype factory
	 */
	private static DatatypeFactory getFactory() {
		return (Util.getDataTypeFactory());
	}
} 