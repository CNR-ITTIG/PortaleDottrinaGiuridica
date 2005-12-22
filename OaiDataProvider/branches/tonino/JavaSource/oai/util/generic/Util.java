
//         ============================ 
//        |  Autore: Lomartire Cosimo  |
//        |  Data: 19.01.2004          |
//        |  Firenze                   |
//         ============================ 

package oai.util.generic;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import oai.util.xml.XmlDocument;

public class Util {
   
    public static Hashtable propert(String file){

        Hashtable hash=new Hashtable();
        
        try{
            Properties props=new Properties();
            FileInputStream istream=new FileInputStream(file);
            props.load(istream);
            Enumeration enum=props.keys();            
            String name="";
            while(enum.hasMoreElements()) {
                name=(String)enum.nextElement();
                hash.put(name, props.getProperty(name));
	        }
            return hash;
        }catch(IOException e){
            System.err.println("Impossibile trovare il file " + file );
		    return null;
		}
    }
   
    public static Properties prop(String file){
        
        try{
            Properties props=new Properties();
            FileInputStream istream=new FileInputStream(file);
            props.load(istream);
            return props;
        }catch(IOException e){
            System.err.println("Impossibile trovare il file " + file );
		    return null;
		}
    }
   
    
    public static Vector tokenize(String str){
        Vector vec=new Vector();
        StringTokenizer st = new StringTokenizer(str,",");
        while (st.hasMoreTokens()) {
            vec.addElement(st.nextToken().trim());            
        }
        return vec;
    }
    
       
    public static void writeTo(String file, String ip, String verb, int value){
        
        XmlDocument tmp=new XmlDocument(file);
        NodeList nl=((tmp.getRoot()).getElementsByTagName("a"+ip)).item(0).getChildNodes();
        Node n=null;
        for (int i=0;i<nl.getLength();i++){
            n=nl.item(i);
            if (verb.compareTo(n.getNodeName())==0){
                (n.getFirstChild()).setNodeValue((new Long(value)).toString());                          
            }
        }
        tmp.saveDocument(file);
    }
    
    public static String readTo(String file, String ip, String verb){  
        
        XmlDocument tmp=new XmlDocument(file);
        NodeList nl=(tmp.getRoot()).getElementsByTagName("a"+ip);
        if (nl.getLength()==0){
            Node r=((tmp.getRoot()).getElementsByTagName("Document")).item(0);
            Element el=tmp.getRoot().createElement("a"+ip);
            Element el1=tmp.getRoot().createElement("listIdentifiers");
            Element el2=tmp.getRoot().createElement("listRecords");
            el.appendChild(el1);el.appendChild(el2);
            Node el3=tmp.getRoot().createTextNode("1");
            Node el4=tmp.getRoot().createTextNode("1");
            el1.appendChild(el3);el2.appendChild(el4);
            r.appendChild(el);
            tmp.saveDocument(file);
            return "1";
        }
        
        Node n=((tmp.getRoot()).getElementsByTagName("a"+ip)).item(0);
        NodeList nodeL=n.getChildNodes();
        for (int i=0;i<nodeL.getLength();i++){
            n=nodeL.item(i);
            if (verb.compareTo(n.getNodeName())==0){
                return n.getFirstChild().getNodeValue();            
            }
        }
        return "1";
        
    }
    
    public static Node importNode(Document d, Node n, boolean deep) {
	Node r=cloneNode(d,n);
	if(deep) {
	    NodeList nl=n.getChildNodes();
	    for(int i=0;i<nl.getLength();i++) {
		Node n1=importNode(d,nl.item(i),deep);
		r.appendChild(n1);
	    }
	}
	return r;
    }
    
    public static Node cloneNode(Document d,Node n) {
	    Node r=null;
	    switch(n.getNodeType()) {
	    case Node.TEXT_NODE: 
	        r = d.createTextNode(((Text)n).getData());
	        break;
	    case Node.CDATA_SECTION_NODE:
	        r = d.createCDATASection(((CDATASection)n).getData());
	        break;
	    case Node.ELEMENT_NODE:
	        r = d.createElement(((Element)n).getTagName());
	        NamedNodeMap map=n.getAttributes();
	        for(int i=0;i<map.getLength();i++) {
		    ((Element)r).setAttribute(((Attr)map.item(i)).getName(),
					    ((Attr)map.item(i)).getValue());
	        }
	        break;	    
	    }
	    return r;
    }   
        
}