/* Copyright 2010 - 2014 by Brian Uri!
   
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

import buri.ddmsence.AbstractBaseComponent;

/**
 * Identifying interface for a TSPI shape element, which may be used to fill a boundingGeometry element.
 * 
 * @author Brian Uri!
 * @since 2.2.0
 */
public interface ITspiShape extends IDDMSComponent {
	
	/**
	 * I consider this to be an internal method, that unfortunately must be marked as public to allow cross-package
	 * access when generating output. Use toHTML() and toText() as the formal, public methods to generate output.
	 * 
	 * @see AbstractBaseComponent#getHTMLTextOutput(OutputFormat, String, String)
	 */
	public abstract String getHTMLTextOutput(OutputFormat format, String prefix, String suffix);
}
