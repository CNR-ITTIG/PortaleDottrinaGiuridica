<?xml version='1.0' encoding='utf-8'?>
<xsl:stylesheet exclude-result-prefixes="a dc oai_dc" version="1.0" xmlns:a="http://www.openarchives.org/OAI/2.0/" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:template match="/">
		<html xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/">
			<head>
				<title>					
- Cerca con ITTIG - Portale di Dottrina Giuridica</title>
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
				<table border="0" cellPadding="0" cellSpacing="0" width="100%">
					<tbody>
						<tr>
							<td vAlign="top">
								<a href="http://localhost">
									<img alt="Vai alla pagina principale del Portale di Dottrina Giuridica" border="0" src="http://localhost/PdgSearch_files/logo_sm.gif"/>
								</a>
							</td>
							<td/>
							<td vAlign="top" width="100%">
								<table border="0" cellPadding="0" cellSpacing="0">
									<tbody>
										<tr>
											<td class="q">
												<font color="#000000" size="-1">Cerca su PDG</font>
											</td>
										</tr>
									</tbody>
								</table>
								<form action="http://localhost:8080/SearchInterface/Search" method="get" name="f">
									<table border="0" cellPadding="0" cellSpacing="0">
										<tbody>
											<tr>
												<td width="25%">
													<input maxLength="256" name="simple" size="55"/>
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
												<td align="left">
													<font size="-1">
Cerca:														<input CHECKED="1" id="all" name="target" type="radio" value="on"/>
														<label>Tutti</label>
														<input id="web" name="target" type="radio" value="web"/>
														<label>Web</label>
														<input id="dogi" name="target" type="radio" value="dogi"/>
														<label>DoGi</label>
													</font>
												</td>
											</tr>
										</tbody>
									</table>
								</form>
							</td>
						</tr>
					</tbody>
				</table>
				<table border="0" cellPadding="0" cellSpacing="0" width="100%">
					<tbody>
						<tr>
							<td bgColor="#3366cc">
								<img height="1" width="1"/>
							</td>
						</tr>
					</tbody>
				</table>
				<table bgColor="#e5ecf9" border="0" cellPadding="0" cellSpacing="0" width="100%">
					<tbody>
						<tr>
							<td align="left" bgColor="#e5ecf9" width="45%">
								<table align="left" bgColor="#e5ecf9" border="0" cellPadding="0" cellSpacing="0" width="100%">
									<tbody>
										<tr>
											<td align="left" noWrap="1" vAlign="top" width="20%">
												<font size="-2">Ricerca su Portale di Dottrina Giuridica per:
												<xsl:comment>********** GetRecord **********</xsl:comment><xsl:apply-templates select="a:OAI-PMH/a:GetRecord/a:record/a:header"/></font>

											</td>
										</tr>
										<tr>
											<td align="left" vAlign="top"/>
										</tr>
									</tbody>
								</table>
							</td>
							<td align="right" bgColor="#e5ecf9" noWrap="1" vAlign="top"/>
						</tr>
					</tbody>
				</table>
				
				<div>
					<xsl:apply-templates select="a:OAI-PMH/a:GetRecord/a:record/a:metadata/oai_dc:dc"/>
				</div>
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="a:OAI-PMH/a:GetRecord/a:record/a:header">
		<xsl:comment>********** match header **********</xsl:comment>
		<xsl:value-of select="a:identifier"/>
	</xsl:template>
	
	
	<xsl:template match="a:OAI-PMH/a:GetRecord/a:record/a:metadata/oai_dc:dc">
		<xsl:comment>********** record **********</xsl:comment>
		<div>
			<p class="g">
				<table border="0" cellPadding="0" cellSpacing="0" width="70%">
					<tbody>
						<tr>
							<td align="left" width="50%">
								<xsl:comment>dc:creator</xsl:comment>
								<font color="#7777cc" size="-1">
									<b>Autore: </b>
									<xsl:value-of select="dc:creator"/>
								</font>
							</td>
							<td align="left" width="50%">
								<font color="#7777cc" size="-1">
									<b>Editore: </b>
									<xsl:value-of select="dc:publisher"/>
								</font>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="j">
								<xsl:comment>dc:title</xsl:comment>
								<b>
									<span nowrap="nowrap"><xsl:value-of select="dc:title"/></span>
								</b>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<font size="-1">
									<xsl:comment>dc:description</xsl:comment>
									<xsl:value-of select="dc:description"/>
...								</font>
							</td>
						</tr>
						<tr>
						<td colspan="2"><br/>
										<span dir="ltr">
											<font color="#a29f9f" size="-2">
												<xsl:comment>dc:source</xsl:comment>
												Fonte: <b>
												<xsl:value-of select="dc:source"/></b>
											</font>
										</span>
							</td>
						</tr>
						<tr>
						<td colspan="2"><br/>
										<span dir="ltr">
											<font color="#a29f9f" size="-2">
												<xsl:comment>dc:subject</xsl:comment>
												Soggetto: <b>
												<xsl:value-of select="dc:subject"/></b>
											</font>
										</span>
							</td>
						</tr>
						<tr>
						<td colspan="2"><br/>
										<span dir="ltr">
											<font color="#a29f9f" size="-2">
												<xsl:comment>dc:type</xsl:comment>
												Tipo: <b>
												<xsl:value-of select="dc:type"/></b>
											</font>
										</span>
							</td>
						</tr>
					</tbody>
				</table>
			</p>
		</div>
	</xsl:template>
</xsl:stylesheet>
