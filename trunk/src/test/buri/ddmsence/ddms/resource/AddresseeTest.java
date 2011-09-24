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

import java.util.ArrayList;
import java.util.List;

import nu.xom.Element;
import buri.ddmsence.AbstractComponentTestCase;
import buri.ddmsence.ddms.IRoleEntity;
import buri.ddmsence.ddms.InvalidDDMSException;
import buri.ddmsence.ddms.security.ism.SecurityAttributesTest;
import buri.ddmsence.util.DDMSVersion;
import buri.ddmsence.util.PropertyReader;
import buri.ddmsence.util.Util;

/**
 * <p>Tests related to ddms:addressee elements</p>
 * 
 * <p> Because a ddms:addressee is a local component, we cannot load a valid document from a unit test data file. We
 * have to build the well-formed Element ourselves. </p>
 * 
 * @author Brian Uri!
 * @since 2.0.0
 */
public class AddresseeTest extends AbstractComponentTestCase {

	/**
	 * Constructor
	 */
	public AddresseeTest() {
		super(null);
		removeSupportedVersions("2.0 3.0 3.1");
	}

	/**
	 * Returns a fixture object for testing.
	 * 
	 * @param useOrg true to put an organization in, false for a person
	 */
	public static Element getFixtureElement(boolean useOrg) {
		try {
			DDMSVersion version = DDMSVersion.getCurrentVersion();
			Element element = Util.buildDDMSElement(Addressee.getName(version), null);
			element.addNamespaceDeclaration(PropertyReader.getPrefix("ddms"), version.getNamespace());
			element.appendChild(useOrg ? OrganizationTest.getFixture().getXOMElementCopy() : PersonTest.getFixture().getXOMElementCopy());
			SecurityAttributesTest.getFixture().addTo(element);
			return (element);
		}
		catch (InvalidDDMSException e) {
			fail("Could not create fixture: " + e.getMessage());
		}
		return (null);
	}
	

	/**
	 * Returns a fixture object for testing.
	 */
	public static List<Addressee> getFixtureList() {
		try {
			List<Addressee> list = new ArrayList<Addressee>();
			list.add(new Addressee(getFixtureElement(true)));
			return (list);
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
	private Addressee testConstructor(boolean expectFailure, Element element) {
		Addressee component = null;
		try {
			component = new Addressee(element);
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
	 * @param entity the person or organization in this role
	 * @param org the organization
	 */
	private Addressee testConstructor(boolean expectFailure, IRoleEntity entity) {
		Addressee component = null;
		try {
			component = new Addressee(entity, SecurityAttributesTest.getFixture());
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
		text.append(buildOutput(isHTML, "addressee.entityType", "organization"));
		text.append(buildOutput(isHTML, "addressee.name", "DISA"));
		text.append(buildOutput(isHTML, "addressee.classification", "U"));
		text.append(buildOutput(isHTML, "addressee.ownerProducer", "USA"));
		return (text.toString());
	}

	/**
	 * Returns the expected XML output for this unit test
	 */
	private String getExpectedXMLOutput() {
		StringBuffer xml = new StringBuffer();
		xml.append("<ddms:addressee ").append(getXmlnsDDMS()).append(" ").append(getXmlnsISM()).append(" ");
		xml.append("ISM:classification=\"U\" ISM:ownerProducer=\"USA\">");
		xml.append("<ddms:organization><ddms:name>DISA</ddms:name></ddms:organization>");
		xml.append("</ddms:addressee>");
		return (xml.toString());
	}

	public void testNameAndNamespace() throws InvalidDDMSException {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(sVersion);

			assertNameAndNamespace(testConstructor(WILL_SUCCEED, getFixtureElement(true)), DEFAULT_DDMS_PREFIX,
				Addressee.getName(version));
			testConstructor(WILL_FAIL, getWrongNameElementFixture());
		}
	}

	public void testElementConstructorValid() throws InvalidDDMSException {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			// All fields, organization
			testConstructor(WILL_SUCCEED, getFixtureElement(true));

			// All fields, person
			testConstructor(WILL_SUCCEED, getFixtureElement(false));
		}
	}

	public void testDataConstructorValid() throws InvalidDDMSException {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			// All fields, organization
			testConstructor(WILL_SUCCEED, OrganizationTest.getFixture());

			// All fields, person
			testConstructor(WILL_SUCCEED, PersonTest.getFixture());
		}
	}

	public void testElementConstructorInvalid() throws InvalidDDMSException {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(sVersion);

			// Missing entity
			Element element = Util.buildDDMSElement(Addressee.getName(version), null);
			SecurityAttributesTest.getFixture().addTo(element);
			testConstructor(WILL_FAIL, element);

			// Missing security attributes
			element = Util.buildDDMSElement(Addressee.getName(version), null);
			element.appendChild(OrganizationTest.getFixture().getXOMElementCopy());
			testConstructor(WILL_FAIL, element);
		}
	}

	public void testDataConstructorInvalid() throws InvalidDDMSException {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			// Missing entity		
			testConstructor(WILL_FAIL, (IRoleEntity) null);

			// Wrong entity
			testConstructor(WILL_FAIL, new Service(Util.getXsListAsList("Name"), null, null));

			// Missing security attributes
			try {
				new Addressee(OrganizationTest.getFixture(), null);
				fail("Allowed invalid data.");
			}
			catch (InvalidDDMSException e) {
				// Good
			}
		}
	}

	public void testWarnings() throws InvalidDDMSException {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			// No warnings
			Addressee component = testConstructor(WILL_SUCCEED, getFixtureElement(true));
			assertEquals(0, component.getValidationWarnings().size());
		}
	}

	public void testConstructorEquality() throws InvalidDDMSException {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			Addressee elementComponent = testConstructor(WILL_SUCCEED, getFixtureElement(true));
			Addressee dataComponent = testConstructor(WILL_SUCCEED, OrganizationTest.getFixture());
			assertEquals(elementComponent, dataComponent);
			assertEquals(elementComponent.hashCode(), dataComponent.hashCode());
		}
	}

	public void testConstructorInequalityDifferentValues() throws InvalidDDMSException {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			Addressee elementComponent = testConstructor(WILL_SUCCEED, getFixtureElement(true));
			Addressee dataComponent = testConstructor(WILL_SUCCEED, PersonTest.getFixture());
			assertFalse(elementComponent.equals(dataComponent));
		}
	}

	public void testHTMLTextOutput() throws InvalidDDMSException {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			Addressee component = testConstructor(WILL_SUCCEED, getFixtureElement(true));
			assertEquals(getExpectedOutput(true), component.toHTML());
			assertEquals(getExpectedOutput(false), component.toText());

			component = testConstructor(WILL_SUCCEED, OrganizationTest.getFixture());
			assertEquals(getExpectedOutput(true), component.toHTML());
			assertEquals(getExpectedOutput(false), component.toText());
		}
	}

	public void testXMLOutput() throws InvalidDDMSException {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			Addressee component = testConstructor(WILL_SUCCEED, getFixtureElement(true));
			assertEquals(getExpectedXMLOutput(), component.toXML());

			component = testConstructor(WILL_SUCCEED, OrganizationTest.getFixture());
			assertEquals(getExpectedXMLOutput(), component.toXML());
		}
	}

	public void test20Usage() {
		try {
			DDMSVersion.setCurrentVersion("2.0");
			new Addressee(OrganizationTest.getFixture(), SecurityAttributesTest.getFixture());
			fail("Allowed invalid data.");
		}
		catch (InvalidDDMSException e) {
			// Good
		}
	}

	public void testBuilder() throws InvalidDDMSException {
		for (String sVersion : getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(sVersion);

			// Equality after Building, organization
			Addressee component = testConstructor(WILL_SUCCEED, getFixtureElement(true));
			Addressee.Builder builder = new Addressee.Builder(component);
			assertEquals(builder.commit(), component);

			// Equality after Building, person
			component = testConstructor(WILL_SUCCEED, getFixtureElement(false));
			builder = new Addressee.Builder(component);
			assertEquals(builder.commit(), component);

			// Empty case
			builder = new Addressee.Builder();
			assertNull(builder.commit());

			// Validation
			builder = new Addressee.Builder();
			builder.getPerson().setNames(Util.getXsListAsList("Name"));
			builder.getPerson().setSurname("Surname");
			try {
				builder.commit();
				fail("Builder allowed invalid data.");
			}
			catch (InvalidDDMSException e) {
				// Good
			}
			builder.getSecurityAttributes().setClassification("U");
			builder.getSecurityAttributes().setOwnerProducers(Util.getXsListAsList("USA"));
			builder.commit();
		}
	}
}
