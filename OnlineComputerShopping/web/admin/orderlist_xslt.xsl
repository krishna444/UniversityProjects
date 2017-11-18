<?xml version="1.0" encoding="UTF-8" ?>
<!-- Example:
<order>
  <OrderId>1</OrderId>
  <Username>suman</Username>
  <Product>Javaprogramming</Product>
  <OrderDate>23</OrderDate>
  <OrderStatus>234</OrderStatus>
</order>
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <xsl:output method="html"/>

  <xsl:template match="Orders">
  <table class="right_content">
    <tr><td colspan="5" class="content_head">Orders</td></tr>
    <tr bgcolor="silver" cellspacing="0">
    <td>Customer</td>
    <td>Product</td>
    <td>Ordered Date</td>
    <td>Status</td>
    </tr>
        <xsl:apply-templates/>
   </table>
  </xsl:template>

  <xsl:template match="Order">
    <form method="post" action="shop">
    <tr bgcolor="#C3FFA4" >
        <td>
            <xsl:value-of select="Customer"/>
        </td>
        <td>
            <xsl:value-of select="Product"/>
        </td>
        <td>
            <xsl:value-of select="OrderDate"/>
        </td>
        <td>
            <xsl:value-of select="OrderStatus"/>
        </td>
    </tr>
   </form>
  </xsl:template>

</xsl:stylesheet>