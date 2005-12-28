package searchInterface.util.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLTool2 {
	/**
     * Crea un nuovo nodo con relativo nodo di testo
     */
	public static Element createNodeAndText(Document root, String selementID,	String sTesto) {
		Node lnewTextNode;
		Element lnewDCElement;

		// Creo l'elemento identifier ed il nodo di testo
		lnewTextNode = root.createTextNode(sTesto.trim());
		lnewDCElement = root.createElement(selementID.trim());
		// Collego gli elementi creati all'header
		// header -> identifier
		lnewDCElement.appendChild(lnewTextNode);
		return lnewDCElement;
	}
}
