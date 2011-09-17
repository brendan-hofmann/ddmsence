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

import nu.xom.Element;
import buri.ddmsence.AbstractComponentTestCase;
import buri.ddmsence.ddms.InvalidDDMSException;
import buri.ddmsence.ddms.resource.Rights;
import buri.ddmsence.util.DDMSVersion;
import buri.ddmsence.util.PropertyReader;
import buri.ddmsence.util.Util;

/**
 * <p>Tests related to ddms:verticalExtent elements</p>
 * 
 * @author Brian Uri!
 * @since 0.9.b
 */
public class VerticalExtentTest extends AbstractComponentTestCase {

	private static final String TEST_UOM = "Meter";
	private static final String TEST_DATUM = "AGL";
	private static final double TEST_MIN = 0.1;
	private static final double TEST_MAX = 100.1;

	/**
	 * Constructor
	 */
	public VerticalExtentTest() {
		super("verticalExtent.xml");
	}

	/**
	 * Returns a verticalExtent fixture
	 */
	protected static VerticalExtent getFixture() throws InvalidDDMSException {
		return (new VerticalExtent(1.1, 2.2, "Meter", "HAE"));
	}

	/**
	 * Attempts to build a component from a XOM element.
	 * 
	 * @param expectFailure true if this operation is expected to fail, false otherwise
	 * @param element the element to build from
	 * 
	 * @return a valid object
	 */
	private VerticalExtent testConstructor(boolean expectFailure, Element element) {
		VerticalExtent component = null;
		try {
			component = new VerticalExtent(element);
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
	 * @param minVerticalExtent the minimum (required)
	 * @param maxVerticalExtent the maximum (required)
	 * @param unitOfMeasure the unit of measure (required)
	 * @param datum the datum (required)
	 * @return a valid object
	 */
	private VerticalExtent testConstructor(boolean expectFailure, double minVerticalExtent, double maxVerticalExtent,
		String unitOfMeasure, String datum) {
		VerticalExtent component = null;
		try {
			component = new VerticalExtent(minVerticalExtent, maxVerticalExtent, unitOfMeasure, datum);
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
		StringBuffer text = new StringBuffer();
		text.append(buildOutput(isHTML, "verticalExtent.unitOfMeasure", String.valueOf(TEST_UOM)));
		text.append(buildOutput(isHTML, "verticalExtent.datum", String.valueOf(TEST_DATUM)));
		text.append(buildOutput(isHTML, "verticalExtent.minimum", String.valueOf(TEST_MIN)));
		text.append(buildOutput(isHTML, "verticalExtent.maximum", String.valueOf(TEST_MAX)));
		return (text.toString());
	}

	/**
	 * Helper method to get the correct-case of the minVerticalExtent eleemnt.
	 */
	private String getMinVerticalExtentName() {
		return (DDMSVersion.getCurrentVersion().isAtLeast("4.0") ? "minVerticalExtent" : "MinVerticalExtent");
	}

	/**
	 * Helper method to get the correct-case of the maxVerticalExtent eleemnt.
	 */
	private String getMaxVerticalExtentName() {
		return (DDMSVersion.getCurrentVersion().isAtLeast("4.0") ? "maxVerticalExtent" : "MaxVerticalExtent");
	}

	/**
	 * Returns the expected XML output for this unit test
	 * 
	 * @param preserveFormatting if true, include line breaks and tabs.
	 */
	private String getExpectedXMLOutput(boolean preserveFormatting) {
		StringBuffer xml = new StringBuffer();
		xml.append("<ddms:verticalExtent ").append(getXmlnsDDMS()).append(" ");
		xml.append("ddms:unitOfMeasure=\"").append(TEST_UOM).append("\" ");
		xml.append("ddms:datum=\"").append(TEST_DATUM).append("\">\n\t");
		xml.append("<ddms:").append(getMinVerticalExtentName()).append(">").append(TEST_MIN).append("</ddms:")
			.append(getMinVerticalExtentName()).append(">\n\t");
		xml.append("<ddms:").append(getMaxVerticalExtentName()).append(">").append(TEST_MAX).append("</ddms:")
			.append(getMaxVerticalExtentName()).append(">\n");
		xml.append("</ddms:verticalExtent>");
		return (formatXml(xml.toString(), preserveFormatting));
	}

	public void testNameAndNamespace() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(versionString);
			VerticalExtent component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(VerticalExtent.getName(version), component.getName());
			assertEquals(PropertyReader.getPrefix("ddms"), component.getPrefix());
			assertEquals(PropertyReader.getPrefix("ddms") + ":" + VerticalExtent.getName(version),
				component.getQualifiedName());

			// Wrong name/namespace
			Element element = Util.buildDDMSElement("wrongName", null);
			testConstructor(WILL_FAIL, element);
		}
	}

	public void testElementConstructorValid() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			testConstructor(WILL_SUCCEED, getValidElement(versionString));
		}
	}

	public void testDataConstructorValid() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			testConstructor(WILL_SUCCEED, TEST_MIN, TEST_MAX, TEST_UOM, TEST_DATUM);
		}
	}

	public void testElementConstructorInvalid() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion version = DDMSVersion.setCurrentVersion(versionString);
			String extentName = VerticalExtent.getName(version);
			// Missing UOM
			Element element = Util.buildDDMSElement(extentName, null);
			Util.addDDMSAttribute(element, "datum", TEST_DATUM);
			element.appendChild(Util.buildDDMSElement(getMinVerticalExtentName(), String.valueOf(TEST_MIN)));
			element.appendChild(Util.buildDDMSElement(getMaxVerticalExtentName(), String.valueOf(TEST_MAX)));
			testConstructor(WILL_FAIL, element);

			// Invalid UOM
			element = Util.buildDDMSElement(extentName, null);
			Util.addDDMSAttribute(element, "unitOfMeasure", "furlong");
			Util.addDDMSAttribute(element, "datum", TEST_DATUM);
			element.appendChild(Util.buildDDMSElement(getMinVerticalExtentName(), String.valueOf(TEST_MIN)));
			element.appendChild(Util.buildDDMSElement(getMaxVerticalExtentName(), String.valueOf(TEST_MAX)));
			testConstructor(WILL_FAIL, element);

			// Missing Datum
			element = Util.buildDDMSElement(extentName, null);
			Util.addDDMSAttribute(element, "unitOfMeasure", TEST_UOM);
			element.appendChild(Util.buildDDMSElement(getMinVerticalExtentName(), String.valueOf(TEST_MIN)));
			element.appendChild(Util.buildDDMSElement(getMaxVerticalExtentName(), String.valueOf(TEST_MAX)));
			testConstructor(WILL_FAIL, element);

			// Invalid Datum
			element = Util.buildDDMSElement(extentName, null);
			Util.addDDMSAttribute(element, "unitOfMeasure", TEST_UOM);
			Util.addDDMSAttribute(element, "datum", "PDQ");
			element.appendChild(Util.buildDDMSElement(getMinVerticalExtentName(), String.valueOf(TEST_MIN)));
			element.appendChild(Util.buildDDMSElement(getMaxVerticalExtentName(), String.valueOf(TEST_MAX)));
			testConstructor(WILL_FAIL, element);

			// Missing MinVerticalExtent
			element = Util.buildDDMSElement(extentName, null);
			Util.addDDMSAttribute(element, "unitOfMeasure", TEST_UOM);
			Util.addDDMSAttribute(element, "datum", TEST_DATUM);
			element.appendChild(Util.buildDDMSElement(getMaxVerticalExtentName(), String.valueOf(TEST_MAX)));
			testConstructor(WILL_FAIL, element);

			// Missing MaxVerticalExtent
			element = Util.buildDDMSElement(extentName, null);
			Util.addDDMSAttribute(element, "unitOfMeasure", TEST_UOM);
			Util.addDDMSAttribute(element, "datum", TEST_DATUM);
			element.appendChild(Util.buildDDMSElement(getMinVerticalExtentName(), String.valueOf(TEST_MIN)));
			testConstructor(WILL_FAIL, element);

			// MinVerticalExtent UOM doesn't match parent
			Element minElement = Util.buildDDMSElement(getMinVerticalExtentName(), String.valueOf(TEST_MIN));
			Util.addDDMSAttribute(minElement, "unitOfMeasure", "Inch");
			element = Util.buildDDMSElement(extentName, null);
			Util.addDDMSAttribute(element, "unitOfMeasure", TEST_UOM);
			Util.addDDMSAttribute(element, "datum", TEST_DATUM);
			element.appendChild(minElement);
			element.appendChild(Util.buildDDMSElement(getMaxVerticalExtentName(), String.valueOf(TEST_MAX)));
			testConstructor(WILL_FAIL, element);

			// MinVerticalExtent Datum doesn't match parent
			minElement = Util.buildDDMSElement(getMinVerticalExtentName(), String.valueOf(TEST_MIN));
			Util.addDDMSAttribute(minElement, "datum", "PDQ");
			element = Util.buildDDMSElement(extentName, null);
			Util.addDDMSAttribute(element, "unitOfMeasure", TEST_UOM);
			Util.addDDMSAttribute(element, "datum", TEST_DATUM);
			element.appendChild(minElement);
			element.appendChild(Util.buildDDMSElement(getMaxVerticalExtentName(), String.valueOf(TEST_MAX)));
			testConstructor(WILL_FAIL, element);

			// MaxVerticalExtent UOM doesn't match parent
			Element maxElement = Util.buildDDMSElement(getMaxVerticalExtentName(), String.valueOf(TEST_MAX));
			Util.addDDMSAttribute(maxElement, "unitOfMeasure", "Inch");
			element = Util.buildDDMSElement(extentName, null);
			Util.addDDMSAttribute(element, "unitOfMeasure", TEST_UOM);
			Util.addDDMSAttribute(element, "datum", TEST_DATUM);
			element.appendChild(maxElement);
			element.appendChild(Util.buildDDMSElement(getMinVerticalExtentName(), String.valueOf(TEST_MIN)));
			testConstructor(WILL_FAIL, element);

			// MaxVerticalExtent Datum doesn't match parent
			maxElement = Util.buildDDMSElement(getMaxVerticalExtentName(), String.valueOf(TEST_MAX));
			Util.addDDMSAttribute(maxElement, "datum", "PDQ");
			element = Util.buildDDMSElement(extentName, null);
			Util.addDDMSAttribute(element, "unitOfMeasure", TEST_UOM);
			Util.addDDMSAttribute(element, "datum", TEST_DATUM);
			element.appendChild(maxElement);
			element.appendChild(Util.buildDDMSElement(getMinVerticalExtentName(), String.valueOf(TEST_MIN)));
			testConstructor(WILL_FAIL, element);

			// MinVerticalExtent is not less than MaxVerticalExtent
			element = Util.buildDDMSElement(extentName, null);
			Util.addDDMSAttribute(element, "unitOfMeasure", TEST_UOM);
			Util.addDDMSAttribute(element, "datum", TEST_DATUM);
			element.appendChild(Util.buildDDMSElement(getMinVerticalExtentName(), String.valueOf(TEST_MAX)));
			element.appendChild(Util.buildDDMSElement(getMaxVerticalExtentName(), String.valueOf(TEST_MIN)));
			testConstructor(WILL_FAIL, element);

			// Not Double
			element = Util.buildDDMSElement(extentName, null);
			Util.addDDMSAttribute(element, "unitOfMeasure", TEST_UOM);
			Util.addDDMSAttribute(element, "datum", TEST_DATUM);
			element.appendChild(Util.buildDDMSElement(getMinVerticalExtentName(), String.valueOf(TEST_MIN)));
			element.appendChild(Util.buildDDMSElement(getMaxVerticalExtentName(), String.valueOf("ground-level")));
			testConstructor(WILL_FAIL, element);
		}
	}

	public void testDataConstructorInvalid() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			// Missing UOM
			testConstructor(WILL_FAIL, TEST_MIN, TEST_MAX, null, TEST_DATUM);

			// Invalid UOM
			testConstructor(WILL_FAIL, TEST_MIN, TEST_MAX, "furlong", TEST_DATUM);

			// Missing Datum
			testConstructor(WILL_FAIL, TEST_MIN, TEST_MAX, TEST_UOM, null);

			// Invalid Datum
			testConstructor(WILL_FAIL, TEST_MIN, TEST_MAX, TEST_UOM, "PDQ");

			// MinVerticalExtent is not less than MaxVerticalExtent
			testConstructor(WILL_FAIL, TEST_MAX, TEST_MIN, TEST_UOM, TEST_DATUM);
		}
	}

	public void testWarnings() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			// No warnings
			VerticalExtent component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(0, component.getValidationWarnings().size());
		}
	}

	public void testConstructorEquality() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			VerticalExtent elementComponent = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			VerticalExtent dataComponent = testConstructor(WILL_SUCCEED, TEST_MIN, TEST_MAX, TEST_UOM, TEST_DATUM);
			assertEquals(elementComponent, dataComponent);
			assertEquals(elementComponent.hashCode(), dataComponent.hashCode());
		}
	}

	public void testConstructorInequalityDifferentValues() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			VerticalExtent elementComponent = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			VerticalExtent dataComponent = testConstructor(WILL_SUCCEED, TEST_MIN, TEST_MAX, "Inch", TEST_DATUM);
			assertFalse(elementComponent.equals(dataComponent));

			dataComponent = testConstructor(WILL_SUCCEED, TEST_MIN, TEST_MAX, TEST_UOM, "HAE");
			assertFalse(elementComponent.equals(dataComponent));

			dataComponent = testConstructor(WILL_SUCCEED, 1, TEST_MAX, TEST_UOM, TEST_DATUM);
			assertFalse(elementComponent.equals(dataComponent));

			dataComponent = testConstructor(WILL_SUCCEED, TEST_MIN, 101, TEST_UOM, TEST_DATUM);
			assertFalse(elementComponent.equals(dataComponent));
		}
	}

	public void testConstructorInequalityWrongClass() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			VerticalExtent elementComponent = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			Rights wrongComponent = new Rights(true, true, true);
			assertFalse(elementComponent.equals(wrongComponent));
		}
	}

	public void testHTMLOutput() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			VerticalExtent component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(getExpectedOutput(true), component.toHTML());

			component = testConstructor(WILL_SUCCEED, TEST_MIN, TEST_MAX, TEST_UOM, TEST_DATUM);
			assertEquals(getExpectedOutput(true), component.toHTML());
		}
	}

	public void testTextOutput() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			VerticalExtent component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(getExpectedOutput(false), component.toText());

			component = testConstructor(WILL_SUCCEED, TEST_MIN, TEST_MAX, TEST_UOM, TEST_DATUM);
			assertEquals(getExpectedOutput(false), component.toText());
		}
	}

	public void testXMLOutput() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			VerticalExtent component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(getExpectedXMLOutput(true), component.toXML());

			component = testConstructor(WILL_SUCCEED, TEST_MIN, TEST_MAX, TEST_UOM, TEST_DATUM);
			assertEquals(getExpectedXMLOutput(false), component.toXML());
		}
	}

	public void testDoubleEquality() {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			VerticalExtent component = testConstructor(WILL_SUCCEED, getValidElement(versionString));
			assertEquals(component.getMinVerticalExtent(), Double.valueOf(TEST_MIN));
			assertEquals(component.getMaxVerticalExtent(), Double.valueOf(TEST_MAX));
		}
	}

	public void testBuilder() throws InvalidDDMSException {
		for (String versionString : DDMSVersion.getSupportedVersions()) {
			DDMSVersion.setCurrentVersion(versionString);
			VerticalExtent component = testConstructor(WILL_SUCCEED, getValidElement(versionString));

			// Equality after Building
			VerticalExtent.Builder builder = new VerticalExtent.Builder(component);
			assertEquals(builder.commit(), component);

			// Empty case
			builder = new VerticalExtent.Builder();
			assertNull(builder.commit());

			// Validation
			builder = new VerticalExtent.Builder();
			builder.setUnitOfMeasure(TEST_UOM);
			try {
				builder.commit();
				fail("Builder allowed invalid data.");
			} catch (InvalidDDMSException e) {
				// Good
			}
		}
	}
}
