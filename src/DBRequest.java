import java.io.Serializable;
import java.util.ArrayList;

/**
 * Input parameter of methods within RMI interface.
 * @author Calapova Maria
 * @version 1.1, 3/23/2017
 */
public class DBRequest implements Serializable {
    /**
     * An array of request data.
     */
    private ArrayList<Object> inputData;

    /**
     * Constructor for the DBRequest class.
     */
    DBRequest(){ inputData=new ArrayList<>(); }

    /**
     * Adds object to the request data.
     * @param o {@link Object} to be added.
     */
    public void addData(Object o){
        inputData.add(o);
    }

    /**
     * Adds array of objects to the request data.
     * @param data Array of objects to be added.
     * @see ArrayList
     */
    public void addArrayData(ArrayList<Object> data){
        inputData=data;
    }

    /**
     * Gets array of objects of the request data.
     * @return ArrayList&lt;Object&gt; Returns the array of request data.
     * @see ArrayList
     */
    public ArrayList<Object> getRequestData(){
        return inputData;
    }
}
