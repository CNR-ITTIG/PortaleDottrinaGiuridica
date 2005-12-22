
//         ============================ 
//        |  Autore: Lomartire Cosimo  |
//        |  Data: 20.01.2004          |
//        |  Firenze                   |
//         ============================ 

package oai.dataprovider;

import java.io.*;
import java.util.*;
import java.text.*;
import java.net.*;

import oai.util.xml.XmlDocument;
import oai.util.generic.Util;

import org.w3c.dom.*;

public class IRecordFactory implements RecordFactory{
   
    Hashtable hashString;
    String postAttrib;
        
    public IRecordFactory(Hashtable hashString){
        this.hashString=hashString;
    }
    
    public Element getIdentify(){
        String identifyFile=(String)hashString.get("supportPath")+(String)hashString.get("identifyFile");
        XmlDocument xml=new XmlDocument(identifyFile);
        return xml.getRootElement();
    }

    public Vector getSets(int startno, int size){
        Vector v=new Vector();
        /*
            Il DB Dogi non supporta i set.
        */
        return v;
    }

    public Vector getRecords(String fileafter,String filebefore,String setspec,int startno, int size){
        String verb="listRecords";
        String supporto=(String)hashString.get("supportPath")+(String)hashString.get("supporto");
                                        
        String ip=(String)hashString.get("ip");
        Integer from=new Integer(Util.readTo(supporto,ip,verb));
        Integer to=new Integer(from.intValue()+size-1);
        
        SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
	simple.setTimeZone(TimeZone.getTimeZone("GMT"));	
        String datestring = simple.format(new java.util.Date())+"Z";   
        String prefix=((String)hashString.get("dbPrefix")).trim();
        
        String isisScript=(String)hashString.get("scriptPath")+(String)hashString.get(verb);
        postAttrib="IsisScript="+URLEncoder.encode(isisScript)+"&"+
            "dbPath="+URLEncoder.encode((String)hashString.get("dbPath"))+"&"+
            "from="+URLEncoder.encode(from.toString())+"&"+"to="+URLEncoder.encode(to.toString())+"&"+
            "datestamp="+URLEncoder.encode(datestring)+"&prefix="+prefix;
                
        XmlDocument xml;
        try{
            xml=execute(postAttrib);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        Vector v=xmlToVector(xml,"record");
        int a=v.size()+from.intValue();
        Util.writeTo(supporto, ip, verb, a);
                        
        return v;
    }
    
    public Vector getRecords(int cursor, int size){
        String verb="listRecords";
        String supporto=(String)hashString.get("supportPath")+(String)hashString.get("supporto");
                        
        String ip=(String)hashString.get("ip");
        Integer from=new Integer(cursor);
        Integer from2=new Integer(Util.readTo(supporto,ip,verb));
        Integer to=new Integer(from.intValue()+size-1);
        
        SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
	simple.setTimeZone(TimeZone.getTimeZone("GMT"));	
        String datestring = simple.format(new java.util.Date())+"Z";        
        String prefix=((String)hashString.get("dbPrefix")).trim();
        
        String isisScript=(String)hashString.get("scriptPath")+(String)hashString.get(verb);
        postAttrib="IsisScript="+URLEncoder.encode(isisScript)+"&"+
            "dbPath="+URLEncoder.encode((String)hashString.get("dbPath"))+"&"+
            "from="+URLEncoder.encode(from.toString())+"&"+"to="+URLEncoder.encode(to.toString())+"&"+
            "datestamp="+datestring+"&prefix="+prefix;
        XmlDocument xml;
        try{
            xml=execute(postAttrib);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        Vector v=xmlToVector(xml,"record");
        int a=v.size()+from.intValue();
        if (from2.compareTo(new Integer(a))<0) 
        	Util.writeTo(supporto, ip, verb, a);
                
        return v;        
    }
    
    public Vector getIdentifiers(String fileafter,String filebefore,String setspec,int startno, int size){
        String verb="listIdentifiers";
        String supporto=(String)hashString.get("supportPath")+(String)hashString.get("supporto");
                        
        String ip=(String)hashString.get("ip");
        Integer from=new Integer(Util.readTo(supporto,ip,verb));
        Integer to=new Integer(from.intValue()+size-1);
        
        SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
	simple.setTimeZone(TimeZone.getTimeZone("GMT"));	
        String datestring = simple.format(new java.util.Date())+"Z";        
        String prefix=((String)hashString.get("dbPrefix")).trim();

        String isisScript=(String)hashString.get("scriptPath")+(String)hashString.get(verb);
        postAttrib="IsisScript="+URLEncoder.encode(isisScript)+"&"+
            "dbPath="+URLEncoder.encode((String)hashString.get("dbPath"))+"&"+
            "from="+URLEncoder.encode(from.toString())+"&"+"to="+URLEncoder.encode(to.toString())+"&"+
            "datestamp="+datestring+"&prefix="+prefix;
        XmlDocument xml;
        try{
            xml=execute(postAttrib);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        Vector v=xmlToVector(xml,"header");
        int a=v.size()+from.intValue();
        Util.writeTo(supporto, ip, verb, a);
        return v;        
    }
    
    public Vector getIdentifiers(int cursor, int size){
        String verb="listIdentifiers";
        String supporto=(String)hashString.get("supportPath")+(String)hashString.get("supporto");
                        
        String ip=(String)hashString.get("ip");
        Integer from=new Integer(cursor);
        Integer from2=new Integer(Util.readTo(supporto,ip,verb));
        Integer to=new Integer(from.intValue()+size-1);
        
        SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
	simple.setTimeZone(TimeZone.getTimeZone("GMT"));	
        String datestring = simple.format(new java.util.Date())+"Z";
        String prefix=((String)hashString.get("dbPrefix")).trim();

        String isisScript=(String)hashString.get("scriptPath")+(String)hashString.get(verb);
        postAttrib="IsisScript="+URLEncoder.encode(isisScript)+"&"+
            "dbPath="+URLEncoder.encode((String)hashString.get("dbPath"))+"&"+
            "from="+URLEncoder.encode(from.toString())+"&"+"to="+URLEncoder.encode(to.toString())+"&"+
            "datestamp="+datestring+"&prefix="+prefix;
        XmlDocument xml;
        try{
            xml=execute(postAttrib);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        Vector v=xmlToVector(xml,"header");
        int a=v.size()+from.intValue();
        if (from2.compareTo(new Integer(a))<0) 
        	Util.writeTo(supporto, ip, verb, a);
                
        return v;        
    }
    
    
    public Node getRecord(String handle){
        
        String verb="getRecord";
        String isisScript=(String)hashString.get("scriptPath")+(String)hashString.get(verb);
        
        SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
	simple.setTimeZone(TimeZone.getTimeZone("GMT"));	
        String datestring = simple.format(new java.util.Date())+"Z";
        String prefix=((String)hashString.get("dbPrefix")).trim();
                
        postAttrib="IsisScript="+URLEncoder.encode(isisScript)+"&"+
            "dbPath="+URLEncoder.encode((String)hashString.get("dbPath"))+"&"+
            "identifier="+handle+"&datestamp="+datestring+"&prefix="+prefix;
        System.err.println(postAttrib+"........");
        XmlDocument xml;
        try{
            xml=execute(postAttrib);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }        
                               
        return ((xml.getRoot()).getElementsByTagName("record")).item(0);
    }
    
    private Vector xmlToVector(XmlDocument xml, String tag){
        Vector v=new Vector();
        NodeList nl=(xml.getRootElement()).getElementsByTagName(tag);
        for(int i=0;i<nl.getLength();i++){
            v.add(nl.item(i));
        }        
        return v;
    }     
    
   protected XmlDocument execute(String attrib)throws Exception{
                
        String wxisPath=(String)hashString.get("wxisPath");
        
        URL url = new URL(wxisPath);
        URLConnection con = url.openConnection();
            
        con.setDoOutput(true);
        PrintWriter out= new PrintWriter(con.getOutputStream());
        out.print(attrib);
        
        out.flush();
        out.close();
                
        XmlDocument xml=new XmlDocument(con.getInputStream());
        return xml;
        
   }

   public int getListIdentifiers() {
       String verb="listIdentifiers";
       String supporto=(String)hashString.get("supportPath")+(String)hashString.get("supporto");
                        
       String ip=(String)hashString.get("ip");
       Integer from=new Integer(Util.readTo(supporto,ip,verb));
       return from.intValue();
   }
   
   public int getListRecords() {
       String verb="listRecords";
       String supporto=(String)hashString.get("supportPath")+(String)hashString.get("supporto");
                        
       String ip=(String)hashString.get("ip");
       Integer from=new Integer(Util.readTo(supporto,ip,verb));
       return from.intValue();
   }
   
}



