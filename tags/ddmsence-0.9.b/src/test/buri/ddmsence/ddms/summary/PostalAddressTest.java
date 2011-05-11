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

import java.util.ArrayList;
import java.util.List;

import nu.xom.Element;
import buri.ddmsence.ddms.AbstractComponentTestCase;
import buri.ddmsence.ddms.InvalidDDMSException;
import buri.ddmsence.ddms.resource.Rights;
import buri.ddmsence.util.Util;

/**
 * <p>Tests related to ddms:postalAddress elements</p>
 * 
 * <p>Assumes that unit testing on individual components of the ddms:countryCode tag is done separately.
 * 
 * @author Brian Uri!
 * @since 0.9.b
 */
public class PostalAddressTest extends AbstractComponentTestCase {
		
	private static final List<String> TEST_STREETS = new ArrayList<String>();
	static {
		TEST_STREETS.add("1600 Pennsylvania Avenue, NW");
	}
	private static final String TEST_CITY = "Washington";
	private static final String TEST_STATE = "DC";
	private static final String TEST_PROVINCE = "Alberta";
	private static final String TEST_POSTAL_CODE = "20500";
	private CountryCode TEST_COUNTRY_CODE;
	
	/**
	 * Constructor
	 */
	public PostalAddressTest() throws InvalidDDMSException {
		super("postalAddress.xml");
		TEST_COUNTRY_CODE = new CountryCode(PostalAddress.NAME, "ISO-3166", "USA");
	}
	
	/**
	 * Attempts to build a component from a XOM element.
	 * @param expectFailure	true if this operation is expected to fail, false otherwise
	 * @param element	the element to build from
	 * 
	 * @return a valid object
	 */
	private PostalAddress testConstructor(boolean expectFailure, Element element) {
		PostalAddress component = null;
		try {
			component = new PostalAddress(element);
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
	 * @param streets the street address lines (0-6)
	 * @param city the city (optional)
	 * @param stateOrProvince the state or province (optional)
	 * @param postalCode the postal code (optional)
	 * @param countryCode the country code (optional)
	 * @param hasState true if the stateOrProvince is a state, false if it is a province (only 1 of state or province can exist in a postalAddress)
	 * @return a valid object
	 */
	private PostalAddress testConstructor(boolean expectFailure, List<String> streets, String city, String stateOrProvince, String postalCode, CountryCode countryCode, boolean hasState) {
		PostalAddress component = null;
		try {
			component = new PostalAddress(streets, city, stateOrProvince, postalCode, countryCode, hasState);
			checkConstructorSuccess(expectFailure);
		}
		catch (InvalidDDMSException e) {
			checkConstructorFailure(expectFailure, e);
		}
		return (component);
	}
		
	/**
	 * Returns the expected HTML output for this unit test
	 * 
	 * @boolean whether this address has a state or a province
	 */
	private String getExpectedHTMLOutput(boolean hasState) {
		StringBuffer html = new StringBuffer();
		html.append("<meta name=\"geospatial.address.street\" content=\"").append(TEST_STREETS.get(0)).append("\" />\n");
		html.append("<meta name=\"geospatial.address.city\" content=\"").append(TEST_CITY).append("\" />\n");
		if (hasState)
			html.append("<meta name=\"geospatial.address.state\" content=\"").append(TEST_STATE).append("\" />\n");
		else
			html.append("<meta name=\"geospatial.address.province\" content=\"").append(TEST_PROVINCE).append("\" />\n");
		html.append("<meta name=\"geospatial.address.postalcode\" content=\"").append(TEST_POSTAL_CODE).append("\" />\n");
		html.append("<meta name=\"geospatial.address.country.qualifier\" content=\"ISO-3166\" />\n");
		html.append("<meta name=\"geospatial.address.country\" content=\"USA\" />\n");
		return (html.toString());
	}
	
	/**
	 * Returns the expected Text output for this unit test
	 * 
	 * @boolean whether this address has a state or a province
	 */
	private String getExpectedTextOutput(boolean hasState) {
		StringBuffer text = new StringBuffer();
		text.append("Postal Address Street: ").append(TEST_STREETS.get(0)).append("\n");
		text.append("Postal Address City: ").append(TEST_CITY).append("\n");
		if (hasState)
			text.append("Postal Address State: ").append(TEST_STATE).append("\n");
		else
			text.append("Postal Address Province: ").append(TEST_PROVINCE).append("\n");
		text.append("Postal Address Postal Code: ").append(TEST_POSTAL_CODE).append("\n");
		text.append("Postal Address Country Qualifier: ISO-3166\n");
		text.append("Postal Address Country: USA\n");
		return (text.toString());
	}

	/**
	 * Returns the expected XML output for this unit test
	 * 
	 * @param preserveFormatting if true, include line breaks and tabs.
	 * @boolean whether this address has a state or a province
	 */
	private String getExpectedXMLOutput(boolean preserveFormatting, boolean hasState) {
		StringBuffer xml = new StringBuffer();
		xml.append("<ddms:postalAddress xmlns:ddms=\"").append(DDMS_NAMESPACE).append("\">\n\t");
		xml.append("<ddms:street>1600 Pennsylvania Avenue, NW</ddms:street>\n\t");
		xml.append("<ddms:city>Washington</ddms:city>\n\t");
		if (hasState)
			xml.append("<ddms:state>DC</ddms:state>\n\t");
		else
			xml.append("<ddms:province>Alberta</ddms:province>\n\t");
		xml.append("<ddms:postalCode>20500</ddms:postalCode>\n\t");
		xml.append("<ddms:countryCode ddms:qualifier=\"ISO-3166\" ddms:value=\"USA\" />\n");
		xml.append("</ddms:postalAddress>");
		return (formatXml(xml.toString(), preserveFormatting));		
	}
	
	public void testName() {
		PostalAddress component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(PostalAddress.NAME, component.getName());
	}

	public void testElementConstructorValid() {
		// All fields
		testConstructor(WILL_SUCCEED, getValidElement());

		// No optional fields
		Element element = Util.buildDDMSElement(PostalAddress.NAME, null);
		testConstructor(WILL_SUCCEED, element);
	}
	
	public void testDataConstructorValid() {
		// All fields
		testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, TEST_STATE, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, true);
		
		// All fields with a province
		testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, TEST_PROVINCE, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, false);
		
		// No optional fields
		testConstructor(WILL_SUCCEED, null, null, null, null, null, false);
	}
		
	public void testElementConstructorInvalid() throws InvalidDDMSException {
		// Either a state or a province but not both.
		Element element = Util.buildDDMSElement(PostalAddress.NAME, null);
		element.appendChild(Util.buildDDMSElement("state", TEST_STATE));
		element.appendChild(Util.buildDDMSElement("province", TEST_PROVINCE));
		testConstructor(WILL_FAIL, element);
		
		// Too many streets
		element = Util.buildDDMSElement(PostalAddress.NAME, null);
		for (int i = 0; i < 7; i++) {
			element.appendChild(Util.buildDDMSElement("street", "street" + i));	
		}		
		testConstructor(WILL_FAIL, element);
		
		// Too many cities
		element = Util.buildDDMSElement(PostalAddress.NAME, null);
		for (int i = 0; i < 2; i++) {
			element.appendChild(Util.buildDDMSElement("city", "city" + i));	
		}		
		testConstructor(WILL_FAIL, element);
		
		// Too many states
		element = Util.buildDDMSElement(PostalAddress.NAME, null);
		for (int i = 0; i < 2; i++) {
			element.appendChild(Util.buildDDMSElement("state", "state" + i));	
		}		
		testConstructor(WILL_FAIL, element);
		
		// Too many provinces
		element = Util.buildDDMSElement(PostalAddress.NAME, null);
		for (int i = 0; i < 2; i++) {
			element.appendChild(Util.buildDDMSElement("province", "province" + i));	
		}		
		testConstructor(WILL_FAIL, element);
		
		// Too many postalCodes
		element = Util.buildDDMSElement(PostalAddress.NAME, null);
		for (int i = 0; i < 2; i++) {
			element.appendChild(Util.buildDDMSElement("postalCode", "postalCode" + i));	
		}		
		testConstructor(WILL_FAIL, element);
		
		// Too many country codes
		element = Util.buildDDMSElement(PostalAddress.NAME, null);
		for (int i = 0; i < 2; i++) {
			element.appendChild(new CountryCode(PostalAddress.NAME, "ISO-123" + i, "US" + i).getXOMElementCopy());	
		}
		testConstructor(WILL_FAIL, element);
	}

	public void testDataConstructorInvalid() {		
		// Too many streets
		List<String> streets = new ArrayList<String>();
		for (int i = 0; i < 7; i++)
			streets.add("Street" + i);
		testConstructor(WILL_FAIL, streets, TEST_CITY, TEST_PROVINCE, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, true);
	}
	
	public void testConstructorEquality() {
		PostalAddress elementComponent = testConstructor(WILL_SUCCEED, getValidElement());
		PostalAddress dataComponent = testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, TEST_STATE, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, true);
		assertEquals(elementComponent, dataComponent);
		assertEquals(elementComponent.hashCode(), dataComponent.hashCode());
	}

	public void testConstructorInequalityDifferentValues() {
		PostalAddress elementComponent = testConstructor(WILL_SUCCEED, getValidElement());
		PostalAddress dataComponent = testConstructor(WILL_SUCCEED, null, TEST_CITY, TEST_STATE, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, true);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, TEST_STREETS, null, TEST_STATE, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, true);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, null, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, true);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, null, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, false);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, TEST_STATE, null, TEST_COUNTRY_CODE, true);
		assertFalse(elementComponent.equals(dataComponent));
		
		dataComponent = testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, TEST_STATE, TEST_POSTAL_CODE, null, true);
		assertFalse(elementComponent.equals(dataComponent));
	}
	
	public void testConstructorInequalityWrongClass() throws InvalidDDMSException {
		PostalAddress elementComponent = testConstructor(WILL_SUCCEED, getValidElement());
		Rights wrongComponent = new Rights(true, true, true);
		assertFalse(elementComponent.equals(wrongComponent));
	}
	
	public void testHTMLOutput() {
		PostalAddress component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(getExpectedHTMLOutput(true), component.toHTML());

		component = testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, TEST_STATE, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, true);
		assertEquals(getExpectedHTMLOutput(true), component.toHTML());
		
		component = testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, TEST_PROVINCE, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, false);
		assertEquals(getExpectedHTMLOutput(false), component.toHTML());
	}	
	
	public void testTextOutput() {
		PostalAddress component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(getExpectedTextOutput(true), component.toText());

		component = testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, TEST_STATE, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, true);
		assertEquals(getExpectedTextOutput(true), component.toText());
		
		component = testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, TEST_PROVINCE, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, false);
		assertEquals(getExpectedTextOutput(false), component.toText());
	}
	
	public void testXMLOutput() {
		PostalAddress component = testConstructor(WILL_SUCCEED, getValidElement());
		assertEquals(getExpectedXMLOutput(true, true), component.toXML());

		component = testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, TEST_STATE, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, true);
		assertEquals(getExpectedXMLOutput(false, true), component.toXML());
		
		component = testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, TEST_PROVINCE, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, false);
		assertEquals(getExpectedXMLOutput(false, false), component.toXML());
	}	

	public void testCountryCodeReuse() {
		testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, TEST_STATE, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, true);
		testConstructor(WILL_SUCCEED, TEST_STREETS, TEST_CITY, TEST_STATE, TEST_POSTAL_CODE, TEST_COUNTRY_CODE, true);
	}
}