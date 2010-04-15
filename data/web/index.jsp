<html>
<head>
	<title>DDMSence: An open source Java library for DDMS</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="description" content="An open source Java library for the DoD Discovery Metadata Specification (DDMS)">
	<meta name="keywords" content="DDMSence,DDMS,Metadata,Discovery,Java,DoD,XML" />
		
	<!-- DDMS Resource Record --> 
	<meta name="security.resourceElement" content="true" />
	<meta name="security.createDate" content="2010-03-21" />
	<meta name="security.DESVersion" content="2" />
	<meta name="security.classification" content="U" />
	<meta name="security.ownerProducer" content="USA" />
	<meta name="identifier.qualifier" content="http://purl.org/dc/terms/URI" />
	<meta name="identifier.value" content="urn:buri:ddmsence:website" />
	<meta name="title" content="DDMSence Home Page" />
	<meta name="title.classification" content="U" />
	<meta name="title.ownerProducer" content="USA" />
	<meta name="subtitle" content="Multiple Versions" />
	<meta name="subtitle.classification" content="U" />
	<meta name="subtitle.ownerProducer" content="USA" />
	<meta name="description" content="An open source Java library for DDMS." />
	<meta name="description.classification" content="U" />
	<meta name="description.ownerProducer" content="USA" />
	<meta name="language.qualifier" content="http://purl.org/dc/elements/1.1/language" />
	<meta name="language" content="en" />
	<meta name="date.created" content="2010-03-13" />
	<meta name="date.posted" content="2003-03-13" />
	<meta name="rights.privacy" content="false" />
	<meta name="rights.intellectualproperty" content="true" />
	<meta name="rights.copy" content="true" />
	<meta name="type.qualifier" content="DCMITYPE" />
	<meta name="type.value" content="http://purl.org/dc/dcmitype/Text" />
	<meta name="creator.entityType" content="Person" />
	<meta name="creator.name" content="Brian" />
	<meta name="creator.name" content="BU" />
	<meta name="creator.surname" content="Uri" />
	<meta name="publisher.entityType" content="Person" />
	<meta name="publisher.name" content="Brian" />
	<meta name="publisher.name" content="BU" />
	<meta name="publisher.surname" content="Uri" />
	<meta name="pointOfContact.entityType" content="Person" />
	<meta name="pointOfContact.name" content="Brian" />
	<meta name="pointOfContact.name" content="BU" />
	<meta name="pointOfContact.surname" content="Uri" />
	<meta name="format.media" content="text/html" />
	<meta name="format.medium" content="digital" />
	<meta name="subject.keyword" content="DDMSence" />
	<meta name="subject.keyword" content="DDMS" />
	<meta name="subject.keyword" content="Metadata" />
	<meta name="subject.keyword" content="Discovery" />
	<meta name="subject.keyword" content="Java" />
	<meta name="subject.keyword" content="DoD" />
	<meta name="subject.keyword" content="XML" />
	<meta name="virtual.address" content="http://ddmsence.urizone.net/" />
	<meta name="virtual.networkProtocol" content="URL" />
	<meta name="temporal.TimePeriod" content="Unknown" />
	<meta name="temporal.DateStart" content="2010-03-13" />
	<meta name="temporal.DateEnd" content="Unknown" />
	<meta name="security.excludeFromRollup" content="true" />
	<meta name="security.classification" content="U" />
	<meta name="security.ownerProducer" content="USA" />
	<meta name="extensible.layer" content="false" />
	<meta name="ddms.generator" content="DDMSence 1.1.0" />
	
</head>
<body>
<%@ include file="../shared/header.jspf" %>
<div class="newsFeed">
	<b><u>Recent News</u></b><br /><br />
	<div class="newsUpdate"><u>04/08/2010</u>: <a href="downloads.jsp">v1.1.0</a> released.</div>
	<div class="newsUpdate"><u>04/01/2010</u>: v1.0.0 released. DDMSence information posted on the
		<a href="https://metadata.dod.mil/mdr/topic.htm?topicId=4662" target="_new">DoD Metadata Registry Community Forum</a> and on 
		<a href="http://freshmeat.net/projects/ddmsence" target="_new">freshmeat</a>.</div>
	<div class="newsUpdate"><u>03/29/2010</u>: v0.9.d released.</div>
	<div class="newsUpdate"><u>03/25/2010</u>: v0.9.c released.</div>
	<div class="newsUpdate"><u>03/24/2010</u>: v0.9.b released.</div><br />
	<div class="feed">New Release Feed: <a href="http://code.google.com/feeds/p/ddmsence/downloads/basic"><img border="0" src="./images/atom.png" width="14" height="14" title="Atom Feed" /></a></div>	
	<div class="feed">SVN Code Feed: <a href="http://code.google.com/feeds/p/ddmsence/svnchanges/basic"><img border="0" src="./images/atom.png" width="14" height="14" title="Atom Feed" /></a></div>
	<div class="feed">Issue Tracker Feed: <a href="http://code.google.com/feeds/p/ddmsence/issueupdates/basic"><img border="0" src="./images/atom.png" width="14" height="14" title="Atom Feed" /></a></div>
	
</div>

<p>
DDMSence (pronounced "<i>dee-dee-em-Essence</i>") is an open source Java API which supports the 
<a href="http://metadata.dod.mil/mdr/irs/DDMS/" target="_new">DoD Discovery Metadata Specification (DDMS)</a>. 
Using <a href="http://xom.nu/" target="_new">XOM</a> and the <a href="http://xerces.apache.org/" target="_new">Xerces</a> Java parser, 
DDMSence can transform XML DDMS Resource records into a Java object model, allowing them to be manipulated or traversed within the context of a Java environment. 
DDMSence also performs logical validation of rules which are not coded into the DDMS schema (such as the 
the validation of longitude/latitude values).</p>

<ul>
	<li>Provides a complete implementation of all five Layers in the DDMS specification, including the Extensible Layer.</li>
	<li>Supports the creation and validation of both DDMS 2.0 and DDMS 3.0 Pre-Release components.</li>
	<li>Java object model can be created from existing XML files, or built up from scratch with basic Java data types.</li>
	<li>Java object model can automatically transform into valid, well-formed XML, HTML, and Text records, according to the DDMS specification.</li>
	<li>Adds an additional layer of validation to all components, including the Controlled Vocabulary Enumerations for ICISM security attributes, as defined by the Intelligence Community.</li>
	<li>Three sample applications and accompanying <a href="documentation.jsp#started">tutorials</a> provide an introduction to the library,
		and <a href="documentation.jsp#tips">Power Tips</a> are available for more experienced users.</li>
</ul>

<p>Components created with this library maintain an underlying XOM element structure, which provides latent avenues for future 
improvement, based on XOM's support of XPath, XInclude, and XSLT. However, you do not need to have any experience with XOM to get started
with this library.</p>

<p>DDMSence comes with a complete set of JUnit tests, and code coverage was aided by <a href="http://www.eclemma.org/" target="_new">EclEmma</a>.
It is being released under the GNU Lesser General Public License (<a href="license.jsp">LGPL</a>).</p>

<h1>Current Release: v1.1.0</h1>

<p><a href="downloads.jsp">Version 1.1.0</a> is available for download. This stable version is the second official public 
release.</p>

<h1>On the Horizon</h1>

<p>Now that v1.1.0 is complete, I will take a couple weeks to focus on improving the unit tests and documentation.
I would like this release to evoke some feedback before I decide what enhancements to pursue in the next release. Please follow the 
"<a href="http://code.google.com/p/ddmsence/issues/list">Issue Tracking</a>" link in the menu above to see a complete list of enhancements under 
consideration for future releases, or to add your own suggestions.</p>
			
<h1>About the Author</h1>

<p><img src="./images/BU.jpg" width="73" height="100" title="BU" align="left" />
<b>Brian Uri!</b> is a software engineer at <a href="http://www.fgm.com/" target="_new">FGM, Inc.</a> in Reston, Virginia. He has been a member
of the <a href="http://metadata.dod.mil/" target="_new">DoD Metadata Registry</a> team since 2004, and is currently a
technical lead on that project. DDMSence grew out of the author's desire to gain more practical XML Schema experience, to write something useful
which had never been done before, and an unusually large amount of free time.</p>

<div class="clear"></div>
<%@ include file="../shared/footer.jspf" %>
</body>
</html>