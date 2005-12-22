package oai.servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.*;
import java.net.*;

import org.w3c.dom.*;

import oai.exception.*;
import oai.util.xml.*;
import oai.util.generic.*;
import oai.dataprovider.*;
    
public class OAIServlet implements Servlet{

    private Hashtable hashString=new Hashtable();
    private ServletConfig servlet_config;
    
    protected RecordFactory rf;
    protected DataProvider dp;
    
           
    public void init(ServletConfig config) throws ServletException {
        String properties="";
        servlet_config=config;
                
        System.err.println(new Date()+" - OAIServlet - Init......");        
        System.err.println("=======================================================");
        System.err.println("            "+getServletInfo());
        System.err.println("=======================================================");        
                             
        try{
            if(config.getInitParameter("properties") == null) {
                throw new GenericException("Parametro di Properties non trovato!!");
            } else {	    
                properties = config.getInitParameter("properties");
	        }
	    }catch (GenericException ex){
	        System.err.println("Non è stato impostato il parametro iniziale Properties!");
	        System.exit(1);
	    }
	    this.hashString=Util.propert(properties);
    	    
        System.err.println("Inizializzazione completata.");
        System.err.println("=======================================================");
    }
   
    public void service(ServletRequest Req, ServletResponse Res) 
            throws ServletException, IOException {
        HttpServletRequest request=(HttpServletRequest)Req;
	    HttpServletResponse response=(HttpServletResponse)Res;
        
        //response.setContentType("text/xml;charset=ISO-8859-1");
        response.setContentType("text/xml;charset=UTF-8");    
        PrintWriter out = response.getWriter();
        String formname=request.getParameter("verb").toLowerCase();
        String token=request.getParameter("resumptionToken");
        
        String addr=request.getRemoteAddr();
        Element elem=null;
	XmlDocument xd=new XmlDocument();
	Document doc= xd.getRoot();
        
        hashString.put("ip",addr);
//        rf= new IRecordFactory(hashString);
//        @author Antonino Fazio
//        Implementazione dell'interfaccia RecordFactory per il nuovo DOGI
//        Sostituisce la vecchia implementazione per ISIS con la nuova per db in XML
        rf= new DogiRecordFactory(hashString);
//        rf= new IRecordFactory(hashString);
	    dp=new DataProvider(rf,doc);
	    OAIError oaierror=null;
        if ((token==null)||token.trim().equals("")) token=null;
        
        try{
	    //check the number of parameters
	    int numofparameters=0;
	    Hashtable params=new Hashtable();
       
	    String queryString=request.getQueryString();
	   
	    if (queryString!=null){
		    StringTokenizer st=new StringTokenizer(queryString,"&");
		    while(st.hasMoreTokens()){
			    Object o=params.put(st.nextToken(),"anything");
			    if (o!=null) throw new BadArgument("Duplicate Argument");		
			    numofparameters++;			
		    }
		} 
	    if ((token!=null)&&(numofparameters>2))	throw new BadArgument("The wrong argument with resumptionToken");
            
	    if (formname.equals("identify")) {
		    if (numofparameters!=1) throw new BadArgument("bad argument for Identify");
            elem=dp.identify();
        }else 
            if (formname.equals("listidentifiers")){
                if (token==null){
		            elem=dp.listIdentifiers(clear(request.getParameter("from")),clear(request.getParameter("until")),clear(request.getParameter("set")),clear(request.getParameter("metadataPrefix")));
		        }else{
		            elem=dp.listIdentifiers (token);
		        }
                }else if (formname.equals("getrecord")){
		            elem=dp.getRecord(clear(request.getParameter("identifier")),clear(request.getParameter("metadataPrefix")));
                }else if (formname.equals("listrecords")){
                    if (token==null)
		                elem=dp.listRecords(clear(request.getParameter("from")),clear(request.getParameter("until")),clear(request.getParameter("set")),clear(request.getParameter("metadataPrefix")));
                    else
		                elem=dp.listRecords (token);
                }else if (formname.equals("listsets")){
                    if (token==null)
		                elem=dp.listSets();
                    else
		                elem=dp.listSets(token);
                    }else if (formname.equals ("listmetadataformats")){
                        elem=dp.listMetadataFormats (clear(request.getParameter("identifier")));
                        }else throw(new BadVerb("badVerb"));
                }
       	catch (OAIError err)
	    {		
		oaierror=err;
		elem=doc.createElement("error");
		elem.setAttribute("code",err.getCode());
		elem.appendChild(doc.createTextNode(err.toString()));
		
	    }
	
	
       	Element root = doc.createElement ("OAI-PMH");
	root.setAttribute ("xmlns", "http://www.openarchives.org/OAI/2.0/");
        root.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
        root.setAttribute("xsi:schemaLocation","http://www.openarchives.org/OAI/2.0/"+" http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd");
	
	Element date=doc.createElement("responseDate");
        SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
	simple.setTimeZone(TimeZone.getTimeZone("GMT"));
	
        String datestring = simple.format(new java.util.Date())+"Z";
	date.appendChild(doc.createTextNode(datestring));
	root.appendChild(date);

	
        Element url=doc.createElement("request");
	    if((oaierror!=null)&& (oaierror.getCode().equals("badVerb")||oaierror.getCode().equals("badArgument"))){
	        url.appendChild(doc.createTextNode(HttpUtils.getRequestURL(request).toString()));	    
	    }else{
	        for (Enumeration enum=request.getParameterNames();enum.hasMoreElements();){
		    String key=(String)enum.nextElement();
		    String value=(String)request.getParameter(key);
		    url.setAttribute(key,URLEncoder.encode(value));
	    }
	    
	    url.appendChild(doc.createTextNode(HttpUtils.getRequestURL(request).toString()));	
	    
	}
	root.appendChild (url);
	
	root.appendChild (elem);
	doc.appendChild(root);
	//XMLTool.Dom2Stream(doc,out);
        //String ot=String(XMLTool.output(doc),"ISO-8859-1");
        out.write(XMLTool.output(doc));
		
	out.close();
	return;
    }
    

    private String clear(String in){
        if (in==null)
            return null;
        else if (in.trim().equals(""))
            return null;
        else
            return in;
    }
  

    public String getServletInfo() {
        return "OAIServlet Version 1.0 (2004)";
    }
    
    public ServletConfig getServletConfig() {
        return servlet_config;
    }
    
    public void destroy() {
        System.err.println("=======================================================");
        System.err.println("OAIServlet sta terminando il suo lavoro!");
    }

}
