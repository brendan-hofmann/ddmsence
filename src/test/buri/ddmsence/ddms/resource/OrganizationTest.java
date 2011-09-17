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

import nu.xom.Attribute;
import nu.xom.Element;
import buri.ddmsence.AbstractComponentTestCase;
import buri.ddmsence.ddms.InvalidDDMSException;
import buri.ddmsence.util.DDMSVersion;
import buri.ddmsence.util.PropertyReader;
import buri.ddmsence.util.Util;

/**
 * <p>Tests related to ddms:organization elements</p>
 * 
 * @author Brian Uri!
 * @since 0.9.b
 */
public class OrganizationTest extends AbstractComponentTestCase {

	private static final List<String> TEST_NAMES = new ArrayList<String>();
	private static final List<String> TEST_PHONES = new ArrayList<String>();
	private static final List<String> TEST_EMAILS = new ArrayList<String>();
	static {
		TEST_NAMES.add("DISA");
		TEST_NAMES.add("PEO-GES");
		TEST_PHONES.add("703-882-1000");
		TEST_PHONES.add("703-885-1000");
		TEST_EMAILS.add("ddms@fgm.com");
	}

	/**
	 * Constructor
	 */
	public OrganizationTest() {
		super("organization.xml");
	}

	/**
	 * Generates an acronym for testing.
	 */
	private String getAcronym() {
		return (DDMSVersion.getCurrentVersion().isAtLeast("4.0") ? "DISA" : "");
	}

	/**
	 * Attempts to build a component from a XOM element.
	 * 
	 * @param expectFailure true if this operation is expected to fail, false otherwise
	 * @param element the element to build from
	 * 
	 * @return a valid object
	 */
	private Organization testConstructor(boolean expectFailure, Element element) {
		Organization component = null;
		try {
			component = new Organization(element);
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
	 * @param names an ordered list of names
	 * @param phones an ordered list of phone numbers
	 * @param emails an ordered list of email addresses
	 * @param subOrganizations an ordered list of suborganizations
	 * @param acronym the organization acronym
	 */
	private Organization testConstructor(boolean expectFailure, List<String> names, List<String> phones,
		List<String> emails, List<SubOrganization> subOrganizations, String acronym) {
		Organization component = null;
		try {
			component = new Organization(names, phones, emails, subOrganizations, acronym, null);
			checkConstructorSuccess(expectFailure);
		} catch (InvalidDDMSException e) {
			checkConstructorFailure(expectFailure, e);
		}
		return (component);
	}

	/**
	 * Returns the expected HTML or Text output for this unit test
	 */
	private String getExpectedOutput(boolean isHTML) throws InvalidDDMSException {
		DDMSVersion version = DDMSVersion.getCurrentVersion();
		StringBuffer text = new StringBuffer();
		text.append(buildOutput(isHTML, "entityType", Organization.getName(version)));
		for (String name : TEST_NAMES)
			text.append(buildOutput(isHTML, "name", name));
		for (String phone : TEST_PHONES)
			text.append(buildOutput(isHTML, "phone", phone));
		for (String email : TEST_EMAILS)
			text.append(buildOutput(isHTML, "email", email));
		if (version.isAtLeast("4.0")) {
			text.append(buildOutput(isHTML, "subOrganization", "sub1"));
			text.append(buildOutput(isHTML, "subOrganization.classification", "U"));
			text.append(buildOutput(isHTML, "subOrganization.ownerProducer", "USA"));
			text.append(buildOutput(isHTML, "subOrganization", "sub2"));
			text.append(buildOutput(isHTML, "subOrganization.classification", "U"));
			text.append(buildOutput(isHTML, "subOrganization.ownerProducer", "USA"));
			text.append(buildOutput(isHTML, "acronym", "DISA"));
		}
		return (text.toString());
	}

	/**
	 * Returns the expected XML output for this unit test
	 * 
	 * @param preserveFormatting if true, include line breaks and tabs.
	 */
	private String getExpectedXMLOutput(boolean preserveFormatting) {
		DDMSVersion version = DDMSVersion.getCurrentVersion();
		StringBuffer xml = new StringBuffer();
		xml.append("<ddms:").append(Organization.getName(version)).append(" ").append(getXmlnsDDMS());
		if (version.isAtLeast("4.0"))
			xml.append(" ddms:acronym=\"DISA\"");
		xml.append(">\n");
		for (String name : TEST_NAMES)
			xml.append("\t<ddms:name>").append(name).append("</ddms:name>\n");
		for (String phone : TEST_PHONES)
			xml.append("\t<ddms:phone>").append(phone).append("</ddms:phone>\n");
		for (String email : TEST_EMAILS)
			xml.append("\t<ddms:email>").append(email).append("</ddms:email>\n");
		if (version.isAtLeast("4.0")) {
			xml.append("\t<ddms:subOrganization ").append(getXmlnsISM())
				.append(" ISM:classification=\"U\" ISM:ownerProducer=\"USA\">sub1</ddms:subOrganization>\n");
			xml.append("\t<ddms:subOrganization ").append(getXmlnsISM())
				.append(" ISM:classification=\"U\" ISM:ownerProducer=\"USA\">sub2</ddms:subOrganization>\n");
		}
		xml.append("</ddms:").append(Organization.getName(version)).append(">");
		return (formatXml(xml.toString(), preserveFormatting));
	}

	public void testNameAndNamespace() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(versionString);
			Organization component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(Organization.getName(version), component.getName());
			assertEquals(PropertyReader.getProperty("ddms.prefix"), component.getPrefix());
			assertEquals(PropertyReader.getProperty("ddms.prefix") + ":" + Organization.getName(version),
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
			Element element = Util.buildDDMSElement(Organization.getName(version), null);
			element.appendChild(Util.buildDDMSElement("name", TEST_NAMES.get(0)));
			testConstructor(WILL_SUCCEED, element);
		}
	}

	public void testDataConstructorValid() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			// All fields
			testConstructor(WILL_SUCCEED, TEST_NAMES, TEST_PHONES, TEST_EMAILS, SubOrganizationTest.getFixtureList(),
				getAcronym());

			// No optional fields
			testConstructor(WILL_SUCCEED, TEST_NAMES, null, null, SubOrganizationTest.getFixtureList(), getAcronym());
		}
	}

	public void testElementConstructorInvalid() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(versionString);
			// Missing name
			Element entityElement = Util.buildDDMSElement(Organization.getName(version), null);
			testConstructor(WILL_FAIL, entityElement);

			// Empty name
			entityElement = Util.buildDDMSElement(Organization.getName(version), null);
			entityElement.appendChild(Util.buildDDMSElement("name", ""));
			testConstructor(WILL_FAIL, entityElement);
		}
	}

	public void testDataConstructorInvalid() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			// Missing name		
			testConstructor(WILL_FAIL, null, TEST_PHONES, TEST_EMAILS, SubOrganizationTest.getFixtureList(),
				getAcronym());

			// Empty name
			List<String> names = new ArrayList<String>();
			names.add("");
			testConstructor(WILL_FAIL, names, TEST_PHONES, TEST_EMAILS, SubOrganizationTest.getFixtureList(),
				getAcronym());
		}
	}

	public void testWarnings() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(versionString);
			// No warnings
			Organization component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(0, component.getValidationWarnings().size());

			// Empty acronym in DDMS 4.0
			if (version.isAtLeast("4.0")) {
				Element element = Util.buildDDMSElement(Organization.getName(version), null);
				element.appendChild(Util.buildDDMSElement("name", TEST_NAMES.get(0)));
				element.addAttribute(new Attribute("ddms:acronym", version.getNamespace(), ""));
				component = testConstructor(WILL_SUCCEED, element);
				assertEquals(1, component.getValidationWarnings().size());
				String text = "A ddms:acronym attribute was found with no value.";
				String locator = "ddms:organization";
				assertWarningEquality(text, locator, component.getValidationWarnings().get(0));
			}
		}
	}

	public void testConstructorEquality() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			Organization elementComponent = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			Organization dataComponent = testConstructor(WILL_SUCCEED, TEST_NAMES, TEST_PHONES, TEST_EMAILS,
				SubOrganizationTest.getFixtureList(), getAcronym());
			assertEquals(elementComponent, dataComponent);
			assertEquals(elementComponent.hashCode(), dataComponent.hashCode());
		}
	}

	public void testConstructorInequalityDifferentValues() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(versionString);
			Organization elementComponent = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			Organization dataComponent = testConstructor(WILL_SUCCEED, TEST_NAMES, null, TEST_EMAILS,
				SubOrganizationTest.getFixtureList(), getAcronym());
			assertFalse(elementComponent.equals(dataComponent));

			dataComponent = testConstructor(WILL_SUCCEED, TEST_NAMES, TEST_PHONES, null,
				SubOrganizationTest.getFixtureList(), getAcronym());
			assertFalse(elementComponent.equals(dataComponent));

			if (version.isAtLeast("4.0")) {
				dataComponent = testConstructor(WILL_SUCCEED, TEST_NAMES, TEST_PHONES, TEST_EMAILS, null, getAcronym());
				assertFalse(elementComponent.equals(dataComponent));

				dataComponent = testConstructor(WILL_SUCCEED, TEST_NAMES, TEST_PHONES, TEST_EMAILS,
					SubOrganizationTest.getFixtureList(), "NewACRONYM");
				assertFalse(elementComponent.equals(dataComponent));
			}
		}
	}

	public void testConstructorInequalityWrongClass() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			Organization elementComponent = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			Rights wrongComponent = new Rights(true, true, true);
			assertFalse(elementComponent.equals(wrongComponent));
		}
	}

	public void testHTMLOutput() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			Organization component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(getExpectedOutput(true), component.toHTML());

			component = testConstructor(WILL_SUCCEED, TEST_NAMES, TEST_PHONES, TEST_EMAILS,
				SubOrganizationTest.getFixtureList(), getAcronym());
			assertEquals(getExpectedOutput(true), component.toHTML());
		}
	}

	public void testTextOutput() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			Organization component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(getExpectedOutput(false), component.toText());

			component = testConstructor(WILL_SUCCEED, TEST_NAMES, TEST_PHONES, TEST_EMAILS,
				SubOrganizationTest.getFixtureList(), getAcronym());
			assertEquals(getExpectedOutput(false), component.toText());
		}
	}

	public void testXMLOutput() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			Organization component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(getExpectedXMLOutput(true), component.toXML());

			component = testConstructor(WILL_SUCCEED, TEST_NAMES, TEST_PHONES, TEST_EMAILS,
				SubOrganizationTest.getFixtureList(), getAcronym());
			assertEquals(getExpectedXMLOutput(false), component.toXML());
		}
	}

	public void testSubOrganizationReuse() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			List<SubOrganization> subOrgs = SubOrganizationTest.getFixtureList();
			testConstructor(WILL_SUCCEED, TEST_NAMES, TEST_PHONES, TEST_EMAILS, subOrgs, getAcronym());
			testConstructor(WILL_SUCCEED, TEST_NAMES, TEST_PHONES, TEST_EMAILS, subOrgs, getAcronym());
		}
	}

	public void testAcronymWrongVersion() {
		DDMSVersion.setCurrentVersion("4.0");
		Organization component = testConstructor(WILL_SUCCEED, getValidElement("4.0"));
		Organization.Builder builder = new Organization.Builder(component);
		builder.getSubOrganizations().clear();
		try {
			DDMSVersion.setCurrentVersion("3.1");
			builder.commit();
			fail("Builder allowed invalid data.");
		} catch (InvalidDDMSException e) {
			// Good
		}
	}

	public void testSubOrgWrongVersion() {
		// This test is implicit -- SubOrganization cannot even be instantiated except in DDMS 4.0 or later.
	}

	public void testBuilder() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(versionString);
			Organization component = testConstructor(WILL_SUCCEED, getValidElement(versionString));

			// Equality after Building
			Organization.Builder builder = new Organization.Builder(component);
			assertEquals(builder.commit(), component);

			// Empty case
			builder = new Organization.Builder();
			assertNull(builder.commit());

			// List Emptiness
			if (version.isAtLeast("4.0")) {
				assertTrue(builder.isEmpty());
				builder.getSubOrganizations().get(0);
				assertTrue(builder.isEmpty());
				builder.getSubOrganizations().get(0).setValue("TEST");
				assertFalse(builder.isEmpty());
			}
			// Validation
			builder = new Organization.Builder();
			builder.setPhones(Util.getXsListAsList("703-885-1000"));
			try {
				builder.commit();
				fail("Builder allowed invalid data.");
			} catch (InvalidDDMSException e) {
				// Good
			}
		}
	}

	public void testBuilderLazyList() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			Organization.Builder builder = new Organization.Builder();
			assertNotNull(builder.getNames().get(1));
			assertNotNull(builder.getPhones().get(1));
			assertNotNull(builder.getEmails().get(1));
		}
	}
}
