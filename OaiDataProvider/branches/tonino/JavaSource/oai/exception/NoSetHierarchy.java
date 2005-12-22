package oai.exception;
public class NoSetHierarchy extends OAIError{
    public NoSetHierarchy(String text){
	
	super(text);
	code="noSetHierarchy";
    }
}
