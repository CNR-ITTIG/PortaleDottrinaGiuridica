package oai.exception;
public class BadResumptionToken extends OAIError{
    public BadResumptionToken(String text){
	
	super(text);
	code="badResumptionToken";
    }
}
