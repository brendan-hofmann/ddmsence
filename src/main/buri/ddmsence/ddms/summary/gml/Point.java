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
package buri.ddmsence.ddms.summary.gml;

import java.io.Serializable;

import nu.xom.Element;
import buri.ddmsence.ddms.AbstractBaseComponent;
import buri.ddmsence.ddms.IBuilder;
import buri.ddmsence.ddms.InvalidDDMSException;
import buri.ddmsence.util.DDMSVersion;
import buri.ddmsence.util.PropertyReader;
import buri.ddmsence.util.Util;

/**
 * An immutable implementation of gml:Point.
 * 
 * <p>
 * The DDMS documentation has no Text/HTML examples for the output of this component, so a best guess was taken
 * (suggestions are welcome, as this is probably not an optimal solution):
 * </p>
 * 
 * <ul>
 * <p>
 * <b>Suggested Text Output</b><br />
 * <code>
 * boundingGeometry.id: value<br />
 * boundingGeometry.type: Point<br />
 * boundingGeometry.srsName: value<br />
 * boundingGeometry.srsDimension: value<br />
 * boundingGeometry.axisLabels: value<br />
 * boundingGeometry.uomLabels: value<br />
 * boundingGeometry.position: value<br />
 * boundingGeometry.position.srsName: value<br />
 * boundingGeometry.position.srsDimension: value<br />
 * boundingGeometry.position.axisLabels: value<br />
 * boundingGeometry.position.uomLabels: value<br />
 * </code>
 * </p>
 * 
 * <p>
 * <b>Suggested HTML Output</b><br />
 * <code>
 * &lt;meta name="geospatialCoverage.GeospatialExtent.boundingGeometry.id" content="value" /&gt;<br />
 * &lt;meta name="geospatialCoverage.GeospatialExtent.boundingGeometry.type" content="Point" /&gt;<br />
 * &lt;meta name="geospatialCoverage.GeospatialExtent.boundingGeometry.srsName" content="value" /&gt;<br />
 * &lt;meta name="geospatialCoverage.GeospatialExtent.boundingGeometry.srsDimension" content="value" /&gt;<br />
 * &lt;meta name="geospatialCoverage.GeospatialExtent.boundingGeometry.axisLabels" content="value" /&gt;<br />
 * &lt;meta name="geospatialCoverage.GeospatialExtent.boundingGeometry.uomLabels" content="value" /&gt;<br />
 * &lt;meta name="geospatialCoverage.GeospatialExtent.boundingGeometry.position" content="value" /&gt;<br />
 * &lt;meta name="geospatialCoverage.GeospatialExtent.boundingGeometry.position.srsName" content="value" /&gt;<br />
 * &lt;meta name="geospatialCoverage.GeospatialExtent.boundingGeometry.position.srsDimension" content="value" /&gt;<br />
 * &lt;meta name="geospatialCoverage.GeospatialExtent.boundingGeometry.position.axisLabels" content="value" /&gt;<br />
 * &lt;meta name="geospatialCoverage.GeospatialExtent.boundingGeometry.position.uomLabels" content="value" /&gt;<br />
 * </code>
 * </p>
 * </ul>
 * 
 * <table class="info"><tr class="infoHeader"><th>Strictness</th></tr><tr><td class="infoBody">
 * <p>DDMSence is stricter than the specification in the following ways:</p>
 * <ul>
 * <li>The srsName must also be non-empty.</li>
 * </ul>
 * </td></tr></table>
 * 
 * 
 * <table class="info"><tr class="infoHeader"><th>Nested Elements</th></tr><tr><td class="infoBody">
 * <u>gml:pos</u>: the position (exactly 1 required)<br />
 * </td></tr></table>
 * 
 * <table class="info"><tr class="infoHeader"><th>Attributes</th></tr><tr><td class="infoBody">
 * <u>srsName</u>: A URI-based name (required)<br />
 * <u>srsDimension</u>: A positive integer dimension (optional)<br />
 * <u>axisLabels</u>: Ordered list of labels for the axes, as a space-delimited list of NCNames (valid XML names without
 * colons) (optional, but if no srsName is set, this should be omitted too)<br />
 * <u>uomLabels</u>: Ordered list of unit of measure (uom) labels for all the axes, as a space-delimited list of NCNames
 * (valid XML names without colons) (required when axisLabels is set)<br />
 * <u>gml:id</u>: unique ID (required)<br />
 * </td></tr></table>
 * 
 * <table class="info"><tr class="infoHeader"><th>DDMS Information</th></tr><tr><td class="infoBody">
 * <u>Link</u>: http://metadata.ces.mil/mdr/irs/DDMS/ddms_categories.htm#geospatialCoverage_GeospatialExtent_boundingGeometry_Point<br />
 * <u>Description</u>: Specifies a position using a single coordinate tuple.<br />
 * <u>Obligation</u>: Optional<br />
 * <u>Schema Modification Date</u>: 2010-01-25<br />
 * </td></tr></table>
 * 
 * @author Brian Uri!
 * @since 0.9.b
 */
public final class Point extends AbstractBaseComponent {

	// Values are cached upon instantiation, so objects are only created once
	private SRSAttributes _cachedSrsAttributes;
	private Position _cachedPosition;
	
	private static final String ID_NAME = "id";
	
	/**
	 * Constructor for creating a component from a XOM Element
	 *  
	 * @param element the XOM element representing this 
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	public Point(Element element) throws InvalidDDMSException {
		try {
			setXOMElement(element, false);
			Element posElement = element.getFirstChildElement(Position.getName(getDDMSVersion()), 
				element.getNamespaceURI());
			if (posElement != null)
				_cachedPosition = new Position(posElement);
			_cachedSrsAttributes = new SRSAttributes(element);
			setXOMElement(element, true);
		} catch (InvalidDDMSException e) {
			e.setLocator(getQualifiedName());
			throw (e);
		}
	}
	
	/**
	 * Constructor for creating a component from raw data
	 * 
	 * @param position the position of the Point (required)
	 * @param srsAttributes the attribute group containing srsName, srsDimension, axisLabels, and uomLabels (srsName
	 * required)
	 * @param id the id value (required)
	 * @throws InvalidDDMSException if any required information is missing or malformed
	 */
	public Point(Position position, SRSAttributes srsAttributes, String id) throws InvalidDDMSException {
		try {
			_cachedPosition = position;
			_cachedSrsAttributes = srsAttributes;
			DDMSVersion version = DDMSVersion.getCurrentVersion();
			Element element = Util.buildElement(PropertyReader.getProperty("gml.prefix"), Point.getName(version),
				version.getGmlNamespace(), null);
			if (position != null) {
				element.appendChild(position.getXOMElementCopy());
			}
			if (srsAttributes != null)
				srsAttributes.addTo(element);
			Util.addAttribute(element, PropertyReader.getProperty("gml.prefix"), ID_NAME, DDMSVersion
				.getCurrentVersion().getGmlNamespace(), id);
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
	 * <li>The srsName is required.</li>
	 * <li>The ID is required, and must be a valid NCName.</li>
	 * <li>If the position has an srsName, it matches the srsName of this Point.</li>
	 * <li>Does not perform any special validation on the third coordinate (height above ellipsoid).</li>
	 * </td></tr></table>
	 *
	 * @see AbstractBaseComponent#validate()
	 */
	protected void validate() throws InvalidDDMSException {
		super.validate();
		Util.requireQName(getXOMElement(), getNamespace(), Point.getName(getDDMSVersion()));
		Util.requireDDMSValue("srsAttributes", getSRSAttributes());
		Util.requireDDMSValue("srsName", getSRSAttributes().getSrsName());
		Util.requireDDMSValue(ID_NAME, getId());
		Util.requireValidNCName(getId());
		Util.requireDDMSValue("position", getPosition());
		Util.requireCompatibleVersion(this, getPosition());
		String srsName = getPosition().getSRSAttributes().getSrsName();
		if (!Util.isEmpty(srsName) && !srsName.equals(getSRSAttributes().getSrsName()))
			throw new InvalidDDMSException("The srsName of the position must match the srsName of the Point.");
		
		validateWarnings();
	}
	
	/**
	 * Validates any conditions that might result in a warning.
	 * 
	 * <table class="info"><tr class="infoHeader"><th>Rules</th></tr><tr><td class="infoBody">
	 * <li>Include any validation warnings from the SRS attributes and the child position.</li>
	 * </td></tr></table>
	 */
	protected void validateWarnings() {
		addWarnings(getPosition().getValidationWarnings(), false);
		addWarnings(getSRSAttributes().getValidationWarnings(), true);
	}
	
	/**
	 * @see AbstractBaseComponent#toHTML()
	 */
	public String toHTML() {
		return (toHTML(""));
	}
	
	/**
	 * @see AbstractBaseComponent#toText()
	 */
	public String toText() {
		return (toText(""));
	}

	/**
	 * Outputs to HTML with a prefix at the beginning of each meta tag.
	 * 
	 * @param prefix the prefix to add
	 * @return the HTML output
	 */
	public String toHTML(String prefix) {
		prefix = Util.getNonNullString(prefix);
		StringBuffer html = new StringBuffer();
		html.append(buildHTMLMeta(prefix + ID_NAME, getId(), true));
		html.append(buildHTMLMeta(prefix + "type", getName(), true));
		html.append(buildHTMLMeta(prefix + "srsName", getSRSAttributes().getSrsName(), true));
		if (getSRSAttributes().getSrsDimension() != null) {
			html.append(buildHTMLMeta(prefix + "srsDimension", String.valueOf(getSRSAttributes().getSrsDimension()),
				false));
		}
		html.append(buildHTMLMeta(prefix + "axisLabels", getSRSAttributes().getAxisLabelsAsXsList(), false));
		html.append(buildHTMLMeta(prefix + "uomLabels", getSRSAttributes().getUomLabelsAsXsList(), false));
		html.append(getPosition().toHTML(prefix));
		return (html.toString());
	}
	
	/**
	 * Outputs to Text with a prefix at the beginning of each line.
	 * 
	 * @param prefix the prefix to add
	 * @return the Text output
	 */
	public String toText(String prefix) {
		prefix = Util.getNonNullString(prefix);
		StringBuffer text = new StringBuffer();
		text.append(buildTextLine(prefix + ID_NAME, getId(), true));
		text.append(buildTextLine(prefix + "type", getName(), true));
		text.append(buildTextLine(prefix + "srsName", getSRSAttributes().getSrsName(), true));
		if (getSRSAttributes().getSrsDimension() != null) {
			text.append(buildTextLine(prefix + "srsDimension", String.valueOf(getSRSAttributes().getSrsDimension()),
				false));
		}
		text.append(buildTextLine(prefix + "axisLabels", getSRSAttributes().getAxisLabelsAsXsList(), false));
		text.append(buildTextLine(prefix + "uomLabels", getSRSAttributes().getUomLabelsAsXsList(), false));
		text.append(getPosition().toText(prefix));
		return (text.toString());
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object obj) {
		if (!super.equals(obj) || !(obj instanceof Point))
			return (false);
		Point test = (Point) obj;
		return (getSRSAttributes().equals(test.getSRSAttributes()) 
			&& getPosition().equals(test.getPosition())
			&& getId().equals(test.getId()));
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		int result = super.hashCode();
		result = 7 * result + getSRSAttributes().hashCode();
		result = 7 * result + getId().hashCode();
		result = 7 * result + getPosition().hashCode();
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
		return ("Point");
	}
	 
	/**
	 * Accessor for the SRS Attributes. Will always be non-null.
	 */
	public SRSAttributes getSRSAttributes() {
		return (_cachedSrsAttributes);
	}
	
	/**
	 * Accessor for the ID
	 */
	public String getId() {
		return (getAttributeValue(ID_NAME, getXOMElement().getNamespaceURI()));
	}
	
	/**
	 * Accessor for the coordinates of the position. May return null, but cannot happen after instantiation.
	 */
	public Position getPosition() {
		return (_cachedPosition);
	}
	
	/**
	 * Builder for this DDMS component.
	 * 
	 * @see IBuilder
	 * @author Brian Uri!
	 * @since 1.8.0
	 */
	public static class Builder implements IBuilder, Serializable {
		private static final long serialVersionUID = 4003805386998809149L;
		private SRSAttributes.Builder _srsAttributes;
		private Position.Builder _position;
		private String _id;
		
		/**
		 * Empty constructor
		 */
		public Builder() {}
		
		/**
		 * Constructor which starts from an existing component.
		 */
		public Builder(Point point) {
			setSrsAttributes(new SRSAttributes.Builder(point.getSRSAttributes()));
			setPosition(new Position.Builder(point.getPosition()));
			setId(point.getId());
		}
		
		/**
		 * @see IBuilder#commit()
		 */
		public Point commit() throws InvalidDDMSException {
			return (isEmpty() ? null : new Point(getPosition().commit(), getSrsAttributes().commit(), getId()));
		}
		
		/**
		 * @see IBuilder#isEmpty()
		 */
		public boolean isEmpty() {
			return (Util.isEmpty(getId()) && getPosition().isEmpty() && getSrsAttributes().isEmpty());
		}
		
		/**
		 * Builder accessor for the SRS Attributes
		 */
		public SRSAttributes.Builder getSrsAttributes() {
			if (_srsAttributes == null)
				_srsAttributes = new SRSAttributes.Builder();
			return _srsAttributes;
		}
		
		/**
		 * Builder accessor for the SRS Attributes
		 */
		public void setSrsAttributes(SRSAttributes.Builder srsAttributes) {
			_srsAttributes = srsAttributes;
		}

		/**
		 * Builder accessor for the position
		 */
		public Position.Builder getPosition() {
			if (_position == null)
				_position = new Position.Builder();
			return _position;
		}

		/**
		 * Builder accessor for the position
		 */
		public void setPosition(Position.Builder position) {
			_position = position;
		}

		/**
		 * Accessor for the ID
		 */
		public String getId() {
			return _id;
		}

		/**
		 * Accessor for the ID
		 */
		public void setId(String id) {
			_id = id;
		}			
	}	
} 