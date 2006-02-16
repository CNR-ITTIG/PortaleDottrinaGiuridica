/*
 * Handle.java
 *
 * Created on 1 aprile 2004, 16.49
 */

package k_notes.verb;

/**
 * 
 * @author root
 */
import java.text.SimpleDateFormat;
import java.util.*;

import org.w3c.dom.*;

import k_notes.xml.XmlDocument;
import k_notes.generic.*;

public class Handle {

	private ContentStore content;
	public Handle(ContentStore contentStore) {
		this.content = contentStore;
	}

	public void recupera() {

		XmlDocument xml = null;
		String resumptionToken = "";

		String invoke = "";

		try {
			// Legge dal file k-notes.properties l'url della servelt
			invoke = content.getContent("OAIServerInvoke");
			// Legge dal file di supporto la data dell'ultima raccolta
			String dataUltimoRecupero = lastHarvestingRead();

			// Se è la prima volta non include il valore from nella richiesta
			String sInvokeFrom = "";
			invoke = invoke + "?verb=ListRecords&metadataPrefix=oai_dc";

			if (dataUltimoRecupero != null && !dataUltimoRecupero.equals("0")) {
				sInvokeFrom = "&from=" + dataUltimoRecupero;
				invoke = invoke + sInvokeFrom;
			}

			System.out.print("Richiesta OAI-PMH\n" + invoke);
			if(content.getContent("debug").equals("true")) System.out.println("Esecuzione richiesta");
			xml = Util.execute(invoke, "");
			if(content.getContent("debug").equals("true")) System.out.println("eseguita");
			dividi(xml, content.getContent("RecordDir"));

			System.out.println(" ok");

		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("Execute ha causato un errore!!");
			System.err.println(invoke);
		}

		// Il ciclo viene eseguito fino a quando c'è un campo resumptionToken
		// nella risposta
		Node nResumptionToken = (xml.getRoot()
				.getElementsByTagName("resumptionToken")).item(0);
		int j = 1;
		while (nResumptionToken != null) {
			// Legge il resumptionToken
			resumptionToken = nResumptionToken.getFirstChild().getNodeValue();
			// Costruisce l'url per la richiesta in OAI-PMH utilizzando il
			// resumptionToken
			try {
				invoke = content.getContent("OAIServerInvoke");
				invoke = invoke + "?verb=ListRecords&resumptionToken="
						+ resumptionToken;

				System.out.print("Richiesta OAI-PMH nr." + (++j) + " ");
				System.err.println(j + ": " + resumptionToken);
				xml = Util.execute(invoke, "");
				dividi(xml, content.getContent("RecordDir"));

				System.out.println(" ok");

			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println("Execute ha causato un errore!!");
				System.err.println(invoke);
			}
			nResumptionToken = (xml.getRoot()
					.getElementsByTagName("resumptionToken")).item(0);
		}
		if (nResumptionToken == null && xml != null) {
			// Scrivo sul file di supporto la data di oggi
			SimpleDateFormat formatter;
			formatter = new SimpleDateFormat("yyyy-MM-dd");

			String sDataOggi = formatter.format(new Date());
			System.out.println("Raccolta dati completata il " + sDataOggi);

			lastHarvestingWrite(sDataOggi);
		}
	}

	public String lastHarvestingRead() {

		String sFileSupporto = content.getContent("supportPath")
				+ content.getContent("supporto");
		return Util.readTo(sFileSupporto, "lastHarvesting");
	}

	public void lastHarvestingWrite(String sData) {

		String sFileSupporto = content.getContent("supportPath")
				+ content.getContent("supporto");
		Util.writeTo(sFileSupporto, "lastHarvesting", sData);
	}


	public XmlDocument record() {
		XmlDocument xml = new XmlDocument(content.getContent("RecordDir")
				+ content.getContent("file"));

		Document doc = xml.getRoot();
		Element doot = (Element) doc.getElementsByTagName("record").item(0);
		Element root = doc.createElement("File");
		root.appendChild(doc.createTextNode(content.getContent("file")));
		root = doc.createElement("urlBase");
		root.appendChild(doc.createTextNode(content.getContent("urlBase")));

		doot.appendChild(root);

		return xml;
	}

	private void dividi(XmlDocument xml, String dir) {
		
		if(content.getContent("debug").equals("true")) System.out.println("Ingresso metodo dividi");
		Element tmp = null;
		XmlDocument tXml = null;
		String id = "";
		Document doc = xml.getRoot();
		NodeList nl = doc.getElementsByTagName("record");

		for (int i = 0; i < nl.getLength(); i++) {
			tmp = (Element) nl.item(i);
			tXml = new XmlDocument(tmp);
			id = (tmp.getElementsByTagName("identifier").item(0))
					.getFirstChild().getNodeValue();
			id = id.replace('/', '_');
			if(content.getContent("debug").equals("true")) System.out.println("Tentativo scrittura documento \""+ id+"\"su filesystem");
			tXml.saveDocument(dir, id);
			if(content.getContent("debug").equals("true")) System.out.println("Documento salvato");
		}
	}

}
