/* Chiama la classe Handler con i giusti parametri */

//         =============================== 
//        |  Revisione: Fazio Antonino    |
//        |  Data: novembre 2005          |
//        |  Firenze                      |
//         =============================== 

package k_notes.main;

import java.util.*;
import k_notes.verb.Handle;
import k_notes.generic.*;
import java.util.Hashtable;

class Caller 
{

    private static Hashtable hashString=new Hashtable();
    
    public static void main(String[] args) 
    {
        String properties="OaiHarvester.properties";
                
        System.err.println(new Date()+" - OaiHarvester");        
        System.err.println("=======================================================");

	hashString=Util.propert(properties);

        System.err.println("Inizializzazione completata.");
        System.err.println("=======================================================");
	
	ContentStore contentStore = new ContentStore(hashString);

	Handle handle = new Handle(contentStore);
	handle.recupera();

        System.err.println("=======================================================");
        System.err.println("ControlServlet sta terminando il suo lavoro!");
    }
}
