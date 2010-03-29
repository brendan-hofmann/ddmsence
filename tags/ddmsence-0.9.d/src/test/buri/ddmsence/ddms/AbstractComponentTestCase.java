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
package buri.ddmsence.ddms;

import java.io.File;

import junit.framework.TestCase;
import nu.xom.Element;
import buri.ddmsence.util.DDMSReader;
import buri.ddmsence.util.DDMSVersion;
import buri.ddmsence.util.PropertyReader;
import buri.ddmsence.util.Util;

/**
 * Base class for test cases involving components.
 * 
 * @author Brian Uri!
 * @since 0.9.b
 */
public abstract class AbstractComponentTestCase extends TestCase {
	
	private Element _validElement;

	protected static final String TEST_ID = "IDValue";
	
	protected static final boolean WILL_SUCCEED = false;
	protected static final boolean WILL_FAIL = true;
	protected static final String INVALID_URI = ":::::";
	protected static final String DIFFERENT_VALUE = "Different";
	
	protected static final String DDMS_NAMESPACE = DDMSVersion.getDefaultNamespace();
	protected static final String GML_PREFIX = PropertyReader.getProperty("gml.prefix");
	protected static final String GML_NAMESPACE = PropertyReader.getProperty("gml.xmlNamespace");
	protected static final String GML_XSD = PropertyReader.getProperty("gml.xsdLocation");
	protected static final String ICISM_PREFIX = PropertyReader.getProperty("icism.prefix");
	protected static final String ICISM_NAMESPACE = PropertyReader.getProperty("icism.xmlNamespace");
	
	private static final String TEST_DATA_DIR = PropertyReader.getProperty("test.unit.data");
	
	/**
	 * Constructor
	 * 
	 * @param validDocumentFile	the valid instance of some component to use as a correct base case.
	 */
	public AbstractComponentTestCase(String validDocumentFile) {
		this(validDocumentFile, DDMSVersion.getDefaultNamespace(), DDMSVersion.getDefaultSchema());
	}
	
	/**
	 * Constructor
	 * 
	 * @param validDocumentFile	the valid instance of some component to use as a correct base case.
	 * @param namespaceURI the namespace URI for the schema file
	 * @param schemaLocation the local location of the schema file
	 */
	public AbstractComponentTestCase(String validDocumentFile, String namespaceURI, String schemaLocation) {
		if (Util.isEmpty(validDocumentFile))
			return;
		try {
			DDMSReader reader = new DDMSReader(namespaceURI, schemaLocation);
			File file = new File(TEST_DATA_DIR, validDocumentFile);
			_validElement = reader.getElement(file);
		}
		catch (Exception e) {
			throw new RuntimeException("Cannot run tests without valid DDMSReader and valid unit test object.", e);
		}
	}
	
	/**
	 * Should be called after a successful constructor execution. If the constructor was expected to fail, but
	 * it succeeded, the test will fail.
	 * 
	 * @param expectFailure true if the constructor was expected to fail.
	 */
	protected static void checkConstructorSuccess(boolean expectFailure) {
		if (expectFailure)
			fail("Constructor allowed invalid data.");
	}
	
	/**
	 * Should be called after a failed constructor execution. If the constructor was expected to succeed, but
	 * it failed, the test will fail.
	 * 
	 * @param expectFailure true if the constructor was expected to fail.
	 * @param exception the exception that occurred
	 */
	protected static void checkConstructorFailure(boolean expectFailure, InvalidDDMSException exception) {
		if (!expectFailure)
			fail("Constructor failed on valid data: " + exception.getMessage());
	}
	
	/**
	 * Strips tabs and new lines from XML output where appropriate. The unit test samples in the XML files
	 * have tabs and new lines, but the default implementation of XOM toXML() returns XML on a single line.
	 * 
	 * @param string	the original string
	 * @param preserveFormatting true to retain tabs and new lines, false to strip them.
	 * @return the modified string
	 */
	protected static String formatXml(String string, boolean preserveFormatting) {
		if (!preserveFormatting) {
			string = string.replaceAll("\t", "");
			string = string.replaceAll("\n", "");
		}
		return (string);
	}
		
	/**
	 * Accessor for a valid DDMS XOM Element constructed from the root element of an XML file, which can be used in testing as a
	 * "correct base case".
	 */
	public Element getValidElement() {
		return (_validElement);
	}	
}
