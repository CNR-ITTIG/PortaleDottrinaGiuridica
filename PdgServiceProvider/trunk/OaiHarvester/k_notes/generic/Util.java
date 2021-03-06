//         ============================ 
//        |  Autore: Lomartire Cosimo  |
//        |  Data: 19.01.2004          |
//        |  Firenze                   |
//         ============================ 

package k_notes.generic;

import java.io.*;
import java.util.*;
import java.net.*;

import org.w3c.dom.*;
import k_notes.xml.XmlDocument;

public class Util {

	public static Hashtable propert(String file) {

		Hashtable hash = new Hashtable();

		try {
			Properties props = new Properties();
			FileInputStream istream = new FileInputStream(file);
			props.load(istream);
			Enumeration enum = props.keys();
			String name = "";
			while (enum.hasMoreElements()) {
				name = (String) enum.nextElement();
				hash.put(name, props.getProperty(name));
			}
			return hash;
		} catch (IOException e) {
			System.err.println("Impossibile trovare il file " + file);
			return null;
		}
	}

	public static Properties prop(String file) {

		try {
			Properties props = new Properties();
			FileInputStream istream = new FileInputStream(file);
			props.load(istream);
			return props;
		} catch (IOException e) {
			System.err.println("Impossibile trovare il file " + file);
			return null;
		}
	}

	public static Vector tokenize(String str) {
		Vector vec = new Vector();
		StringTokenizer st = new StringTokenizer(str, ",");
		while (st.hasMoreTokens()) {
			vec.addElement(st.nextToken().trim());
		}
		return vec;
	}

	public static void writeTo(String file, String skey, String sData) {

		XmlDocument tmp = new XmlDocument(file);
		Node n = ((tmp.getRoot()).getElementsByTagName(skey)).item(0).getChildNodes().item(0);;
		if (n != null) {
			n.setNodeValue(sData);
			tmp.saveDocument(file);
		} else{
			System.err.println("Errore nell'accesso al file \""+file+"\"\nE' possibile che la data di fine harvest non sia stata scritta.\nE' possibile correggere il file manualmente inserendo la data nel campo lastHarvesting oppure inserire 0 e ripetere l'harvest");
		}
		
	}

	public static String readTo(String file, String skey) {

		XmlDocument tmp = new XmlDocument(file);
		Node n = ((tmp.getRoot()).getElementsByTagName(skey)).item(0);

		if (n == null) {
			Node r = ((tmp.getRoot()).getElementsByTagName("Document")).item(0);
			Element el = tmp.getRoot().createElement(skey);
			Node el2 = tmp.getRoot().createTextNode("0");
			el.appendChild(el2);
			r.appendChild(el);
			tmp.saveDocument(file);
			return null;
		}

		n = ((tmp.getRoot()).getElementsByTagName(skey)).item(0)
				.getChildNodes().item(0);
		return (n != null && n.getNodeValue() != "0" ? n.getNodeValue() : null);
	}

	public static Node importNode(Document d, Node n, boolean deep) {
		Node r = cloneNode(d, n);
		if (deep) {
			NodeList nl = n.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node n1 = importNode(d, nl.item(i), deep);
				r.appendChild(n1);
			}
		}
		return r;
	}

	public static Node cloneNode(Document d, Node n) {
		Node r = null;
		switch (n.getNodeType()) {
		case Node.TEXT_NODE:
			r = d.createTextNode(((Text) n).getData());
			break;
		case Node.CDATA_SECTION_NODE:
			r = d.createCDATASection(((CDATASection) n).getData());
			break;
		case Node.ELEMENT_NODE:
			r = d.createElement(((Element) n).getTagName());
			NamedNodeMap map = n.getAttributes();
			for (int i = 0; i < map.getLength(); i++) {
				((Element) r).setAttribute(((Attr) map.item(i)).getName(),
						((Attr) map.item(i)).getValue());
			}
			break;
		}
		return r;
	}

	public static XmlDocument execute(String exec, String attrib)
			throws Exception {
		System.out.println("Ingresso metodo execute");
		URL url = new URL(exec);
		URLConnection con = url.openConnection();
		System.out
				.println("Connessione url eseguita, esecuzione getInputStream");
		XmlDocument xml = new XmlDocument(con.getInputStream());
		System.out.println("con.getInputStream eseguito");
		return xml;

	}

}