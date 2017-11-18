<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <xsl:output method="html"/>
  
  <xsl:template match="Components">
  <table border="0" class="right_content">
    <tr><td colspan="5" class="content_head">Components</td></tr>
    <tr bgcolor="silver" cellspacing="0">
    
    <td>Name</td>
    <td>Rate</td>
    <td>Quantity</td>
    <td>Description</td>
    <td></td>
    </tr>
        <xsl:apply-templates/>
   </table>
  </xsl:template>


  <xsl:template match="component">    
    <tr bgcolor="#C3FFA4" >
        
        <td width="20%">
            <xsl:value-of select="name"/>
        </td>
        <td width="10%">
            <xsl:value-of select="rate"/>
        </td>
        <td width="5%">
            <xsl:value-of select="quantity"/>
        </td>
        <td>
            <xsl:value-of select="description"/>
        </td>
       
        <td width="20%">
            <form method="get" action="Admin">
            <input type="text" name="quantity" size="2" value="0"/>
            <xsl:element name="input">
                 <xsl:attribute name="name">id</xsl:attribute>
                 <xsl:attribute name="type">hidden</xsl:attribute>
                 <xsl:attribute name="value"><xsl:value-of select="id"/></xsl:attribute>
            </xsl:element>
            <xsl:element name="input">
                 <xsl:attribute name="name">action</xsl:attribute>
                 <xsl:attribute name="type">hidden</xsl:attribute>
                 <xsl:attribute name="value">component_update</xsl:attribute>
            </xsl:element>
            <xsl:element name="input">
                 <xsl:attribute name="type">submit</xsl:attribute>
                 <xsl:attribute name="value">Add</xsl:attribute>
            </xsl:element>
            </form>
        </td>
    </tr>
   
  </xsl:template>

</xsl:stylesheet>