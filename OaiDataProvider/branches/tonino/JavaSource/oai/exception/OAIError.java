package oai.exception;
public class OAIError  extends Exception{
    public String code=null;
    OAIError(String text){
	super(text);
    }
    
    public String getCode(){
	return code;
    }
    
    
}
