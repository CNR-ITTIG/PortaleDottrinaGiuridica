package searchInterface;

import java.io.PrintWriter;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.Document;
import java.io.*;

import searchInterface.util.xml.XMLTool;

class ResponsePage {
	
	private Document xml;
/**
 * 
 * @param xmldoc Documento xml
 */
	ResponsePage(Document xmldoc) {
		xml=xmldoc;
	}
	
/**
 * Scrive il contenuto xml utilizzando il foglio di stile sStyleSheet sull'uscita pw
 * @param sStyleSheet File foglio di stile
 * @param pw Printwrite sul quale scrivere il risultato della trasformazione
 */
	public void writePage(String sStyleSheet,PrintWriter pw) {
	
		Transformer transformer;
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			transformer = factory.newTransformer(new StreamSource(sStyleSheet));
//			transformer.transform(new StreamSource("file:///home/tonix/Desktop/file.xml"), new StreamResult(pw));
			transformer.transform(new StreamSource(new StringReader(XMLTool.output(xml))), new StreamResult(pw));
		} catch (Exception e) {
			System.err.println("Errore trasformazione xml con foglio di stile: "+e.getMessage());
		}
	}
}
