import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Main panel that presents database information.
 * @author Calapova Maria
 * @version 1.1, 3/18/2017
 */
public class DBPanel extends Component {
    private JMenuBar jMenuBar;
    private JMenuItem [] menuItems;
    private JMenuItem[] studentItems;
    private JPanel panel;
    private JScrollPane pane;
    private JTable table;
    private DBTableModel tableModel;
    private DBInteractor dbInteractor;
    private InformationPanel informationPanel;

    public DBPanel(){
        panel=new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setSize(400, 400);
        createMenuBar();
        createListeners();
        table=new JTable(30,2);
        tableModel=new DBTableModel();
        pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(395, 395));
        panel.add(pane);
        informationPanel=new InformationPanel();
    }

    public JPanel getPanel(){return panel;}
    public JMenuBar getMenuBar(){return jMenuBar;}
    public JTable getTable(){return table;}
    public void setDBInteractor(DBInteractor interactor){dbInteractor=interactor;}
    private void createMenuBar(){
        jMenuBar=new JMenuBar();
        JMenu menu=new JMenu("Menu");
        menuItems = new JMenuItem[3];
        menuItems[0] = new JMenuItem("Add student");
        menuItems[1] = new JMenuItem("Edit student information");
        menuItems[2] = new JMenuItem("Delete student");
        for(JMenuItem item: menuItems)
            menu.add(item);
        jMenuBar.add(menu);
        JMenu students=new JMenu("Print");
        studentItems=new JMenuItem[4];
        studentItems[0]=new JMenuItem("Not passed students");
        studentItems[1]=new JMenuItem("Average students");
        studentItems[2]=new JMenuItem("Excellent students");
        studentItems[3]=new JMenuItem("Statistics");
        for(JMenuItem item: studentItems)
            students.add(item);
        jMenuBar.add(students);
    }
    private ArrayList<Object> fillStudentInformationPanel(String title){
        int result=JOptionPane.showConfirmDialog(null, informationPanel.getPanel(),
                title, JOptionPane.OK_CANCEL_OPTION);
        if(result!=JOptionPane.OK_OPTION){
            informationPanel.clear();
            return null;
        }
        ArrayList<Object> data = informationPanel.getStudentEntry();
        if(data==null){
            JOptionPane.showMessageDialog(null, "Wrong input data!");
        }
        informationPanel.clear();
        return data;
    }
    private boolean createStudentGradeInformationPanel(String title){
        ArrayList<ArrayList<Object>> content=null;
        ArrayList<Object> names=new ArrayList<>();
        names.add(title);
        DBResponse response = null;
        try {
            response = dbInteractor.getExams();
            if(!checkResponse(response))
                return false;
        } catch (RemoteException e1) {
            JOptionPane.showMessageDialog(null,"Remote exception: "+e1.getMessage()+"\n");
            e1.printStackTrace();
            return false;
        }
        content=response.getTableModel().getContent();
        content.add(0, names);
        informationPanel.setStudentGradePanel(content);
        return true;
    }
    private ArrayList<Object> getStudentGradeInformationPanelEntry(String title) {
        int result = JOptionPane.showConfirmDialog(null, informationPanel.getPanel(), title,
                JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION){
            informationPanel.clear();
            return null;
        }
        ArrayList<Object> data=informationPanel.getStudentGradeEntry();
        if(data==null){
            JOptionPane.showMessageDialog(null, "Wrong input data!");
        }
        informationPanel.clear();
        return data;
    }
    private boolean checkResponse(DBResponse response){
        if(response.getTableModel().getColumnCount()==0) {
            JOptionPane.showMessageDialog(null, response.getTableModel().getContent().get(0).get(0));
            return false;
        }
        return true;
    }
    private void createListeners(){
        menuItems[0].addActionListener(e -> {
            if(!createStudentGradeInformationPanel("Enter student's grades for exams:"))
                return;
            ArrayList<Object> data=getStudentGradeInformationPanelEntry("Enter student's information");
            if(data==null)
                return;
            DBRequest request = new DBRequest();
            request.addArrayData(data);
            try {
                dbInteractor.addStudent(request);
            } catch (RemoteException e1) {
                JOptionPane.showMessageDialog(null,"Remote exception: "+e1.getMessage()+"\n");
                e1.printStackTrace();
            }
        });
        menuItems[1].addActionListener(e->{
            ArrayList<Object> data=fillStudentInformationPanel("Select a student to edit information about him:");
            if(data==null)
                return;
            DBRequest request = new DBRequest();
            request.addArrayData(data);

            if(!createStudentGradeInformationPanel("Student's grades for exams:"))
                return;
            DBResponse response;
            try {
                response=dbInteractor.getStudentInformation(request);
                if(!checkResponse(response)){
                    informationPanel.clear();
                    return;
                }
                if(response.getTableModel().getContent().get(0).size()==0){
                    JOptionPane.showMessageDialog(null, "There is no such student.");
                    informationPanel.clear();
                    return;
                }
                informationPanel.fillStudentGradePanel(response.getTableModel().getContent().get(0));

                request = new DBRequest();
                data=getStudentGradeInformationPanelEntry("Edit student's information");
                if(data==null)
                    return;
                data.add(0, response.getTableModel().getContent().get(0).get(0));
                data.add(1, response.getTableModel().getContent().get(0).get(1));
                request.addArrayData(data);
                dbInteractor.editStudentInformation(request);
            } catch (RemoteException e1) {
                JOptionPane.showMessageDialog(null,"Remote exception: "+e1.getMessage()+"\n");
                e1.printStackTrace();
            }
        });
        menuItems[2].addActionListener(e->{
            ArrayList<Object> data=fillStudentInformationPanel("Enter group and name of student to delete");
            if(data==null)
                return;
            DBRequest request = new DBRequest();
            request.addArrayData(data);
            DBResponse response = null;
            try {
                response=dbInteractor.deleteStudent(request);
                if(!checkResponse(response))
                    return;
                JOptionPane.showMessageDialog(null, response.getTableModel().getContent().get(0).get(0),
                        "Result of deletion",JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException e1) {
                JOptionPane.showMessageDialog(null,"Remote exception: "+e1.getMessage()+"\n");
                e1.printStackTrace();
            }
        });
        studentItems[0].addActionListener(e->{
            try {
                DBResponse response=dbInteractor.getNotPassedStudents();
                if(!checkResponse(response))
                    return;
                tableModel=response.getTableModel();
                table.setModel(tableModel);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                repaint();
            } catch (RemoteException e1) {
                JOptionPane.showMessageDialog(null,"Remote exception: "+e1.getMessage()+"\n");
                e1.printStackTrace();
            }
        });
        studentItems[1].addActionListener(e->{
            try {
                DBResponse response=dbInteractor.getAverageStudents();
                if(!checkResponse(response))
                    return;
                tableModel=response.getTableModel();
                table.setModel(tableModel);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                repaint();
            } catch (RemoteException e1) {
                JOptionPane.showMessageDialog(null,"Remote exception: "+e1.getMessage()+"\n");
                e1.printStackTrace();
            }
        });
        studentItems[2].addActionListener(e->{
            try {
                DBResponse response=dbInteractor.getExcellentStudents();
                if(!checkResponse(response))
                    return;
                tableModel=response.getTableModel();
                table.setModel(tableModel);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                repaint();
            } catch (RemoteException e1) {
                JOptionPane.showMessageDialog(null,"Remote exception: "+e1.getMessage()+"\n");
                e1.printStackTrace();
            }
        });
        studentItems[3].addActionListener(e->{
            try {
                DBResponse response=dbInteractor.getStatistics();
                if(!checkResponse(response))
                    return;
                tableModel=response.getTableModel();
                table.setModel(tableModel);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                repaint();
            } catch (RemoteException e1) {
                JOptionPane.showMessageDialog(null,"Remote exception: "+e1.getMessage()+"\n");
                e1.printStackTrace();
            }
        });
    }
}