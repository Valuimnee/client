import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A panel for performing changes in students base.
 * @author Calapova Maria
 * @version 1.1, 3/30/2017
 */
public class InformationPanel {
    private JPanel infoPanel;
    private JPanel labels;
    private JPanel controls;
    private JLabel name;
    private JLabel group;
    private JLabel examsLabel;
    private JLabel emptyLabel;
    private JTextField nameField;
    private JTextField groupField;
    private JLabel[] examsLb;
    private JTextField[] examsTx;

    InformationPanel(){
        infoPanel = new JPanel(new BorderLayout(5, 5));
        labels = new JPanel(new GridLayout(0, 1, 2, 2));
        controls = new JPanel(new GridLayout(0, 1, 2, 2));
        name = new JLabel("Full name: ", SwingConstants.RIGHT);
        group = new JLabel("Group: ", SwingConstants.RIGHT);
        examsLabel = new JLabel();
        emptyLabel = new JLabel();
        nameField = new JTextField(30);
        groupField = new JTextField(10);
        examsLb=null;
        examsTx=null;

        labels.add(name);
        labels.add(group);
        controls.add(nameField);
        controls.add(groupField);
        infoPanel.add(labels, BorderLayout.WEST);
        infoPanel.add(controls, BorderLayout.CENTER);
    }

    public String getNameFieldText() {
        return nameField.getText();
    }

    public String getGroupFieldText() {
        return groupField.getText();
    }

    public String getExamsText(int i) {
        return examsTx[i].getText();
    }

    public void setExamsLabel(String text){
        examsLabel.setText(text);
    }

    public JPanel getPanel(){return infoPanel;}

    public void setStudentGradePanel(ArrayList<ArrayList<Object>> content){
        examsLabel.setText((String)content.get(0).get(0));
        labels.add(examsLabel);
        controls.add(emptyLabel);
        int size=content.size()-1;
        examsLb = new JLabel[size];
        examsTx = new JTextField[size];
        for (int i = 0; i < size; i++) {
            examsLb[i] = new JLabel((String) content.get(i+1).get(0));
            examsTx[i] = new JTextField(10);
            labels.add(examsLb[i]);
            controls.add(examsTx[i]);
        }
        infoPanel.revalidate();
        infoPanel.repaint();
    }
    public void fillStudentGradePanel(ArrayList<Object> data) {
        nameField.setText((String)data.get(0));
        groupField.setText(data.get(1).toString());
        for(int i=0;i<examsLb.length;i++){
            examsTx[i].setText(data.get(2+i).toString());
        }
    }

    public ArrayList<Object> getStudentEntry(){
        ArrayList<Object> data = new ArrayList<>();
        data.add(nameField.getText());
        int number = 0;
        try {
            number = Integer.valueOf(groupField.getText());
        } catch (NumberFormatException exc) {
            return null;
        }
        if (!(number > 0 && number < 8)) {
            return null;
        }
        data.add(number);
        return data;
    }

    public ArrayList<Object> getStudentGradeEntry(){
        ArrayList<Object> data = getStudentEntry();
        if(data==null)
            return data;
        int number=0;
        for (int i = 0; i < examsTx.length; i++) {
            try {
                number = Integer.valueOf(examsTx[i].getText());
            } catch (NumberFormatException exc) {
                return null;
            }
            if (!(number >= 0 && number <= 10)) {
                return null;
            }
            data.add(number);
        }
        return data;
    }

    public void clear(){
        nameField.setText("");
        groupField.setText("");
        examsLabel.setText("");
        labels.remove(examsLabel);
        controls.remove(emptyLabel);
        if(examsLb!=null)
            for(int i=0;i<examsLb.length;i++){
                labels.remove(examsLb[i]);
                controls.remove(examsTx[i]);
            }
        examsLb=null;
        examsTx=null;
        infoPanel.revalidate();
        infoPanel.repaint();
    }

}