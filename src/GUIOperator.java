import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Main class of a client side.
 * @author Calapova Maria
 * @version 1.1, 3/23/2017
 */
class GUIOperator extends JFrame {
    private DBPanel dbPanel;

    public GUIOperator(){
        this.setTitle("Students education archive");
        setBounds(400, 300, 400, 450);
        this.setResizable(false);
        Font font = new Font("Arial", Font.PLAIN, 14);
        setFont(font);

        dbPanel=new DBPanel();
        getContentPane().setLayout(null);
        setContentPane(dbPanel.getPanel());
        setJMenuBar(dbPanel.getMenuBar());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        try{
            Registry registry = LocateRegistry.getRegistry("localhost",1102);
            DBInteractor remObject = (DBInteractor) registry.lookup("DBInteractor");
            JOptionPane.showMessageDialog(null, "Lookup successed...");
            dbPanel.setDBInteractor(remObject);
        }
        catch (RemoteException re) {
            System.out.println("RemoteException: "+re);
        }
        catch (NotBoundException nbe) {
            System.out.println("NotBoundException: "+nbe);
        nbe.printStackTrace();}
    }

    /**
     * Main method of a client's side.
     * @param args - Command line arguments.
     */
    public static void main(String args[]){new GUIOperator();}
}
