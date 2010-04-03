<html>
<head>
	<title>DDMSence: Downloads</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="description" content="An open source Java library for the DoD Discovery Metadata Specification (DDMS)">
</head>
<body>
<%@ include file="../shared/header.jspf" %>

<h1>Downloads</h1>

<p>Before you begin, you will need a <a href="http://java.sun.com/javase/downloads/index.jsp" target="_new">JRE or JDK</a> which supports Java 1.5 or higher. Downloads come in two flavors:</p>

<p><b><code>ddmsence-bin-version.zip</code></b>: This download is intended for end users who wish to used DDMSence in their own projects. It contains the DDMSence JAR files,
the complete API documentation (which is also available <a href="/docs/">online</a>), source code for the sample applications,
and supporting 3rd-party JAR files. Sample applications can be run from the <a href="documentation.jsp#started">command line</a>.</p>

<p><b><code>ddmsence-src-version.zip</code></b>: This download is intended for developers who wish to see what DDMSence is doing under the hood. It contains source 
code for the main library, unit tests, and the sample applications, as well as supporting 3rd-party JAR files. This download contains a <code>build.xml</code> 
file which will allow you to compile source code, run unit tests, generate API documentation, or even generate the "bin"-flavored archive with <a href="http://ant.apache.org/">Apache Ant</a>. 
I have include the <code>.project</code> file if you wish to import the project into Eclipse.</p>

<p>Please see the "<a href="http://code.google.com/p/ddmsence/issues/list">Issue Tracker</a>" for a comprehensive list of fixes and enhancements
included in each release.</a></li>

<h3>Latest Release</h3>

<p>Version <b>1.0.0</b> (4/1/2010) is the first official stable release of DDMSence.</p>

<ul>
<li><b><a href="http://ddmsence.googlecode.com/files/ddmsence-bin-1.0.0.zip"><code>ddmsence-bin-1.0.0.zip</code></a></b> (1.7 MB, compiled with JDK1.5.0_22)</li>
<li><b><a href="http://ddmsence.googlecode.com/files/ddmsence-src-1.0.0.zip"><code>ddmsence-src-1.0.0.zip</code></a></b> (2.3 MB)</li>
</ul>

<h3>Versioning</h3>

<p>DDMSence will adopt a "major.minor.patch" versioning system for as long as it makes sense to do so:</p>
<ul>
	<li>A change in major version will include major architecture changes or break backwards compatibility.</li>
	<li>A change in minor version will include new features or minor bug fixes. Some minor releases may break backwards compatibility.<b>*</b></li>
	<li>A change in patch version will be limited to minor bug fixes.</li>
	<li>If the patch version is a letter, such as "<b>b</b>", the release may be unstable. Major/minor numbers will increment after an unstable release (so the beta/pre-release version of 1.2 is probably 1.1.b, and NOT 1.2.b).</li>
</ul>

<p><b>*</b>In initial 1.x releases, I will be more aggressive about creating a more robust baseline at the expense of backwards compatibility, at least
until I am aware that there are serious users of the library. However, all changes which are not backwards compatible will be listed on this page, even
if they do not trigger a change in major version.</p> 

<h3>Older Releases</h3>

<p>Version <b>0.9.d</b> (3/29/2010) includes further improvements to the validation system, controlled vocabulary validation for ICISM security
attributes, and minor improvements to the sample applications. This version is believed to be stable, and is intended to be the test candidate
for v1.0.0.</p>

<ul>
<li><b><a href="http://ddmsence.googlecode.com/files/ddmsence-bin-0.9.d.zip"><code>ddmsence-bin-0.9.d.zip</code></a></b> (1.9 MB, compiled with JDK1.6.0_16)</li>
<li><b><a href="http://ddmsence.googlecode.com/files/ddmsence-src-0.9.d.zip"><code>ddmsence-src-0.9.d.zip</code></a></b> (2.3 MB)</li>
</ul>

<p>Version <b>0.9.c</b> (3/25/2010) improves the validation system to support warnings in addition to errors, as outlined in <a href="http://code.google.com/p/ddmsence/issues/detail?id=7">Issue #7</a>.
One method of the <code>IDDMSComponent</code> interface was removed, which breaks compatibility with 0.9.b. This version is believed to be stable, and is intended to be a limited audience release
for early feedback.</p>

<ul>
<li><b><a href="http://ddmsence.googlecode.com/files/ddmsence-bin-0.9.c.zip"><code>ddmsence-bin-0.9.c.zip</code></a></b> (1.8 MB, compiled with JDK1.6.0_16)</li>
<li><b><a href="http://ddmsence.googlecode.com/files/ddmsence-src-0.9.c.zip"><code>ddmsence-src-0.9.c.zip</code></a></b> (2.5 MB)</li>
</ul>

<p>Version <b>0.9.b</b> (3/24/2010) is the initial beta release. It provides a full implementation of the core layers of DDMS 3.0, in addition to a few
sample applications. This version is believed to be stable, and is intended to be a limited audience release for early feedback.</p>

<ul>
<li><b><a href="http://ddmsence.googlecode.com/files/ddmsence-bin-0.9.b.zip"><code>ddmsence-bin-0.9.b.zip</code></a></b> (1.8 MB, compiled with JDK1.6.0_16)</li>
<li><b><a href="http://ddmsence.googlecode.com/files/ddmsence-src-0.9.b.zip"><code>ddmsence-src-0.9.b.zip</code></a></b> (2.5 MB)</li>
</ul>

<div class="clear"></div>
<%@ include file="../shared/footer.jspf" %>
</body>
</html>