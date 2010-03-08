December 13, 2003

Readme and License for the JavaServer Pages, 3rd Edition example package
------------------------------------------------------------------------

This package contains all JSP application examples and the source
code for all Java classes described in the book JavaServer Pages, 3rd
Edition (O'Reilly) by Hans Bergsten. Please see the Release Notes in
the same directory as this file for the latest news about the current
version.

The examples are located in the "ora" directory in a format supported 
by most Servlet 2.2 (or later) compliant containers (the Web Application 
Archive structure as an open file system structure). For the Tomcat 5
container, all you have to do to install it is to copy the directory under 
Tomcat's "webapps" directory and restart the server.

The "ora/WEB-INF/web.xml" file contains a number of different configuration
settings for the database examples, see the book for details. The default 
settings are for a MySQL database named "test", using the MM JDBC driver 
available at http://mmmysql.sourceforge.net/. You need to modify these 
settings to fit your JDBC driver and database and create the database tables 
described in the book. The "ora/WEB-INF" directory contains a file named 
"examples.sql" that contains the SQL statements for creating the tables used 
in the book. They have beem tested with MySQL but you may need to adjust 
them to the SQL syntax supported by your database.

The "src" directory contains all source code for the Java classes (beans, 
custom action tag handlers, servlets, etc.) used in the examples.
It also includes an Ant 1.5 build.xml file for building all example source 
code and package all tag library classes and the TLD in one JAR file, all 
servlet and generic database classes in another JAR, and the few servlet 
intro classes and the JspSourceServlet classes in a "classes" directory. 
Ant is a very powerful build tool that I really recommend. You can download 
and read more about it at http://jakarta.apache.org/ant/.

You can use the examples and the source code for the examples any way you 
want, but please include a reference to where it comes from if you use it in
your own products or services. Also note that this software is provided by 
me "as is", with no expressed or implied warranties. In no event shall I,
the author (Hans Bergsten), be liable for any direct or indirect damages 
arising in any way out of the use of this software.

In addition to the code developed by me, this package also includes binary 
versions of software in the ora/WEB-INF/lib directory developed by others as 
follows:
* The JSTL Reference Implementation, covered by the license in
  doc/asf-taglibs-license.txt. It's packaged in these files: dom.jar,
  jaxp-api.jar, jdbc2_0-stdext.jar, jstl.jar,sax.jar, standard.jar, 
  xalan.jar, xercesImpl.jar

* JDOM, covered by the license in doc/jdom-license.jar. It's
  packaged in the jdom.jar file.

* Struts, covered by the license in doc/asf-struts-license.txt. It's
  packaged in the struts.jar file.

Please read the licenses for this software before you use it outside the
book examples.

I appreciate your feedback on the book and the examples. Please
let me know what you think at hans@thejspbook.com. If you have questions,
please check out the FAQ and other resources on http://thejspbook.com
before mailing me.

Enjoy,
Hans Bergsten
