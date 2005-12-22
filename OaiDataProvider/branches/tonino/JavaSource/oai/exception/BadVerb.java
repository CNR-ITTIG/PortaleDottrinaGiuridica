package oai.exception;
public class BadVerb extends OAIError{
    public BadVerb(String text){
	
	super(text);
	code="badVerb";
    }
}
