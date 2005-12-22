package oai.exception;
public class CannotDisseminateFormat extends OAIError{
    public CannotDisseminateFormat(String text){
	
	super(text);
	code="cannotDisseminateFormat";
    }
}
