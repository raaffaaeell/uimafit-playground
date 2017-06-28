<?xml version="1.0" encoding="ISO-8859-1"?>
	
<!-- Heavily modified from the original version of the uimaj-tools package -->

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:axsl="http://www.w3.org/1999/XSL/TransformAlias"
	xmlns:baxsl="http://www.w3.org/1999/XSL/TransformAliasBlank">

	<xsl:namespace-alias stylesheet-prefix="axsl"
		result-prefix="xsl" />
	<xsl:namespace-alias stylesheet-prefix="baxsl"
		result-prefix="" />



	<xsl:template match="/">
		<axsl:stylesheet>
			<axsl:template match="/">
				<axsl:include baxsl:href="annotations.xsl" />
				<html>

					<head>
						<title>
							Annotation viewer
						</title>
					</head>

					<body>
						<div class="container-fluid">
							<div class="row">
								<div class="col-lg-9 col-sm-9" id="text">
									<h2>
										Text
									</h2>
									<pre>
										<xsl:text> <axsl:apply-templates /> </xsl:text>
									</pre>
								</div>
								<div class="col-lg-3 col-sm-3">
										<xsl:for-each select="styleMap/rule">
									<div class="col-lg-12 col-sm-12 form-check">
											<xsl:variable name="className" select="label" />
											<label class="{$className}">
												<input type="checkbox" class="chk" value="{$className}" />
												<xsl:value-of select="label" />
											</label>
									</div>
										</xsl:for-each>
									<div class="col-lg-12 col-sm-12 text-center">
										<h2>
											Annotations
										</h2>
										<div id="info">
										</div>
									</div>
								</div>
							</div>
						</div>
					</body>

					<link rel="stylesheet" href="annotations.css" />
					<script
						src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
					<link rel="stylesheet"
						href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
					<script
						src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
					<script type="text/javascript" src="annotationViewer.js"></script>


				</html>
			</axsl:template>

			<xsl:text> <xsl:apply-templates /> </xsl:text>

		</axsl:stylesheet>

	</xsl:template>




	<xsl:template match="rule">
		<axsl:template>
			<xsl:attribute name="match"><xsl:value-of select="pattern" /></xsl:attribute>

			<axsl:call-template name="Annotation">
				<axsl:with-param name="label">
					<xsl:value-of select="label" />
				</axsl:with-param>
			</axsl:call-template>
		</axsl:template>
	</xsl:template>


</xsl:stylesheet>