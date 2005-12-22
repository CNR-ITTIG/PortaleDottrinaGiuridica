
//         ============================ 
//        |  Autore: Lomartire Cosimo  |
//        |  Data: 19.01.2004          |
//        |  Firenze                   |
//         ============================ 

package oai.exception;

import java.io.*;

public class GenericException extends Exception {
    
    private String msg_errore="Errore!!";
    
    public GenericException() {
	    super();
    }    
    
    public GenericException(String s) {
        super(s);
        msg_errore=s;
    }
    
    public void printError(){
        System.err.println(msg_errore);
    }
} 
