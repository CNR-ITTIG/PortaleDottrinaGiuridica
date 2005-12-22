package oai.exception;
public class IdDoesNotExist extends OAIError{
    public IdDoesNotExist(String text){
	
	super(text);
	code="idDoesNotExist";
    }
}
