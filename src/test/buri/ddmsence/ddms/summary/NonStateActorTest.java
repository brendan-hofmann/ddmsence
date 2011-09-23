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
package buri.ddmsence.ddms.summary;

import java.util.ArrayList;
import java.util.List;

import nu.xom.Element;
import buri.ddmsence.AbstractComponentTestCase;
import buri.ddmsence.ddms.InvalidDDMSException;
import buri.ddmsence.ddms.security.ism.SecurityAttributesTest;
import buri.ddmsence.util.DDMSVersion;
import buri.ddmsence.util.Util;

/**
 * <p>Tests related to ddms:nonStateActor elements</p>
 * 
 * @author Brian Uri!
 * @since 2.0.0
 */
public class NonStateActorTest extends AbstractComponentTestCase {

	private static final String TEST_VALUE = "Laotian Monks";
	private static final Integer TEST_ORDER = new Integer(1);

	/**
	 * Constructor
	 */
	public NonStateActorTest() {
		super("nonStateActor.xml");
		removeSupportedVersions("2.0 3.0 3.1");
	}

	/**
	 * Returns a fixture object for testing.
	 */
	public static List<NonStateActor> getFixtureList() {
		try {
			DDMSVersion version = DDMSVersion.getCurrentVersion();
			List<NonStateActor> actors = new ArrayList<NonStateActor>();
			if (version.isAtLeast("4.0"))
				actors.add(new NonStateActor("Laotian Monks", new Integer(1), SecurityAttributesTest.getFixture()));
			return (actors);
		}
		catch (InvalidDDMSException e) {
			fail("Could not create fixture: " + e.getMessage());
		}
		return (null);
	}

	/**
	 * Attempts to build a component from a XOM element.
	 * 
	 * @param expectFailure true if this operation is expected to fail, false otherwise
	 * @param element the element to build from
	 * 
	 * @return a valid object
	 */
	private NonStateActor testConstructor(boolean expectFailure, Element element) {
		NonStateActor component = null;
		try {
			component = new NonStateActor(element);
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
	 * @param expectFailure true if this operation is expected to succeed, false otherwise
	 * @param value the value of the actor (optional)
	 * @param order the order of the actor (optional)
	 * @return a valid object
	 */
	private NonStateActor testConstructor(boolean expectFailure, String value, Integer order) {
		NonStateActor component = null;
		try {
			component = new NonStateActor(value, order, SecurityAttributesTest.getFixture());
			checkConstructorSuccess(expectFailure);
		}
		catch (InvalidDDMSException e) {
			checkConstructorFailure(expectFailure, e);
		}
		return (component);
	}

	/**
	 * Returns the expected HTML or Text output for this unit test
	 */
	private String getExpectedOutput(boolean isHTML) throws InvalidDDMSException {
		StringBuffer text = new StringBuffer();
		text.append(buildOutput(isHTML, "nonStateActor.value", TEST_VALUE));
		text.append(buildOutput(isHTML, "nonStateActor.order", String.valueOf(TEST_ORDER)));
		text.append(buildOutput(isHTML, "nonStateActor.classification", "U"));
		text.append(buildOutput(isHTML, "nonStateActor.ownerProducer", "USA"));
		return (text.toString());
	}

	/**
	 * Returns the expected XML output for this unit test
	 */
	private String getExpectedXMLOutput() {
		StringBuffer xml = new StringBuffer();
		xml.append("<ddms:nonStateActor ").append(getXmlnsDDMS()).append(" ");
		xml.append(getXmlnsISM()).append(" ISM:classification=\"U\" ISM:ownerProducer=\"USA\" ");
		xml.append("ddms:order=\"").append(TEST_ORDER).append("\"");
		xml.append(">").append(TEST_VALUE).append("</ddms:nonStateActor>");
		return (xml.toString());
	}

	public void testNameAndNamespace() {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(sVersion);

			assertNameAndNamespace(testConstructor(WILL_SUCCEED, getValidElement(sVersion)), DEFAULT_DDMS_PREFIX,
				NonStateActor.getName(version));
			testConstructor(WILL_FAIL, getWrongNameElementFixture());
		}
	}

	public void testElementConstructorValid() {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(sVersion);

			// All fields
			testConstructor(WILL_SUCCEED, getValidElement(sVersion));

			// No optional fields
			Element element = Util.buildDDMSElement(NonStateActor.getName(version), null);
			testConstructor(WILL_SUCCEED, element);
		}
	}

	public void testDataConstructorValid() {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			// All fields
			testConstructor(WILL_SUCCEED, TEST_VALUE, TEST_ORDER);

			// No optional fields
			testConstructor(WILL_SUCCEED, null, null);
		}
	}

	public void testElementConstructorInvalid() {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			// There are no invalid constructors right now -- every field is optional.
		}
	}

	public void testDataConstructorInvalid() {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			// There are no invalid constructors right now -- every field is optional.
		}
	}

	public void testWarnings() {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(sVersion);

			// No warnings
			NonStateActor component = testConstructor(WILL_SUCCEED, getValidElement(sVersion));
			assertEquals(0, component.getValidationWarnings().size());

			// Empty value
			Element element = Util.buildDDMSElement(NonStateActor.getName(version), null);
			component = testConstructor(WILL_SUCCEED, element);
			assertEquals(1, component.getValidationWarnings().size());
			String text = "A ddms:nonStateActor element was found with no value.";
			String locator = "ddms:nonStateActor";
			assertWarningEquality(text, locator, component.getValidationWarnings().get(0));
		}
	}

	public void testConstructorEquality() throws InvalidDDMSException {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			NonStateActor elementComponent = testConstructor(WILL_SUCCEED, getValidElement(sVersion));
			NonStateActor dataComponent = testConstructor(WILL_SUCCEED, TEST_VALUE, TEST_ORDER);

			assertEquals(elementComponent, dataComponent);
			assertEquals(elementComponent.hashCode(), dataComponent.hashCode());
		}
	}

	public void testConstructorInequalityDifferentValues() {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			NonStateActor elementComponent = testConstructor(WILL_SUCCEED, getValidElement(sVersion));
			NonStateActor dataComponent = testConstructor(WILL_SUCCEED, DIFFERENT_VALUE, TEST_ORDER);
			assertFalse(elementComponent.equals(dataComponent));

			dataComponent = testConstructor(WILL_SUCCEED, TEST_VALUE, null);
			assertFalse(elementComponent.equals(dataComponent));
		}
	}

	public void testHTMLTextOutput() throws InvalidDDMSException {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			NonStateActor component = testConstructor(WILL_SUCCEED, getValidElement(sVersion));
			assertEquals(getExpectedOutput(true), component.toHTML());
			assertEquals(getExpectedOutput(false), component.toText());

			component = testConstructor(WILL_SUCCEED, TEST_VALUE, TEST_ORDER);
			assertEquals(getExpectedOutput(true), component.toHTML());
			assertEquals(getExpectedOutput(false), component.toText());
		}
	}

	public void testXMLOutput() {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			NonStateActor component = testConstructor(WILL_SUCCEED, getValidElement(sVersion));
			assertEquals(getExpectedXMLOutput(), component.toXML());

			component = testConstructor(WILL_SUCCEED, TEST_VALUE, TEST_ORDER);
			assertEquals(getExpectedXMLOutput(), component.toXML());
		}
	}

	public void testWrongVersion() {
		try {
			DDMSVersion.setCurrentVersion("2.0");
			new NonStateActor(TEST_VALUE, TEST_ORDER, null);
			fail("Allowed invalid data.");
		}
		catch (InvalidDDMSException e) {
			// Good
		}
	}

	public void testBuilder() throws InvalidDDMSException {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			NonStateActor component = testConstructor(WILL_SUCCEED, getValidElement(sVersion));

			NonStateActor.Builder builder = new NonStateActor.Builder();
			assertNull(builder.commit());

			// Equality after Building
			builder = new NonStateActor.Builder(component);
			assertEquals(builder.commit(), component);
		}
	}
}
