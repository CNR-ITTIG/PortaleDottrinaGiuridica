
//         ============================ 
//        |  Autore: Lomartire Cosimo  |
//        |  Data: 11.11.2001          |
//        |  Firenze                   |
//         ============================ 
//         =============================== 
//        |  Revisione: Fazio Antonino    |
//        |  Data: novembre 2005          |
//        |  Firenze                      |
//         =============================== 

package k_notes.generic;

import java.util.*;

public class ContentStore{

    private Hashtable content;
    
    public ContentStore(){
        content=new Hashtable();
    }
    
    public ContentStore(Hashtable hash){
        content=hash;
    }

    public Hashtable getContent(){
	return content;
    }

    public String getContent(String key){
	    if(content!=null) {
	        Object o=content.get(key);
	        if(o == null) {
		        return null;
	        }else 
	            if(o instanceof String) {
		            return (String)o;
	            } else return "";
	    } else return "";
	}

    public boolean isContentSet(String key){
	    Object s=content.get(key);
	    return s != null && !(s instanceof String && ((String)s).trim().equals(""));
    }

    public void setContent(String key, String value){
	    content.put(key,value);
    }
    
    public Enumeration getContentKeys(){
	    return content.keys();
    }
} 
