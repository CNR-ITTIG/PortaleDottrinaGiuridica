package oai.exception;
public class BadArgument extends OAIError{
    public BadArgument(String text){
	super(text);
	code="badArgument";
    }
}
