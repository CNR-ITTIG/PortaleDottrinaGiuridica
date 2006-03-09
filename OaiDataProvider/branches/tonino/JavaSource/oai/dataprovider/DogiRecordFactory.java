package oai.dataprovider;

import java.io.File;
import java.util.*;
import java.text.SimpleDateFormat;

import oai.util.xml.XMLTool;
import oai.util.xml.XmlDocument;

import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DogiRecordFactory implements RecordFactory {

	Hashtable hashString;

	// Tabella utilizzata per memorizzare i codici di classificazione di DoGi
	// per
	// riempire il campo dublinCore dc:subject
	ArrayList arrayRangeDoGiClassCode;

	String SDBPATH, SDBNAME;

	public DogiRecordFactory(Hashtable hashString) {
		this.hashString = hashString;

		arrayRangeDoGiClassCode = new ArrayList();

		SDBPATH = hashString.get("dbDogiPath").toString();
		SDBNAME = hashString.get("dbDogiPrefix").toString();
		// Lettura del file xml dei codici di classificazione di DoGi
		XmlDocument xmlDoGiClassCode = new XmlDocument(hashString.get(
				"supportPath").toString()
				+ hashString.get("DoGiClassificationCodeFile").toString());

		// Leggo i tutti tag class
		NodeList nlDoGiCodes = xmlDoGiClassCode.getRoot().getElementsByTagName(
				"code");
		String sCodeName = "";
		String sCode_from, sCode_to;

		// Lettura del file xml ed inserimento nell'array
		if (nlDoGiCodes != null) {
			for (int i = 0; i < nlDoGiCodes.getLength(); i++) {
				sCodeName = xmlDoGiClassCode.getRoot().getElementsByTagName(
						"value").item(i).getFirstChild().getNodeValue();
				sCode_to = xmlDoGiClassCode.getRoot().getElementsByTagName(
						"class_to").item(i).getFirstChild().getNodeValue();
				sCode_from = xmlDoGiClassCode.getRoot().getElementsByTagName(
						"class_from").item(i).getFirstChild().getNodeValue();

				DoGiClassCode classDoGiCode = new DoGiClassCode(sCode_from,
						sCode_to, sCodeName);
				arrayRangeDoGiClassCode.add(classDoGiCode);
			}
		}

	}

	public Node getRecord(String id) {
		Node nNode = null;
		// Lettura del file xml e creazione DOMParser
		// tramite la classe XMLDocument
		String sNomeFile = SDBPATH + id + ".xml";
		XmlDocument xml = new XmlDocument(sNomeFile);
		if (xml.getRoot() != null) {
			// Recupero il nodo root del record letto
			nNode = (xml.getRoot().getElementsByTagName("RECORD")).item(0);
			// Traduco il file xml in dc
			nNode = dogiXmlToDC(xml.getRoot(), nNode);
		}
		return nNode;
	}

	public Vector getRecords(String fileafter, String filebefore,
			String setspec, int startno, int size) {

		ArrayList lListaFile = getDogiFileList();
		List listListaFile = null;
		if ((fileafter != null) || (filebefore != null)) {
			// Estraggo solo i riferimenti ai file che mi interessano
			listListaFile = getDogiRange(lListaFile, fileafter, filebefore,
					startno);
			// *****************************************

		} else {
			// Estraggo solo i riferimenti ai file che mi interessano
			listListaFile = getDogiRange(lListaFile, startno, startno + size);
			// *****************************************
		}

		Document newDoc = new DocumentImpl();
		newDoc = dogiGetRecords(listListaFile);
		// Codice che deve restare anche per le altre implementazioni
		// **********************************************************
		Vector v = xmlToVector(newDoc, "record");
		// **********************************************************
		return v;
	}

	public Vector getIdentifiers(String fileafter, String filebefore,
			String setspec, int startno, int size) {

		ArrayList lListaFile = getDogiFileList();
		List listListaFile = null;
		if ((fileafter != null) || (filebefore != null)) {
			// Estraggo solo i riferimenti ai file che mi interessano
			listListaFile = getDogiRange(lListaFile, fileafter, filebefore,
					startno);
			// *****************************************

		} else {
			// Estraggo solo i riferimenti ai file che mi interessano
			listListaFile = getDogiRange(lListaFile, startno, startno + size);
			// *****************************************
		}

		Document newDoc = new DocumentImpl();
		newDoc = dogiListIdentifier(listListaFile);

		// Codice che deve restare anche per le altre implementazioni
		// **********************************************************
		// Trasformo la struttura xml in vettore
		Vector v = xmlToVector(newDoc, "header");
		return v;
		// **********************************************************
	}

	private List getDogiRange(ArrayList alListaFileTmp, String fileafter,
			String filebefore, int startno) {

		// Riduco alListaFile se il numero di partenza startno è <>0
		List alListaFile = alListaFileTmp.subList(startno, alListaFileTmp
				.size());

		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dataFrom = null, dataTo = null;
		try {
			if (fileafter != null)
				dataFrom = formatter.parse(fileafter);
			if (filebefore != null)
				dataTo = formatter.parse(filebefore);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		// Se esistono file nel range specificato da fileafter e fileafter
		// questi vengono restituiti in lListaFileInRange
		ArrayList lListaFileInRange = new ArrayList();
		if (dataFrom != null && dataTo != null) {
			// Ricerca con entrambe le date
			int i = 0, j = 0;
			while (i < 100 && j < alListaFile.size()) {
				Date dDataFileTmp = new Date(((File) (alListaFile.get(j)))
						.lastModified());
				Date dDataFile = null;
				try {
					dDataFile = formatter.parse(formatter.format(dDataFileTmp));
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
				if ((dDataFile.after(dataFrom) && dDataFile.before(dataTo))
						|| dDataFile.equals(dataFrom)
						|| dDataFile.equals(dataTo)) {
					lListaFileInRange.add(alListaFile.get(j));
					i++;
				}
				j++;
			}
			// **********************
		} else if (dataFrom != null) {
			// Ricerca con data di inizio
			int i = 0, j = 0;
			while (i < 100 && j < alListaFile.size()) {
				Date dDataFileTmp = new Date(((File) (alListaFile.get(j)))
						.lastModified());
				Date dDataFile = null;
				try {
					dDataFile = formatter.parse(formatter.format(dDataFileTmp));
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
				if (dDataFile.after(dataFrom) || dDataFile.equals(dataFrom)) {
					lListaFileInRange.add(alListaFile.get(j));
					i++;
				}
				j++;
			}
			// **********************
		} else {
			// Ricerca con data di fine
			int i = 0, j = 0;
			while (i < 100 && j < alListaFile.size()) {
				Date dDataFileTmp = new Date(((File) (alListaFile.get(j)))
						.lastModified());
				Date dDataFile = null;
				try {
					dDataFile = formatter.parse(formatter.format(dDataFileTmp));
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
				if (dDataFile.before(dataTo) || dDataFile.equals(dataTo)) {
					lListaFileInRange.add(alListaFile.get(j));
					i++;
				}
				j++;
			}
			// **********************
		}
		return lListaFileInRange;
	}

	private List getDogiRange(ArrayList alListaFile, int from, int to) {

		if (from > alListaFile.size())
			return null;
		else if (to > alListaFile.size())
			to = alListaFile.size();
		return alListaFile.subList(from, to);

	}

	private ArrayList getDogiFileList() {
		// Leggo i nomi dei file presenti nella directory del DB
		File fDOGIDir = new File(SDBPATH);
		File[] sListaFile = fDOGIDir.listFiles();
		// Ordino i file in ordine alfabetico per nome
		ArrayList alListaFile = new ArrayList();
		for (int i = 0; i < sListaFile.length; i++)
			alListaFile.add(sListaFile[i]);
		Collections.sort(alListaFile);
		return alListaFile;

	}

	private Document dogiListIdentifier(List listListaFile) {
		Document newRoot = new DocumentImpl();
		Node newRootNode, nNodeHeader;
		Element eDCElement;

		// Chiamo 'record' il nodo root. In uscita passero solo un vettore con i
		// nodi header.
		newRootNode = (Node) newRoot.createElement("record");
		// Generazione della data
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		simple.setTimeZone(TimeZone.getTimeZone("GMT"));

		String sDataUltimaModificaFile;
		for (int i = 0; i < listListaFile.size(); i++) {
			String sNomeFile = ClearFileName(((File) listListaFile.get(i))
					.getName());
			nNodeHeader = (Node) newRoot.createElement("header");
			// Generazione di identifier
			eDCElement = createNodeAndText(newRoot, "identifier", SDBNAME
					+ sNomeFile);
			nNodeHeader.appendChild(eDCElement);
			// Generazione di datestamp
			sDataUltimaModificaFile = simple.format(new java.util.Date(
					((File) listListaFile.get(i)).lastModified()))
					+ "Z";
			eDCElement = createNodeAndText(newRoot, "datestamp",
					sDataUltimaModificaFile);
			nNodeHeader.appendChild(eDCElement);

			newRootNode.appendChild(nNodeHeader);
		}
		// Appendo il nodo newRootNode al documento newRoot
		newRoot.appendChild(newRootNode);
		return newRoot;
	}

	private String ClearFileName(String sFileName) {
		// Restituisce il nome del file senza l'estensione .xml
		int indexEndFileName = sFileName.length() - 4;
		return sFileName = sFileName.substring(0, indexEndFileName);
	}

	public Element getIdentify() {
		// Codice che deve restare anche per le altre implementazioni
		// **********************************************************
		String identifyFile = (String) hashString.get("supportPath")
				+ (String) hashString.get("identifyFile");
		XmlDocument xml = new XmlDocument(identifyFile);
		return xml.getRootElement();
		// **********************************************************
	}

	public Vector getSets(int startno, int endno) {

		return null;
	}

	public Vector getIdentifiers(int cursor, int size) {
		Document newRoot = new DocumentImpl();

		ArrayList lListaFile = getDogiFileList();
		List listListaFile = getDogiRange(lListaFile, cursor, cursor + size);
		if (listListaFile != null)
			newRoot = dogiListIdentifier(listListaFile);

		// Codice che deve restare anche per le altre implementazioni
		// **********************************************************
		// Trasformo la struttura xml in vettore
		Vector v = xmlToVector(newRoot, "header");
		return v;
		// **********************************************************
	}

	public Vector getRecords(int cursor, int size) {

		Document newRoot = new DocumentImpl();

		ArrayList lListaFile = getDogiFileList();
		List listListaFile = getDogiRange(lListaFile, cursor, cursor + size);
		if (listListaFile != null)
			newRoot = dogiGetRecords(listListaFile);

		// Codice che deve restare anche per le altre implementazioni
		// **********************************************************
		Vector v = xmlToVector(newRoot, "record");
		return v;
		// **********************************************************
	}

	private Document dogiGetRecords(List listListaFile) {

		// Document newDoc = new DocumentImpl();
		Document newDoc = XMLTool.createDocumentRoot();
		Node newRoot;
		Node dogiRecordNode;
		// A questo elemento appendo tutti i record
		newRoot = (Node) newDoc.createElement("ListaRecord");

		newDoc.appendChild(newRoot);

		for (int i = 0; i < listListaFile.size(); i++) {
			String sNomeFile = ((File) listListaFile.get(i)).getPath();
			XmlDocument xml = new XmlDocument(sNomeFile);

			// Recupero il nodo root del record letto
			Node nNode = (xml.getRoot().getElementsByTagName("RECORD")).item(0);
			// Traduco il file xml in dc
			dogiRecordNode = dogiXmlToDC(newDoc, nNode);
			newRoot.appendChild(dogiRecordNode);
		}
		return newDoc;
	}

	private Node dogiXmlToDC(Document newRoot, Node nNode) {

		Document root = nNode.getOwnerDocument();

		// Document newRoot = new DocumentImpl();
		Node newRootNode = newRoot.createElement("record");

		Element newDCElement, newDCOai_dc, newDCHeader, newDCMetadata;

		// *********************************
		// ****** oai_dc:dc e attributi ****
		// *********************************
		newDCOai_dc = newRoot.createElement("oai_dc:dc");
		// Creazione attributi
		addDCNameSpace(newDCOai_dc);
		// *********************************
		// *********************************

		// record -> header
		newDCHeader = newRoot.createElement("header");
		newRootNode.appendChild(newDCHeader);
		// record -> metadata -> oai_dc:dc
		newDCMetadata = newRoot.createElement("metadata");
		newDCMetadata.appendChild(newDCOai_dc);
		newRootNode.appendChild(newDCMetadata);

		// *********************************
		// ********* identifier *********
		// *********************************
		// Leggo IDDoc_a e IDDoc_n == identifier
		// Prendo il nodo IDDoc_a, il contenuto è testo del suo figlio.
		String sIDDoc_a = root.getElementsByTagName("IDDoc_a").item(0)
				.getChildNodes().item(0).getNodeValue();
		String sIDDoc_n = root.getElementsByTagName("IDDoc_n").item(0)
				.getChildNodes().item(0).getNodeValue();
		// Creo l'elemento identifier ed il nodo di testo
		newDCElement = createNodeAndText(newRoot, "identifier", SDBNAME
				+ sIDDoc_a + sIDDoc_n);

		// Appendo a Header
		newDCHeader.appendChild(newDCElement);
		// *********************************
		// *********************************

		// *********************************
		// *********** datestamp ***********
		// *********************************
		// Leggo la data del file xml
		File ffile = null;

		ffile = new File(SDBPATH + sIDDoc_a + sIDDoc_n + ".xml");
		long llastmodified = ffile.lastModified();

		Date dDate = new Date(llastmodified);

		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		simple.setTimeZone(TimeZone.getTimeZone("GMT"));
		String datestring = simple.format(dDate) + "Z";

		// Creo l'elemento datestamp ed il nodo di testo
		newDCElement = createNodeAndText(newRoot, "datestamp", datestring);
		// Appendo a Header
		newDCHeader.appendChild(newDCElement);
		// *********************************
		// *********************************

		// *********************************
		// *********** dc:title ***********
		// *********************************
		// Leggo Titolo e Tag_31 e Tag_32 e Tag_33 Tag_34== dc:title
		// Prendo il nodo Titolo, il contenuto è testo del suo figlio.
		Node nTmp = null;
		String sTitolo = "";

		nTmp = root.getElementsByTagName("Titolo").item(0);
		if (nTmp != null)
			sTitolo = nTmp.getChildNodes().item(0).getNodeValue();

		nTmp = root.getElementsByTagName("Tag_31").item(0);
		if (nTmp != null)
			sTitolo += " - " + nTmp.getChildNodes().item(0).getNodeValue();

		nTmp = root.getElementsByTagName("Tag_32").item(0);
		if (nTmp != null)
			sTitolo += " - " + nTmp.getChildNodes().item(0).getNodeValue();
		nTmp = root.getElementsByTagName("Tag_33").item(0);
		if (nTmp != null)
			sTitolo += " - " + nTmp.getChildNodes().item(0).getNodeValue();
		nTmp = root.getElementsByTagName("Tag_34").item(0);
		if (nTmp != null)
			sTitolo += " - " + nTmp.getChildNodes().item(0).getNodeValue();

		// Creo l'elemento dc:title ed il nodo di testo
		newDCElement = createNodeAndText(newRoot, "dc:title", sTitolo);
		// Appendo a oai_dc
		newDCOai_dc.appendChild(newDCElement);
		// *********************************
		// *********************************

		// *********************************
		// *********** dc:creator **********
		// *********************************
		// responsabilità == dc:creator
		// Leggo i tutti tag responsabilita
		NodeList nlResp = root.getElementsByTagName("Responsabilita");
		String sResponsabilita = "";
		if (nlResp != null) {
			for (int i = 0; i < nlResp.getLength(); i++) {
				sResponsabilita += getElementsText((Element) nlResp.item(i));
				if (i < nlResp.getLength() - 1)
					sResponsabilita += "- ";
			}
		}
		// Creo l'elemento dc:creator ed il nodo di testo
		newDCElement = createNodeAndText(newRoot, "dc:creator", sResponsabilita
				.trim());
		// Appendo a oai_dc
		newDCOai_dc.appendChild(newDCElement);
		// *********************************
		// *********************************

		// *********************************
		// *********** dc:subject **********
		// *********************************
		// ClasseC == dc:subject
		// Leggo i tutti tag ClasseC
		NodeList nlClas = root.getElementsByTagName("ClasseC");
		String sClasseC = "";
		if (nlClas != null) {
			for (int i = 0; i < nlClas.getLength(); i++) {
				sClasseC += dogiTraslateClassificationCode(getElementsText((Element) nlClas
						.item(i)))
						+ " ";
			}
		}
		// Creo l'elemento dc:subject ed il nodo di testo
		newDCElement = createNodeAndText(newRoot, "dc:subject", sClasseC.trim());
		// Appendo a oai_dc
		newDCOai_dc.appendChild(newDCElement);
		// *********************************
		// *********************************

		// *********************************
		// *********** dc:description ******
		// *********************************
		// Tag_61 == dc:description
		// AbstractSommario == dc:description
		// Leggo i tutti tag Tag_61
		NodeList nlTag61 = root.getElementsByTagName("Tag_61");
		String sDescr = "";
		if (nlTag61 != null) {
			for (int i = 0; i < nlTag61.getLength(); i++) {
				sDescr += getElementsText((Element) nlTag61.item(i)) + " ";
			}
		}
		// Leggo i tutti tag AbstractSommario
		NodeList nlAbstractSommario = root
				.getElementsByTagName("AbstractSommario");
		if (nlAbstractSommario != null) {
			for (int i = 0; i < nlAbstractSommario.getLength(); i++) {
				sDescr += getElementsText((Element) nlAbstractSommario.item(i))
						+ " ";
			}
		}
		// Leggo i tutti tag Classe
		NodeList nlClasse = root.getElementsByTagName("Classe");
		if (nlClasse != null) {
			for (int i = 0; i < nlClasse.getLength(); i++) {
				sDescr += "- " + getElementsText((Element) nlClasse.item(i))
						+ " ";
			}
		}
		// Creo l'elemento dc:description ed il nodo di testo
		newDCElement = createNodeAndText(newRoot, "dc:description", sDescr
				.trim());
		// Appendo a oai_dc
		newDCOai_dc.appendChild(newDCElement);
		// *********************************
		// *********************************

		// *********************************
		// *********** dc:source ***********
		// *********************************
		// Fonte == dc:source
		// Leggo i tutti tag Fonte
		NodeList nlSource = root.getElementsByTagName("Fonte");
		String sFonte = "";
		if (nlSource != null) {
			for (int i = 0; i < nlSource.getLength(); i++) {
				sFonte += getElementsText((Element) nlSource.item(i));
				if (i < nlSource.getLength() - 1)
					sFonte += "- ";
			}
		}
		// Creo l'elemento dc:subject ed il nodo di testo
		newDCElement = createNodeAndText(newRoot, "dc:source", sFonte.trim());
		// Appendo a oai_dc
		newDCOai_dc.appendChild(newDCElement);
		// *********************************
		// *********************************

		// *********************************
		// *********** dc:type ***********
		// *********************************
		// Leggo Tag_12_d == dc:type
		// Prendo il nodo Tag_12_d, il contenuto è testo del suo figlio.

		String sTag12d = "";
		nTmp = root.getElementsByTagName("Tag_12_d").item(0);
		if (nTmp != null)
			if (nTmp.getChildNodes().item(0) != null)
				sTag12d = nTmp.getChildNodes().item(0).getNodeValue();

		// Creo l'elemento dc:type ed il nodo di testo
		newDCElement = createNodeAndText(newRoot, "dc:type", sTag12d.trim());
		// Appendo a oai_dc
		newDCOai_dc.appendChild(newDCElement);
		// *********************************
		// *********************************

		// TODO: Per ora ho fatto così:
		// IDDoc_a e IDDoc_n == identifier
		// data del file == datestamp
		// Titolo e Tag_31 e Tag_32 e Tag_33 Tag_34== dc:title
		// responsabilità == dc:creator
		// ClasseC == dc:subject ( ? )
		// Tag_61 e AbstractSommario e Classe == dc:description
		// Fonte == dc:source
		// Tag_12_d == dc:type

		//
		// Poi ci sono una serie di tag FonteC con caratteri strani.
		// Ho ancora da decifrare: Tag_53, Classe, Tag_40, Tag_12, Tag_11.

		// E' importante manterere sempre newRootNode
		// e via via newDCOai_dc e oai_dc:dc
		return newRootNode;
	}

	/*
	 * Traduce il codice di classificazione di DoGi in una stringa
	 */
	private String dogiTraslateClassificationCode(String sClassification) {
//	Tengo da parte la stringa originaria per ripristinarla in caso di errore.
		String sSaveClassification=sClassification;
		// Toglie la lettera S dalla classificazione Sxxxx
		// Se la classificazione è del tipo Sxxxx Dyyyy viene ignorata
		if (sClassification != null && !sClassification.equals("")) {
			if (sClassification.trim().length() > 5)
				sClassification = "";
			else
				sClassification = sClassification.substring(1);
		}

		// Converte in intero
		int iClassification = 0;
		if (sClassification != null && !sClassification.equals("")) {
			try{
			iClassification = Integer.valueOf(sClassification.trim())
					.intValue();
			}catch(Exception ex){
				System.err.println("Errore traduzione codifica DoGi - "+ex.getMessage());
//				Pongo la variabile a zero e proseguo. 
				iClassification=0;
				sClassification=sSaveClassification;
			}
			Iterator iteratorDoGiCodeClassCode = arrayRangeDoGiClassCode
					.iterator();
			int iClass_to, iClass_from;
			DoGiClassCode doGiCode;
			// Iterazione array
			while (iteratorDoGiCodeClassCode.hasNext()) {

				doGiCode = (DoGiClassCode) iteratorDoGiCodeClassCode.next();
				iClass_to = Integer.valueOf(doGiCode.svalue_to.substring(1))
						.intValue();
				iClass_from = Integer
						.valueOf(doGiCode.svalue_from.substring(1)).intValue();

				// Controllo range
				if ((iClass_from <= iClassification)
						&& (iClassification <= iClass_to)) {
					sClassification = doGiCode.sDescription;
				}
			}
		}
		return sClassification;
	}

	private void addDCNameSpace(Element e) {

		e.setAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
		e.setAttribute("xmlns:oai_dc",
				"http://www.openarchives.org/OAI/2.0/oai_dc/");
		e
				.setAttribute("xmlns:xsi",
						"http://www.w3.org/2001/XMLSchema-instance");
		e
				.setAttribute(
						"xsi:schemaLocation",
						"http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd");
	}

	private Element createNodeAndText(Document root, String selementID,
			String sTesto) {
		Node lnewTextNode;
		Element lnewDCElement;

		// Creo l'elemento identifier ed il nodo di testo
		lnewTextNode = root.createTextNode(sTesto);
		lnewDCElement = root.createElement(selementID);
		// Collego gli elementi creati all'header
		// header -> identifier
		lnewDCElement.appendChild(lnewTextNode);
		return lnewDCElement;
	}

	private String getElementsText(Element e) {
		String out = "";

		NodeList nl = e.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			if (nl.item(i).getNodeType() == Node.ELEMENT_NODE)
				out += getElementsText((Element) nl.item(i));
			else if (nl.item(i).getNodeType() == Node.TEXT_NODE) {
				out += nl.item(i).getNodeValue() + " ";
			}
		}
		return out;
	}

	private Vector xmlToVector(Document xml, String tag) {
		Vector v = new Vector();
		NodeList nl = null;
		Element tmp = xml.getDocumentElement();
		if (tmp != null) {
			nl = tmp.getElementsByTagName(tag);
			for (int i = 0; i < nl.getLength(); i++) {
				v.add(nl.item(i));
			}
		}
		return v;
	}

}
