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

import buri.ddmsence.ddms.resource.Rights;
import junit.framework.TestCase;

/**
 * <p>Tests related to ValidationMessages</p>
 * 
 * @author Brian Uri!
 * @since 0.9.b
 */
public class ValidationMessageTest extends TestCase {

	public void testFactory() {
		ValidationMessage message = ValidationMessage.newWarning("Test");
		assertEquals(ValidationMessage.WARNING_TYPE, message.getType());
		assertEquals("Test", message.getText());
		
		message = ValidationMessage.newError("Test");
		assertEquals(ValidationMessage.ERROR_TYPE, message.getType());
		assertEquals("Test", message.getText());
	}
	
	public void testEquality() {
		ValidationMessage message1 = ValidationMessage.newWarning("Test");
		ValidationMessage message2 = ValidationMessage.newWarning("Test");
		assertEquals(message1, message1);
		assertEquals(message1, message2);
		assertEquals(message1.hashCode(), message2.hashCode());
		assertEquals(message1.toString(), message2.toString());
	}
	
	public void testInequalityWrongClass() throws InvalidDDMSException {
		ValidationMessage message = ValidationMessage.newWarning("Test");
		Rights wrongComponent = new Rights(true, true, true);
		assertFalse(message.equals(wrongComponent));
	}
}
