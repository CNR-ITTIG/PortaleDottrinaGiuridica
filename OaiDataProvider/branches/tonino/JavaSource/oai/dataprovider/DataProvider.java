package oai.dataprovider;

import java.util.*;
import java.text.*;

import org.w3c.dom.*;

import oai.exception.*;
import oai.util.token.*;
import oai.util.generic.Util;

/**
 * Data provider class
 */

public class DataProvider implements DataProviderInterface{
    RecordFactory rf=null;
    int cursor=0;
    Document doc=null;
    /**
     * Constructor.
     * @param rf
     * @param doc
     */

    public DataProvider(RecordFactory rf,Document doc){
        this.rf=rf;
	this.doc=doc;
    }
    /**
     * Constructor
     * @param rf
     */

    public DataProvider (RecordFactory rf){
	this.rf=rf;
    }
    /**
     *
     * @param doc
     */

    public void setDocument(Document doc){
        this.doc=doc;
    } 

    /**
     *
     * @return Element
     */
     
    //va visto meglio 
    public Element identify(){
       	Element root = (Element)Util.importNode(doc,rf.getIdentify(),true);
       	doc.appendChild (root);
       	       	          
        return root;
    }
           
    /**
     *
     * @return Element
     */
    public Element listSets() throws OAIError{
        
        Vector v=rf.getSets(cursor,Token.TOKENSIZE);
       	
       	if (v==null||v.size()<1){
            throw new NoSetHierarchy("This repository does not support sets");
	    }
       	
       	Element root = doc.createElement ("ListSets");
        Element curr,parent;
  
             
        doc.appendChild (root);

        for (Enumeration enum=v.elements(); enum.hasMoreElements() ;) {
            curr=doc.createElement("set");
            root.appendChild(curr);
            parent=curr;

            String name=(String)enum.nextElement();
            curr=doc.createElement("setSpec");
            curr.appendChild(doc.createTextNode(name));
            parent.appendChild(curr);

            curr=doc.createElement("setName");
            curr.appendChild(doc.createTextNode(name));
            parent.appendChild(curr);
        }

        if (v.size()==Token.TOKENSIZE){
            SetToken token=new SetToken();
            token.setCursor(cursor+Token.TOKENSIZE);
            curr=doc.createElement("resumptionToken");
            curr.appendChild(doc.createTextNode(TokenModem.encode(token)));
            root.appendChild(curr);
        }
// reset cursor;
        cursor=0;
        return root;
    }

    /**
     *
     * @param resumptionToken
     * @return Element
     */
    public Element listSets(String resumptionToken) throws OAIError{
        Token token=TokenModem.decode(resumptionToken);
        if (!(token.getType()==Token.SET_TOKEN)){
            cursor=token.getCursor();
            return listSets();
        }
        else
            throw new BadResumptionToken("malformat");


    }
    /**
     *
     * @param id
     * @return Element
     */

    public Element listMetadataFormats(String id) throws OAIError{
                
        Node record=null;
        
        if (id!=null){
            try{
                StringTokenizer st = new StringTokenizer(id,"/");
                st.nextToken();
                record=rf.getRecord(st.nextToken());
                if (record==null)
		    throw new IdDoesNotExist("id not exist");
            }catch(Exception ex) 
                {throw new IdDoesNotExist("id not exist");}
	    }

    	Element root = doc.createElement ("ListMetadataFormats");
	
        Element curr,parent;
        Text text;
       	doc.appendChild (root);

        if ((record!=null)||(id==null)){
            curr=doc.createElement("metadataFormat");
            root.appendChild (curr);
            parent=curr;

            curr=doc.createElement("metadataPrefix");
            curr.appendChild(doc.createTextNode("oai_dc"));
            parent.appendChild(curr);

            curr=doc.createElement("schema");
            curr.appendChild(doc.createTextNode("http://www.openarchives.org/OAI/2.0/oai_dc.xsd"));
            parent.appendChild(curr);
	    
	    curr=doc.createElement("metadataNamespace");
            curr.appendChild(doc.createTextNode("http://www.openarchives.org/OAI/2.0/oai_dc/"));
            parent.appendChild(curr);
	   
        }
	else
	    throw new IdDoesNotExist("id not exist");
        return root;
    }

    /**
     *
     * @param fileafter
     * @param filebefore
     * @param setspec
     * @param metaformat
     * @return Element
     */
    public Element listIdentifiers(String fileafter,String filebefore,String setspec,String metaformat) throws OAIError{
	
        if ((fileafter!=null)&&(filebefore!=null))
            if (fileafter.compareTo(filebefore)>0) throw new NoRecordsMatch("not items match");
        
        if ((fileafter!=null)&&(!fileafter.trim().equals("")))
            fileafter=checkValidaty(fileafter);
        else
            fileafter=null;

        if ((filebefore!=null)&&(!filebefore.trim().equals("")))
            filebefore=checkValidaty(filebefore);
        else
            filebefore=null;

	    if (metaformat==null)
            throw new BadArgument("metadataPrefix error");

        if ((setspec==null)||(setspec.trim().equals("")))
            setspec=null;
	
        if ((metaformat!=null)&&(!metaformat.trim().equals(""))&&(!metaformat.equals("oai_dc")))
            throw new CannotDisseminateFormat("can not disseminate "+metaformat);
	
      	
        Vector v=rf.getIdentifiers(fileafter,filebefore,setspec,cursor,Token.TOKENSIZE );
        if ((v==null) ||(v.isEmpty()))
            throw new NoRecordsMatch("not items match");

        Element root = doc.createElement ("ListIdentifiers");
          
        Element curr;
      	doc.appendChild (root);
      	
        for (Enumeration e = v.elements() ; e.hasMoreElements() ;) {
            root.appendChild(Util.importNode(doc,(Node)e.nextElement(),true));
        }
        
        if (Token.TOKENSIZE==v.size()){
            IdentifierToken token=new IdentifierToken();
            token.setCursor (cursor+Token.TOKENSIZE);
//            token.setCursor (rf.getListIdentifiers());
            token.from=fileafter;
            token.until =filebefore;
            token.metadataPrefix=metaformat;
            token.set=setspec;
            curr=doc.createElement("resumptionToken");
            curr.appendChild(doc.createTextNode(TokenModem.encode (token)));
            root.appendChild(curr);
        }

        //reset cursor
        cursor=0;
        return root;

    }
    
    private Element listIdentifiers(IdentifierToken token) throws OAIError{
	      
    	Vector v=null;
    	if(token.from==null&&token.until==null)
    	    	v=rf.getIdentifiers(token.getCursor(), Token.TOKENSIZE);
    	else{
    		v=rf.getIdentifiers(token.from,token.until,token.set,token.getCursor(),token.TOKENSIZE);
    	}
    		
        if ((v==null) ||(v.isEmpty()))
            throw new NoRecordsMatch("not items match");

        Element root = doc.createElement ("ListIdentifiers");
          
        Element curr;
      	doc.appendChild (root);
      	
        for (Enumeration e = v.elements() ; e.hasMoreElements() ;) {
            root.appendChild(Util.importNode(doc,(Node)e.nextElement(),true));
        }
        
        if (Token.TOKENSIZE==v.size()){
//            IdentifierToken token=new IdentifierToken();
            token.setCursor (token.getCursor()+Token.TOKENSIZE);
            curr=doc.createElement("resumptionToken");
            curr.appendChild(doc.createTextNode(TokenModem.encode (token)));
            root.appendChild(curr);
        }

        //reset cursor
        cursor=0;
        return root;

    }


    /**
     *
     * @param resumptionToken
     * @return Element
     */
    public Element listIdentifiers(String resumptionToken) throws OAIError{
        IdentifierToken token=(IdentifierToken)(TokenModem.decode(resumptionToken));
        if (token.getType()==Token.IDENTIFIER_TOKEN){
            return listIdentifiers(token);
        }
        else
            throw new BadResumptionToken("malformat");

    }


    /**
     *
     * @param fileafter
     * @param filebefore
     * @param setspec
     * @param metaformat
     * @return Element
     */
    public Element listRecords(String fileafter,String filebefore, String setspec, String metaformat) throws OAIError {

        if ((fileafter!=null)&&(filebefore!=null))
            if (fileafter.compareTo(filebefore)>0) throw new NoRecordsMatch("not items match");        
        
        if ((fileafter!=null)&&(!fileafter.trim().equals("")))
            fileafter=checkValidaty(fileafter);
        else
            fileafter=null;

        if ((filebefore!=null)&&(!filebefore.trim().equals("")))
            filebefore=checkValidaty(filebefore);
        else
            filebefore=null;

        if (metaformat==null)
            throw new BadArgument("metadataPrefix error");
	
	    if (!metaformat.equals("oai_dc"))
	        throw new CannotDisseminateFormat("can not disseminate "+metaformat);


        Vector v=rf.getRecords(fileafter,filebefore,setspec,cursor,Token.TOKENSIZE);
	    if ((v==null)||(v.isEmpty()))
	        throw new NoRecordsMatch("not items match");

	    Element root = doc.createElement ("ListRecords");
     
        Element curr;
      	doc.appendChild (root);
      	
        for (Enumeration e = v.elements() ; e.hasMoreElements() ;) {
            root.appendChild(Util.importNode(doc,(Node)e.nextElement(),true));
        }

        if (Token.TOKENSIZE==v.size()){
            RecordsToken token=new RecordsToken();
            token.setCursor (cursor+Token.TOKENSIZE);
//            token.setCursor (rf.getListRecords());
            token.from=fileafter;
            token.until =filebefore;
            token.set=setspec;
            token.metadataPrefix=metaformat;
            curr=doc.createElement("resumptionToken");
            curr.appendChild(doc.createTextNode(TokenModem.encode (token)));
            root.appendChild(curr);
        }

        //reset cursor
        cursor=0;

        return root;
    }
    
    private Element listRecords(RecordsToken token) throws OAIError{
	             	
    	Vector v=null;
        if(token.from==null&&token.until==null)
	    	v=rf.getRecords(token.getCursor(), Token.TOKENSIZE);
	else{
		v=rf.getRecords(token.from,token.until,token.set,token.getCursor(),token.TOKENSIZE);
	}
        
        
        if ((v==null) ||(v.isEmpty()))
            throw new NoRecordsMatch("not items match");

        Element root = doc.createElement ("ListRecords");
          
        Element curr;
      	doc.appendChild (root);
      	
        for (Enumeration e = v.elements() ; e.hasMoreElements() ;) {
            root.appendChild(Util.importNode(doc,(Node)e.nextElement(),true));
        }
        
        if (Token.TOKENSIZE==v.size()){
//            RecordsToken token=new RecordsToken();
            token.setCursor (token.getCursor()+Token.TOKENSIZE);
            curr=doc.createElement("resumptionToken");
            curr.appendChild(doc.createTextNode(TokenModem.encode (token)));
            root.appendChild(curr);
        }

        //reset cursor
        cursor=0;
        return root;

    }

    /**
     *
     * @param resumptionToken
     * @return Element
     */

    public Element listRecords(String resumptionToken) throws OAIError{
        RecordsToken token=(RecordsToken)(TokenModem.decode(resumptionToken));

	if (token.getType()==Token.RECORDS_TOKEN){
            return listRecords(token);
        }
        else
            throw new BadResumptionToken("malformat");
      
    }


    /**
     *
     * @param handle
     * @param metaformat
     * @return Element
     */
    public Element getRecord(String handle,String metaformat) throws OAIError{
                     
        if ((metaformat==null)||(handle==null))
            throw new BadArgument("Missing Mandatory Field");

	    if (!metaformat.equals("oai_dc"))
	  	    throw new CannotDisseminateFormat("can not disseminate "+metaformat);
		
        StringTokenizer st = new StringTokenizer(handle,"/");
        st.nextToken();
        Node record=null;
        try{		    
	        record=rf.getRecord(st.nextToken());
	        //record=rf.getRecord(handle);
	    }catch(Exception ex){
	        ex.printStackTrace();
	    }
	    
	    if (record==null)
	        throw new IdDoesNotExist("id not exist");
       	
       	Element root = doc.createElement ("GetRecord");
        doc.appendChild (root);
	    root.appendChild(Util.importNode(doc,record,true));
	    return root;
    }

    private String checkValidaty(String day) throws OAIError{
	
	
	    SimpleDateFormat formatter;

    	
	    formatter= new SimpleDateFormat ("yyyy-MM-dd");
	    java.util.Date date;

        formatter.setLenient (true);
        try{
            date=formatter.parse(day);
        }
        catch(Exception e){
            throw  new BadArgument("date format error");
        }

	    if (day.length()>10)
	        throw new BadArgument("bad Granularity for "+day);
    	
	    return formatter.format(date);
	

    }


    private void addDCNameSpace(Element e){
	
        e.setAttribute ("xmlns:dc", "http://purl.org/dc/elements/1.1/");
	e.setAttribute("xmlns:oai_dc","http://www.openarchives.org/OAI/2.0/oai_dc/");
        e.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
        e.setAttribute("xsi:schemaLocation","http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd");
    }




}


