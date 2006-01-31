<xsl:stylesheet version="1.0" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:template match="/">
		<html>
			<head>
				<title>
					<xsl:for-each select="/SWISH-E/lookfor">
						<xsl:value-of select="value"/>
					</xsl:for-each>
- Cerca con ITTIG - Portale di Dottrina Giuridica				</title>
				<style>
					<xsl:comment>body,td,div,.p,a{font-family:arial,sans-serif }
div,td{color:#000}
.f{color:#6f6f6f}
.fl:link{color:#77c}
a:link,.w,a.w:link,.w a:link{color:#00c}
a:visited,.fl:visited{color:#551a8b}
a:active,.fl:active{color:#f00}
.t a:link,.t a:active,.t a:visited,.t{color:#000}
.t{background-color:#e5ecf9}
.k{background-color:#36c}
.j{width:34em}
.h{color:#36c;font-size:14px}
.i,.i:link{color:#a90a08}
.a,.a:link{color:#008000}
.z{display:none}
div.n {margin-top: 1ex}
.n a{font-size:10pt; color:#000}
.n .i{font-size:10pt; font-weight:bold}
.q a:visited,.q a:link,.q a:active,.q{color:#00c;}
.b{font-size: 12pt; color:#00c; font-weight:bold}
.ch{cursor:pointer;cursor:hand}
h3{font-weight:normal;font-size=100%}
.sem{display:inline;margin:0;font-size:100%;font-weight:inherit}
.e{margin-top: .75em; margin-bottom: .75em}
.g{margin-top: 1em; margin-bottom: 1em}
.sm{display:block; margin-top: 0px; margin-bottom: 0px; margin-left: 40px}</xsl:comment>
				</style>
				<script>
<![CDATA[
<!--function ss(w,id){window.status=w;return true;}
function cs(){window.status='';}
function rwt(el,ct,cd,sg){el.href="/url?sa=t&ct="+escape(ct)+"&cd="+escape(cd)+"&url="+escape(el.href).replace(/\+/g,"%2B")+"&ei=uziLQ__5JqDuQd709Ww"+sg;el.onmousedown="";return true;}
function ga(o,e) {if (document.getElementById) {var a = o.id.substring(1); var p = "", r = "", t, f, h;var g = e.target;if (g) { t = g.id;f = g.parentNode;if (f) {p = f.id;h = f.parentNode;if (h)r = h.id;}} else {h = e.srcElement;f = h.parentNode;if (f)p = f.id;t = h.id;}if (t==a || p==a || r==a)return true;top.location.href=document.getElementById(a).href}}-->
]]>				</script>
			</head>
			<body bgcolor="#ffffff" marginheight="3" onload="document.gs.reset()" topmargin="3">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td valign="top">
							<a href="http://localhost">
								<img alt="Vai alla pagina principale del Portale di Dottrina Giuridica" border="0" src="http://localhost/PdgSearch_files/logo_sm.gif"/>
							</a>
						</td>
						<td/>
						<td valign="top" width="100%">
							<table border="0" cellpadding="" cellspacing="0">
								<tr>
									<td align="bottom" class="q">
										<font color="#000000" size="-1">Cerca su PDG</font>
									</td>
								</tr>
							</table>
							<form action="http://localhost:8080/SearchInterface/Search" method="get" name="f">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="25%">
											<input maxlength="256" name="simple" size="55" value=""/>
											<input name="modo" type="hidden" value="0"/>
											<input name="act" size="5" type="hidden" value="1"/>
											<input name="rfp" size="5" type="hidden" value="10"/>
											<input name="btnG" type="submit" value="Cerca con PdgSearch"/>
											<font size="-2">
												<a href="http://localhost/advanced_search.html">Ricerca avanzata</a>
											</font>
										</td>
										<br/>
									</tr>
									<tr>
										<td align="left" colspan="1">
											<font size="-1">
Cerca:												<input checked="checked" id="all" name="target" type="radio" value="">
													<label for="all">Tutti</label>
												</input>
												<input id="web" name="target" type="radio" value="web"/>
												<label for="web">Web</label>
												<input id="dogi" name="target" type="radio" value="dogi"/>
												<label for="dogi">				DoGi</label>
											</font>
										</td>
									</tr>
								</table>
							</form>
						</td>
					</tr>
				</table>
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td bgcolor="#3366cc">
							<img alt="" height="1" width="1"/>
						</td>
					</tr>
				</table>
				<table bgcolor="#e5ecf9" border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="left" bgcolor="#e5ecf9" width="45%">
							<table align="left" bgcolor="#e5ecf9" border="0" cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td align="left" nowrap="nowrap" valign="top" width="20%">
										<font size="-2">Ricerca su Portale di Dottrina Giuridica per:</font>
									</td>
								</tr>
								<tr>
									<td align="left" valign="top">
										<xsl:for-each select="/SWISH-E/lookfor">
											<font size="-2">
												<xsl:value-of select="name"/>
:												<b>
													<xsl:value-of select="value"/>
												</b>
											</font>
											<br/>
										</xsl:for-each>
									</td>
								</tr>
							</table>
						</td>
						<td align="right" bgcolor="#e5ecf9" nowrap="nowrap" valign="top">
							<font size="-1">
Risultati								<b>
									<xsl:value-of select="/SWISH-E/start"/>
								</b>
		-								<b>
									<xsl:value-of select="/SWISH-E/end"/>
								</b>
su								<b>
									<xsl:value-of select="/SWISH-E/recordsNumber"/>
								</b>
							</font>
						</td>
					</tr>
				</table>
				<xsl:comment>Applicazione template match=/SWISH-E/record/metadata</xsl:comment>
				<xsl:apply-templates select="/SWISH-E/record/metadata"/>
				<xsl:apply-templates select="/SWISH-E/pages"/>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="/SWISH-E/record/metadata">
		<xsl:comment>********** record **********</xsl:comment>
		<div>
			<p class="g">
				<xsl:comment>provenienza</xsl:comment>
				<font size="-2">
					<xsl:comment>archivio provenienza</xsl:comment>
					<xsl:value-of select="../provenienza"/>&#160;
				</font>
				<!-- Se il documento è Web document il link sarà l'url alla pagina-->
				<xsl:variable name="value-provenienza">
					<xsl:value-of select="../provenienza"/>
				</xsl:variable>
				<xsl:choose>
					<xsl:when test="$value-provenienza=&apos;Web document&apos;">
					<xsl:element name="a">
					<xsl:attribute name="href">http://<xsl:value-of select="dc:identifier"/></xsl:attribute>
					<xsl:attribute name="class">l</xsl:attribute>
					<xsl:value-of select="dc:title"/>
				</xsl:element>
					</xsl:when>
					<xsl:otherwise><xsl:element name="a">
					<xsl:attribute name="href">
						<xsl:value-of select="/SWISH-E/url/servlet"/>?getRecord=<xsl:value-of select="dc:identifier"/>&amp;target=<xsl:value-of select="../provenienza"/></xsl:attribute>
					<xsl:attribute name="class">l</xsl:attribute>
					<xsl:value-of select="dc:title"/>
				</xsl:element></xsl:otherwise>
				</xsl:choose>
				 <xsl:comment>dc:title</xsl:comment>
				
				<table border="0" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td class="j">
								<font size="-1"><font color="#7777CC" size="-2">
										<xsl:comment>dc:creator</xsl:comment>
										<span nowrap="nowrap">
[											<xsl:element name="a">
												<xsl:attribute name="href">
													<xsl:value-of select="/SWISH-E/url/servlet"/>?dc:creator=<xsl:value-of select="dc:creator"/></xsl:attribute>
												<xsl:attribute name="class">fl</xsl:attribute>
												<xsl:value-of select="dc:creator"/>
											</xsl:element>
]										</span>
									</font>&#160;&#160;<xsl:comment>dc:description</xsl:comment>
									<xsl:value-of select="dc:description"/>...
									<br/>
									<font color="#008000">
										<span dir="ltr">
											<xsl:comment>dc:identifier</xsl:comment>
											<xsl:value-of select="dc:identifier"/>&#160;&#160;
											<font color="#a29f9f" size="-2">
												<xsl:comment>dc:subject</xsl:comment>
												<xsl:value-of select="dc:subject"/>
											</font>
										</span>
									</font>
									<nobr/>
								</font>
							</td>
						</tr>
					</tbody>
				</table>
			</p>
		</div>
	</xsl:template>
	<xsl:template match="/SWISH-E/pages">
		<xsl:comment>paginazione</xsl:comment>
		<br clear="all"/>
		<div class="n">
			<table align="center" border="0" cellpadding="0" cellspacing="0" width="1%">
				<tbody>
					<tr align="center" valign="top">
						<td nowrap="nowrap" valign="bottom">
							<font size="-1">Pagina dei risultati:</font>
						</td>
						<td nowrap="nowrap">
							<img alt="" border="0" height="26" src="http://localhost/PdgSearch_files/nav_first.gif" width="16"/>
							<br/>
						</td>
						<xsl:for-each select="/SWISH-E/pages/page">
							<td nowrap="nowrap">
<!--link paginazione-->
								<xsl:element name="a">
									<xsl:attribute name="href"><xsl:value-of select="link"/></xsl:attribute>
									<xsl:element name="img">
										<xsl:attribute name="src">
											<xsl:value-of select="/SWISH-E/url/images"/>/nav_page.gif</xsl:attribute>
										<xsl:attribute name="height">26</xsl:attribute>
										<xsl:attribute name="width">16</xsl:attribute>
										<xsl:attribute name="border">0</xsl:attribute>
									</xsl:element>
									<br/>
<!--numero di pagina-->
									<xsl:value-of select="number"/>
								</xsl:element>
								<br/>
							</td>
						</xsl:for-each>
						<td nowrap="nowrap">
							<img alt="" border="0" height="26" src="http://localhost/PdgSearch_files/nav_next.gif"/>
							<br/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</xsl:template>
</xsl:stylesheet>
