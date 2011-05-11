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
import buri.ddmsence.ddms.AbstractBaseComponent;
import buri.ddmsence.ddms.AbstractProducer;
import buri.ddmsence.ddms.InvalidDDMSException;
import buri.ddmsence.util.DDMSVersion;
import buri.ddmsence.util.Util;

/**
 * An immutable implementation of ddms:rights.
 * 
 * <table class="info"><tr class="infoHeader"><th>Attributes</th></tr><tr><td class="infoBody">
 * <u>ddms:privacyAct</u>: protected by the Privacy Act (optional, default=false).<br />
 * <u>ddms:intellectualProperty</u>: has an intellectual property rights owner (optional, default=false)<br />
 * <u>ddms:copyright</u>: has a copyright owner (optional, default=false)<br />
 * </td></tr></table>
 * 
 * <table class="info"><tr class="infoHeader"><th>DDMS Information</th></tr><tr><td class="infoBody">
 * <u>Link</u>: http://metadata.ces.mil/mdr/irs/DDMS/ddms_categories.htm#Rights<br />
 * <u>Description</u>: Information about rights held in and over the resource<br />
 * <u>Obligation</u>: Optional<br />
 * <u>Schema Modification Date</u>: 2003-05-16<br />
 * </td></tr></table>
 *  
 * @author Brian Uri!
 * @since 0.9.b
 */
public final class Rights extends AbstractBaseComponent {

	/** The element name of this component */
	public static final String NAME = "rights";

	private static final String PRIVACY_ACT_NAME = "privacyAct";
	private static final String INTELLECTUAL_PROPERY_NAME = "intellectualProperty";
	private static final String COPYRIGHT_NAME = "copyright";
	
	/**
	 * Constructor for creating a component from a XOM Element
	 * 
	 * @param element the XOM element representing this
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	public Rights(Element element) throws InvalidDDMSException {
		super(element);
	}

	/**
	 * Constructor for creating a component from raw data
	 * 
	 * @param privacyAct the value for the privacyAct attribute
	 * @param intellectualProperty the value for the intellectualProperty attribute
	 * @param copyright the value for the copyright attribute
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	public Rights(boolean privacyAct, boolean intellectualProperty, boolean copyright) throws InvalidDDMSException {
		Element element = Util.buildDDMSElement(Rights.NAME, null);
		Util.addDDMSAttribute(element, PRIVACY_ACT_NAME, Boolean.toString(privacyAct));
		Util.addDDMSAttribute(element, INTELLECTUAL_PROPERY_NAME, Boolean.toString(intellectualProperty));
		Util.addDDMSAttribute(element, COPYRIGHT_NAME, Boolean.toString(copyright));
		setXOMElement(element, true);
		// This cannot actually throw an exception, so locator information is not inserted in a catch statement.
	}
		
	/**
	 * Validates the component.
	 * 
	 * <table class="info"><tr class="infoHeader"><th>Rules</th></tr><tr><td class="infoBody">
	 * <li>The qualified name of the element is correct.</li>
	 * </td></tr></table>
	 * 
	 * @see AbstractProducer#validate()
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	protected void validate() throws InvalidDDMSException {
		super.validate();
		Util.requireDDMSQName(getXOMElement(), DDMSVersion.getVersionFor(getDDMSVersion()).getNamespace(), NAME);
	}
	
	/**
	 * @see AbstractBaseComponent#toHTML()
	 */
	public String toHTML() {
		StringBuffer html = new StringBuffer();
		html.append(buildHTMLMeta("rights.privacy", String.valueOf(getPrivacyAct()), true));
		html.append(buildHTMLMeta("rights.intellectualproperty", String.valueOf(getIntellectualProperty()), true));
		html.append(buildHTMLMeta("rights.copy", String.valueOf(getCopyright()), true));
		return (html.toString());
	}
	
	/**
	 * @see AbstractBaseComponent#toText()
	 */
	public String toText() {
		StringBuffer text = new StringBuffer();
		text.append(buildTextLine("Privacy Act", String.valueOf(getPrivacyAct()), true));
		text.append(buildTextLine("Intellectual Property Rights", String.valueOf(getIntellectualProperty()), true));
		text.append(buildTextLine("Copyright", String.valueOf(getCopyright()), true));
		return (text.toString());
	}
	
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object obj) {
		if (!super.equals(obj) || !(obj instanceof Rights))
			return (false);
		Rights test = (Rights) obj;
		return (getPrivacyAct() == test.getPrivacyAct() 
			&& getIntellectualProperty() == test.getIntellectualProperty() 
			&& getCopyright() == test.getCopyright());
	}
		
	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		int result = super.hashCode();
		result = 7 * result + Util.booleanHashCode(getPrivacyAct());
		result = 7 * result + Util.booleanHashCode(getIntellectualProperty());
		result = 7 * result + Util.booleanHashCode(getCopyright());
		return (result);
	}
	
	/**
	 * Accessor for the privacyAct attribute. The default value is false.
	 */
	public boolean getPrivacyAct() {
		return (Boolean.valueOf(getAttributeValue(PRIVACY_ACT_NAME))); 
	}
	
	/**
	 * Accessor for the intellectualProperty attribute. The default value is false.
	 */
	public boolean getIntellectualProperty() {
		return (Boolean.valueOf(getAttributeValue(INTELLECTUAL_PROPERY_NAME))); 
	}
	
	/**
	 * Accessor for the copyright attribute. The default value is false.
	 */
	public boolean getCopyright() {
		return (Boolean.valueOf(getAttributeValue(COPYRIGHT_NAME))); 
	}

	/**
	 * Builder for this DDMS component. The builder should be used when a DDMS record needs to be built up over time,
	 * but validation should not occur until the end. The commit() method attempts to finalize the immutable object
	 * based on the values gathered.
	 * 
	 * @author Brian Uri!
	 * @since 1.8.0
	 */
	public static class Builder {
		private boolean _privacyAct;
		private boolean _intellectualProperty;
		private boolean _copyright;
		
		/**
		 * Empty constructor
		 */
		public Builder() {}
		
		/**
		 * Constructor which starts from an existing component.
		 */
		public Builder(Rights rights) {
			setPrivacyAct(rights.getPrivacyAct());
			setIntellectualProperty(rights.getIntellectualProperty());
			setCopyright(rights.getCopyright());
		}
		
		/**
		 * Finalizes the data gathered for this builder instance.
		 * 
		 * @throws InvalidDDMSException if any required information is missing or malformed
		 */
		public Rights commit() throws InvalidDDMSException {
			return (new Rights(getPrivacyAct(), getIntellectualProperty(), getCopyright()));
		}
		
		/**
		 * Builder accessor for the privacyAct attribute.
		 */
		public boolean getPrivacyAct() {
			return _privacyAct;
		}
		
		/**
		 * Builder accessor for the privacyAct attribute.
		 */
		public void setPrivacyAct(boolean privacyAct) {
			_privacyAct = privacyAct;
		}
		
		/**
		 * Builder accessor for the intellectualProperty attribute.
		 */
		public boolean getIntellectualProperty() {
			return _intellectualProperty;
		}
		
		/**
		 * Builder accessor for the intellectualProperty attribute.
		 */
		public void setIntellectualProperty(boolean intellectualProperty) {
			_intellectualProperty = intellectualProperty;
		}
		
		/**
		 * Builder accessor for the copyright attribute.
		 */
		public boolean getCopyright() {
			return _copyright;
		}
		
		/**
		 * Builder accessor for the copyright attribute.
		 */
		public void setCopyright(boolean copyright) {
			_copyright = copyright;
		}		
	}
} 