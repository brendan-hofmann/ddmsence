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

import nu.xom.Element;
import buri.ddmsence.ddms.AbstractComponentTestCase;
import buri.ddmsence.ddms.InvalidDDMSException;
import buri.ddmsence.ddms.ValidationMessage;
import buri.ddmsence.ddms.resource.Rights;
import buri.ddmsence.ddms.security.SecurityAttributesTest;
import buri.ddmsence.util.Util;

/**
 * <p>Tests related to ddms:temporalCoverage elements</p>
 * 
 * @author Brian Uri!
 * @since 0.9.b
 */
public class TemporalCoverageTest extends AbstractComponentTestCase {
	
	private static final String TEST_NAME = "My Time Period";
	private static final String TEST_START = "1979-09-15";
	private static final String TEST_END = "Not Applicable";
		
	/**
	 * Constructor
	 */
	public TemporalCoverageTest() {
		super("temporalCoverage.xml");
	}
	
	/**
	 * Helper method to create an object which is expected to be valid.
	 * 
	 * @param element	the element to build from
	 * @return a valid object
	 */
	private TemporalCoverage testConstructor(boolean expectFailure, Element element) {
		TemporalCoverage component = null;
		try {
			component = new TemporalCoverage(element);
			checkConstructorSuccess(expectFailure);
		}
		catch (InvalidDDMSException e) {
			checkConstructorFailure(expectFailure, e);
		}
		return (component);
	}
	
	/**
	 * Helper method to create an object which is expected to be valid.
	 * 
	 * @param expectFailure	true if this operation is expected to succeed, false otherwise
	 * @param timePeriodName	the time period name (optional)
	 * @param startString		a string representation of the date (required)
	 * @param endString 		a string representation of the end date (required)
	 * @return a valid object
	 */
	private TemporalCoverage testConstructor(boolean expectFailure, String timePeriodName, String startString, String endString) {
		TemporalCoverage component = null;
		try {
			component = new TemporalCoverage(timePeriodName, startString, endString, null);
			checkConstructorSuccess(expectFailure);
		}
		catch (InvalidDDMSException e) {
			checkConstructorFailure(expectFailure, e);
		}
		return (component);			
	}
	
	/**
	 * Returns the expected HTML output for this unit test
	 */
	private String getExpectedHTMLOutput() {
		StringBuffer html = new StringBuffer();
		html.append("<meta name=\"temporal.TimePeriod\" content=\"").append(TEST_NAME).append("\" />\n");
		html.append("<meta name=\"temporal.DateStart\" content=\"").append(TEST_START).append("\" />\n");
		html.append("<meta name=\"temporal.DateEnd\" content=\"").append(TEST_END).append("\" />\n");
		return (html.toString());
	}				
				
	/**
	 * Returns the expected Text output for this unit test
	 */
	private String getExpectedTextOutput() {
		StringBuffer text = new StringBuffer();
		text.append("Time Period: ").append(TEST_NAME).append("\n");
		text.append("Date Start: ").append(TEST_START).append("\n");
		text.append("Date End: ").append(TEST_END).append("\n");
		return (text.toString());
	}
		
	/**
	 * Returns the expected XML output for this unit test
	 * 
	 * @param preserveFormatting if true, include line breaks and tabs.
	 */
	private String getExpectedXMLOutput(boolean preserveFormatting) {
		StringBuffer xml = new StringBuffer();
		xml.append("<ddms:temporalCoverage xmlns:ddms=\"").append(DDMS_NAMESPACE).append("\">\n\t");
		xml.append("<ddms:TimePeriod>\n\t\t");
		xml.append("<ddms:name>").append(TEST_NAME).append("</ddms:name>\n\t\t");
		xml.append("<ddms:start>").append(TEST_START).append("</ddms:start>\n\t\t");
		xml.append("<ddms:end>").append(TEST_END).append("</ddms:end>\n\t");
		xml.append("</ddms:TimePeriod>\n");
		xml.append("</ddms:temporalCoverage>");				
		return (formatXml(xml.toString(), preserveFormatting));		
	}
	
	public void testNameAndNamespace() {
		TemporalCoverage component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(TemporalCoverage.NAME, component.getName());
		assertEquals(Util.DDMS_PREFIX, component.getPrefix());
		assertEquals(Util.DDMS_PREFIX + ":" + TemporalCoverage.NAME, component.getQualifiedName());
		
		// Wrong name/namespace
		Element element = Util.buildDDMSElement("wrongName", null);
		testConstructor(WILL_FAIL, element);
	}
	
	public void testElementConstructorValid() {
		// All fields
		testConstructor(WILL_SUCCEED, getValidElement());
		
		// No optional fields
		Element periodElement = Util.buildDDMSElement("TimePeriod", null);
		periodElement.appendChild(Util.buildDDMSElement("start", TEST_START));
		periodElement.appendChild(Util.buildDDMSElement("end", TEST_END));
		Element element = Util.buildDDMSElement(TemporalCoverage.NAME, null);
		element.appendChild(periodElement);
		testConstructor(WILL_SUCCEED, element);
		
		// No optional fields, empty name element rather than no name element
		periodElement = Util.buildDDMSElement("TimePeriod", null);
		periodElement.appendChild(Util.buildDDMSElement("name", ""));
		periodElement.appendChild(Util.buildDDMSElement("start", TEST_START));
		periodElement.appendChild(Util.buildDDMSElement("end", TEST_END));
		element = Util.buildDDMSElement(TemporalCoverage.NAME, null);
		element.appendChild(periodElement);
		testConstructor(WILL_SUCCEED, element);
	}
	
	public void testDataConstructorValid() {
		// All fields
		testConstructor(WILL_SUCCEED, TEST_NAME, TEST_START, TEST_END);
		
		// No optional fields
		testConstructor(WILL_SUCCEED, "", TEST_START, TEST_END);
	}
	
	public void testElementConstructorInvalid() {
		// Missing start
		Element periodElement = Util.buildDDMSElement("TimePeriod", null);
		periodElement.appendChild(Util.buildDDMSElement("end", TEST_END));
		Element element = Util.buildDDMSElement(TemporalCoverage.NAME, null);
		element.appendChild(periodElement);
		testConstructor(WILL_FAIL, element);
		
		// Missing end
		periodElement = Util.buildDDMSElement("TimePeriod", null);
		periodElement.appendChild(Util.buildDDMSElement("start", TEST_START));
		element = Util.buildDDMSElement(TemporalCoverage.NAME, null);
		element.appendChild(periodElement);
		testConstructor(WILL_FAIL, element);
		
		// Wrong date format (using xs:gDay here)
		periodElement = Util.buildDDMSElement("TimePeriod", null);
		periodElement.appendChild(Util.buildDDMSElement("start", "---31"));
		periodElement.appendChild(Util.buildDDMSElement("end", TEST_END));
		element = Util.buildDDMSElement(TemporalCoverage.NAME, null);
		element.appendChild(periodElement);
		testConstructor(WILL_FAIL, element);

		// Wrong extended value
		periodElement = Util.buildDDMSElement("TimePeriod", null);
		periodElement.appendChild(Util.buildDDMSElement("start", "N/A"));
		periodElement.appendChild(Util.buildDDMSElement("end", TEST_END));
		element = Util.buildDDMSElement(TemporalCoverage.NAME, null);
		element.appendChild(periodElement);
		testConstructor(WILL_FAIL, element);
		
		// Bad range
		periodElement = Util.buildDDMSElement("TimePeriod", null);
		periodElement.appendChild(Util.buildDDMSElement("start", "2004"));
		periodElement.appendChild(Util.buildDDMSElement("end", "2003"));
		element = Util.buildDDMSElement(TemporalCoverage.NAME, null);
		element.appendChild(periodElement);
		testConstructor(WILL_FAIL, element);
	}
	
	public void testDataConstructorInvalid() {
		// Wrong date format (using xs:gDay here)
		testConstructor(WILL_FAIL, TEST_NAME, "---31", TEST_END);
		
		// Wrong extended value
		testConstructor(WILL_FAIL, TEST_NAME, "N/A", TEST_END);
		
		// Bad range
		testConstructor(WILL_FAIL, TEST_NAME, "2004", "2003");
	}
	
	public void testWarnings() {
		// No warnings
		TemporalCoverage component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(0, component.getValidationWarnings().size());
		
		// Empty name element
		Element periodElement = Util.buildDDMSElement("TimePeriod", null);
		periodElement.appendChild(Util.buildDDMSElement("name", null));
		periodElement.appendChild(Util.buildDDMSElement("start", TEST_START));
		periodElement.appendChild(Util.buildDDMSElement("end", TEST_END));
		Element element = Util.buildDDMSElement(TemporalCoverage.NAME, null);
		element.appendChild(periodElement);
		component = testConstructor(WILL_SUCCEED, element);
		assertEquals(1, component.getValidationWarnings().size());
		assertEquals(ValidationMessage.WARNING_TYPE, component.getValidationWarnings().get(0).getType());
		assertEquals("A ddms:name element was found with no value. Defaulting to \"Unknown\".", component
			.getValidationWarnings().get(0).getText());
		assertEquals("/ddms:temporalCoverage/ddms:TimePeriod", component.getValidationWarnings().get(0).getLocator());
	}
	
	public void testConstructorEquality() {
		TemporalCoverage elementComponent = testConstructor(WILL_SUCCEED, getValidElement());
		TemporalCoverage dataComponent = testConstructor(WILL_SUCCEED, TEST_NAME, TEST_START, TEST_END);
		assertEquals(elementComponent, dataComponent);
		assertEquals(elementComponent.hashCode(), dataComponent.hashCode());
	}

	public void testConstructorInequalityDifferentValues() {
		TemporalCoverage elementComponent = testConstructor(WILL_SUCCEED, getValidElement());
		TemporalCoverage dataComponent = testConstructor(WILL_SUCCEED, "", TEST_START, TEST_END);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, TEST_NAME, "Not Applicable", TEST_END);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, TEST_NAME, TEST_START, "2050");
		assertFalse(elementComponent.equals(dataComponent));
	}
	
	public void testConstructorInequalityWrongClass() throws InvalidDDMSException {
		TemporalCoverage elementComponent = testConstructor(WILL_SUCCEED, getValidElement());
		Rights wrongComponent = new Rights(true, true, true);
		assertFalse(elementComponent.equals(wrongComponent));
	}
	
	public void testHTMLOutput() {
		TemporalCoverage component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(getExpectedHTMLOutput(), component.toHTML());
		
		component = testConstructor(WILL_SUCCEED, TEST_NAME, TEST_START, TEST_END);
		assertEquals(getExpectedHTMLOutput(), component.toHTML());
	}	

	public void testTextOutput() {
		TemporalCoverage component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(getExpectedTextOutput(), component.toText());
		
		component = testConstructor(WILL_SUCCEED, TEST_NAME, TEST_START, TEST_END);
		assertEquals(getExpectedTextOutput(), component.toText());
	}
	
	public void testXMLOutput() {
		TemporalCoverage component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(getExpectedXMLOutput(true), component.toXML());

		component = testConstructor(WILL_SUCCEED, TEST_NAME, TEST_START, TEST_END);
		assertEquals(getExpectedXMLOutput(false), component.toXML());
	}	
	
	public void testValidateExtendedDateTypeSuccess() {
		try {
			TemporalCoverage.validateExtendedDateType("Not Applicable");
		}
		catch (InvalidDDMSException e) {
			fail("Did not allow valid data.");
		}
	}
	
	public void testValidateExtendedDateTypeFailure() {
		try {
			TemporalCoverage.validateExtendedDateType("N/A");
			fail("Allowed invalid data.");
		}
		catch (InvalidDDMSException e) {
			// Good
		}
	}	
	
	public void testDefaultValues() {
		TemporalCoverage component = testConstructor(WILL_SUCCEED, "", "", "");
		assertEquals("Unknown", component.getTimePeriodName());
		assertEquals("Unknown", component.getStartString());	
		assertEquals("Unknown", component.getEndString());
	}
	
	public void testDateEquality() {
		TemporalCoverage component = testConstructor(WILL_SUCCEED, TEST_NAME, TEST_START, "2050");
		assertEquals(component.getStart().toXMLFormat(), component.getStartString());
		assertEquals(component.getEnd().toXMLFormat(), component.getEndString());
	}
	
	public void testSecurityAttributes() throws InvalidDDMSException {
		TemporalCoverage component = new TemporalCoverage(TEST_NAME, TEST_START, TEST_END, SecurityAttributesTest.getFixture(false));
		assertEquals(SecurityAttributesTest.getFixture(false), component.getSecurityAttributes());		
	}
}