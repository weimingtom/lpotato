<?xml version="1.0"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="employees">
    <html>
      <head>
        <title>Phone List</title>
      </head>
      <body bgcolor="white">
        <table border="1" width="100%">
          <tr>
            <th>ID</th>
            <th>Employee Name</th>
            <th>Phone Number</th>
          </tr>
          <xsl:for-each select="employee">
            <tr>
              <td>
                <xsl:value-of select="@id"/>
              </td>
              <td>
                <xsl:value-of select="last-name"/>, 
                <xsl:value-of select="first-name"/>
              </td>
              <td>
                <xsl:value-of select="telephone"/>
              </td>
            </tr>
          </xsl:for-each>
        </table>
      </body>
    </html>
  </xsl:template>

</xsl:stylesheet>
