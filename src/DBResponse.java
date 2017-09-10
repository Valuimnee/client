import java.io.Serializable;
import java.util.ArrayList;

/**
 * Output parameter of methods within RMI interface.
 * @author Calapova Maria
 * @version 1.1, 3/23/2017
 */
public class DBResponse implements Serializable {
    /**
     * Response table.
     */
    private DBTableModel tableModel;

    /**
     * Constructor of the DBResponse class.
     */
    DBResponse(){
        tableModel=new DBTableModel();
    }

    /**
     * Sets a new table model to the response.
     * @param model {@link DBTableModel} to be set.
     */
    public void setTableModel(DBTableModel model){
        tableModel=model;
    }

    /**
     * Gets the table model of the response.
     * @return Returns the certain {@link DBTableModel}.
     */
    public DBTableModel getTableModel(){
        return tableModel;
    }

    /**
     * Adds specified row to the response table.
     * @param row The row to be added to the response table.
     * @see ArrayList
     */
    public void addRow(ArrayList<Object> row){tableModel.addRow(row);}
}
