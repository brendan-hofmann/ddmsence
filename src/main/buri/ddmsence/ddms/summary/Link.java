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

import java.io.Serializable;

import nu.xom.Element;
import buri.ddmsence.AbstractBaseComponent;
import buri.ddmsence.ddms.IBuilder;
import buri.ddmsence.ddms.InvalidDDMSException;
import buri.ddmsence.ddms.security.ism.SecurityAttributes;
import buri.ddmsence.ddms.summary.xlink.XLinkAttributes;
import buri.ddmsence.util.DDMSVersion;
import buri.ddmsence.util.Util;

/**
 * An immutable implementation of ddms:link.
 * 
 * <p>This element is not a global component, but is being implemented because it has attributes.</p>
 * 
 * <table class="info"><tr class="infoHeader"><th>Strictness</th></tr><tr><td class="infoBody">
 * <p>DDMSence is stricter than the specification in the following ways:</p>
 * <ul>
 * <li>The href value must not be empty.</li>
 * </ul>
 * </td></tr></table>
 * 
 * <table class="info"><tr class="infoHeader"><th>Attributes</th></tr><tr><td class="infoBody">
 * This class is decorated with {@link XLinkAttributes). The xlink:type attribute is required and must have a fixed
 * value of "locator". The xlink:href attribute is also required.<br />
 * This class is also decorated with ISM {@link SecurityAttributes}, starting in DDMS 4.0 when used in the context
 * of a {@link RevisionRecall}. The classification and ownerProducer attributes are required.
 * </td></tr></table>
 * 
 * <table class="info"><tr class="infoHeader"><th>DDMS Information</th></tr><tr><td class="infoBody">
 * <u>Description</u>: An XLink locator element for the resource being related.<br />
 * <u>Obligation</u>: At least 1 link is required in a RelatedResource.<br />
 * <u>Schema Modification Date</u>: 2011-08-31<br />
 * </td></tr></table>
 * 
 * @author Brian Uri!
 * @since 0.9.b
 */
public final class Link extends AbstractBaseComponent {

	private XLinkAttributes _cachedXLinkAttributes = null;
	private SecurityAttributes _cachedSecurityAttributes = null;
		
	private static final String FIXED_TYPE = "locator";
	
	/**
	 * Constructor for creating a component from a XOM Element
	 *  
	 * @param element the XOM element representing this 
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	public Link(Element element) throws InvalidDDMSException {
		try {
			_cachedXLinkAttributes = new XLinkAttributes(element);
			_cachedSecurityAttributes = new SecurityAttributes(element);
			setXOMElement(element, true);
		} catch (InvalidDDMSException e) {
			e.setLocator(getQualifiedName());
			throw (e);
		}
	}
	
	/**
	 * Constructor for creating a component from raw data
	 *  
	 * @param xlinkAttributes the xlink attributes
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	public Link(XLinkAttributes xlinkAttributes) throws InvalidDDMSException {
		this(xlinkAttributes, null);
	}

	/**
	 * Constructor for creating a component from raw data
	 *  
	 * @param xlinkAttributes the xlink attributes
	 * @param securityAttributes attributes, which are only allowed on links within a ddms:revisionRecall
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	public Link(XLinkAttributes xlinkAttributes, SecurityAttributes securityAttributes)
		throws InvalidDDMSException {
		try {
			Element element = Util.buildDDMSElement(Link.getName(DDMSVersion.getCurrentVersion()), null);
			_cachedXLinkAttributes = (xlinkAttributes == null ? new XLinkAttributes(null, null, null, null, null)
				: xlinkAttributes);
			_cachedXLinkAttributes.addTo(element);
			_cachedSecurityAttributes = (securityAttributes == null ? new SecurityAttributes(null, null, null)
				: securityAttributes);
			_cachedSecurityAttributes.addTo(element);
			setXOMElement(element, true);
		} catch (InvalidDDMSException e) {
			e.setLocator(getQualifiedName());
			throw (e);
		}
	}
	
	/**
	 * Validates the component.
	 * 
	 * <table class="info"><tr class="infoHeader"><th>Rules</th></tr><tr><td class="infoBody">
	 * <li>The qualified name of the element is correct.</li>
	 * <li>The xlink:type is set and has a value of "locator".</li>
	 * <li>The xlink:href is set and non-empty.</li>
	 * <li>Does not validate the security attributes. It is the parent class' responsibility
	 * to do that.
	 * </td></tr></table>
	 * 
	 * @see AbstractBaseComponent#validate()
	 */
	protected void validate() throws InvalidDDMSException {
		Util.requireDDMSQName(getXOMElement(), Link.getName(getDDMSVersion()));
		Util.requireDDMSValue("type attribute", getXLinkAttributes().getType());
		Util.requireDDMSValue("href attribute", getXLinkAttributes().getHref());

		if (!getXLinkAttributes().getType().equals(FIXED_TYPE))
			throw new InvalidDDMSException("The type attribute must have a fixed value of \"" + FIXED_TYPE + "\".");
				
		super.validate();
	}
	
	/**
	 * @see AbstractBaseComponent#getOutput(boolean, String)
	 */
	public String getOutput(boolean isHTML, String prefix) {
		prefix = Util.getNonNullString(prefix) + getName() + ".";
		StringBuffer text = new StringBuffer();
		text.append(getXLinkAttributes().getOutput(isHTML, prefix));
		text.append(getSecurityAttributes().getOutput(isHTML, prefix));
		return (text.toString());
	}
		
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object obj) {
		if (!super.equals(obj) || !(obj instanceof Link))
			return (false);
		Link test = (Link) obj;
		return (getXLinkAttributes().equals(test.getXLinkAttributes()));
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		int result = super.hashCode();
		result = 7 * result + getXLinkAttributes().hashCode();
		return (result);
	}
	
	/**
	 * Accessor for the element name of this component, based on the version of DDMS used
	 * 
	 * @param version the DDMSVersion
	 * @return an element name
	 */
	public static String getName(DDMSVersion version) {
		Util.requireValue("version", version);
		return ("link");
	}
		
	/**
	 * Accessor for the XLink Attributes. Will always be non-null, even if it has no values set.
	 */
	public XLinkAttributes getXLinkAttributes() {
		return (_cachedXLinkAttributes);
	}
	
	/**
	 * Accessor for the Security Attributes. Will always be non-null, even if it has no values set.
	 */
	public SecurityAttributes getSecurityAttributes() {
		return (_cachedSecurityAttributes);
	}
	
	/**
	 * Builder for this DDMS component.
	 * 
	 * @see IBuilder
	 * @author Brian Uri!
	 * @since 1.8.0
	 */
	public static class Builder implements IBuilder, Serializable {
		private static final long serialVersionUID = 4325950371570699184L;
		private XLinkAttributes.Builder _xlinkAttributes;
		private SecurityAttributes.Builder _securityAttributes;
		
		/**
		 * Empty constructor
		 */
		public Builder() {}
		
		/**
		 * Constructor which starts from an existing component.
		 */
		public Builder(Link link) {
			setXLinkAttributes(new XLinkAttributes.Builder(link.getXLinkAttributes()));
			setSecurityAttributes(new SecurityAttributes.Builder(link.getSecurityAttributes()));
		}
		
		/**
		 * @see IBuilder#commit()
		 */
		public Link commit() throws InvalidDDMSException {
			return (isEmpty() ? null : new Link(getXLinkAttributes().commit(), getSecurityAttributes().commit()));
		}

		/**
		 * @see IBuilder#isEmpty()
		 */
		public boolean isEmpty() {
			return (getXLinkAttributes().isEmpty() && getSecurityAttributes().isEmpty());				
		}
		
		/**
		 * Builder accessor for the XLink Attributes
		 */
		public XLinkAttributes.Builder getXLinkAttributes() {
			if (_xlinkAttributes == null)
				_xlinkAttributes = new XLinkAttributes.Builder();
			return _xlinkAttributes;
		}
		
		/**
		 * Builder accessor for the XLink Attributes
		 */
		public void setXLinkAttributes(XLinkAttributes.Builder xlinkAttributes) {
			_xlinkAttributes = xlinkAttributes;
		}
		
		/**
		 * Builder accessor for the Security Attributes
		 */
		public SecurityAttributes.Builder getSecurityAttributes() {
			if (_securityAttributes == null)
				_securityAttributes = new SecurityAttributes.Builder();
			return _securityAttributes;
		}
		
		/**
		 * Builder accessor for the Security Attributes
		 */
		public void setSecurityAttributes(SecurityAttributes.Builder securityAttributes) {
			_securityAttributes = securityAttributes;
		}
		
	}
} 