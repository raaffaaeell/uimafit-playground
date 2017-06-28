<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- 
  XSL stylesheet that translates a style map XML file to a CSS stylesheet.
  
  Slightly modified version
-->

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:axsl="http://www.w3.org/1999/XSL/TransformAlias">

<xsl:output method="text"/>

<xsl:template match="rule">
  .<xsl:value-of select="translate(label,' .','__')"/> {<xsl:value-of select="style"/>}
</xsl:template>

</xsl:stylesheet>