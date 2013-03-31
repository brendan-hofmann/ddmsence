/* Copyright 2010 - 2013 by Brian Uri!
   
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
import buri.ddmsence.AbstractBaseComponent;
import buri.ddmsence.AbstractQualifierValue;
import buri.ddmsence.ddms.IBuilder;
import buri.ddmsence.ddms.InvalidDDMSException;
import buri.ddmsence.util.DDMSVersion;
import buri.ddmsence.util.Util;

/**
 * An immutable implementation of ddms:subDivisionCode.
 * <br /><br />
 * {@ddms.versions 00011}
 * 
 * <p></p>
 * 
 *  {@table.header History}
 * 		<p>In DDMS 5.0, the "qualifier" and "value" attributes are renamed to "codespace" and "code". 
 * 		The name of the Java accessors remain the same to maintain consistency across versions.</p>
 * {@table.footer}
 * {@table.header Nested Elements}
 * 		None.
 * {@table.footer}
 * {@table.header Attributes}
 * 		{@child.info ddms:qualifier|1|00010}
 * 		{@child.info ddms:value|1|00010}
 * 		{@child.info ddms:codespace|1|00001}
 * 		{@child.info ddms:code|1|00001}
 * {@table.footer}
 * {@table.header Validation Rules}
 * 		{@ddms.rule Component is not used before the DDMS version in which it was introduced.|Error|11111}
 * 		{@ddms.rule The qualified name of this element is correct.|Error|11111}
 * 		{@ddms.rule ddms:qualifier is required.|Error|00010}
 * 		{@ddms.rule ddms:codespace is required.|Error|00001}
 * 		{@ddms.rule ddms:value is required.|Error|00010}
 * 		{@ddms.rule ddms:code is required.|Error|00001}
 * 		<p>Does not validate that the value is valid against the qualifier's vocabulary.</p>
 * {@table.footer}
 *  
 * @author Brian Uri!
 * @since 2.0.0
 */
public final class SubDivisionCode extends AbstractQualifierValue {

	/**
	 * Constructor for creating a component from a XOM Element
	 * 
	 * @param element the XOM element representing this
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	public SubDivisionCode(Element element) throws InvalidDDMSException {
		super(element, !DDMSVersion.getVersionForNamespace(element.getNamespaceURI()).isAtLeast("5.0"));
	}

	/**
	 * Constructor for creating a component from raw data
	 * 
	 * @param qualifier the value of the qualifier attribute
	 * @param value the value of the value attribute
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	public SubDivisionCode(String qualifier, String value) throws InvalidDDMSException {
		super(SubDivisionCode.getName(DDMSVersion.getCurrentVersion()), qualifier, value, true,
			!DDMSVersion.getCurrentVersion().isAtLeast("5.0"));
	}

	/**
	 * @see AbstractBaseComponent#validate()
	 */
	protected void validate() throws InvalidDDMSException {
		requireAtLeastVersion("4.0.1");
		Util.requireDDMSQName(getXOMElement(), SubDivisionCode.getName(getDDMSVersion()));
		Util.requireDDMSValue(getQualifierName() + " attribute", getQualifier());
		Util.requireDDMSValue(getValueName() + " attribute", getValue());		
		super.validate();
	}

	/**
	 * @see AbstractBaseComponent#getOutput(boolean, String, String)
	 */
	public String getOutput(boolean isHTML, String prefix, String suffix) {
		String localPrefix = buildPrefix(prefix, getName(), suffix + ".");
		StringBuffer text = new StringBuffer();
		text.append(buildOutput(isHTML, localPrefix + getQualifierName(), getQualifier()));
		text.append(buildOutput(isHTML, localPrefix + getValueName(), getValue()));
		return (text.toString());
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object obj) {
		if (!super.equals(obj) || !(obj instanceof SubDivisionCode))
			return (false);
		return (true);
	}

	/**
	 * Accessor for the element name of this component, based on the version of DDMS used
	 * 
	 * @param version the DDMSVersion
	 * @return an element name
	 */
	public static String getName(DDMSVersion version) {
		Util.requireValue("version", version);
		return ("subDivisionCode");
	}

	/**
	 * Builder for this DDMS component.
	 * 
	 * @see IBuilder
	 * @author Brian Uri!
	 * @since 2.0.0
	 */
	public static class Builder extends AbstractQualifierValue.Builder {
		private static final long serialVersionUID = 2136329013144660166L;

		/**
		 * Empty constructor
		 */
		public Builder() {
			super();
		}

		/**
		 * Constructor which starts from an existing component.
		 */
		public Builder(SubDivisionCode code) {
			super(code);
		}

		/**
		 * @see IBuilder#commit()
		 */
		public SubDivisionCode commit() throws InvalidDDMSException {
			return (isEmpty() ? null : new SubDivisionCode(getQualifier(), getValue()));
		}
	}
}