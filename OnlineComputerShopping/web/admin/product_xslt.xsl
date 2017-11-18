<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <xsl:output method="html"/>

  <xsl:template match="/">
  <table border="1" class="right_content">
    <tr>
        <td  class="content_head">Product Entry Form</td>
    </tr>

    
    <tr><td>
  <table border="1" class="products">
    
       <tr bgcolor="#C3FFA4"><td align="left">Product Name : <xsl:value-of select="name"/></td></tr>

       <tr><td>Assembling Components</td></tr>
       
       <tr><td><!--[CDATA[&nbsp;]]--></td></tr>
       
   </table>
   </td></tr>
 

   </table>
  </xsl:template>



  

</xsl:stylesheet>

<!--
<tr>
        <td>
            <table class="components">
                <tr>
                    <td>Components</td>
                    <td>Available Quantity</td>
                    <td>Required Quantity</td>
                </tr>
                <tr>
                    <td>this</td>
                    <td>this</td>
                    <td>this</td>
                </tr>
            </table>
        </td>

    </tr>
-->