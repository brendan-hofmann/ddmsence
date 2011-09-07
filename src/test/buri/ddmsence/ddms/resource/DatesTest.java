/* Copyright 2010 - 2011 by Brian Uri!
   
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
import buri.ddmsence.ddms.ValidationMessage;
import buri.ddmsence.util.DDMSVersion;
import buri.ddmsence.util.PropertyReader;
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
	private static final String TEST_APPROVED = "2003-02-16";
	private static final String TEST_RECEIVED = "2003-02-17";

	/**
	 * Constructor
	 */
	public DatesTest() {
		super("dates.xml");
	}

	/**
	 * Attempts to build a component from a XOM element.
	 * 
	 * @param expectFailure true if this operation is expected to fail, false otherwise
	 * @param element the element to build from
	 * 
	 * @return a valid object
	 */
	private Dates testConstructor(boolean expectFailure, Element element) {
		Dates component = null;
		try {
			component = new Dates(element);
			checkConstructorSuccess(expectFailure);
		} catch (InvalidDDMSException e) {
			checkConstructorFailure(expectFailure, e);
		}
		return (component);
	}

	/**
	 * Helper method to create an object which is expected to be valid.
	 * 
	 * @param expectFailure true if this operation is expected to succeed, false otherwise
	 * @param created the creation date (optional)
	 * @param posted the posting date (optional)
	 * @param validTil the expiration date (optional)
	 * @param infoCutOff the info cutoff date (optional)
	 * @param approvedOn the approved on date (optional, starting in 3.1)
	 * @param receivedOn the received on date (optional, starting in 4.0)
	 * @return a valid object
	 */
	private Dates testConstructor(boolean expectFailure, String created, String posted, String validTil,
		String infoCutOff, String approvedOn, String receivedOn) {
		Dates component = null;
		try {
			component = new Dates(created, posted, validTil, infoCutOff, approvedOn, receivedOn);
			checkConstructorSuccess(expectFailure);
		} catch (InvalidDDMSException e) {
			checkConstructorFailure(expectFailure, e);
		}
		return (component);
	}

	/**
	 * Returns the expected HTML output for this unit test
	 */
	private String getExpectedHTMLOutput() {
		StringBuffer html = new StringBuffer();
		html.append("<meta name=\"dates.created\" content=\"").append(TEST_CREATED).append("\" />\n");
		html.append("<meta name=\"dates.posted\" content=\"").append(TEST_POSTED).append("\" />\n");
		html.append("<meta name=\"dates.validTil\" content=\"").append(TEST_VALID).append("\" />\n");
		html.append("<meta name=\"dates.infoCutOff\" content=\"").append(TEST_CUTOFF).append("\" />\n");
		if (isDDMS31OrGreater())
			html.append("<meta name=\"dates.approvedOn\" content=\"").append(TEST_APPROVED).append("\" />\n");
		if (isDDMS40OrGreater())
			html.append("<meta name=\"dates.receivedOn\" content=\"").append(TEST_RECEIVED).append("\" />\n");
		return (html.toString());
	}

	/**
	 * Returns the expected Text output for this unit test
	 */
	private String getExpectedTextOutput() {
		StringBuffer text = new StringBuffer();
		text.append("created: ").append(TEST_CREATED).append("\n");
		text.append("posted: ").append(TEST_POSTED).append("\n");
		text.append("validTil: ").append(TEST_VALID).append("\n");
		text.append("infoCutOff: ").append(TEST_CUTOFF).append("\n");
		if (isDDMS31OrGreater())
			text.append("approvedOn: ").append(TEST_APPROVED).append("\n");
		if (isDDMS40OrGreater())
			text.append("receivedOn: ").append(TEST_RECEIVED).append("\n");
		return (text.toString());
	}

	/**
	 * Returns the expected XML output for this unit test
	 */
	private String getExpectedXMLOutput() {
		StringBuffer xml = new StringBuffer();
		xml.append("<ddms:dates xmlns:ddms=\"").append(DDMSVersion.getCurrentVersion().getNamespace()).append("\" ");
		xml.append("ddms:created=\"").append(TEST_CREATED).append("\" ");
		xml.append("ddms:posted=\"").append(TEST_POSTED).append("\" ");
		xml.append("ddms:validTil=\"").append(TEST_VALID).append("\" ");
		xml.append("ddms:infoCutOff=\"").append(TEST_CUTOFF).append("\" ");
		if (isDDMS31OrGreater())
			xml.append("ddms:approvedOn=\"").append(TEST_APPROVED).append("\" ");
		if (isDDMS40OrGreater())
			xml.append("ddms:receivedOn=\"").append(TEST_RECEIVED).append("\" ");
		xml.append("/>");
		return (xml.toString());
	}

	public void testNameAndNamespace() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(versionString);
			Dates component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(Dates.getName(version), component.getName());
			assertEquals(PropertyReader.getProperty("ddms.prefix"), component.getPrefix());
			assertEquals(PropertyReader.getProperty("ddms.prefix") + ":" + Dates.getName(version),
				component.getQualifiedName());

			// Wrong name/namespace
			Element element = Util.buildDDMSElement("wrongName", null);
			testConstructor(WILL_FAIL, element);
		}
	}

	public void testElementConstructorValid() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(versionString);
			// All fields
			testConstructor(WILL_SUCCEED, getValidElement(versionString));

			// No optional fields
			Element element = Util.buildDDMSElement(Dates.getName(version), null);
			testConstructor(WILL_SUCCEED, element);
		}
	}

	public void testDataConstructorValid() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			String approvedOn = (isDDMS31OrGreater() ? TEST_APPROVED : "");
			String receivedOn = (isDDMS40OrGreater() ? TEST_RECEIVED : "");

			// All fields
			testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF, approvedOn, receivedOn);

			// No optional fields
			testConstructor(WILL_SUCCEED, "", "", "", "", "", "");
		}
	}

	public void testElementConstructorInvalid() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(versionString);
			// Wrong date format (using xs:gDay here)
			Element element = Util.buildDDMSElement(Dates.getName(version), null);
			Util.addDDMSAttribute(element, "created", "---31");
			testConstructor(WILL_FAIL, element);
		}
	}

	public void testDataConstructorInvalid() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			String approvedOn = (isDDMS31OrGreater() ? TEST_APPROVED : "");
			String receivedOn = (isDDMS40OrGreater() ? TEST_RECEIVED : "");

			// Wrong date format (using xs:gDay here)
			testConstructor(WILL_FAIL, "---31", TEST_POSTED, TEST_VALID, TEST_CUTOFF, approvedOn, receivedOn);
		}
	}

	public void testWarnings() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(versionString);
			// No warnings
			Dates component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(0, component.getValidationWarnings().size());

			// Empty element
			Element element = Util.buildDDMSElement(Dates.getName(version), null);
			component = testConstructor(WILL_SUCCEED, element);
			assertEquals(1, component.getValidationWarnings().size());
			assertEquals(ValidationMessage.WARNING_TYPE, component.getValidationWarnings().get(0).getType());
			assertEquals("A completely empty ddms:dates element was found.", component.getValidationWarnings().get(0)
				.getText());
			assertEquals("/ddms:dates", component.getValidationWarnings().get(0).getLocator());
		}
	}

	public void testConstructorEquality() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			String approvedOn = isDDMS31OrGreater() ? TEST_APPROVED : "";
			String receivedOn = (isDDMS40OrGreater() ? TEST_RECEIVED : "");

			Dates elementComponent = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			Dates dataComponent = testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF,
				approvedOn, receivedOn);
			assertEquals(elementComponent, dataComponent);
			assertEquals(elementComponent.hashCode(), dataComponent.hashCode());
			
			// Backwards compatible constructors
			assertEquals(new Dates(TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF, TEST_APPROVED), new Dates(
				TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF, TEST_APPROVED, null));
		}
	}

	public void testConstructorInequalityDifferentValues() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			String approvedOn = (isDDMS31OrGreater() ? TEST_APPROVED : "");
			String receivedOn = (isDDMS40OrGreater() ? TEST_RECEIVED : "");

			Dates elementComponent = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			Dates dataComponent = testConstructor(WILL_SUCCEED, "", TEST_POSTED, TEST_VALID, TEST_CUTOFF, approvedOn,
				receivedOn);
			assertFalse(elementComponent.equals(dataComponent));

			dataComponent = testConstructor(WILL_SUCCEED, TEST_CREATED, "", TEST_VALID, TEST_CUTOFF, approvedOn,
				receivedOn);
			assertFalse(elementComponent.equals(dataComponent));

			dataComponent = testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, "", TEST_CUTOFF, approvedOn,
				receivedOn);
			assertFalse(elementComponent.equals(dataComponent));

			dataComponent = testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, TEST_VALID, "", approvedOn,
				receivedOn);
			assertFalse(elementComponent.equals(dataComponent));

			if (isDDMS31OrGreater()) {
				dataComponent = testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF, "",
					receivedOn);
				assertFalse(elementComponent.equals(dataComponent));
			}

			if (isDDMS40OrGreater()) {
				dataComponent = testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF,
					approvedOn, "");
				assertFalse(elementComponent.equals(dataComponent));
			}
		}
	}

	public void testConstructorInequalityWrongClass() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			Dates elementComponent = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			Rights wrongComponent = new Rights(true, true, true);
			assertFalse(elementComponent.equals(wrongComponent));
		}
	}

	public void testHTMLOutput() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			String approvedOn = (isDDMS31OrGreater() ? TEST_APPROVED : "");
			String receivedOn = (isDDMS40OrGreater() ? TEST_RECEIVED : "");
			Dates component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(getExpectedHTMLOutput(), component.toHTML());

			component = testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF, approvedOn,
				receivedOn);
			assertEquals(getExpectedHTMLOutput(), component.toHTML());
		}
	}

	public void testTextOutput() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			String approvedOn = (isDDMS31OrGreater() ? TEST_APPROVED : "");
			String receivedOn = (isDDMS40OrGreater() ? TEST_RECEIVED : "");
			Dates component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(getExpectedTextOutput(), component.toText());

			component = testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF, approvedOn,
				receivedOn);
			assertEquals(getExpectedTextOutput(), component.toText());
		}
	}

	public void testXMLOutput() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			String approvedOn = (isDDMS31OrGreater() ? TEST_APPROVED : "");
			String receivedOn = (isDDMS40OrGreater() ? TEST_RECEIVED : "");
			Dates component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(getExpectedXMLOutput(), component.toXML());

			component = testConstructor(WILL_SUCCEED, TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF, approvedOn,
				receivedOn);
			assertEquals(getExpectedXMLOutput(), component.toXML());
		}
	}

	public void testApprovedOnWrongVersion() {
		DDMSVersion.setCurrentVersion("3.0");
		try {
			new Dates(TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF, TEST_APPROVED, null);
			fail("Allowed invalid data.");
		} catch (InvalidDDMSException e) {
			// Good
		}
	}

	public void testReceivedOnWrongVersion() {
		DDMSVersion.setCurrentVersion("3.0");
		try {
			new Dates(TEST_CREATED, TEST_POSTED, TEST_VALID, TEST_CUTOFF, null, TEST_RECEIVED);
			fail("Allowed invalid data.");
		} catch (InvalidDDMSException e) {
			// Good
		}
	}

	public void testBuilder() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			Dates component = testConstructor(WILL_SUCCEED, getValidElement(versionString));

			// Equality after Building
			Dates.Builder builder = new Dates.Builder(component);
			assertEquals(builder.commit(), component);

			// Empty case
			builder = new Dates.Builder();
			assertNull(builder.commit());

			// Validation
			builder = new Dates.Builder();
			builder.setCreated("notAnXmlDate");
			try {
				builder.commit();
				fail("Builder allowed invalid data.");
			} catch (InvalidDDMSException e) {
				// Good
			}
			builder.setCreated(TEST_CREATED);
			builder.commit();
		}
	}
}