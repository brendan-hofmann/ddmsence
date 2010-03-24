<html>
<head>
	<title>DDMSence: Tutorial #1 (Essentials)</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="description" content="An open source Java library for the DoD Discovery Metadata Specification (DDMS)">
</head>
<body>
<%@ include file="../shared/header.jspf" %>

<h1>Tutorial #1: <u>Essentials</u></h1>

<p><u>Essentials</u> is a simple reader application which loads an XML file containing a DDMS Resource and displays it in four different formats: the
original XML, HTML, Text, and Java code (which you can reuse to build your own components from scratch). The source code for this application provides
an example of how to create DDMS components from an XML file.</p>

<h3>Getting Started</h3>

<p><u>Essentials</u> can be run from the command line with the class, <b>buri.ddmsence.samples.Essentials</b>. The application accepts a single 
optional parameter: the name of a file to load upon startup.</p>

<p>Please see "<a href="documentation.jsp#started">Getting Started</a>" section for classpath details and command line syntax.</p> 

<h3>Walkthrough</h3>

<p>When the application first opens, go to the <i>File</i> menu and choose <i>Open...</i>. You will be able to select an XML file containing
a ddms:Resource. The four sample files that are bundled with DDMS v3.0 can be found in the <b>data/sample/</b> directory. Let's start by
selecting the sample file, <b>DDMS-v3_0_Identifier_Postal_Address_Example.xml</b> and clicking on the <i>Open</i> button.</p>

<img src="../images/essentials-02.png" width="300" height="206" title="Essentials File Chooser" />
<p class="figure">Figure 1. Selecting an existing DDMS Record from an XML file</p>

<p>The application will convert the XML file into a Java object model and then display the results in four separate panes.</p>

<img src="../images/essentials-01.png" width="800" height="531" title="Essentials Screenshot" />
<p class="figure">Figure 1. The four output formats</p>

<p>The top pane contains the result of calling <b>toXML()</b> on the Resource object. It should be identical to the data from the file.</p>

<p>The middle-left pane contains the result of calling <b>toHTML()</b> on the Resource object. The naming conventions here are dictated by the DDMS Specification,
and in the rare cases where no examples could be found on the DDMS website, I followed DDMS conventions to come up with logical names.</p>

<p>The middle-right pane contains the result of calling <b>toText()</b> on the Resource object. Again, the naming conventions follow the DDMS Specification, or make
logical assumptions where the Specification was unclear.</p>

<p>The bottom pane contains Java code which you can paste into your own program to rebuild this Resource from scratch. The Resource
created by this code and the Resource created from the XML file will be logically equal according to <b>Object.equals()</b>.</p>

<p>Now, let's take a look at the source code in <b>/src/samples/buri/ddmsence/samples/Essentials.java</b> to see how this was accomplished. The important lines are found in the
<b>loadFile()</b> method:</p>

<div class="example"><pre>// The DDMS reader builds a Resource object from the XML in the file.
_resource = getReader().getDDMSResource(file);
			
// The four output formats
String xmlFormat = getResource().toXML();
String htmlFormat = getResource().toHTML();
String textFormat = getResource().toText();
String javaFormat = JavaConvertor.toJavaCode(getResource());</pre></div>
<p class="figure">Figure 3. The main Essentials code</p>

<p>The remaining code in this method merely renders the data on the screen.</p>

<p>As you can see from the code, building an object model from an XML file only requires a single-line call to <b>DDMSReader.getDDMSResource()</b>. The conversion of
the Resource into XML, HTML, and Text is built-in to the Object. (I created the sample JavaConvertor to make learning the syntax a little easier, 
but do not consider it to be part of the main DDMSence library).</p>

<p>Now that you have seen a valid Resource, let's try opening an invalid one. Return to the <i>File</i> menu and select the sample file, <b>InvalidResource.xml</b> from the
samples directory. This XML file is invalid, because it tries to use an incorrect security classification (SuperSecret).</p>

<p>Opening this file should display the following error message:</p>

<div class="example">Could not create the DDMS Resource: nu.xom.ValidityException: cvc-enumeration-valid: 
Value 'SuperSecret' is not facet-valid with respect to enumeration '[U, C, S, TS, R, CTS, CTS-B, CTS-BALK, NU, 
NR, NC, NS, CTSA, NSAT, NCA]'. It must be a value from the enumeration. at line 18, column 110 in file:///DDMSence/data/sample/InvalidResource.xml
</div>
<p class="figure">Figure 4. A sample error message</p>

<p>This error comes from the underlying XML-parsing libraries which are attempting to validate the XML file against the DDMS schema
before building the object model. Objects are always validated upon instantiation, so it is impossible to have fully-formed, but invalid DDMS components
at any given time.</p>

<p>Validation from an XML file proceeds in the following order:</p>

<ul>
	<li>The XML file is initially validated by the underlying XML parsers to confirm that it adheres to the DDMS schema.</li>
	<li>As the objects are built in Java, the schema rules are revalidated in Java. This is not essential for file-based resources, but
	becomes more important when we are building from scratch.</li>
	<li>Next, any rules mandated in the DDMS Specification but not implemented in the schema are validated (such as constraints on latitude/longitude values).</li>
	<li>Validating any DDMS component will implicitly validate any nested components, so when the top-level Resource is finally created, this will trigger a 
	revalidation of all the subcomponents. This is probably overkill, but I view it as harmless right now.</li>
</ul>

<h3>Conclusion</h3>

<p>In this tutorial, you have seen how DDMS Resources can be built from an existing XML file and transformed into various outputs. You
have also had a small taste of the validation that occurs when a Resource is built.</p>

<p>The next tutorial, covering the <u>Escort</u> application, will show how a DDMS Resource can be constructed from scratch, building on the Java output you saw in the bottom pane of the <u>Essentials</u> application.</p>

<div class="example"><pre>&lt;ddms:identifier
   ddms:qualifier="http://metadata.dod.mil/mdr/ns/MDR/0.1/MDR.owl#URI"
   ddms:value="http://www.whitehouse.gov/news/releases/2005/06/20050621.html" /&gt;</pre></div>
<p class="figure">Figure 5. A DDMS Identifier element in XML</p>
   
<div class="example"><pre>Identifier identifier1 = new Identifier("http://metadata.dod.mil/mdr/ns/MDR/0.1/MDR.owl#URI",
   "http://www.whitehouse.gov/news/releases/2005/06/20050621.html");
</pre></div>   
<p class="figure">Figure 6. Building a DDMS Identifier from scratch</p>   

<p>
	Tutorial #1: Essentials <span class="notify">(you are here)</span><br />
	<a href="tutorials-02.jsp">Tutorial #2: Escort</a><br />
	<a href="tutorials-03.jsp">Tutorial #3: Escape</a><br />
	<a href="documentation.jsp#samples">Back to Samples Documentation</a>
</p>

<div class="clear"></div>
<%@ include file="../shared/footer.jspf" %>
</body>
</html>