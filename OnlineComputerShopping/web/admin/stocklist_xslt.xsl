<?xml version="1.0" encoding="UTF-8" ?>
<!--
<product>
   <id>1</id>
   <name>this is name</name>
   <description>this is description</description>
</product>
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <xsl:output method="html"/>

  <xsl:template match="/">
  <table border="0" class="right_content">
    <tr>
        <td  class="content_head">Stocks</td>
    </tr>

    <xsl:template match="productlist/product/components">
    <tr><td align="center">
  <table border="0" width="100%">
    
   <xsl:for-each select="productlist/product">
       <tr bgcolor="#C3FFA4">
           <td align="left" class="stock_head">
           <div class="comp_div">
               <div class="comp_link"><xsl:value-of select="name"/></div>
               <div id="comp_desc_prod"><u>Product Detail</u><br/><xsl:value-of select="description"/></div>
               </div>
           </td>
       </tr>
       
           <tr><td colspan="2" class="stock_data">
               
            <xsl:for-each select="components/component">
                    <div class="comp_div">
                            <xsl:element name="a">
                                <xsl:attribute name="href"><xsl:text disable-output-escaping="yes"><![CDATA[#]]></xsl:text><xsl:value-of select="id"/></xsl:attribute>
                                <xsl:attribute name="class">comp_link</xsl:attribute>
                                <xsl:text><xsl:value-of select="name"/></xsl:text>
                            </xsl:element>
                        <div id="comp_desc">
                            <u>Component Detail</u><br/>
                            <strong>Rate :</strong> <xsl:value-of select="rate"/><br/>
                            <strong>Available qty :</strong> <xsl:value-of select="quantity"/><br/>
                            <strong>Detail :</strong> <xsl:value-of select="description"/>
                        </div>
                    </div>
                    </xsl:for-each>
           </td></tr>
       
       
       </xsl:for-each>
   </table>
   </td></tr>
  </xsl:template>

   </table>
  </xsl:template>

</xsl:stylesheet>