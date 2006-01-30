//         ============================ 
//        |  Autore: Fazio Antonino    |
//        |  Data: novembre 2005       |
//        |  Firenze                   |
//         ============================ 

package searchInterface;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


import searchInterface.util.generic.Util;
import searchInterface.util.xml.*;

/**
 * Servlet implementation class for Servlet: indiceweb
 * 
 */

/**
 * @author tonix
 *
 */
/**
 * @author tonix
 * 
 */
public class Search extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {

	static final long serialVersionUID=0;
	// *************************************
	boolean bDEBUG = false; // Impostazione predefinita. Viene comunque letta

	// l'impostazione dal file delle proprietà
	// *************************************
	private Hashtable hashString = new Hashtable();

	private ServletConfig servlet_config;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Search() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		String properties = "";
		servlet_config = config;

		System.err.println(new Date()
				+ " - Portale Dottrina Giuridica - Init......");
		System.err
				.println("=======================================================");
		System.err.println("            " + getServletInfo());
		System.err
				.println("=======================================================");

		try {
			if (config.getInitParameter("properties") == null) {
				throw new Exception("Parametro properties non trovato!!");
			} else {
				properties = config.getInitParameter("properties");
			}
		} catch (Exception ex) {
			System.err
					.println("Non è stato impostato il parametro iniziale properties!");
			System.exit(1);
		}
		this.hashString = Util.propert(properties);
		// *************************************
		// Inizializzazione variabile debug/normale
		bDEBUG = Boolean.parseBoolean((String) hashString.get("DebugMode"));
		// *************************************

		System.err.println("Inizializzazione completata.");
		System.err
				.println("=======================================================");
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Stabilisce il tipo MIME della risposta
		response.setContentType("text/html");
		// response.setContentType("text/plain");
		// response.setContentType("text/xml;charset=UTF-8");

		// Ottengo oggetto per scrittura pagina web
		PrintWriter pw = response.getWriter();

		if (bDEBUG) {
			Enumeration e = request.getParameterNames();
			System.out.print("inizio scansione argomenti\n");
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String value = request.getParameter(name);
				System.out.println(name + " = " + value);
			}
		}

		// se esiste il parametro getrecord allora
		// leggo il record e preparo la pagina di risposta
		String sRecordName = request.getParameter("getRecord");
		String sTarget = request.getParameter("target");
		if (sRecordName != null) {
			// *******************************************
			// **** getRecord ****
			// *******************************************
			// leggo il record in XML
			XmlDocument sRecordXML = getrecord(sTarget, sRecordName);
			System.out.println("\n"+XMLTool.showElement(sRecordXML.getRootElement()));
			// Genero la pagina di risposta
			ResponsePage recordview=new ResponsePage(sRecordXML.getRoot());
			recordview.writePage((String) hashString.get("styleSheetPath")
					+ (String) hashString.get("ssGetRecord"), pw);
			// *******************************************
			// *******************************************
		} else {
			// altrimenti continuo l'esecuzione normale

			// Questi parametri vengono passati dalla pagina chiamante
			String sLookFor = request.getParameter("simple");
			// prepareString(String,Int) prepara la stringa secondo la modalità
			// richiesta: and, or, fraase completa
			// Modalità di ricerca.
			// 0 and
			// 1 or
			// 2 frase intera
			int iModalita = 0;
			if (request.getParameter("modo") != null)
				iModalita = Integer.parseInt(request.getParameter("modo"));

			// Inserisco le informazioni per la ricerca in una mappa
			HashMap<String, String> mapTargetLookFor = new HashMap<String, String>();

			// target indica l'archivio su cui cercare
			mapTargetLookFor.put("target", sTarget);
			if (sLookFor != null) {
				// Inserisco le informazioni per la ricerca semplice
				mapTargetLookFor.put("simple", prepareString(sLookFor,
						iModalita));
			} else {
				// RECUPERO INFORMAZIONI PER LA RICERCA AVANZATA
				// Modalità=0 AND
				// Modalità=1 OR
				String stmp;

				stmp = prepareString(request.getParameter("dc:title"), 0);
				if (!stmp.equals("null"))
					mapTargetLookFor.put("dc:title", prepareString(request
							.getParameter("dc:title"), 0));

				stmp = prepareString(request.getParameter("dc:description"), 0);
				if (!stmp.equals("null"))
					mapTargetLookFor.put("dc:description", stmp);

				stmp = prepareString(request.getParameter("dc:creator"), 0);
				if (!stmp.equals("null")){
					mapTargetLookFor.put("dc:creator", stmp);
//					mapTargetLookFor.put("dc:publisher", stmp);
				}

				stmp = prepareString(request.getParameter("dc:subject"), 0);
				if (!stmp.equals("null"))
					mapTargetLookFor.put("dc:subject", stmp);
			}
			// Recupero delle informazioni sulla pagina attuale e sul numero di
			// record per pagina
			int iRecordForPage = 10;
			int iThisPage = 1;
			try {
				iRecordForPage = Integer.parseInt(request.getParameter("rfp"));
			} catch (Exception ex) {
				System.err
						.println("Lettura parametro rfp da GET - Errore - Assunto default [10]");
			}
			try {
				iThisPage = Integer.parseInt(request.getParameter("act"));
			} catch (Exception ex) {
				System.err
						.println("Lettura parametro act da GET - Errore - Assunto default [1]");
			}

			// ****************************************
			// ***** ricerca *****
			// ****************************************
			XmlDocument risultatoXml = findXml(mapTargetLookFor, iThisPage,
					iRecordForPage);
			// ****************************************
			// ****************************************
			 
			ResponsePage paginaweb = new ResponsePage(risultatoXml.getRoot());
			paginaweb.writePage((String) hashString.get("styleSheetPath")
					+ (String) hashString.get("ssResponsePage"), pw);
			pw.flush();
			pw.close();
		}
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	private XmlDocument getrecord(String sDBName, String srecordName) {
		if (sDBName.equals("DoGi")) {
			String invoke = (String) hashString.get("oaiServletUrl");
			invoke = invoke
					+ "?verb=GetRecord&metadataPrefix=oai_dc&identifier="
					+ srecordName;
			System.out.print("Richiesta OAI-PMH\n" + invoke);
			try {
				URL url = new URL(invoke);
				URLConnection con = url.openConnection();
				InputStream isOAI = con.getInputStream();
				XmlDocument xmlgetrecord=new XmlDocument(isOAI);
				return xmlgetrecord;
			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println("Execute ha causato un errore!!");
				System.err.println(invoke);
			}
//			return bufferOutputOAIServlet.toString();
		} else if (sDBName.equals("Web document")) {
			String invoke = "http://" + srecordName;
			System.out.print("Richiesta pagina web\n" + invoke);
//			TODO:Recuperare pagina web indicata da invoke
			String sresponse = "Costruzione pagina web che rimanda all'indirizzo <a href=\""
					+ invoke + "\">" + invoke + "</a>";
//			return sresponse;
		}
		// else if(sDBName.equals("?????")){
		// aggiungere il codice per recuperare record da altri archivi
		// }

		return null;
	}

	// Ricerca con swish-e
	// -f specifica il file indice
	// -H tipo di output. 0 -> nessuna intestazione, quindi il codice
	// per eliminare le righe che iniziano per # può essere eliminato
	// -w parole da cercare
	// -x defines the output format string
	// These are available for output when using the -x format.
	//
	// Name Type Contents
	// -------------- -------
	// ----------------------------------------------
	// swishreccount Integer Result record counter
	// swishtitle String Document title
	// swishrank Integer Result rank for this hit
	// swishdocpath String URL or filepath to document
	// swishdocsize Integer Document size in bytes
	// swishlastmodified Date Last modified date of document
	// swishdescription String Description of document
	// (see:StoreDescription)
	// swishdbfile String Path of swish database indexfile

	// paginazione
	// -m *number* (max results)
	//
	// While searching, this specifies the maximum number
	// of results to return. The default is to return all results.
	//
	// -b *number* (beginning result)
	//
	// Sets the begining search result to return (records
	// are numbered from 1).

	int iCAMPI_SWISHE = 12;

	/**
	 * Si occupa della ricerca tramite Swish-e Costruisce in modo opportuno la
	 * riga di comando Legge i risultati Genera il codice xml
	 */
	private XmlDocument findXml(HashMap<String, String> lmapTargetLookFor,
			int ilThisPage, int ilRecordForPage) {
		// Eseguo swish-e
		Process myProcess = null;

		String CARATTERESEPARAZIONE = "\t";
		String sLineaComando = "swish-e ";
		sLineaComando = sLineaComando + "-m " + ilRecordForPage + " -b "
				+ ((ilThisPage - 1) * ilRecordForPage + 1);
		// Posizione del file dell'indice
		sLineaComando = sLineaComando + " -f "
				+ (String) hashString.get("indexFile") + " ";
		// formatstring (extended output format)
		sLineaComando = sLineaComando
				+ " -x <swishrank>\\t<swishdocsize>\\t<swishdocpath>\\t<dc:title>\\t<dc:description>\\t<dc:subject>\\t<fonte>\\t<dc:source>\\t<dc:publisher>\\t<dc:creator>\\t<dc:type>\\t<identifier>\\t";

		// Leggo e rimuovo dalla mappa il valore di target, che indica
		// l'archivio su cui cercare
		String sltarget = lmapTargetLookFor.remove("target");

		// elementi della mappa - iteratori
		Set<String> SetTargetLook = lmapTargetLookFor.keySet();
		Collection<String> cTargetValue = lmapTargetLookFor.values();

		Iterator TargetLookIterator = SetTargetLook.iterator();
		Iterator TargetValueIterator = cTargetValue.iterator();

		// ********************************************************
		// Scansione di Map e creazione della linea di comando
		// ********************************************************

		sLineaComando = sLineaComando + " -w ";
		while (TargetLookIterator.hasNext()) {
			String name = (String) TargetLookIterator.next();
			String value = (String) TargetValueIterator.next();
			if (name.equals("simple")) {
				// RICERCA SEMPLICE: Combino tutti i metatag con
				// la stessa parola e l'operatore di congiunzione OR
				sLineaComando = sLineaComando + "dc:title=(" + value
						+ ") or dc:description=(" + value + ") or dc:subject=("
						+ value + ") or dc:creator=(" + value
						+ ") or dc:publisher=(" + value + ")";
			} else {
				if (!value.equals("")) {
					// //Il metadato fonte è un alias di dc:creator e
					// dc:publisher
					// Swish-e a quanto pare non consente la ricerca usando un
					// alias come chiave
					// quindi esplicito dc:creator e dc:publisher
					if (name.equals("fonte"))
						sLineaComando = sLineaComando + "dc:creator=(" + value
								+ ") or dc:publisher=(" + value + ")";
					else
						sLineaComando = sLineaComando + name + "=(" + value
								+ ")";
					// Se dopo non ci sono altri termini allora non scrivo
					// l'operatore
					// di congiunzione
					if (TargetLookIterator.hasNext() && !value.equals(""))
						sLineaComando = sLineaComando + " and ";
				}
			}
		}

//		TODO: and dc:type=( Web document) NON FUNZIONA
		
		if (sltarget!=null && !sltarget.equals(""))  {
			if (sltarget.equals("web"))
				sLineaComando = sLineaComando + " and dc:type=(\"Web document\")";
			else  if (sltarget.equals("dogi"))
				sLineaComando = sLineaComando + " and identifier=(oai:dogi.ittig.cnr.it*)";
		}
		// Ho costruito: sLineaComando
		// ********************************************************
		// ********************************************************

		// Per il debug - Stampo la linea di comando di swish-e
		if (bDEBUG)
			System.out.print("\nLinea di comando swish-e:\n" + sLineaComando
					+ "\n");

		// Ricerca con swish-e
		// -f specifica il file indice
		// -H tipo di output. 0 -> nessuna intestazione, quindi il codice
		// per eliminare le righe che iniziano per # può essere eliminato
		// -w parole da cercare
		// -x defines the output format string
		// These are available for output when using the -x format.
		//
		// Name Type Contents
		// -------------- -------
		// ----------------------------------------------
		// swishreccount Integer Result record counter
		// swishtitle String Document title
		// swishrank Integer Result rank for this hit
		// swishdocpath String URL or filepath to document
		// swishdocsize Integer Document size in bytes
		// swishlastmodified Date Last modified date of document
		// swishdescription String Description of document
		// (see:StoreDescription)
		// swishdbfile String Path of swish database indexfile

		// paginazione
		// -m *number* (max results)
		//
		// While searching, this specifies the maximum number
		// of results to return. The default is to return all results.
		//
		// -b *number* (beginning result)
		//
		// Sets the begining search result to return (records
		// are numbered from 1).

		// ********************************************************
		// Eseguo Swish-e
		// ********************************************************
		InputStream outputSwishe = null;
		try {
			// Ottengo un riferimento al mio ambiente di runtime
			Runtime myRuntime = Runtime.getRuntime();
			myProcess = myRuntime.exec(sLineaComando);
			// Ottengo il flusso di output di swish-e
			outputSwishe = myProcess.getInputStream();

		} catch (Exception excp) {
			System.out.print("\n" + excp.getMessage());
		}

		// Classe che implementa il thread per la lettura del buffer
		LettoreBufferThread tLettoreOutput = null;
		// Istanzio il thread che deve leggere l'output di swish-e
		tLettoreOutput = new LettoreBufferThread(myProcess, outputSwishe);
		try {
			// Attendo la fine del processo
			myProcess.waitFor();
			// Comunico al thread quando swish-e termina
			tLettoreOutput.stop();
			// Aspetto sino a quando il thread
			// comunica di aver finito di leggere l'output
			while (!tLettoreOutput.done()) {
				// Attende senza fare niente che il
				// thread esterno abbia finito di leggere
				// l'output di swish-e
			}
		} catch (InterruptedException excp) {
			System.out.print("\n" + excp.getLocalizedMessage());
		}
		// Creo uno StringBuffer
		StringBuffer bufferOutputSwishe = tLettoreOutput.getStreamSwishe();
		// ********************************************************
		// ********************************************************

		// *****************************************************************************
		// *** Output Cleaner ********** pulizia del testo
		// *****************************
		// *****************************************************************************
		int iFineindex = 0; // indice di fine della riga con #
		String stot = "";// indica il numero totale di record trovati
		// Elimino le righe che iniziano per #
		while (bufferOutputSwishe.substring(0, 1).equals("#")) {

			iFineindex = bufferOutputSwishe.indexOf(String.valueOf('\n'));
			if (iFineindex > 0)
				if (bufferOutputSwishe.substring(0, 1).equals("#")) {
					// Leggo il numero totale di risultati
					String tmp = bufferOutputSwishe.substring(2, 16);
					if (tmp.equals("Number of hits")) {
						iFineindex = bufferOutputSwishe.indexOf(String
								.valueOf('\n'));
						stot = bufferOutputSwishe.substring(18, iFineindex)
								.trim();
					}
					// Ho trovato gli indici di inizio e fine riga, ora la
					// // cancello
					bufferOutputSwishe.delete(0, iFineindex + 1);
				}
		}
		// Elimino l'ultimo punto e gli ultimi due ritorni a capo
		int itmp;
		for (int i = 0; i < 2; i++) {
			itmp = bufferOutputSwishe.lastIndexOf(String.valueOf('\n'));
			if (itmp != -1)
				bufferOutputSwishe.delete(itmp, itmp + 1);
		}
		itmp = bufferOutputSwishe.lastIndexOf(".");
		if (itmp != -1)
			bufferOutputSwishe.delete(itmp, itmp + 1);
		// *****************************************************************************
		// *****************************************************************************
		// *****************************************************************************
		ArrayList<String> asResult = swisheTokenizer(bufferOutputSwishe,
				CARATTERESEPARAZIONE);

		XmlDocument xml = null;

		// Aggiungo in prima posizione
		// Numero di record contenuti
		asResult.add(0, String.valueOf(asResult.size() / iCAMPI_SWISHE));

		// ******************
		// Metto nell'ultima posizione dell'array:

		if (stot == "")
			stot = "0";
		// - numero totale di risultati
		asResult.add(stot);
		// - pagina attuale
		asResult.add(String.valueOf(ilThisPage));
		// record per pagina - usato per inserire nel flusso xml-->xsl il link
		// alle altre pagine
		asResult.add(String.valueOf(ilRecordForPage));
		// - start
		int istart = (ilThisPage - 1) * ilRecordForPage + 1;
		// - end
		int iend = istart + ilRecordForPage;
		int itot;
		itot = Integer.parseInt(stot);

		if (istart > itot)
			istart = 0;

		if (istart == 0)
			iend = 0;
		else if (iend > itot)
			iend = itot;

		// start
		asResult.add(String.valueOf(istart));
		// end
		asResult.add(String.valueOf(iend));
		// ******************
		// archivio target
		asResult.add(String.valueOf(sltarget));
		// ******************

		// ****************************************
		// *********indicazione metadati cercati***
		// ****************************************
		// Scansione della mappa che contiene i nomi ed i valori dei
		// metadati da cercare
		Iterator TargetLookIterator2 = SetTargetLook.iterator();
		Iterator TargetValueIterator2 = cTargetValue.iterator();

		while (TargetLookIterator2.hasNext()) {
			String name = (String) TargetLookIterator2.next();
			String value = (String) TargetValueIterator2.next();
			asResult.add(name);
			asResult.add(value);
			// ****************************************
			// ****************************************
			// ****************************************

			// ******************************************************
			// ******************************************************
			// CAMPI (nell'ordine)

			// swishrank
			// dc:title
			// swishdocpath
			// dc:description
			// swishdocsize
			// dc:subject
			// fonte
			// dc:source
			// dc:publisher
			// dc:creator
			// dc:type
			// dc:identifier
			// ******************************************************
			// ******************************************************
		}
		xml = array2xml(asResult);

		if (bDEBUG)
			System.out.println(XMLTool.showElement(xml.getRootElement()));

		return xml;

	}

	private XmlDocument array2xml(ArrayList<String> asArraySwishe) {
		// TODO:Traduzione dell'array in XML
		XmlDocument xml = new XmlDocument();

		// ******************************************************
		// ******************************************************
		// CAMPI (nell'ordine)

		// swishrank
		// swishdocsize
		// dc:title
		// swishdocpath
		// dc:description
		// dc:subject
		// fonte
		// dc:publisher
		// dc:creator
		// dc:type
		// dc:identifier
		// ******************************************************
		// ******************************************************

		Element tmpElement, rootElement, eDCMetadata, eRecord;
		Document root;

		root = xml.getRoot();
		// SWISH-E
		rootElement = root.createElement("SWISH-E");
		root.appendChild(rootElement);

		// ***************************************
		// ********** COSTRUZIONE RISPOSTA *******
		// ***************************************
		int iNumeroRecord = Integer.parseInt(asArraySwishe.remove(0));

		// while ((asArraySwishe.size()-5)>0) {
		for (int i = 0; i < iNumeroRecord; i++) {
			// scansione risultati e paginazione
			// TODO dividere il testo in token ed evidenziare le parole
			// cercate

			eRecord = root.createElement("record");
			rootElement.appendChild(eRecord);

			// swishrank
			tmpElement = XMLTool2.createNodeAndText(root, "swishrank",
					asArraySwishe.remove(0));
			eRecord.appendChild(tmpElement);
			// swishdocsize
			tmpElement = XMLTool2.createNodeAndText(root, "swishdocsize",
					asArraySwishe.remove(0));
			eRecord.appendChild(tmpElement);
			// swishdocpath
			tmpElement = XMLTool2.createNodeAndText(root, "swishdocpath",
					asArraySwishe.remove(0));
			eRecord.appendChild(tmpElement);
			
			// *********************************
			// ****** oai_dc:dc e attributi ****
			// *********************************
			eDCMetadata = root.createElement("metadata");
			// Creazione attributi
			addDCNameSpace(eDCMetadata);
			// *********************************
			eRecord.appendChild(eDCMetadata);
			// *********************************

			// Titolo
			tmpElement = XMLTool2.createNodeAndText(root, "dc:title",
					asArraySwishe.remove(0));
			eDCMetadata.appendChild(tmpElement);

			// dc:description
			tmpElement = XMLTool2.createNodeAndText(root, "dc:description",
					asArraySwishe.remove(0));
			eDCMetadata.appendChild(tmpElement);

			// dc:subject
			tmpElement = XMLTool2.createNodeAndText(root, "dc:subject",
					asArraySwishe.remove(0));
			eDCMetadata.appendChild(tmpElement);
			// fonte
			tmpElement = XMLTool2.createNodeAndText(root, "dc:source",
					asArraySwishe.remove(0));
			eDCMetadata.appendChild(tmpElement);
			// dc:source
			tmpElement = XMLTool2.createNodeAndText(root, "dc:source",
					asArraySwishe.remove(0));
			eDCMetadata.appendChild(tmpElement);
			// dc:publisher
			tmpElement = XMLTool2.createNodeAndText(root, "dc:publisher",
					asArraySwishe.remove(0));
			eDCMetadata.appendChild(tmpElement);
			// dc:creator
			tmpElement = XMLTool2.createNodeAndText(root, "dc:creator",
					asArraySwishe.remove(0));
			eDCMetadata.appendChild(tmpElement);

			// I record web hanno Web document nel metatag dc:type
			// I record dogi invece hanno la propria classificazione
			// dc:type
			String sdc_type = asArraySwishe.remove(0);
			tmpElement = XMLTool2.createNodeAndText(root, "dc:type", sdc_type);
			eDCMetadata.appendChild(tmpElement);

			// dc:identifier
			String sdc_identifier = asArraySwishe.remove(0).trim();
			tmpElement = XMLTool2.createNodeAndText(root, "dc:identifier",
					sdc_identifier);
			eDCMetadata.appendChild(tmpElement);

			// archivio provenienza (viene messo fuori da metadata)
			tmpElement = XMLTool2.createNodeAndText(root, "provenienza",
					getDocumentType(sdc_type, sdc_identifier));
			eRecord.appendChild(tmpElement);
		}

		// Elementi aggiunti in fondo all'array prima della chiamata di
		// questo metodo
		// Numero totale record trovati
		String sNumeroTotaleRecord = asArraySwishe.remove(0);
		int iNumeroTotaleRecord = Integer.parseInt(sNumeroTotaleRecord);
		tmpElement = XMLTool2.createNodeAndText(root, "recordsNumber",
				sNumeroTotaleRecord);
		rootElement.appendChild(tmpElement);

		// Pagina
		String sthispage = asArraySwishe.remove(0);
		int ithispage = Integer.parseInt(sthispage);
		tmpElement = XMLTool2.createNodeAndText(root, "pageNumber", sthispage);
		rootElement.appendChild(tmpElement);

		// Record per pagina - uso il valore per stabilire il numero totale di
		// pagine e scrivere
		// nel flusso xml i link per la paginazione
		int irecordforpage = Integer.parseInt(asArraySwishe.remove(0));
		int itotalpages;
		// operazione di modulo
		if ((iNumeroTotaleRecord % irecordforpage) == 0)
			itotalpages = (iNumeroTotaleRecord / irecordforpage);
		else
			itotalpages = (iNumeroTotaleRecord / irecordforpage) + 1;

		// Elemento di inizio
		tmpElement = XMLTool2.createNodeAndText(root, "start", asArraySwishe
				.remove(0));
		rootElement.appendChild(tmpElement);

		// Elemento di fine
		tmpElement = XMLTool2.createNodeAndText(root, "end", asArraySwishe
				.remove(0));
		rootElement.appendChild(tmpElement);

		// Archivio target della ricerca
		String sTarget = asArraySwishe.remove(0);
		tmpElement = XMLTool2.createNodeAndText(root, "target", sTarget);
		rootElement.appendChild(tmpElement);

		ArrayList<String> aslookfor = new ArrayList<String>();

		while (!asArraySwishe.isEmpty()) {
			// Scrivo i metadati cercati nella chiave lookfor
			Element elookfor = root.createElement("lookfor");
			rootElement.appendChild(elookfor);
			// *********************************
			String name = asArraySwishe.remove(0);
			String value = asArraySwishe.remove(0);

			// Inserisco nell'array le parole cercate.
			// Le utilizzo dopo per costruire i link da inserire nell'xml
			aslookfor.add(name);
			aslookfor.add(value);

			// Traduco i campi ricercati per visualizzarli con termini più
			// familiari
			if (name.equals("simple")) {
				name = "";
			} else if (name.equals("dc:title")) {
				name = "Titolo";
			} else if (name.equals("dc:description")) {
				name = "Descrizione";
			} else if (name.equals("dc:subject")) {
				name = "Soggetto";
			} else if (name.equals("dc:creator")) {
				name = "Autore";
			} else if (name.equals("dc:publisher")) {
				name = "Editore";
			}
			// *********************************
			tmpElement = XMLTool2.createNodeAndText(root, "name", name);
			elookfor.appendChild(tmpElement);
			tmpElement = XMLTool2.createNodeAndText(root, "value", value);
			elookfor.appendChild(tmpElement);
		}

		// ***********************************
		// **** PAGINAZIONE ****
		// ***********************************
		// link alle pagine successive e precedenti
		Element ePages = root.createElement("pages");
		rootElement.appendChild(ePages);
		// *********************************
		int ipagestart = 0;
		int ipageend = 0;
		if (itotalpages > 0) {

			if ((ithispage - 3) <= 0)
				ipagestart = 1;
			else
				ipagestart = ithispage - 3;

			ipageend = ipagestart + 10;// visualizzo l'indice di dieci pagine

			if (ipageend > itotalpages) {
				ipageend = itotalpages + 1;
				if ((ipageend - 10) < 1)
					ipagestart = 1;
				else
					ipagestart = ipageend - 10;
			}
		}
		// *********************************
		String slinkpart = "?";
		while (aslookfor.size() != 0) {
			String name = aslookfor.remove(0);
			if (name == "")
				name = "simple";
			slinkpart = slinkpart + name + "=" + aslookfor.remove(0) + "&amp;";
		}

		// Se c'è una sola pagina il ciclo non va eseguito
		for (int i = ipagestart; i < ipageend; i++) {
			Element ePage = root.createElement("page");
			// numero di pagina
			tmpElement = XMLTool2.createNodeAndText(root, "number", String
					.valueOf(i));
			ePage.appendChild(tmpElement);
			// link alla pagina
			String slink = (String) hashString.get("servletPath") + slinkpart
					+ "act=" + i;
			if (sTarget != "")
				slink = slink + "&amp;target=" + sTarget;

			tmpElement = XMLTool2.createNodeAndText(root, "link", slink);
			ePage.appendChild(tmpElement);

			ePages.appendChild(ePage);
		}
		// ***********************************
		// ***********************************

		// ***********************************
		// Url della servlet e delle immagini
		Element eUrl = root.createElement("url");
		rootElement.appendChild(eUrl);
		tmpElement = XMLTool2.createNodeAndText(root, "servlet",
				(String) hashString.get("servletPath"));
		eUrl.appendChild(tmpElement);
		tmpElement = XMLTool2.createNodeAndText(root, "images",
				(String) hashString.get("imgPath"));
		eUrl.appendChild(tmpElement);
		// ***********************************
		// ***********************************

		return xml;
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

	/**
	 * 
	 * @param bufferOutputSwishe
	 *            Buffer che contiene la risposta di swish-e
	 * @param carSeparatore
	 *            Carattere che separa i campi
	 * @return Restituisce un ArrayList con i campi dei record di risposta.
	 */
	private ArrayList<String> swisheTokenizer(StringBuffer bufferOutputSwishe,
			String carSeparatore) {

		StringTokenizer myTokenizer = new StringTokenizer(bufferOutputSwishe
				.toString(), carSeparatore, true);

		ArrayList<String> asResult = new ArrayList<String>();

		// Scansione tokenizer
		// Anche il carattere separazione viene restituito come token.
		// Quindi leggo un token si ed uno no
		String name = null;
		String tmpname = null;

		if (myTokenizer.hasMoreTokens()) {
			name = myTokenizer.nextToken();
			// Se la prima lettura del token è la
			// stringa no result allora interrompo
			// la lettura
			if (name.equals("err: no results")) {
				asResult.clear();
			} else {
				while (myTokenizer.hasMoreTokens()) {
					// Se ci sono due token uguali al carattere di
					// separazione accostati allora inserisco un campo vuoto
					// "(null)"
					tmpname = myTokenizer.nextToken();

					if (name.equals(carSeparatore)) {
						if (tmpname.equals(carSeparatore)) {
							asResult.add("(null)"); // Scrivo (null) come
							// stringa
							// perchè in altre occasioni
							// swish-e ha restituito (null)
							// come stringa
							// if (bDEBUG)
							// System.out.print("\n(null)");
						}
						name = tmpname;

					} else {
						// if (bDEBUG)
						// System.out.print("\n" + name);
						asResult.add(name);
						name = tmpname;
					}
				}
			}
		}
		return asResult;
	}

	/**
	 * Serve per determinare la provenienza del record.
	 */
	private String getDocumentType(String stipoDoc_dctype,
			String stipoDoc_identifier) {
		String sTmp = null;

		if (stipoDoc_dctype.equals("Web document"))
			sTmp = "Web document";
		else if (stipoDoc_identifier.contains("dogi"))
			sTmp = "DoGi";
		else
			sTmp = "(null)";

		return sTmp;

	}

	private String prepareString(String sWordToFind, int iModality) {

		// Aggiungo uno spazio alla fine del testo, per eliminare correttamente
		// le parole indesiderate alla fine della stringa
		sWordToFind = " " + sWordToFind + " ";

		String sTestoDaEliminare[] = new String[3];
		sTestoDaEliminare[0] = "'";
		// sTestoDaEliminare[1] = " or ";
		// sTestoDaEliminare[2] = " and ";

		for (int i = 0; i < sTestoDaEliminare.length; i++) {
			if (sTestoDaEliminare[i] != null)
				sWordToFind = sWordToFind.replaceAll(sTestoDaEliminare[i], " ");
		}
		return sWordToFind.trim();
	}

	public String getServletInfo() {
		return "Ittig DoGi Search Version 1.0 (2005)";
	}
}
