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
 * <p>Tests related to ddms:rights elements</p>
 * 
 * @author Brian Uri!
 * @since 0.9.b
 */
public class RightsTest extends AbstractComponentTestCase {
		
	/**
	 * Constructor
	 */
	public RightsTest() {
		super("rights.xml");
	}
	
	/**
	 * Attempts to build a component from a XOM element.
	 * @param expectFailure	true if this operation is expected to fail, false otherwise
	 * @param element	the element to build from
	 * 
	 * @return a valid object
	 */
	private Rights testConstructor(boolean expectFailure, Element element) {
		Rights component = null;
		try {
			component = new Rights(element);
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
	 * @param privacyAct the value for the privacyAct attribute
	 * @param intellectualProperty the value for the intellectualProperty attribute
	 * @param copyright the value for the copyright attribute
	 * @return a valid object
	 */
	private Rights testConstructor(boolean expectFailure, boolean privacyAct, boolean intellectualProperty, boolean copyright) {
		Rights component = null;
		try {
			component = new Rights(privacyAct, intellectualProperty, copyright);
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
		html.append("<meta name=\"rights.privacy\" content=\"true\" />\n");
		html.append("<meta name=\"rights.intellectualproperty\" content=\"true\" />\n");
		html.append("<meta name=\"rights.copy\" content=\"false\" />\n");
		return (html.toString());
	}

	/**
	 * Returns the expected Text output for this unit test
	 */
	private String getExpectedTextOutput() {
		StringBuffer text = new StringBuffer();
		text.append("Privacy Act: true\n");
		text.append("Intellectual Property Rights: true\n");
		text.append("Copyright: false\n");
		return (text.toString());
	}
	
	/**
	 * Returns the expected XML output for this unit test
	 */
	private String getExpectedXMLOutput() {
		StringBuffer xml = new StringBuffer();
		xml.append("<ddms:rights xmlns:ddms=\"").append(DDMS_NAMESPACE).append("\" ");
		xml.append("ddms:privacyAct=\"true\" ddms:intellectualProperty=\"true\" ddms:copyright=\"false\" />");
		return (xml.toString());
	}
	
	public void testName() {
		Rights component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(Rights.NAME, component.getName());
		assertEquals(Util.DDMS_PREFIX, component.getPrefix());
		assertEquals(Util.DDMS_PREFIX + ":" + Rights.NAME, component.getQualifiedName());
	}
	
	public void testElementConstructorValid() {
		// All fields
		testConstructor(WILL_SUCCEED, getValidElement());
		
		// No optional fields
		Element element = Util.buildDDMSElement(Rights.NAME, null);
		testConstructor(WILL_SUCCEED, element);
	}
		
	public void testDataConstructorValid() {
		// All fields
		testConstructor(WILL_SUCCEED, true, true, true);
	}
	
	public void testWarnings() {
		// No warnings
		Rights component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(0, component.getValidationWarnings().size());
	}
	
	public void testConstructorEquality() {
		Rights elementComponent = testConstructor(WILL_SUCCEED, getValidElement());
		Rights dataComponent = testConstructor(WILL_SUCCEED, true, true, false);
		assertEquals(elementComponent, dataComponent);
		assertEquals(elementComponent.hashCode(), dataComponent.hashCode());
	}

	public void testConstructorInequalityDifferentValues() {
		Rights elementComponent = testConstructor(WILL_SUCCEED, getValidElement());
		Rights dataComponent = testConstructor(WILL_SUCCEED, false, true, false);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, true, false, false);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, true, true, true);
		assertFalse(elementComponent.equals(dataComponent));
	}
	
	public void testConstructorInequalityWrongClass() throws InvalidDDMSException {
		Rights elementComponent = testConstructor(WILL_SUCCEED, getValidElement());
		Language wrongComponent = new Language("qualifier", "value");
		assertFalse(elementComponent.equals(wrongComponent));
	}

	public void testHTMLOutput() {
		Rights component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(getExpectedHTMLOutput(), component.toHTML());
		
		component = testConstructor(WILL_SUCCEED, true, true, false);
		assertEquals(getExpectedHTMLOutput(), component.toHTML());
	}	

	public void testTextOutput() {
		Rights component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(getExpectedTextOutput(), component.toText());
		
		component = testConstructor(WILL_SUCCEED, true, true, false);
		assertEquals(getExpectedTextOutput(), component.toText());
	}
	
	public void testXMLOutput() {
		Rights component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(getExpectedXMLOutput(), component.toXML());

		component = testConstructor(WILL_SUCCEED, true, true, false);
		assertEquals(getExpectedXMLOutput(), component.toXML());
	}
	
	public void testDefaultValues() {
		Element element = Util.buildDDMSElement(Rights.NAME, null);
		Rights component = testConstructor(WILL_SUCCEED, element);
		assertFalse(component.getPrivacyAct());
		assertFalse(component.getIntellectualProperty());
		assertFalse(component.getCopyright());
	}
}
