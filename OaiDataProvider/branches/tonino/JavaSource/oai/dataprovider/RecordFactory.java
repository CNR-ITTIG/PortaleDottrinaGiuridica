package oai.dataprovider;
import java.util.*;
import org.w3c.dom.*;

/**
 * This interface is used to implement any data provider record
 */
public interface RecordFactory{
    /**
     * return a dublin core record
     * @param id the record id
     * @return the DC record
     */
    public Node getRecord(String id);
    /**
     *@param from from parameter in OAI -- accession date
     *@param until until parameter in OAI -- accession date
     *@param set set parameter
     *@param startno start cursor in result set
     *@param size expected returned size
     * startno and size are used to support resumptionToken, if no resumption is required, simply
     * assign 0 to size
     */

    public Vector getRecords(String from, String until, String set,int startno,int size);

    /**
     *@param startno start cursor in result set
     *@param endno end cursor in result set
     */

    public Vector getIdentifiers(String from, String until, String set,int startno,int size);
    
    public Element getIdentify();

    public Vector getSets(int startno, int endno);
    
    public Vector getIdentifiers(int cursor,int size);

    public Vector getRecords(int cursor,int size);
//    public int getListIdentifiers();
//    public int getListRecords();
}
