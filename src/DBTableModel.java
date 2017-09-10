import javax.swing.table.AbstractTableModel;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Table model for representing database information.
 * @author Calapova Maria
 * @version 1.1, 3/23/2017
 */
public class DBTableModel extends AbstractTableModel implements Serializable {
    private ArrayList<String> columnNames;
    private ArrayList<Class> columnTypes;
    private ArrayList<ArrayList<Object>> content;

    DBTableModel(){
        columnNames =new ArrayList<>();
        columnTypes= new ArrayList<>();
        content = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return content.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex<getRowCount()&&columnIndex<getColumnCount())
            return content.get(rowIndex).get(columnIndex);
        return null;
    }
    public void addColumnName(String column){
        columnNames.add(column);
    }
    @Override
    public String getColumnName(int column){
        return columnNames.get(column);
    }

    public void clearModel(){
        columnNames.clear();
        columnTypes.clear();
        for(ArrayList<Object> row: content)
            row.clear();
        content.clear();
    }

    public void setContent(ArrayList<ArrayList<Object>> content) {
        this.content = content;
    }

    public ArrayList<ArrayList<Object>> getContent(){
        return content;
    }

    public void addRow(ArrayList<Object> row){
        content.add(row);
        fireTableRowsInserted(content.size()-1, content.size()-1);
    }

    public void setNewModel(ArrayList<String> columnNames, ArrayList<Class> columnTypes,ArrayList<ArrayList<Object>> content){
        clearModel();
        this.columnNames =columnNames;
        this.columnTypes=columnTypes;
        fireTableStructureChanged();
        this.content=content;
        fireTableRowsInserted(0, content.size()-1);
    }
}