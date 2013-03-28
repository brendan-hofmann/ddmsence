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
package buri.ddmsence.ddms.resource;

import java.util.Collections;
import java.util.List;

import nu.xom.Element;
import nu.xom.Elements;
import buri.ddmsence.AbstractBaseComponent;
import buri.ddmsence.AbstractRoleEntity;
import buri.ddmsence.ddms.IBuilder;
import buri.ddmsence.ddms.InvalidDDMSException;
import buri.ddmsence.ddms.extensible.ExtensibleAttributes;
import buri.ddmsence.util.DDMSVersion;
import buri.ddmsence.util.LazyList;
import buri.ddmsence.util.Util;

/**
 * An immutable implementation of ddms:person.
 * 
 * <table class="info"><tr class="infoHeader"><th>Strictness</th></tr><tr><td class="infoBody">
 * <p>DDMSence is stricter than the specification in the following ways:</p>
 * <ul>
 * <li>At least 1 name value must be non-empty. A competing rule and loophole were established in DDMS 5.0.</li>
 * <li>The surname must be non-empty. A competing rule and loophole were established in DDMS 5.0.</li>
 * </ul>
 * 
 * <p>DDMSence allows the following legal, but nonsensical constructs:</p>
 * <ul>
 * <li>A phone number can be set with no value. This loophole goes away in DDMS 5.0.</li>
 * <li>An email can be set with no value. This loophole goes away in DDMS 5.0.</li>
 * <li>A userID can be set with no value. This loophole goes away in DDMS 5.0.</li>
 * <li>An affiliation can be set with no value. This loophole goes away in DDMS 5.0.</li>
 * </ul>
 * </td></tr></table>
 * 
 * <p>The name of this component was changed from "Person" to "person" in DDMS 4.0.1.</p>
 * 
 * <table class="info"><tr class="infoHeader"><th>Nested Elements</th></tr><tr><td class="infoBody">
 * <u>ddms:name</u>: names of the producer (1-many, at least 1 required)<br />
 * <u>ddms:surname</u>: surname of the producer (exactly 1 required)<br />
 * <u>ddms:userID</u>: userId of the producer (0-1 optional)<br />
 * <u>ddms:affiliation</u>: organizational affiliation (0-1 optional through DDMS 4.x, 0-many optional starting 
 * in DDMS 5.0)<br />
 * <u>ddms:phone</u>: phone numbers of the producer (0-many optional)<br />
 * <u>ddms:email</u>: email addresses of the producer (0-many optional)<br />
 * </td></tr></table>
 * 
 * <table class="info"><tr class="infoHeader"><th>Attributes</th></tr><tr><td class="infoBody">
 * <u>{@link ExtensibleAttributes}</u>: Custom attributes (through DDMS 3.1).
 * </td></tr></table>
 * 
 * @author Brian Uri!
 * @since 0.9.b
 */
public final class Person extends AbstractRoleEntity {

	private List<String> _affiliations = null;
	
	private static final String AFFILIATION_NAME = "affiliation";
	private static final String USERID_NAME = "userID";
	private static final String SURNAME_NAME = "surname";

	/**
	 * Constructor for creating a component from a XOM Element
	 * 
	 * @param element the XOM element representing this
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	public Person(Element element) throws InvalidDDMSException {
		super(element, true);
		_affiliations = Util.getDDMSChildValues(element, AFFILIATION_NAME);		
	}

	/**
	 * Constructor for creating a component from raw data.
	 * @param names an ordered list of names
	 * @param surname the surname of the person
	 * @param phones an ordered list of phone numbers
	 * @param emails an ordered list of email addresses
	 * @param userID optional unique identifier within an organization
	 * @param affiliations organizational affiliation of the person
	 */
	public Person(List<String> names, String surname, List<String> phones, List<String> emails,
		String userID, List<String> affiliations) throws InvalidDDMSException {
		this(names, surname, phones, emails, userID, affiliations, null);
	}

	/**
	 * Constructor for creating a component from raw data.
	 * @param names an ordered list of names
	 * @param surname the surname of the person
	 * @param phones an ordered list of phone numbers
	 * @param emails an ordered list of email addresses
	 * @param userID optional unique identifier within an organization
	 * @param affiliations organizational affiliations of the person
	 * @param extensions extensible attributes (optional)
	 */
	public Person(List<String> names, String surname, List<String> phones, List<String> emails, String userID,
		List<String> affiliations, ExtensibleAttributes extensions) throws InvalidDDMSException {
		super(Person.getName(DDMSVersion.getCurrentVersion()), names, phones, emails, extensions);
		try {
			int insertIndex = (names == null ? 0 : names.size());
			addExtraElements(insertIndex, surname, userID, affiliations);
			validate();
		}
		catch (InvalidDDMSException e) {
			e.setLocator(getQualifiedName());
			throw (e);
		}
	}

	/**
	 * Inserts additional elements into the existing entity. Because the personType contains a sequence,
	 * additional fields must be inserted among the name, phone, and email elements.
	 * 
	 * @param insertIndex the index of the position after the last names element
	 * @param surname the surname of the person
	 * @param userID optional unique identifier within an organization
	 * @param affiliations organizational affiliations of the person
	 * @throws InvalidDDMSException if the result is an invalid component
	 */
	private void addExtraElements(int insertIndex, String surname, String userID, List<String> affiliations)
		throws InvalidDDMSException {
		if (affiliations == null)
			affiliations = Collections.emptyList();
		Element element = getXOMElement();
		if (getDDMSVersion().isAtLeast("4.0.1")) {
			element.insertChild(Util.buildDDMSElement(SURNAME_NAME, surname), insertIndex);
			if (!Util.isEmpty(userID))
				element.appendChild(Util.buildDDMSElement(USERID_NAME, userID));
			for (String affiliation : affiliations)
				element.appendChild(Util.buildDDMSElement(AFFILIATION_NAME, affiliation));
		}
		else {
			// 	Inserting in reverse order allow the same index to be reused. Later inserts will "push" the early ones
			// 	forward.
			for (String affiliation : affiliations)
				element.insertChild(Util.buildDDMSElement(AFFILIATION_NAME, affiliation), insertIndex);
			if (!Util.isEmpty(userID))
				element.insertChild(Util.buildDDMSElement(USERID_NAME, userID), insertIndex);
			element.insertChild(Util.buildDDMSElement(SURNAME_NAME, surname), insertIndex);
		}
		_affiliations = affiliations;
	}

	/**
	 * Validates the component.
	 * 
	 * <table class="info"><tr class="infoHeader"><th>Rules</th></tr><tr><td class="infoBody">
	 * <li>The qualified name of the element is correct.</li>
	 * <li>Surname exists and is not empty.</li>
	 * <li>Exactly 1 surname, and 0-1 userIDs exist.</li>
	 * <li>Exactly 0-1 affiliations exist, through DDMS 4.1.</li> 
	 * <li>Extensible attributes cannot be used after DDMS 3.1.</li>
	 * </td></tr></table>
	 * 
	 * @see AbstractRoleEntity#validate()
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	protected void validate() throws InvalidDDMSException {
		Util.requireDDMSQName(getXOMElement(), Person.getName(getDDMSVersion()));
		Util.requireDDMSValue(SURNAME_NAME, getSurname());
		Util.requireBoundedChildCount(getXOMElement(), SURNAME_NAME, 1, 1);
		Util.requireBoundedChildCount(getXOMElement(), USERID_NAME, 0, 1);
		if (!getDDMSVersion().isAtLeast("5.0"))
			Util.requireBoundedChildCount(getXOMElement(), AFFILIATION_NAME, 0, 1);
		
		if (getDDMSVersion().isAtLeast("4.0.1") && !getExtensibleAttributes().isEmpty())
			throw new InvalidDDMSException("ddms:" + getName() + " cannot have extensible attributes after DDMS 3.1.");
		
		super.validate();
	}
	
	/**
	 * Validates any conditions that might result in a warning.
	 * 
	 * <table class="info"><tr class="infoHeader"><th>Rules</th></tr><tr><td class="infoBody">
	 * <li>A ddms:userID element was found with no value, through DDMS 4.1.</li>
	 * <li>A ddms:affiliation element was found with no value, through DDMS 4.1.</li>
	 * </td></tr></table>
	 */
	protected void validateWarnings() {
		if (!getDDMSVersion().isAtLeast("5.0") && Util.isEmpty(getUserID())
			&& getXOMElement().getChildElements(USERID_NAME, getNamespace()).size() == 1)
			addWarning("A ddms:userID element was found with no value.");
		if (!getDDMSVersion().isAtLeast("5.0")) {
			Elements affiliationElements = getXOMElement().getChildElements(AFFILIATION_NAME, getNamespace());
			for (int i = 0; i < affiliationElements.size(); i++) {
				if (Util.isEmpty(affiliationElements.get(i).getValue())) {
					addWarning("A ddms:affiliation element was found with no value.");
					break;
				}
			}			
		}

		super.validateWarnings();
	}

	/**
	 * @see AbstractBaseComponent#getOutput(boolean, String, String)
	 */
	public String getOutput(boolean isHTML, String prefix, String suffix) {
		String localPrefix = buildPrefix(prefix, "", suffix);
		StringBuffer text = new StringBuffer(super.getOutput(isHTML, localPrefix, ""));
		text.append(buildOutput(isHTML, localPrefix + SURNAME_NAME, getSurname()));
		text.append(buildOutput(isHTML, localPrefix + USERID_NAME, getUserID()));
		text.append(buildOutput(isHTML, localPrefix + AFFILIATION_NAME, getAffiliations()));
		return (text.toString());
	}
	
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object obj) {
		if (!super.equals(obj) || !(obj instanceof Person))
			return (false);
		Person test = (Person) obj;
		return (getSurname().equals(test.getSurname()) 
			&& getUserID().equals(test.getUserID()) 
			&& Util.listEquals(getAffiliations(), test.getAffiliations()));
	}
	
	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		int result = super.hashCode();
		result = 7 * result + getSurname().hashCode();
		result = 7 * result + getUserID().hashCode();
		result = 7 * result + getAffiliations().hashCode();
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
		return (version.isAtLeast("4.0.1") ? "person" : "Person");
	}
	
	/**
	 * Accessor for the surname of the person
	 */
	public String getSurname() {
		return (Util.getFirstDDMSChildValue(getXOMElement(), SURNAME_NAME));
	}
	
	/**
	 * Accessor for the userID of the person
	 */
	public String getUserID() {
		return (Util.getFirstDDMSChildValue(getXOMElement(), USERID_NAME));
	}
	
	/**
	 * Accessor for the affiliations of the person
	 */
	public List<String> getAffiliations() {
		return (Collections.unmodifiableList(_affiliations));
	}
	
	/**
	 * Builder for this DDMS component.
	 * 
	 * @see IBuilder
	 * @author Brian Uri!
	 * @since 1.8.0
	 */
	public static class Builder extends AbstractRoleEntity.Builder {
		private static final long serialVersionUID = -2933889158864177338L;
		private String _surname;
		private String _userID;
		private List<String> _affiliations;
		
		/**
		 * Empty constructor
		 */
		public Builder() {
			super();
		}
		
		/**
		 * Constructor which starts from an existing component.
		 */
		public Builder(Person person) {
			super(person);
			setSurname(person.getSurname());
			setUserID(person.getUserID());
			setAffiliations(person.getAffiliations());
		}
		
		/**
		 * @see IBuilder#commit()
		 */
		public Person commit() throws InvalidDDMSException {
			return (isEmpty() ? null : new Person(getNames(), getSurname(), getPhones(), getEmails(), getUserID(),
				getAffiliations(), getExtensibleAttributes().commit()));
		}

		/**
		 * Helper method to determine if any values have been entered for this Person.
		 * 
		 * @return true if all values are empty
		 */
		public boolean isEmpty() {
			return (super.isEmpty()
				&& Util.isEmpty(getSurname())
				&& Util.isEmpty(getUserID())
				&& Util.containsOnlyEmptyValues(getAffiliations()));
		}
		
		/**
		 * Builder accessor for the surname
		 */
		public String getSurname() {
			return _surname;
		}

		/**
		 * Builder accessor for the surname
		 */
		public void setSurname(String surname) {
			_surname = surname;
		}

		/**
		 * Builder accessor for the userID
		 */
		public String getUserID() {
			return _userID;
		}

		/**
		 * Builder accessor for the userID
		 */
		public void setUserID(String userID) {
			_userID = userID;
		}

		/**
		 * Builder accessor for the affiliations
		 */
		public List<String> getAffiliations() {
			if (_affiliations == null)
				_affiliations = new LazyList(String.class);
			return _affiliations;
		}

		/**
		 * Builder accessor for the affiliations
		 */
		public void setAffiliations(List<String> affiliations) {
			_affiliations = new LazyList(affiliations, String.class);
		}
	}
} 