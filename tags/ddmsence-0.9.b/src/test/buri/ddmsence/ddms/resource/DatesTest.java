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
package buri.ddmsence.ddms.resource;

import nu.xom.Element;
import buri.ddmsence.ddms.AbstractComponentTestCase;
import buri.ddmsence.ddms.InvalidDDMSException;
import buri.ddmsence.util.Util;

/**
 * <p>Tests related to ddms:source elements</p>
 * 
 * @author Brian Uri!
 * @since 0.9.b
 */
public class DatesTest extends AbstractComponentTestCase {
	
	private static final String TEST_CREATED = "2003";
	private static final String TEST_POSTED = "2003-02";
	private static final String TEST_VALID = "2003-02-15";
	private static final String TEST_CUTOFF = "2001-10-31T17:00:00Z";
		
	/**
	 * Constructor
	 */
	public DatesTest() {
		super("dates.xml");
	}
	
	/**
	 * Attempts to build a component from a XOM element.
	 * @param expectFailure	true if this operation is expected to fail, false otherwise
	 * @param element	the element to build from
	 * 
	 * @return a valid object
	 */
	private Dates testConstructor(boolean expectFailure, Element element) {
		Dates component = null;
		try {
			component = new Dates(element);
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
	 * @param created the creation date (optional)
	 * @param posted the posting date (optional)
	 * @param validTil the expiration date (optional)
	 * @param infoCutOff the info cutoff date (optional)
	 * @return a valid object
	 */
	private Dates testConstructor(boolean expectFailure, String created, String posted, String validTil, String infoCutOff) {
		Dates component = null;
		try {
			component = new Dates(created, posted, validTil, infoCutOff);
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
		html.append("<meta name=\"date.created\" content=\"").append(TEST_CREATED).append("\" />\n");
		html.append("<meta name=\"date.posted\" content=\"").append(TEST_POSTED).append("\" />\n");
		html.append("<meta name=\"date.validtil\" content=\"").append(TEST_VALID).append("\" />\n");
		html.append("<meta name=\"date.infocutoff\" content=\"").append(TEST_CUTOFF).append("\" />\n");
		return (html.toString());
	}				
	
	/**
	 * Returns the expected Text output for this unit test
	 */
	private String getExpectedTextOutput() {
		StringBuffer text = new StringBuffer();
		text.append("Date Created: ").append(TEST_CREATED).append("\n");
		text.append("Date Posted: ").append(TEST_POSTED).append("\n");
		text.append("Date Valid Til: ").append(TEST_VALID).append("\n");
		text.append("Date Info Cut Off: ").append(TEST_CUTOFF).append("\n");
		return (text.toString());
	}
		
	/**
	 * Returns the expected XML output for this unit test
	 */
	private String getExpectedXMLOutput() {
		StringBuffer xml = new StringBuffer();
		xml.append("<ddms:dates xmlns:ddms=\"").append(DDMS_NAMESPACE).append("\" ");
		xml.append("ddms:created=\"").append(TEST_CREATED).append("\" ");
		xml.append("ddms:posted=\"").append(TEST_POSTED).append("\" ");
		xml.append("ddms:validTil=\"").append(TEST_VALID).append("\" ");
		xml.append("ddms:infoCutOff=\"").append(TEST_CUTOFF).append("\" />");
		return (xml.toString());							
	}
	
	public void testName() {
		Dates component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(Dates.NAME, component.getName());
	}
	
	public void testElementConstructorValid() {
		// All fields
		testConstructor(WILL_SUCCEED, getValidElement());
		
		// No optional fields
		Element element = Util.buildDDMSElement(Dates.NAME, null);
		testConstructor(WILL_SUCCEED, element);
	}
	
	public void testDataConstructorValid() {
		// All fields
		testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF);
		
		// No optional fields
		testConstructor(WILL_SUCCEED, "", "", "", "");
	}
	
	public void testElementConstructorInvalid() {
		// Wrong date format (using xs:gDay here)
		Element element = Util.buildDDMSElement(Dates.NAME, null);
		Util.addDDMSAttribute(element, "created", "---31");
		testConstructor(WILL_FAIL, element);
	}

	public void testDataConstructorInvalid() {
		// Wrong date format (using xs:gDay here)
		testConstructor(WILL_FAIL, "---31", TEST_POSTED, TEST_VALID, TEST_CUTOFF);
	}

	public void testConstructorEquality() {
		Dates elementComponent = testConstructor(WILL_SUCCEED, getValidElement());
		Dates dataComponent = testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF);
		assertEquals(elementComponent, dataComponent);
		assertEquals(elementComponent.hashCode(), dataComponent.hashCode());
	}

	public void testConstructorInequalityDifferentValues() {
		Dates elementComponent = testConstructor(WILL_SUCCEED, getValidElement());
		Dates dataComponent = testConstructor(WILL_SUCCEED, "", TEST_POSTED, TEST_VALID, TEST_CUTOFF);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, TEST_CREATED, "", TEST_VALID, TEST_CUTOFF);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, "", TEST_CUTOFF);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, TEST_VALID, "");
		assertFalse(elementComponent.equals(dataComponent));
	}
	
	public void testConstructorInequalityWrongClass() throws InvalidDDMSException {
		Dates elementComponent = testConstructor(WILL_SUCCEED, getValidElement());
		Rights wrongComponent = new Rights(true, true, true);
		assertFalse(elementComponent.equals(wrongComponent));
	}
	
	public void testHTMLOutput() {
		Dates component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(getExpectedHTMLOutput(), component.toHTML());
		
		component = testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF);
		assertEquals(getExpectedHTMLOutput(), component.toHTML());
	}	

	public void testTextOutput() {
		Dates component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(getExpectedTextOutput(), component.toText());
		
		component = testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF);
		assertEquals(getExpectedTextOutput(), component.toText());
	}
	
	public void testtXMLOutput() {
		Dates component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(getExpectedXMLOutput(), component.toXML());
		
		component = testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF);
		assertEquals(getExpectedXMLOutput(), component.toXML());
	}	
}