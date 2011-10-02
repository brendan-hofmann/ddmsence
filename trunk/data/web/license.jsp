<html>
<head>
	<title>DDMSence: License</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="description" content="An open source Java library for the DoD Discovery Metadata Specification (DDMS)">
</head>
<body>
<%@ include file="../shared/header.jspf" %>

<h1>License</h1>

<p>DDMSence is Copyright &copy; 2010 - 2011 by Brian Uri!. The DDMSence source code is published under the GNU Lesser General Public License (LGPL). The
complete text of this license can be found below.</p>

<p>DDMSence makes use of several third-party libraries, whose licenses are linked below. Required libraries are already bundled with DDMSence, so you
should not need to download them separately.</p>

<ul>
	<li><a href="http://www.schematron.com/">Rick Jelliffe's ISO Schematron implementation 4-21-2010</a> 
		(<a href="http://www.opensource.org/licenses/zlib-license.php">zlib/libpng License</a>): Core library needed for Schematron validation.</li>
	<li><a href="http://saxon.sourceforge.net/">Saxon HE 9.3.0.5</a> 
		(<a href="http://www.opensource.org/licenses/MPL-1.1">Mozilla Public License v1.1</a>): Core library needed for Schematron validation.</li>
	<li><a href="http://xerces.apache.org/xerces2-j/">Xerces 2.11.0</a>
		(<a href="http://www.apache.org/licenses/LICENSE-2.0">Apache License v2.0</a>): Core library needed for XML parsing.</li>
	<li><a href="http://xml.apache.org/commons/">XML-Commons 1.4.01</a>
		(<a href="http://www.apache.org/licenses/LICENSE-2.0">Apache License v2.0</a>): Core library needed for XML parsing.</li>
	<li><a href="http://www.xom.nu/">XOM 1.2.7</a>
		(<a href="http://xom.nu/license.xhtml">GNU Lesser General Public License v2.1</a>): Core library needed for XML parsing.</li>
	<br />	
	<li><a href="http://www.servlets.com/cos/">com.oreilly.servlets 26Dec2008</a>
		(<a href="http://www.servlets.com/cos/license.html">Custom License</a>): Used on the DDMSence website (not bundled).</li>
	<li><a href="http://jquery.com/">jQuery 1.3.2</a>
		(<a href="http://en.wikipedia.org/wiki/MIT_License">MIT License</a>): Used on the DDMSence website.</li>
	<li><a href="http://junit.sourceforge.net/">JUnit 4.8.1</a> 
		(<a href="http://junit.sourceforge.net/cpl-v10.html">Common Public License v1.0</a>): Needed to run unit tests against DDMSence.</li>
	<li><a href="http://www.springsource.org/">Spring MVC 2.0</a>
		(<a href="http://www.apache.org/licenses/LICENSE-2.0">Apache License v2.0</a>): Used on the DDMSence website (not bundled).</li>
	<li><a href="http://alexgorbatchev.com/SyntaxHighlighter/">SyntaxHighlighter 3.0.83</a>
		(<a href="http://en.wikipedia.org/wiki/MIT_License">MIT License</a>): Used on the DDMSence website.</li>
	
</ul>

<h3>GNU LESSER GENERAL PUBLIC LICENSE</h3>
<p>Version 3, 29 June 2007</p>

<p>Copyright (C) 2007 Free Software Foundation, Inc. &lt;<a href="http://fsf.org/" target="_new">http://fsf.org/</a>&gt;</p>

<p>Everyone is permitted to copy and distribute verbatim copies of this license document, but changing it is not allowed.</p>

<p>This version of the GNU Lesser General Public License incorporates the terms and conditions of version 3 of the GNU General Public
License, supplemented by the additional permissions listed below.</p>

<h4><a name="section0"></a>0. Additional Definitions.</h4>

<p>As used herein, &ldquo;this License&rdquo; refers to version 3 of the GNU Lesser General Public License, and the &ldquo;GNU GPL&rdquo; refers to version 3 of the GNU
General Public License.</p>

<p>&ldquo;The Library&rdquo; refers to a covered work governed by this License, other than an Application or a Combined Work as defined below.</p>

<p>An &ldquo;Application&rdquo; is any work that makes use of an interface provided by the Library, but which is not otherwise based on the Library.
Defining a subclass of a class defined by the Library is deemed a mode of using an interface provided by the Library.</p>

<p>A &ldquo;Combined Work&rdquo; is a work produced by combining or linking an Application with the Library.  The particular version of the Library
with which the Combined Work was made is also called the &ldquo;Linked Version&rdquo;.</p>

<p>The &ldquo;Minimal Corresponding Source&rdquo; for a Combined Work means the Corresponding Source for the Combined Work, excluding any source code
for portions of the Combined Work that, considered in isolation, are based on the Application, and not on the Linked Version.</p>

<p>The &ldquo;Corresponding Application Code&rdquo; for a Combined Work means the object code and/or source code for the Application, including any data
and utility programs needed for reproducing the Combined Work from the Application, but excluding the System Libraries of the Combined Work.</p>

<h4><a name="section1"></a>1. Exception to Section 3 of the GNU GPL.</h4>

<p>You may convey a covered work under sections 3 and 4 of this License without being bound by section 3 of the GNU GPL.</p>

<h4><a name="section2"></a>2. Conveying Modified Versions.</h4>

<p>If you modify a copy of the Library, and, in your modifications, a facility refers to a function or data to be supplied by an Application
that uses the facility (other than as an argument passed when the facility is invoked), then you may convey a copy of the modified version:</p>

<ul>
	<li>a) under this License, provided that you make a good faith effort to ensure that, in the event an Application does not supply the
	function or data, the facility still operates, and performs whatever part of its purpose remains meaningful, or</li>
	<li>b) under the GNU GPL, with none of the additional permissions of this License applicable to that copy.</li>
</ul>

<h4><a name="section3"></a>3. Object Code Incorporating Material from Library Header Files.</h4>

<p>The object code form of an Application may incorporate material from a header file that is part of the Library.  You may convey such object
code under terms of your choice, provided that, if the incorporated material is not limited to numerical parameters, data structure
layouts and accessors, or small macros, inline functions and templates (ten or fewer lines in length), you do both of the following:</p>

<ul>
	<li>a) Give prominent notice with each copy of the object code that the Library is used in it and that the Library and its use are
   	covered by this License.</li>
	<li>b) Accompany the object code with a copy of the GNU GPL and this license document.</li>
</ul>

<h4><a name="section4"></a>4. Combined Works.</h4>

<p>You may convey a Combined Work under terms of your choice that,
taken together, effectively do not restrict modification of the
portions of the Library contained in the Combined Work and reverse
engineering for debugging such modifications, if you also do each of
the following:</p>

<ul>
	<li>a) Give prominent notice with each copy of the Combined Work that the Library is used in it and that the Library and its use are
	covered by this License.</li>
	<li>b) Accompany the Combined Work with a copy of the GNU GPL and this license document.</li>
	<li>c) For a Combined Work that displays copyright notices during execution, include the copyright notice for the Library among
   	these notices, as well as a reference directing the user to the copies of the GNU GPL and this license document.</li>
	<li>d) Do one of the following:

<ul>
	<li>0) Convey the Minimal Corresponding Source under the terms of this License, and the Corresponding Application Code in a form
	suitable for, and under terms that permit, the user to recombine or relink the Application with a modified version of the Linked 
	Version to produce a modified Combined Work, in the manner specified by section 6 of the GNU GPL for conveying Corresponding Source.</li>
	<li>1) Use a suitable shared library mechanism for linking with the Library.  A suitable mechanism is one that (a) uses at run time
	a copy of the Library already present on the user's computer system, and (b) will operate properly with a modified version
	of the Library that is interface-compatible with the Linked Version.</li>

</ul></li>

<li>e) Provide Installation Information, but only if you would otherwise be required to provide such information under section 6 of the
   GNU GPL, and only to the extent that such information is necessary to install and execute a modified version of the
   Combined Work produced by recombining or relinking the Application with a modified version of the Linked Version. (If
   you use option 4d0, the Installation Information must accompany the Minimal Corresponding Source and Corresponding Application
   Code. If you use option 4d1, you must provide the Installation Information in the manner specified by section 6 of the GNU GPL
   for conveying Corresponding Source.)</li>
</ul>

<h4><a name="section5"></a>5. Combined Libraries.</h4>

<p>You may place library facilities that are a work based on the Library side by side in a single library together with other library
facilities that are not Applications and are not covered by this License, and convey such a combined library under terms of your
choice, if you do both of the following:</p>

<ul>
<li>a) Accompany the combined library with a copy of the same work based on the Library, uncombined with any other library facilities,
   conveyed under the terms of this License.</li>

<li>b) Give prominent notice with the combined library that part of it is a work based on the Library, and explaining where to find the
   accompanying uncombined form of the same work.</li>
</ul>

<h4><a name="section6"></a>6. Revised Versions of the GNU Lesser General Public License.</h4>

<p>The Free Software Foundation may publish revised and/or new versions of the GNU Lesser General Public License from time to time. Such new
versions will be similar in spirit to the present version, but may differ in detail to address new problems or concerns.</p>

<p>Each version is given a distinguishing version number. If the Library as you received it specifies that a certain numbered version
of the GNU Lesser General Public License &ldquo;or any later version&rdquo; applies to it, you have the option of following the terms and
conditions either of that published version or of any later version published by the Free Software Foundation. If the Library as you
received it does not specify a version number of the GNU Lesser General Public License, you may choose any version of the GNU Lesser
General Public License ever published by the Free Software Foundation.</p>

<p>If the Library as you received it specifies that a proxy can decide whether future versions of the GNU Lesser General Public License shall
apply, that proxy's public statement of acceptance of any version is permanent authorization for you to choose that version for the Library.</p>

<div class="clear"></div>
<%@ include file="../shared/footer.jspf" %>
</body>
</html>