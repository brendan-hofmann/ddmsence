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
import buri.ddmsence.ddms.resource.Rights;
import buri.ddmsence.util.DDMSVersion;
import buri.ddmsence.util.PropertyReader;
import buri.ddmsence.util.Util;

/**
 * <p>Tests related to ddms:link elements</p>
 * 
 * <p>
 * Because a ddms:link is a local component, we cannot load a valid document from a unit test data file. We have to
 * build the well-formed Element ourselves.
 * </p>
 * 
 * @author Brian Uri!
 * @since 0.9.b
 */
public class LinkTest extends AbstractComponentTestCase {
	
	private static final String TEST_TYPE = "locator";
	private static final String TEST_HREF = "http://en.wikipedia.org/wiki/Tank";
	private static final String TEST_ROLE = "tank";
	private static final String TEST_TITLE = "Tank Page";
	private static final String TEST_LABEL = "tank";

	private static final String XLINK_PREFIX = PropertyReader.getProperty("xlink.prefix");
	private static final String XLINK_NAMESPACE = PropertyReader.getProperty("xlink.xmlNamespace");
	
	/**
	 * Constructor
	 */
	public LinkTest() {
		super(null);
	}
	
	/**
	 * Returns a canned fixed value link for testing.
	 * 
	 * @return a XOM element representing a valid link
	 */
	protected static Element getFixtureElement() throws InvalidDDMSException {
		Element linkElement = Util.buildDDMSElement(Link.NAME, null);
		linkElement.addNamespaceDeclaration(Util.DDMS_PREFIX, DDMSVersion.getCurrentVersion().getNamespace());
		linkElement.addNamespaceDeclaration(XLINK_PREFIX, XLINK_NAMESPACE);
		linkElement.addAttribute(Util.buildAttribute(XLINK_PREFIX, "type", XLINK_NAMESPACE, TEST_TYPE));
		linkElement.addAttribute(Util.buildAttribute(XLINK_PREFIX, "href", XLINK_NAMESPACE, TEST_HREF));
		linkElement.addAttribute(Util.buildAttribute(XLINK_PREFIX, "role", XLINK_NAMESPACE, TEST_ROLE));
		linkElement.addAttribute(Util.buildAttribute(XLINK_PREFIX, "title", XLINK_NAMESPACE, TEST_TITLE));
		linkElement.addAttribute(Util.buildAttribute(XLINK_PREFIX, "label", XLINK_NAMESPACE, TEST_LABEL));
		return (linkElement);
	}
	
	/**
	 * Helper method to create an object which is expected to be valid.
	 * 
	 * @param element	the element to build from
	 * @return a valid object
	 */
	private Link testConstructor(boolean expectFailure, Element element) {
		Link component = null;
		try {
			component = new Link(element);
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
	 * @param href	the link href (required)
	 * @param role	the role attribute (optional)
	 * @param title the link title (optional)
	 * @param label the name of the link (optional)
	 * @return a valid object
	 */
	private Link testConstructor(boolean expectFailure, String href, String role, String title, String label) {
		Link component = null;
		try {
			component = new Link(href, role, title, label);
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
		html.append("<meta name=\"Link.type\" content=\"").append(TEST_TYPE).append("\" />\n");
		html.append("<meta name=\"Link.href\" content=\"").append(TEST_HREF).append("\" />\n");
		html.append("<meta name=\"Link.role\" content=\"").append(TEST_ROLE).append("\" />\n");
		html.append("<meta name=\"Link.title\" content=\"").append(TEST_TITLE).append("\" />\n");
		html.append("<meta name=\"Link.label\" content=\"").append(TEST_LABEL).append("\" />\n");
		return (html.toString());
	}
	
	/**
	 * Returns the expected Text output for this unit test
	 */
	private String getExpectedTextOutput() {
		StringBuffer text = new StringBuffer();
		text.append("Link type: ").append(TEST_TYPE).append("\n");
		text.append("Link href: ").append(TEST_HREF).append("\n");
		text.append("Link role: ").append(TEST_ROLE).append("\n");
		text.append("Link title: ").append(TEST_TITLE).append("\n");
		text.append("Link label: ").append(TEST_LABEL).append("\n");
		return (text.toString());
	}
	
	/**
	 * Returns the expected XML output for this unit test
	 */
	private String getExpectedXMLOutput() {
		StringBuffer xml = new StringBuffer();
		xml.append("<ddms:link xmlns:ddms=\"").append(DDMSVersion.getCurrentVersion().getNamespace()).append("\" xmlns:xlink=\"").append(XLINK_NAMESPACE).append("\" ");
		xml.append("xlink:type=\"").append(TEST_TYPE).append("\" ");
		xml.append("xlink:href=\"").append(TEST_HREF).append("\" ");
		xml.append("xlink:role=\"").append(TEST_ROLE).append("\" ");
		xml.append("xlink:title=\"").append(TEST_TITLE).append("\" ");
		xml.append("xlink:label=\"").append(TEST_LABEL).append("\" />");
		return (xml.toString());
	}
	
	public void testNameAndNamespace() throws InvalidDDMSException {
		Link component = testConstructor(WILL_SUCCEED, getFixtureElement());
		assertEquals(Link.NAME, component.getName());
		assertEquals(Util.DDMS_PREFIX, component.getPrefix());
		assertEquals(Util.DDMS_PREFIX + ":" + Link.NAME, component.getQualifiedName());
		
		// Wrong name/namespace
		Element element = Util.buildDDMSElement("wrongName", null);
		testConstructor(WILL_FAIL, element);
	}
	
	public void testElementConstructorValid() throws InvalidDDMSException {
		// All fields
		testConstructor(WILL_SUCCEED, getFixtureElement());
		
		// No optional fields
		Element element = Util.buildDDMSElement(Link.NAME, null);
		element.addNamespaceDeclaration(Util.DDMS_PREFIX, DDMSVersion.getCurrentVersion().getNamespace());
		element.addNamespaceDeclaration(XLINK_PREFIX, XLINK_NAMESPACE);
		element.addAttribute(Util.buildAttribute(XLINK_PREFIX, "type", XLINK_NAMESPACE, TEST_TYPE));
		element.addAttribute(Util.buildAttribute(XLINK_PREFIX, "href", XLINK_NAMESPACE, TEST_HREF));
		testConstructor(WILL_SUCCEED, element);
	}
	
	public void testDataConstructorValid() {
		// All fields
		testConstructor(WILL_SUCCEED, TEST_HREF, TEST_ROLE, TEST_TITLE, TEST_LABEL);
		
		// No optional fields
		testConstructor(WILL_SUCCEED, TEST_HREF, null, null, null);
	}
	
	public void testElementConstructorInvalid() {
		// Missing href
		Element element = Util.buildDDMSElement(Link.NAME, null);
		element.addAttribute(Util.buildAttribute(XLINK_PREFIX, "type", XLINK_NAMESPACE, TEST_TYPE));
		testConstructor(WILL_FAIL, element);
		
		// href not URI
		element = Util.buildDDMSElement(Link.NAME, null);
		element.addAttribute(Util.buildAttribute(XLINK_PREFIX, "type", XLINK_NAMESPACE, TEST_TYPE));
		element.addAttribute(Util.buildAttribute(XLINK_PREFIX, "href", XLINK_NAMESPACE, INVALID_URI));
		testConstructor(WILL_FAIL, element);
		
		// invalid type
		element = Util.buildDDMSElement(Link.NAME, null);
		element.addAttribute(Util.buildAttribute(XLINK_PREFIX, "type", XLINK_NAMESPACE, "type"));
		element.addAttribute(Util.buildAttribute(XLINK_PREFIX, "href", XLINK_NAMESPACE, TEST_HREF));
		testConstructor(WILL_FAIL, element);
	}
	
	public void testDataConstructorInvalid() {
		// Missing href
		testConstructor(WILL_FAIL, "", TEST_ROLE, TEST_TITLE, TEST_LABEL);
		
		// href not URI
		testConstructor(WILL_FAIL, INVALID_URI, TEST_ROLE, TEST_TITLE, TEST_LABEL);
	}
	
	public void testWarnings() throws InvalidDDMSException {
		// No warnings
		Link component = testConstructor(WILL_SUCCEED, getFixtureElement());
		assertEquals(0, component.getValidationWarnings().size());
	}
	
	public void testConstructorEquality() throws InvalidDDMSException {
		Link elementComponent = testConstructor(WILL_SUCCEED, getFixtureElement());
		Link dataComponent = testConstructor(WILL_SUCCEED, TEST_HREF, TEST_ROLE, TEST_TITLE, TEST_LABEL);
		assertEquals(elementComponent, dataComponent);
		assertEquals(elementComponent.hashCode(), dataComponent.hashCode());
	}
	
	public void testConstructorInequalityDifferentValues() throws InvalidDDMSException {
		Link elementComponent = testConstructor(WILL_SUCCEED, getFixtureElement());
		Link dataComponent = testConstructor(WILL_SUCCEED, DIFFERENT_VALUE, TEST_ROLE, TEST_TITLE, TEST_LABEL);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, TEST_HREF, DIFFERENT_VALUE, TEST_TITLE, TEST_LABEL);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, TEST_HREF, TEST_ROLE, DIFFERENT_VALUE, TEST_LABEL);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, TEST_HREF, TEST_ROLE, TEST_TITLE, DIFFERENT_VALUE);
		assertFalse(elementComponent.equals(dataComponent));
	}
	
	public void testConstructorInequalityWrongClass() throws InvalidDDMSException {
		Link elementComponent = testConstructor(WILL_SUCCEED, getFixtureElement());
		Rights wrongComponent = new Rights(true, true, true);
		assertFalse(elementComponent.equals(wrongComponent));
	}

	public void testHTMLOutput() throws InvalidDDMSException {
		Link component = testConstructor(WILL_SUCCEED, getFixtureElement());
		assertEquals(getExpectedHTMLOutput(), component.toHTML());
		
		component = testConstructor(WILL_SUCCEED, TEST_HREF, TEST_ROLE, TEST_TITLE, TEST_LABEL);
		assertEquals(getExpectedHTMLOutput(), component.toHTML());
	}	

	public void testTextOutput() throws InvalidDDMSException {
		Link component = testConstructor(WILL_SUCCEED, getFixtureElement());
		assertEquals(getExpectedTextOutput(), component.toText());
		
		component = testConstructor(WILL_SUCCEED, TEST_HREF, TEST_ROLE, TEST_TITLE, TEST_LABEL);
		assertEquals(getExpectedTextOutput(), component.toText());
	}
	
	public void testXMLOutput() throws InvalidDDMSException {
		Link component = testConstructor(WILL_SUCCEED, getFixtureElement());
		assertEquals(getExpectedXMLOutput(), component.toXML());

		component = testConstructor(WILL_SUCCEED, TEST_HREF, TEST_ROLE, TEST_TITLE, TEST_LABEL);
		assertEquals(getExpectedXMLOutput(), component.toXML());
	}
}
