<?xml version="1.0"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output 
      doctype-public="-//WAPFORUM//DTD WML 1.1//EN" 
      doctype-system="http://www.wapforum.org/DTD/wml_1.1.xml"/>

  <xsl:template match="employees">
    <wml>
      <card id="list" newcontext="true">
      <p>Phone List</p>
      <p>
        <xsl:apply-templates select="." mode="list"/>
      </p>
      </card>
      <xsl:apply-templates select="." mode="detail"/>
    </wml>
  </xsl:template>

  <xsl:template match="employees" mode="list">
      <xsl:for-each select="employee">
        <anchor>
          <xsl:value-of select="last-name"/>, <xsl:value-of select="first-name"/>
          <go><xsl:attribute name="href">#<xsl:value-of select="last-name"/>_<xsl:value-of select="first-name"/>
          </xsl:attribute></go>
        </anchor>
        <br/>
      </xsl:for-each>
  </xsl:template>

  <xsl:template match="employees" mode="detail">
      <xsl:for-each select="employee">
        <card>
          <xsl:attribute name="id">
            <xsl:value-of select="last-name"/>_<xsl:value-of select="first-name"/>
          </xsl:attribute>
          <p>
            <xsl:value-of select="last-name"/>, <xsl:value-of select="first-name"/>
          </p>
          <p>
            Phone: <xsl:value-of select="telephone"/>
            <do type="prev" label="Back">
              <prev/>
            </do>
          </p>
        </card>
      </xsl:for-each>
  </xsl:template>

</xsl:stylesheet>
