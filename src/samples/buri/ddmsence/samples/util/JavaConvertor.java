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
package buri.ddmsence.samples.util;

import nu.xom.Attribute;
import nu.xom.Element;
import buri.ddmsence.ddms.AbstractProducerRole;
import buri.ddmsence.ddms.IDDMSComponent;
import buri.ddmsence.ddms.Resource;
import buri.ddmsence.ddms.extensible.ExtensibleAttributes;
import buri.ddmsence.ddms.extensible.ExtensibleElement;
import buri.ddmsence.ddms.format.Format;
import buri.ddmsence.ddms.resource.Dates;
import buri.ddmsence.ddms.resource.Identifier;
import buri.ddmsence.ddms.resource.Language;
import buri.ddmsence.ddms.resource.Organization;
import buri.ddmsence.ddms.resource.Person;
import buri.ddmsence.ddms.resource.Rights;
import buri.ddmsence.ddms.resource.Service;
import buri.ddmsence.ddms.resource.Source;
import buri.ddmsence.ddms.resource.Subtitle;
import buri.ddmsence.ddms.resource.Title;
import buri.ddmsence.ddms.resource.Type;
import buri.ddmsence.ddms.resource.Unknown;
import buri.ddmsence.ddms.security.Security;
import buri.ddmsence.ddms.security.ism.SecurityAttributes;
import buri.ddmsence.ddms.summary.BoundingBox;
import buri.ddmsence.ddms.summary.BoundingGeometry;
import buri.ddmsence.ddms.summary.Category;
import buri.ddmsence.ddms.summary.Description;
import buri.ddmsence.ddms.summary.GeographicIdentifier;
import buri.ddmsence.ddms.summary.GeospatialCoverage;
import buri.ddmsence.ddms.summary.Keyword;
import buri.ddmsence.ddms.summary.Link;
import buri.ddmsence.ddms.summary.PostalAddress;
import buri.ddmsence.ddms.summary.RelatedResource;
import buri.ddmsence.ddms.summary.RelatedResources;
import buri.ddmsence.ddms.summary.SubjectCoverage;
import buri.ddmsence.ddms.summary.TemporalCoverage;
import buri.ddmsence.ddms.summary.VerticalExtent;
import buri.ddmsence.ddms.summary.VirtualCoverage;
import buri.ddmsence.ddms.summary.gml.Point;
import buri.ddmsence.ddms.summary.gml.Polygon;
import buri.ddmsence.ddms.summary.gml.Position;
import buri.ddmsence.ddms.summary.gml.SRSAttributes;
import buri.ddmsence.util.DDMSVersion;
import buri.ddmsence.util.PropertyReader;
import buri.ddmsence.util.Util;

/**
 * Utility class which can convert a DDMS Resource into the Java source code that would be used to create it from
 * scratch.
 * 
 * <p>
 * This utility is used in the Essentials sample application to show how a DDMS Resource built from an XML file could
 * also be built in Java. It was not put into the IDDMSComponent interface, because it's a one-off and more related to
 * DDMSence than DDMS.
 * </p>
 * 
 * <p>
 * This utility was a late-game brainstorm that I thought might be useful. It is NOT well-tested, NOT well-covered, and
 * NOT thread safe. Should it ever become an official part of the library, I would want to redo this in a more
 * maintainable form.
 * </p>
 * 
 * @author Brian Uri!
 * @since 0.9.b
 */
public class JavaConvertor {
	
	private static int _variableCount = 0;
	
	/**
	 * Empty constructor
	 */
	private JavaConvertor() {}
	
	/**
	 * Converts the resource into sample Java code. This functionality was not included in
	 * the core library, because it's DDMSence-specific, and a one-off from DDMS.
	 *
	 * @param r the resource to convert
	 * @return the Java code as a String
	 */
	public static String toJavaCode(Resource r) {
		resetVariableCount();
		StringBuffer java = new StringBuffer();
		java.append("// Generated by Essentials in DDMSence v" + PropertyReader.getProperty("version") + "\n");
		java.append("DDMSVersion.setCurrentVersion(\"").append(DDMSVersion.getVersionForDDMSNamespace(r.getNamespace())).append("\");\n");
		java.append("SRSAttributes srsAttributes = null;\n");
		java.append("SecurityAttributes securityAttributes = null;\n");
		java.append("ExtensibleAttributes extensions = null;\n");
		java.append("Position position = null;\n");
		java.append("List<Double> coords = new ArrayList<Double>();\n");
		java.append("List<IDDMSComponent> topLevelComponents = new ArrayList<IDDMSComponent>();\n");		
		for (IDDMSComponent component : r.getTopLevelComponents()) {
			// Resource Set
			if (component instanceof Identifier)
				convert(java, (Identifier) component);
			else if (component instanceof Title)
				convert(java, (Title) component);
			else if (component instanceof Subtitle)
				convert(java, (Subtitle) component);
			else if (component instanceof Description)
				convert(java, (Description) component);
			else if (component instanceof Language)
				convert(java, (Language) component);
			else if (component instanceof Dates)
				convert(java, (Dates) component);
			else if (component instanceof Rights)
				convert(java, (Rights) component);
			else if (component instanceof Source)
				convert(java, (Source) component);
			else if (component instanceof Type)
				convert(java, (Type) component);
			else if (component instanceof AbstractProducerRole)
				convert(java, (AbstractProducerRole) component);
			
			// Format Set
			else if (component instanceof Format)
				convert(java, (Format) component);
			
			// Summary Set
			else if (component instanceof SubjectCoverage)
				convert(java, (SubjectCoverage) component);
			else if (component instanceof VirtualCoverage)
				convert(java, (VirtualCoverage) component);
			else if (component instanceof TemporalCoverage)
				convert(java, (TemporalCoverage) component);
			else if (component instanceof GeospatialCoverage)
				convert(java, (GeospatialCoverage) component);
			else if (component instanceof RelatedResources)
				convert(java, (RelatedResources) component);
			
			// Security Set
			else if (component instanceof Security)
				convert(java, (Security) component);
			
			// Extensible Layer
			else if (component instanceof ExtensibleElement)
				convert(java, (ExtensibleElement) component);
		}
				
		
		java.append("\n// ddms:Resource\n");
		convert(java, r.getSecurityAttributes());
		convert(java, r.getExtensibleAttributes());
		java.append("Resource resource = new Resource(topLevelComponents, ");
		if (r.isResourceElement() != null)
			java.append("Boolean.").append(r.isResourceElement().booleanValue() ? "TRUE" : "FALSE").append(", ");
		else
			java.append("null, ");
		if (r.getCreateDate() != null) {
			java.append("\"").append(r.getCreateDate().toXMLFormat()).append("\", ");
		}
		else
			java.append("null, ");
		if (r.getDESVersion() != null) {
			java.append(r.getDESVersion().intValue()).append(", ");
		}
		else
			java.append("null, ");
		java.append("securityAttributes, extensions);\n");
		return (java.toString());
	}
		
	/**
	 * Helper method to convert an identifier into Java code
	 * 
	 * @param java the buffer to add to
	 * @param i the identifier
	 */
	public static void convert(StringBuffer java, Identifier identifier) {
		int count = getVariableCount();
		java.append("\n// ddms:identifier\n");
		java.append("Identifier identifier").append(count).append(" = new Identifier(\"")
			.append(identifier.getQualifier()).append("\", \"").append(identifier.getValue()).append("\");\n");
		java.append("topLevelComponents.add(identifier").append(count).append(");\n");
	}
	
	/**
	 * Helper method to convert a title into Java code
	 * 
	 * @param java the buffer to add to
	 * @param title the title
	 */
	public static void convert(StringBuffer java, Title title) {
		int count = getVariableCount();
		java.append("\n// ddms:title\n");
		convert(java, title.getSecurityAttributes());
		java.append("Title title").append(count).append(" = new Title(\"").append(title.getValue())
			.append("\", securityAttributes);\n");
		java.append("topLevelComponents.add(title").append(count).append(");\n");
	}
	
	/**
	 * Helper method to convert a subtitle into Java code
	 * 
	 * @param java the buffer to add to
	 * @param subtitle the subtitle
	 */
	public static void convert(StringBuffer java, Subtitle subtitle) {
		int count = getVariableCount();
		java.append("\n// ddms:subtitle\n");
		convert(java, subtitle.getSecurityAttributes());
		java.append("Subtitle subtitle").append(count).append(" = new Subtitle(\"").append(subtitle.getValue())
			.append("\", securityAttributes);\n");
		java.append("topLevelComponents.add(subtitle").append(count).append(");\n");
	}
	
	/**
	 * Helper method to convert a description into Java code
	 * 
	 * @param java the buffer to add to
	 * @param description the description
	 */
	public static void convert(StringBuffer java, Description description) {
		java.append("\n// ddms:description\n");
		convert(java, description.getSecurityAttributes());
		java.append("Description description = new Description(\"").append(description.getValue())
			.append("\", securityAttributes);\n");
		java.append("topLevelComponents.add(description);\n");
	}
	
	/**
	 * Helper method to convert a language into Java code
	 * 
	 * @param java the buffer to add to
	 * @param language the language
	 */
	public static void convert(StringBuffer java, Language language) {
		int count = getVariableCount();
		java.append("\n// ddms:language\n");
		java.append("Language language").append(count).append(" = new Language(\"").append(language.getQualifier())
			.append("\", \"").append(language.getValue()).append("\");\n");
		java.append("topLevelComponents.add(language").append(count).append(");\n");
	}
	
	/**
	 * Helper method to convert a dates into Java code
	 * 
	 * @param java the buffer to add to
	 * @param dates the dates
	 */
	public static void convert(StringBuffer java, Dates dates) {
		java.append("\n// ddms:dates\n");
		java.append("Dates dates = new Dates(\"");
		if (dates.getCreated() != null)
			java.append(dates.getCreated().toXMLFormat());
		java.append("\", \"");
		if (dates.getPosted() != null)
			java.append(dates.getPosted().toXMLFormat());
		java.append("\", \"");
		if (dates.getValidTil() != null)
			java.append(dates.getValidTil().toXMLFormat());
		java.append("\", \"");
		if (dates.getInfoCutOff() != null)
			java.append(dates.getInfoCutOff().toXMLFormat());
		java.append("\", \"");
		if (dates.getApprovedOn() != null)
			java.append(dates.getApprovedOn().toXMLFormat());		
		java.append("\");\n");
		java.append("topLevelComponents.add(dates);\n");
	}
	
	/**
	 * Helper method to convert a Rights into Java code
	 * 
	 * @param java the buffer to add to
	 * @param rights the rights
	 */
	public static void convert(StringBuffer java, Rights rights) {
		java.append("\n// ddms:rights\n");
		java.append("Rights rights = new Rights(").append(rights.getPrivacyAct()).append(", ")
			.append(rights.getIntellectualProperty()).append(", ").append(rights.getCopyright()).append(");\n");
		java.append("topLevelComponents.add(rights);\n");
	}
	
	/**
	 * Helper method to convert a Source into Java code
	 * 
	 * @param java the buffer to add to
	 * @param source the source
	 */
	public static void convert(StringBuffer java, Source source) {
		int count = getVariableCount();
		java.append("\n// ddms:source\n");
		convert(java, source.getSecurityAttributes());	
		java.append("Source source").append(count).append(" = new Source(\"").append(source.getQualifier())
			.append("\", \"").append(source.getValue()).append("\", \"").append(source.getSchemaQualifier())
			.append("\", \"").append(source.getSchemaHref()).append("\", securityAttributes);\n");
		java.append("topLevelComponents.add(source").append(count).append(");\n");
	}
	
	/**
	 * Helper method to convert a type into Java code
	 * 
	 * @param java the buffer to add to
	 * @param type the type
	 */
	public static void convert(StringBuffer java, Type type) {
		int count = getVariableCount();
		java.append("\n// ddms:type\n");
		convert(java, type.getSecurityAttributes());	
		java.append("Type type").append(count).append(" = new Type(\"").append(type.getQualifier()).append("\", \"")
			.append(type.getValue()).append("\", securityAttributes);\n");
		java.append("topLevelComponents.add(type").append(count).append(");\n");
	}
	
	/**
	 * Helper method to convert an organization into Java code
	 * 
	 * @param java the buffer to add to
	 * @param parentType the parentType
	 * @param organization the organization
	 */
	public static String convert(StringBuffer java, String parentType, Organization organization) {
		int count = getVariableCount();				
		java.append("\n// ddms:organization\n");
		convert(java, organization.getExtensibleAttributes());
		java.append("List<String> names").append(count).append(" = new ArrayList<String>();\n");
		for (String name : organization.getNames())
			java.append("names").append(count).append(".add(\"").append(name).append("\");\n");
		java.append("List<String> phones").append(count).append(" = new ArrayList<String>();\n");
		for (String phone : organization.getPhones())
			java.append("phones").append(count).append(".add(\"").append(phone).append("\");\n");
		java.append("List<String> emails").append(count).append(" = new ArrayList<String>();\n");
		for (String email : organization.getEmails())
			java.append("emails").append(count).append(".add(\"").append(email).append("\");\n");
		java.append("Organization organization").append(count).append(" = new Organization(\"").append(parentType).append("\", ");
		java.append(" names").append(count).append(", ");
		java.append(" phones").append(count).append(", emails").append(count)
			.append(", extensions);\n");
		return ("organization" + count);
	}
	
	/**
	 * Helper method to convert a service into Java code
	 * 
	 * @param java the buffer to add to
	 * @param parentType the parentType
	 * @param service the service
	 */
	public static String convert(StringBuffer java, String parentType, Service service) {
		int count = getVariableCount();				
		java.append("\n// ddms:service\n");
		convert(java, service.getExtensibleAttributes());
		java.append("List<String> names").append(count).append(" = new ArrayList<String>();\n");
		for (String name : service.getNames())
			java.append("names").append(count).append(".add(\"").append(name).append("\");\n");
		java.append("List<String> phones").append(count).append(" = new ArrayList<String>();\n");
		for (String phone : service.getPhones())
			java.append("phones").append(count).append(".add(\"").append(phone).append("\");\n");
		java.append("List<String> emails").append(count).append(" = new ArrayList<String>();\n");
		for (String email : service.getEmails())
			java.append("emails").append(count).append(".add(\"").append(email).append("\");\n");
		java.append("Service service").append(count).append(" = new Service(\"").append(parentType).append("\", ");
		java.append(" names").append(count).append(", ");
		java.append(" phones").append(count).append(", emails").append(count)
			.append(", extensions);\n");
		return ("service" + count);
	}
	
	/**
	 * Helper method to convert an unknown into Java code
	 * 
	 * @param java the buffer to add to
	 * @param parentType the parentType
	 * @param unknown the unknown
	 */
	public static String convert(StringBuffer java, String parentType, Unknown unknown) {
		int count = getVariableCount();				
		java.append("\n// ddms:unknown\n");
		convert(java, unknown.getExtensibleAttributes());
		java.append("List<String> names").append(count).append(" = new ArrayList<String>();\n");
		for (String name : unknown.getNames())
			java.append("names").append(count).append(".add(\"").append(name).append("\");\n");
		java.append("List<String> phones").append(count).append(" = new ArrayList<String>();\n");
		for (String phone : unknown.getPhones())
			java.append("phones").append(count).append(".add(\"").append(phone).append("\");\n");
		java.append("List<String> emails").append(count).append(" = new ArrayList<String>();\n");
		for (String email : unknown.getEmails())
			java.append("emails").append(count).append(".add(\"").append(email).append("\");\n");
		java.append("Unknown unknown").append(count).append(" = new Service(\"").append(parentType).append("\", ");
		java.append(" names").append(count).append(", ");
		java.append(" phones").append(count).append(", emails").append(count)
			.append(", extensions);\n");
		return ("unknown" + count);
	}
	
	/**
	 * Helper method to convert a person into Java code
	 * 
	 * @param java the buffer to add to
	 * @param parentType the parentType
	 * @param person the person
	 */
	public static String convert(StringBuffer java, String parentType, Person person) {
		int count = getVariableCount();				
		java.append("\n// ddms:person\n");
		convert(java, person.getExtensibleAttributes());
		java.append("List<String> names").append(count).append(" = new ArrayList<String>();\n");
		for (String name : person.getNames())
			java.append("names").append(count).append(".add(\"").append(name).append("\");\n");
		java.append("List<String> phones").append(count).append(" = new ArrayList<String>();\n");
		for (String phone : person.getPhones())
			java.append("phones").append(count).append(".add(\"").append(phone).append("\");\n");
		java.append("List<String> emails").append(count).append(" = new ArrayList<String>();\n");
		for (String email : person.getEmails())
			java.append("emails").append(count).append(".add(\"").append(email).append("\");\n");
		java.append("Person person").append(count).append(" = new Person(\"").append(parentType).append("\", ");
		java.append("\"").append(person.getSurname()).append("\", ");
		java.append(" names").append(count).append(", ");
		java.append("\"").append(person.getUserID()).append("\", \"")
			.append(person.getAffiliation()).append("\", ");
		java.append(" phones").append(count).append(", emails").append(count)
			.append(", extensions);\n");
		return ("person" + count);
	}
	
	/**
	 * Helper method to convert a producer into Java code
	 * 
	 * @param java the buffer to add to
	 * @param producer the producer
	 */
	public static void convert(StringBuffer java, AbstractProducerRole producer) {
		int count = getVariableCount();
		String producerType = producer.getName();
		String producerClass = Util.capitalize(producerType);
				
		java.append("\n// ddms:").append(producerType).append("\n");
		convert(java, producer.getSecurityAttributes());
		String entityVariable = null;
		if (Person.NAME.equals(producer.getProducerEntity().getName()))
			entityVariable = convert(java, producerType, (Person) producer.getProducerEntity());
		if (Organization.NAME.equals(producer.getProducerEntity().getName()))
			entityVariable = convert(java, producerType, (Organization) producer.getProducerEntity());
		if (Service.NAME.equals(producer.getProducerEntity().getName()))
			entityVariable = convert(java, producerType, (Service) producer.getProducerEntity());
		if (Unknown.NAME.equals(producer.getProducerEntity().getName()))
			entityVariable = convert(java, producerType, (Unknown) producer.getProducerEntity());

		java.append(producerClass).append(" ").append(producerType).append(count).append(" = new ");
		java.append(producerClass).append("(").append(entityVariable).append(", \"").append(producer.getPOCType()).append("\", securityAttributes);\n");
		java.append("topLevelComponents.add(").append(producerType).append(count).append(");\n");
	}
		
	/**
	 * Helper method to convert a format into Java code
	 * 
	 * @param java the buffer to add to
	 * @param format the format
	 */
	public static void convert(StringBuffer java, Format format) {
		int count = getVariableCount();
		java.append("\n// ddms:format\n");
		java.append("Format format").append(count).append(" = new Format(\"").append(format.getMimeType())
			.append("\", ");
		if (format.getExtent() != null) {
			java.append(" new MediaExtent(\"").append(format.getExtentQualifier()).append("\", \"")
				.append(format.getExtentValue()).append("\"), ");
		}
		else
			java.append(" null, ");
		java.append("\"").append(format.getMedium()).append("\");\n");
		java.append("topLevelComponents.add(format").append(count).append(");\n");
	}
	
	/**
	 * Helper method to convert a security into Java code
	 * 
	 * @param java the buffer to add to
	 * @param security the security
	 */
	public static void convert(StringBuffer java, Security security) {
		java.append("\n// ddms:security\n");
		convert(java, security.getSecurityAttributes());
		java.append("Security security = new Security(securityAttributes);\n");
		java.append("topLevelComponents.add(security);\n");
	}
	
	/**
	 * Helper method to convert an extensible layer element into Java code
	 * 
	 * @param java the buffer to add to
	 * @param extension the element
	 */
	public static void convert(StringBuffer java, ExtensibleElement extension) {
		java.append("\n// Extensible Layer element\n");
		java.append("// For example's sake, this code just generates the bare bones top-level extensible element.\n");
		
		Element element = extension.getXOMElementCopy();
		java.append("Element element = new Element(\"").append(element.getQualifiedName()).append("\", \"")
			.append(element.getNamespaceURI()).append("\");\n");
		java.append("ExtensibleElement extElement = new ExtensibleElement(element);\n");
		java.append("topLevelComponents.add(extElement);\n");
	}
	
	/**
	 * Helper method to convert a subjectCoverage into Java code
	 * 
	 * @param java the buffer to add to
	 * @param subjectCoverage the subjectCoverage
	 */
	public static void convert(StringBuffer java, SubjectCoverage subjectCoverage) {
		java.append("\n// ddms:subjectCoverage\n");
		convert(java, subjectCoverage.getSecurityAttributes());
		java.append("List<Keyword> keywords = new ArrayList<Keyword>();\n");
		for (Keyword keyword : subjectCoverage.getKeywords()) {
			convert(java, keyword.getExtensibleAttributes());
			java.append("keywords.add(new Keyword(\"").append(keyword.getValue()).append("\", extensions));\n");
		}
		java.append("List<Category> categories = new ArrayList<Category>();\n");
		for (Category category : subjectCoverage.getCategories()) {
			convert(java, category.getExtensibleAttributes());
			java.append("categories.add(new Category(\"").append(category.getQualifier()).append("\", \"")
				.append(category.getCode()).append("\", \"").append(category.getLabel()).append("\", extensions));\n");
		}
		java.append("SubjectCoverage subjectCoverage = new SubjectCoverage(keywords, categories, securityAttributes);\n");
		java.append("topLevelComponents.add(subjectCoverage);\n");
	}
	
	/**
	 * Helper method to convert a virtualCoverage into Java code
	 * 
	 * @param java the buffer to add to
	 * @param virtualCoverage the virtualCoverage
	 */
	public static void convert(StringBuffer java, VirtualCoverage virtualCoverage) {
		int count = getVariableCount();
		java.append("\n// ddms:virtualCoverage\n");
		convert(java, virtualCoverage.getSecurityAttributes());
		java.append("VirtualCoverage virtualCoverage").append(count).append(" = new VirtualCoverage(\"")
			.append(virtualCoverage.getAddress()).append("\", \"").append(virtualCoverage.getProtocol())
			.append("\", securityAttributes);\n");
		java.append("topLevelComponents.add(virtualCoverage").append(count).append(");\n");
	}
	
	/**
	 * Helper method to convert a temporalCoverage into Java code
	 * 
	 * @param java the buffer to add to
	 * @param temporalCoverage the temporalCoverage
	 */
	public static void convert(StringBuffer java, TemporalCoverage temporalCoverage) {
		int count = getVariableCount();
		java.append("\n// ddms:temporalCoverage\n");
		convert(java, temporalCoverage.getSecurityAttributes());
		java.append("TemporalCoverage temporalCoverage").append(count).append(" = new TemporalCoverage(\"")
			.append(temporalCoverage.getTimePeriodName()).append("\", \"").append(temporalCoverage.getStartString())
			.append("\", \"").append(temporalCoverage.getEndString()).append("\", securityAttributes);\n");
		java.append("topLevelComponents.add(temporalCoverage").append(count).append(");\n");
	}
	
	/**
	 * Helper method to convert a geospatialCoverage into Java code
	 * 
	 * @param java the buffer to add to
	 * @param coverage the geospatialCoverage
	 */
	public static void convert(StringBuffer java, GeospatialCoverage coverage) {
		int count = getVariableCount();
		java.append("\n// ddms:geospatialCoverage\n");
		convert(java, coverage.getSecurityAttributes());
		
		java.append("GeographicIdentifier geoId").append(count).append(" = null;\n");
		java.append("BoundingBox boundingBox").append(count).append(" = null;\n");
		java.append("BoundingGeometry boundingGeo").append(count).append(" = null;\n");
		java.append("PostalAddress postalAddress").append(count).append(" = null;\n");
		java.append("VerticalExtent vertExtent").append(count).append(" = null;\n");
		
		if (coverage.getGeographicIdentifier() != null) {
			convert(java, count, coverage.getGeographicIdentifier());
		}
		if (coverage.getBoundingBox() != null) {
			BoundingBox box = coverage.getBoundingBox();
			java.append("boundingBox").append(count).append(" = new BoundingBox(").append(box.getWestBL().doubleValue())
				.append(", ").append(box.getEastBL().doubleValue()).append(", ").append(box.getSouthBL().doubleValue())
				.append(", ").append(box.getNorthBL().doubleValue()).append(");\n");
		}
		if (coverage.getBoundingGeometry() != null) {
			convert(java, count, coverage.getBoundingGeometry());
		}
		if (coverage.getPostalAddress() != null) {
			convert(java, count, coverage.getPostalAddress());
		}
		if (coverage.getVerticalExtent() != null) {
			VerticalExtent vert = coverage.getVerticalExtent();
			java.append("vertExtent").append(count).append(" = new VerticalExtent(")
				.append(vert.getMinVerticalExtent().doubleValue()).append(", ")
				.append(vert.getMaxVerticalExtent().doubleValue()).append(", \"").append(vert.getUnitOfMeasure())
				.append("\", \"").append(vert.getDatum()).append("\");\n");
		}
	java.append("GeospatialCoverage geospatialCoverage").append(count).append(" = new GeospatialCoverage(geoId")
		.append(count).append(", boundingBox").append(count).append(", boundingGeo").append(count)
		.append(", postalAddress").append(count).append(", vertExtent").append(count)
		.append(", securityAttributes);\n");
	java.append("topLevelComponents.add(geospatialCoverage").append(count).append(");\n");		
	}
	
	/**
	 * Helper method to convert a geoId into Java code
	 * 
	 * @param java the buffer to add to
	 * @param the parent count
	 * @param geoId the GeographicIdentifier
	 */
	public static void convert(StringBuffer java, int count, GeographicIdentifier geoId) {
		if (geoId.hasFacilityIdentifier()) {
			java.append("FacilityIdentifier facId").append(count).append(" = new FacilityIdentifier(\"")
				.append(geoId.getFacilityIdentifier().getBeNumber()).append("\", \"")
				.append(geoId.getFacilityIdentifier().getOsuffix()).append("\");\n");
			java.append("geoId").append(count).append(" = new GeographicIdentifier(facId);\n");
		}
		else {
			java.append("List<String> names").append(count).append(" = new ArrayList<String>();\n");
			for (String name : geoId.getNames())
				java.append("names").append(count).append(".add(\"").append(name).append("\");\n");
			java.append("List<String> regions").append(count).append(" = new ArrayList<String>();\n");
			for (String region : geoId.getRegions())
				java.append("regions").append(count).append(".add(\"").append(region).append("\");\n");
			java.append("geoId").append(count).append(" = new GeographicIdentifier(names").append(count)
				.append(", regions").append(count).append(", ");
			if (geoId.getCountryCode() != null)
				java.append("new CountryCode(\"geographicIdentifier\", \"").append(geoId.getCountryCode().getQualifier()).append("\", \"")
					.append(geoId.getCountryCode().getValue()).append("\"));\n");
			else
				java.append("null);\n");
		}
	}
	
	/**
	 * Helper method to convert a geometry into Java code
	 * 
	 * @param java the buffer to add to
	 * @param the parent count
	 * @param b the BoundingGeometry
	 */
	public static void convert(StringBuffer java, int count, BoundingGeometry b) {
		java.append("List<Polygon> polygons").append(count).append(" = new ArrayList<Polygon>();\n");
		for (Polygon polygon : b.getPolygons()) {
			java.append("List<Position> positions").append(count).append(" = new ArrayList<Position>();\n");
			for (Position p : polygon.getPositions()) {
				convert(java, p.getSRSAttributes());
				java.append("coords.clear();\n");
				for (Double coord : p.getCoordinates()) {
					java.append("coords.add(new Double(").append(coord.doubleValue()).append("));\n");
				}
				java.append("positions").append(count).append(".add(new Position(coords, srsAttributes));\n");
			}			
			convert(java, polygon.getSRSAttributes());
			java.append("polygons").append(count).append(".add(new Polygon(positions").append(count)
				.append(", srsAttributes, \"").append(polygon.getId()).append("\"));\n");
		}
		java.append("List<Point> points").append(count).append(" = new ArrayList<Point>();\n");
		for (Point point : b.getPoints()) {
			convert(java, point.getPosition().getSRSAttributes());
			java.append("coords.clear();\n");
			for (Double coord : point.getPosition().getCoordinates()) {
				java.append("coords.add(new Double(").append(coord.doubleValue()).append("));\n");
			}
			java.append("position = new Position(coords, srsAttributes);\n");
			convert(java, point.getSRSAttributes());
			java.append("points").append(count).append(".add(new Point(position, srsAttributes, \"")
				.append(point.getId()).append("\"));\n");
		}
		java.append("boundingGeo").append(count).append(" = new BoundingGeometry(polygons").append(count)
			.append(", points").append(count).append(");\n");
	}
	
	/**
	 * Helper method to convert a postalAddress into Java code
	 * 
	 * @param java the buffer to add to
	 * @param the parent count
	 * @param address the the address
	 */
	public static void convert(StringBuffer java, int count, PostalAddress address) {
		java.append("List<String> streets").append(count).append(" = new ArrayList<String>();\n");
		for (String street : address.getStreets())
			java.append("streets").append(count).append(".add(\"").append(street).append("\");\n");
		boolean hasState = !Util.isEmpty(address.getState());
		java.append("postalAddress").append(count).append(" = new PostalAddress(streets").append(count).append(", \"")
			.append(address.getCity()).append("\", \"")
			.append(hasState ? address.getState() : address.getProvince()).append("\", \"")
			.append(address.getPostalCode()).append("\", ");
		if (address.getCountryCode() != null)
			java.append("new CountryCode(\"postalAddress\", \"").append(address.getCountryCode().getQualifier()).append("\", \"")
				.append(address.getCountryCode().getValue()).append("\"), ");
		else
			java.append("null, ");
		java.append(hasState).append(");\n");
	}
	
	/**
	 * Helper method to convert a relatedResources into Java code
	 * 
	 * @param java the buffer to add to
	 * @param resources the relatedResources
	 */
	public static void convert(StringBuffer java, RelatedResources resources) {
		int count = getVariableCount();
		java.append("\n// ddms:relatedResources\n");
		convert(java, resources.getSecurityAttributes());
		java.append("List<RelatedResource> resources").append(count).append(" = new ArrayList<RelatedResource>();\n");
		
		for (RelatedResource singleResource : resources.getRelatedResources()) {
			int resCount = getVariableCount();
			java.append("List<Link> links").append(resCount).append(" = new ArrayList<Link>();\n");
			for (Link link : singleResource.getLinks()) {
				java.append("links").append(resCount).append(".add(new Link(\"").append(link.getHref()).append("\", \"")
					.append(link.getRole()).append("\", \"").append(link.getTitle()).append("\", \"")
					.append(link.getLabel()).append("\"));\n");
			}
			java.append("resources").append(count).append(".add(new RelatedResource(links").append(resCount)
				.append(", \"").append(singleResource.getQualifier()).append("\", \"")
				.append(singleResource.getValue()).append("\"));\n");
		}		
		java.append("RelatedResources relatedResources").append(count).append(" = new RelatedResources(resources")
			.append(count).append(", \"").append(resources.getRelationship()).append("\", \"")
			.append(resources.getDirection()).append("\", securityAttributes);\n");
		java.append("topLevelComponents.add(relatedResources").append(count).append(");\n");
	}
		
	/**
	 * Helper method to convert SRSAttributes into Java code
	 * 
	 * @param java the buffer to add to
	 * @param attributes the attributes
	 */
	private static void convert(StringBuffer java, SRSAttributes attributes) {
		int count = getVariableCount();
		java.append("List<String> axisLabels").append(count).append(" = new ArrayList<String>();\n");
		for (String axis : attributes.getAxisLabels())
			java.append("axisLabels").append(count).append(".add(\"").append(axis).append("\");\n");
		java.append("List<String> uomLabels").append(count).append(" = new ArrayList<String>();\n");
		for (String uom : attributes.getUomLabels())
			java.append("uomLabels").append(count).append(".add(\"").append(uom).append("\");\n");
		java.append("srsAttributes = new SRSAttributes(\"").append(attributes.getSrsName()).append("\", ");
		if (attributes.getSrsDimension() != null)
			java.append("new Integer(").append(attributes.getSrsDimension()).append("), ");
		else
			java.append("null, ");
		java.append("axisLabels").append(count).append(", uomLabels").append(count).append(");\n");
	}
	
	/**
	 * Helper method to convert SecurityAttributes into Java code
	 * 
	 * @param java the buffer to add to
	 * @param attributes the attributes
	 */
	private static void convert(StringBuffer java, SecurityAttributes attributes) {
		int count = getVariableCount();
		if (attributes == null || attributes.isEmpty()) {
			java.append("securityAttributes = null;\n");
			return;
		}
		java.append("List<String> ownerProducers").append(count).append(" = new ArrayList<String>();\n");
		for (String op : attributes.getOwnerProducers())
			java.append("ownerProducers").append(count).append(".add(\"").append(op).append("\");\n");
		
		java.append("Map<String, String> otherAttributes").append(count).append(" = new HashMap<String, String>();\n");
		addSecurityAttribute(java, count, "SCIcontrols", Util.getXsList(attributes.getSCIcontrols()));
		addSecurityAttribute(java, count, "SARIdentifier", Util.getXsList(attributes.getSARIdentifier()));
		addSecurityAttribute(java, count, "disseminationControls", Util.getXsList(attributes.getDisseminationControls()));
		addSecurityAttribute(java, count, "FGIsourceOpen", Util.getXsList(attributes.getFGIsourceOpen()));
		addSecurityAttribute(java, count, "FGIsourceProtected", Util.getXsList(attributes.getFGIsourceProtected()));		
		addSecurityAttribute(java, count, "releasableTo", Util.getXsList(attributes.getReleasableTo()));
		addSecurityAttribute(java, count, "nonICmarkings", Util.getXsList(attributes.getNonICmarkings()));
		addSecurityAttribute(java, count, "classifiedBy", attributes.getClassifiedBy());
		addSecurityAttribute(java, count, "compilationReason", attributes.getCompilationReason());
		addSecurityAttribute(java, count, "derivativelyClassifiedBy", attributes.getDerivativelyClassifiedBy());
		addSecurityAttribute(java, count, "classificationReason", attributes.getClassificationReason());
		addSecurityAttribute(java, count, "derivedFrom", attributes.getDerivedFrom());
		if (attributes.getDeclassDate() != null)
			addSecurityAttribute(java, count, "declassDate", attributes.getDeclassDate().toXMLFormat());
		addSecurityAttribute(java, count, "declassEvent", attributes.getDeclassEvent());
		addSecurityAttribute(java, count, "declassException", attributes.getDeclassException());
		addSecurityAttribute(java, count, "typeOfExemptedSource", attributes.getTypeOfExemptedSource());
		if (attributes.getDateOfExemptedSource() != null)
			addSecurityAttribute(java, count, "dateOfExemptedSource", attributes.getDateOfExemptedSource().toXMLFormat());
		if (attributes.getDeclassManualReview() != null)
			addSecurityAttribute(java, count, "declassManualReview", attributes.getDeclassManualReview().toString());
		
		java.append("securityAttributes = new SecurityAttributes(\"").append(attributes.getClassification())
			.append("\", ").append("ownerProducers").append(count).append(", otherAttributes").append(count)
			.append(");\n");		
	}
		
	/**
	 * Helper method to add one of the OtherAttributes to the java code.
	 * 
	 * @param java	the string buffer
	 * @param count the suffix count
	 * @param name the name of the attribute
	 * @param value the value of the attribute
	 */
	private static void addSecurityAttribute(StringBuffer java, int count, String name, String value) {
		if (!Util.isEmpty(value)) {
			java.append("otherAttributes").append(count).append(".put(\"").append(name).append("\", \"")
				.append(value).append("\");\n");
		}
	}
	
	/**
	 * Helper method to convert ExtensibleAttributes into Java code
	 * 
	 * @param java the buffer to add to
	 * @param attributes the attributes
	 */
	private static void convert(StringBuffer java, ExtensibleAttributes attributes) {
		int count = getVariableCount();
		if (attributes == null || attributes.isEmpty()) {
			java.append("extensions = null;\n");
			return;
		}
		java.append("List<Attribute> attributes").append(count).append(" = new ArrayList<Attribute>();\n");
		for (Attribute attr : attributes.getAttributes()) {
			java.append("attributes").append(count).append(".add(new Attribute(\"").append(attr.getQualifiedName())
				.append("\", \"").append(attr.getNamespaceURI()).append("\", \"").append(attr.getValue())
				.append("\"));\n");
		}
		java.append("extensions = new ExtensibleAttributes(attributes").append(count).append(");\n");
	}
	/**
	 * Maintains a count for variable names.
	 * 
	 * @return the count
	 */
	private static int getVariableCount() {
		_variableCount++;
		return (_variableCount);
	}
	
	/**
	 * Resets the count for variable names.
	 */
	private static void resetVariableCount() {
		_variableCount = 0;
	}
}
