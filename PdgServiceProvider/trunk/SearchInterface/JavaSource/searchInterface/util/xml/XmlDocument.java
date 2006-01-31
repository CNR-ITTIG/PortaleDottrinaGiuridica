//         ============================ 
//        |  Autore: Lomartire Cosimo  |
//        |  Data: 19.01.2004          |
//        |  Firenze                   |
//         ============================ 

package searchInterface.util.xml;

import java.io.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import org.apache.xerces.dom.*;
import org.apache.xerces.parsers.*;

public class XmlDocument {

	protected Document root;

	private DOMParser parser = new DOMParser();
	
	
	
	public XmlDocument() {
		this.root = new DocumentImpl();
	}

	public XmlDocument(Document doc) {
		this.root = doc;		
	}

	public XmlDocument(String xml) {
		
		String s="";
		// Lettura del file
		FileInputStream fis=null;
		try {
			fis = new FileInputStream(xml);
		} catch (FileNotFoundException exc) {
			System.err.println("Errore accesso file xml");
		}	
		BufferedInputStream bufFis= new BufferedInputStream(fis);

//		Leggo il contenuto del file
		StringBuffer sbuffXml = new StringBuffer();
		int icar=0;
		try {
			while ((icar = bufFis.read())>=0)
//				Voglio tutto su una riga, quindi ignoro il ritorno a capo
				if(icar!=(int)'\n'&&icar!=(int)'\r')
					sbuffXml.append((char)icar);
		} catch (IOException exIO) {
			System.err.println("Errore lettura buffer caratteri xml");
		}
				
		s=sbuffXml.toString();
		StringReader sReader = new StringReader(s);
		InputSource isXml = new InputSource(sReader);
		
		boolean flagUscita = false;

		while (!flagUscita) {
			try {
				isXml.setEncoding("ISO-8859-1");
				parser.parse(isXml);
				flagUscita = true;
			} catch (Exception ex) {
				SAXParseException SAXEx = (SAXParseException) ex;
				System.err.print("Errore nel file "+xml);
				System.err.println(" - Colonna "	+ SAXEx.getColumnNumber());
			
				
				// *********************************************************
				// ************ Gestione dei caratteri illegali ************
				// *********************************************************

				// **********************************
				// Elimino il carattere illegale 0x7
				// **********************************
				s=s.replaceAll("\7","");
				// **********************************
				// Scrivo & come &amp;
				// **********************************
				s=s.replaceAll("&","&amp;amp; ");

				sReader = new StringReader(s);
				isXml = new InputSource(sReader);
			}
			// **********************************
			// **********************************
			
		}
		this.root = parser.getDocument();
	}

	public XmlDocument(InputStream xml) {
		InputSource iXml = new InputSource(xml);
		iXml.setEncoding("UTF-8");
		try {
			parser.parse(iXml);
		} catch (Exception ex) {
			System.err.println("Errore nel parsing!");
			ex.printStackTrace();
			// System.exit(1);
		}
		this.root = parser.getDocument();
	}

	public Document getRoot() {
		return this.root;
	}

	public Element getRootElement() {
		return (this.root).getDocumentElement();
	}

	public void saveDocument(String file_name) {
		try {
			File f = new File(file_name);
			if (!f.exists() || f.canWrite()) {
				Document d = this.root;
				FileOutputStream out = new FileOutputStream(f);
				writeXML(d, out);
				out.flush();
				out.close();
			} else {
				System.err.println("Impossibile effettuare la scrittura!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void saveDocument(String path, String file_name) {
		try {
			File p = new File(path);
			if ((p.exists() && p.isDirectory()) || p.mkdirs()) {
				File f = new File(path + file_name + ".xml");
				if ((!f.exists() && p.canWrite()) || f.canWrite()) {
					Document d = this.root;
					FileOutputStream out = new FileOutputStream(f);
					writeXML(d, out);
					out.flush();
					out.close();
				} else {
					System.err.println("Impossibile effettuare la scrittura!");
				}
			} else {
				System.err.println("Impossibile effettuare la scrittura!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void writeXML(Document d, OutputStream os)
			throws IOException {
		PrintWriter out = new PrintWriter(os);

		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println();
		d.getDocumentElement().normalize();
		writeXMLwalkTree(d.getDocumentElement(), 0, out);
		out.flush();
	}

	private static void writeXMLwalkTree(Node node, int indent, PrintWriter out) {
		if (node == null) {
			System.err.println("??? Node was null ???");
			return;
		}
		if (node.hasChildNodes()) {
			if (node instanceof Element) {
				Element elem = (Element) node;
				elem.normalize();
				out.print("\n");
				for (int j = 0; j < indent; j++) {
					out.print(" ");
				}
				out.print("<" + elem.getTagName());
				NamedNodeMap attrs = elem.getAttributes();
				for (int i = 0; i < attrs.getLength(); i++) {
					Attr a = (Attr) attrs.item(i);
					out.print(" " + a.getName() + "=\"" + a.getValue() + "\"");
				}
				out.print(">");
				NodeList nl = node.getChildNodes();
				for (int i = 0; i < nl.getLength(); i++) {
					writeXMLwalkTree(nl.item(i), indent + 2, out);
				}
				out.println("</" + elem.getTagName() + ">");
			}
		} else {
			if (node instanceof Element) {
				Element elem = (Element) node;
				out.print("\n");
				for (int j = 0; j < indent; j++) {
					out.print(" ");
				}
				out.print("<" + elem.getTagName());
				NamedNodeMap attrs = elem.getAttributes();
				for (int i = 0; i < attrs.getLength(); i++) {
					Attr a = (Attr) attrs.item(i);
					out.print(" " + a.getName() + "=\"" + a.getValue() + "\"");
				}
				out.println("/>");
			} else if (node instanceof CDATASection) {
				CDATASection cdata = (CDATASection) node;
				out.print("<![CDATA[" + cdata.getData() + "]]>");
			} else if (node instanceof Text) {
				Text text = (Text) node;
				StringBuffer buf = new StringBuffer(text.getData().length());
				for (int i = 0; i < text.getData().length(); i++) {
					if (text.getData().charAt(i) == '\n'
							|| text.getData().charAt(i) == '\r'
							|| text.getData().charAt(i) == ' '
							|| text.getData().charAt(i) == '\t') {
						if (buf.length() > 0
								&& buf.charAt(buf.length() - 1) != ' ') {
							buf.append(' ');
						}
					} else {
						buf.append(text.getData().charAt(i));
					}
				}
				if (buf.length() > 0 && !buf.toString().equals(" ")) {
					StringBuffer buf2 = new StringBuffer(buf.length() + indent);
					buf2.append(buf.toString());
					out.print(buf2);
				}
			}
		}
	}

}
