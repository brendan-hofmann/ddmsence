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
package buri.ddmsence;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import nu.xom.Element;
import nu.xom.Elements;
import buri.ddmsence.ddms.IBuilder;
import buri.ddmsence.ddms.IRoleEntity;
import buri.ddmsence.ddms.InvalidDDMSException;
import buri.ddmsence.ddms.extensible.ExtensibleAttributes;
import buri.ddmsence.util.LazyList;
import buri.ddmsence.util.Util;

/**
 * Base class for entities which fulfill some role, such as ddms:person and ddms:organization.
 * 
 * <p> The HTML output of this class depends on the role type which the entity is associated with. For
 * example, if this entity's role is a "pointOfContact", the HTML meta tags will prefix each
 * field with "pointOfContact."</p>
 * 
 * <p> Extensions of this class are generally expected to be immutable, and the underlying XOM element MUST be set
 * before the component is used. </p>
 * 
 * @author Brian Uri!
 * @since 2.0.0
 */
public abstract class AbstractRoleEntity extends AbstractBaseComponent implements IRoleEntity {
	
	private List<String> _names = null;
	private List<String> _phones = null;
	private List<String> _emails = null;
	private ExtensibleAttributes _extensibleAttributes = null;
	
	private static final String NAME_NAME = "name";
	private static final String PHONE_NAME = "phone";
	private static final String EMAIL_NAME = "email";	
	
	/**
	 * Base constructor
	 * 
	 * @param element the XOM element representing this component
	 * @param validateNow true to validate the component immediately. Because Person and Organization entities have
	 * additional fields they should not be validated in the superconstructor.
	 */
	protected AbstractRoleEntity(Element element, boolean validateNow) throws InvalidDDMSException {
		try {
			_names = Util.getDDMSChildValues(element, NAME_NAME);
			_phones = Util.getDDMSChildValues(element, PHONE_NAME);
			_emails = Util.getDDMSChildValues(element, EMAIL_NAME);
			_extensibleAttributes = new ExtensibleAttributes(element);
			setXOMElement(element, validateNow);
		}
		catch (InvalidDDMSException e) {
			e.setLocator(getQualifiedName());
			throw (e);
		}
	}
	
	/**
	 * Constructor which builds from raw data.
	 * 
	 * @param entityName the element name of this entity (i.e. organization, person)
	 * @param names an ordered list of names
	 * @param phones an ordered list of phone numbers
	 * @param emails an ordered list of email addresses
	 * @param extensibleAttributes extensible attributes (optional)
	 * @param validateNow true to validate the component immediately. Because Person and Organization entities have
	 * additional fields they should not be validated in the superconstructor.
	 */
	protected AbstractRoleEntity(String entityName, List<String> names, List<String> phones, List<String> emails,
		ExtensibleAttributes extensibleAttributes, boolean validateNow) throws InvalidDDMSException {
		try {
			Util.requireDDMSValue("entityName", entityName);
			if (names == null)
				names = Collections.emptyList();
			if (phones == null)
				phones = Collections.emptyList();
			if (emails == null)
				emails = Collections.emptyList();
			
			Element element = Util.buildDDMSElement(entityName, null);
			for (String name : names)
				element.appendChild(Util.buildDDMSElement(NAME_NAME, name));
			for (String phone : phones)
				element.appendChild(Util.buildDDMSElement(PHONE_NAME, phone));
			for (String email : emails)
				element.appendChild(Util.buildDDMSElement(EMAIL_NAME, email));

			_names = names;
			_phones = phones;
			_emails = emails;
			_extensibleAttributes = ExtensibleAttributes.getNonNullInstance(extensibleAttributes);
			_extensibleAttributes.addTo(element);
			setXOMElement(element, validateNow);
		}
		catch (InvalidDDMSException e) {
			e.setLocator(getQualifiedName());
			throw (e);
		}
	}
			
	/**
	 * Validates the component.
	 * 
	 * <table class="info"><tr class="infoHeader"><th>Rules</th></tr><tr><td class="infoBody">
	 * <li>The entity has at least 1 non-empty name.</li>
	 * </td></tr></table>
	 * 
	 * @see AbstractBaseComponent#validate()
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	protected void validate() throws InvalidDDMSException {
		if (getXOMElement().getChildElements(NAME_NAME, getNamespace()).size() == 0)
			throw new InvalidDDMSException("At least 1 name element must exist.");
		
		if (Util.containsOnlyEmptyValues(getNames()))
			throw new InvalidDDMSException("At least 1 name element must have a non-empty value.");

		super.validate();	
	}
	
	/**
	 * Validates any conditions that might result in a warning.
	 * 
	 * <table class="info"><tr class="infoHeader"><th>Rules</th></tr><tr><td class="infoBody">
	 * <li>A ddms:phone element was found with no value.</li>
	 * <li>A ddms:email element was found with no value.</li>
	 * </td></tr></table>
	 */
	protected void validateWarnings() {
		Elements phoneElements = getXOMElement().getChildElements(PHONE_NAME, getNamespace());
		for (int i = 0; i < phoneElements.size(); i++) {
			if (Util.isEmpty(phoneElements.get(i).getValue())) {
				addWarning("A ddms:phone element was found with no value.");
				break;
			}
		}
		Elements emailElements = getXOMElement().getChildElements(EMAIL_NAME, getNamespace());
		for (int i = 0; i < emailElements.size(); i++) {
			if (Util.isEmpty(emailElements.get(i).getValue())) {
				addWarning("A ddms:email element was found with no value.");
				break;
			}
		}
		super.validateWarnings();
	}
			
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object obj) {
		if (!super.equals(obj) || !(obj instanceof AbstractRoleEntity))
			return (false);
		AbstractRoleEntity test = (AbstractRoleEntity) obj;
		return (Util.listEquals(getNames(), test.getNames())
			&& Util.listEquals(getPhones(), test.getPhones())
			&& Util.listEquals(getEmails(), test.getEmails())
			&& getExtensibleAttributes().equals(test.getExtensibleAttributes()));
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		int result = super.hashCode();
		result = 7 * result + getNames().hashCode();
		result = 7 * result + getPhones().hashCode();
		result = 7 * result + getEmails().hashCode();
		result = 7 * result + getExtensibleAttributes().hashCode();
		return (result);
	}
	
	/**
	 * @see AbstractBaseComponent#getOutput(boolean, String, String)
	 */
	public String getOutput(boolean isHTML, String prefix, String suffix) {
		prefix = Util.getNonNullString(prefix) + Util.getNonNullString(suffix);
		StringBuffer text = new StringBuffer();
		text.append(buildOutput(isHTML, prefix + "entityType", getName(), true));
		text.append(buildOutput(isHTML, prefix + NAME_NAME, getNames(), true));
		text.append(buildOutput(isHTML, prefix + PHONE_NAME, getPhones(), true));
		text.append(buildOutput(isHTML, prefix + EMAIL_NAME, getEmails(), true));
		text.append(getExtensibleAttributes().getOutput(isHTML, prefix));
		return (text.toString());
	}
	
	/**
	 * Accessor for the names of the entity (1 to many).
	 * 
	 * @return unmodifiable List
	 */
	public List<String> getNames() {
		return (Collections.unmodifiableList(_names));
	}
	
	/**
	 * Accessor for the phone numbers of the entity (0 to many).
	 * 
	 * @return unmodifiable List
	 */
	public List<String> getPhones() {
		return (Collections.unmodifiableList(_phones));
	}
	
	/**
	 * Accessor for the emails of the entity (0 to many).
	 * 
	 * @return unmodifiable List
	 */
	public List<String> getEmails() {
		return (Collections.unmodifiableList(_emails));
	}
	
	/**
	 * Accessor for the extensible attributes. Will always be non-null, even if not set.
	 */
	public ExtensibleAttributes getExtensibleAttributes() {
		return (_extensibleAttributes);
	}
	
	/**
	 * Abstract Builder for this DDMS component.
	 * 
	 * <p>Builders which are based upon this abstract class should implement the commit() method, returning the
	 * appropriate concrete object type.</p>
	 * 
	 * @see IBuilder
	 * @author Brian Uri!
	 * @since 2.0.0
	 */
	public static abstract class Builder implements IBuilder, Serializable {
		private static final long serialVersionUID = -1694935853087559491L;
		private List<String> _names;
		private List<String> _phones;
		private List<String> _emails;
		private ExtensibleAttributes.Builder _extensibleAttributes;
		
		/**
		 * Empty constructor
		 */
		protected Builder() {}
		
		/**
		 * Constructor which starts from an existing component.
		 */
		protected Builder(AbstractRoleEntity entity) {
			setNames(entity.getNames());
			setPhones(entity.getPhones());
			setEmails(entity.getEmails());
			setExtensibleAttributes(new ExtensibleAttributes.Builder(entity.getExtensibleAttributes()));
		}
		
		/**
		 * Helper method to determine if any values have been entered for this producer.
		 * 
		 * @return true if all values are empty
		 */
		public boolean isEmpty() {
			return (Util.containsOnlyEmptyValues(getNames())
				&& Util.containsOnlyEmptyValues(getPhones())
				&& Util.containsOnlyEmptyValues(getEmails())
				&& getExtensibleAttributes().isEmpty());
		}
		
		/**
		 * Builder accessor for the names
		 */
		public List<String> getNames() {
			if (_names == null)
				_names = new LazyList(String.class);
			return _names;
		}

		/**
		 * Builder accessor for the names
		 */
		public void setNames(List<String> names) {
			_names = new LazyList(names, String.class);
		}

		/**
		 * Builder accessor for the phones
		 */
		public List<String> getPhones() {
			if (_phones == null)
				_phones = new LazyList(String.class);
			return _phones;
		}

		/**
		 * Builder accessor for the phones
		 */
		public void setPhones(List<String> phones) {
			_phones = new LazyList(phones, String.class);
		}

		/**
		 * Builder accessor for the emails
		 */
		public List<String> getEmails() {
			if (_emails == null)
				_emails = new LazyList(String.class);
			return _emails;
		}

		/**
		 * Builder accessor for the emails
		 */
		public void setEmails(List<String> emails) {
			_emails = new LazyList(emails, String.class);
		}
		
		/**
		 * Builder accessor for the Extensible Attributes
		 */
		public ExtensibleAttributes.Builder getExtensibleAttributes() {
			if (_extensibleAttributes == null)
				_extensibleAttributes = new ExtensibleAttributes.Builder();
			return _extensibleAttributes;
		}
		
		/**
		 * Builder accessor for the Extensible Attributes
		 */
		public void setExtensibleAttributes(ExtensibleAttributes.Builder extensibleAttributes) {
			_extensibleAttributes = extensibleAttributes;
		}
	}
}