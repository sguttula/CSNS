<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" indent="yes" omit-xml-declaration="yes" />

<xsl:template match="@*|node()">
<xsl:copy>
  <xsl:apply-templates select="@*|node()"/>
</xsl:copy>
</xsl:template>

<xsl:template match="img">
<img>
  <xsl:copy-of select="@*"/>
  <xsl:if test="@changeType='diff-removed-image' or @changeType='diff-added-image'">
        <xsl:attribute name="onLoad">updateOverlays()</xsl:attribute>
        <xsl:attribute name="onError">updateOverlays()</xsl:attribute>
        <xsl:attribute name="onAbort">updateOverlays()</xsl:attribute>
  </xsl:if>

</img>
</xsl:template>

<xsl:template match="span[@class='diff-html-changed']">
<span>
  <xsl:copy-of select="@*"/>
  <xsl:attribute name="onclick">return tipC(constructToolTipC(this));</xsl:attribute>
  <xsl:apply-templates select="node()"/>
</span>
</xsl:template>

<xsl:template match="span[@class='diff-html-added']">
<span>
  <xsl:copy-of select="@*"/>
  <xsl:attribute name="onclick">return tipA(constructToolTipA(this));</xsl:attribute>
  <xsl:apply-templates select="node()"/>
</span>
</xsl:template>

<xsl:template match="span[@class='diff-html-removed']">
<span>
  <xsl:copy-of select="@*"/>
  <xsl:attribute name="onclick">return tipR(constructToolTipR(this));</xsl:attribute>
  <xsl:apply-templates select="node()"/>
</span>
</xsl:template>

</xsl:stylesheet>
