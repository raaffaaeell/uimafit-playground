<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- 
  XSL stylesheet to be included in stylesheets used for transforming inline XML annotations to the
  HTML representation used in the annotation viewer.

  Stylesheets should include this stylesheet and extend it by adding individual rules for all
  annotations that are to be displayed.  Each rule should call the "Annotation" rule defined in
  this stylesheet, and pass a value for the "label" parameter.  This label (with any spaces replaced
  by underscores) should match the name of a style defined in the annotaitons.css stylesheet.
  
  This is a heavily modified version. This template will help create spans with data attributes
  that are equivalent to the annotation features and the right css style to it.   
-->


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


  <xsl:template name="Annotation" match="Annotation">
    <xsl:param name="label" />
    <xsl:variable name="className" select="translate($label,' .','__')" />
    <xsl:variable name="annotType" select="name()" />
    <xsl:variable name="toReplace" select='"&#10;&#13;&apos;"' />
    <xsl:variable name="spannedText" select="translate(.,$toReplace,'  ')" />
    <span class="{$className}">
    <xsl:for-each select="@*">
      <xsl:variable name="dataAttribute" select="concat('data', '-', name())" />
      <xsl:variable name="dataValue" select="translate(.,$toReplace,'  ')" />
      <xsl:attribute name="{$dataAttribute}">
        <xsl:value-of select="$dataValue" />
      </xsl:attribute>
    </xsl:for-each>
    <xsl:apply-templates/>
  </span>
  </xsl:template>

</xsl:stylesheet>