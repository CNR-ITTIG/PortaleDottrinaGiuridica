package oai.exception;
public class NoRecordsMatch extends OAIError{
    public NoRecordsMatch(String text){
	
	super(text);
	code="noRecordsMatch";
    }
}
