package interface;

import DataDefinition.Chord;
import DataDefinition.Note;
import archivo.ArchivoGrabacion;
import archivo.ArchivoLectura;
import duphly.DuphlyMusicComposer;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.RadioButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import jm.util.Play;

public class BaseCreatorWindow extends javax.swing.JFrame {

    private ArrayList<JRadioButton> radioButtons = new ArrayList<JRadioButton>();
    private ArrayList<Double> noteDurations = new ArrayList<Double>();
    private ArrayList<String> notes = new ArrayList<String>();

    private ArrayList<String> usedRules = new ArrayList<String>();

    private List<Chord> chordList = new ArrayList<Chord>();

    private ArrayList<ArrayList<Chord>> undoList = new ArrayList<ArrayList<Chord>>();
    
    public String chordText(Chord c) {
        String text = "";
        if(c.isFlat())
            text += "b";
        if(c.isSharp())
            text += "#";            

        text += c.GetGrade();

        if(c.isMinor())
            text += "m";            
        if(c.isDiminishedMode())
            text += "d";
        if (c.GetSeptima()) 
            text += " :7";
        if(c.isbTone())
            text += "b";
        if(c.isSixth())
            text += " 6";
        if(c.isMSeventh())
            text += "M7";
        if(c.isSeventhComa())
            text += " 7'";
        if(c.isSevenPlusFive())
            text += "7+5";
        if(c.isNinth())
            text += " 9";
        if(c.isEmptyMode())
            text += "o7";
        if(c.isThirtinth())
            text += " 13";
        return text;
    }

    public BaseCreatorWindow() {

        initComponents();
     
        chordList = (ArrayList<Chord>) DuphlyMusicComposer.getInstance().CreateSimpleBase();
        jList2.setListData(DuphlyMusicComposer.getInstance().getExistingChordRules().toArray());
        radioButtons = new ArrayList<JRadioButton>();
        jPanel3.setLayout(new GridLayout(1, chordList.size()));
        for (int i = 0; i < chordList.size(); i++) {
            String text = this.chordText(chordList.get(i));

            JRadioButton rb = new JRadioButton(text);

            rb.setToolTipText(Integer.toString(i));
            rb.setIcon(new ImageIcon(imagenesNotas.ImagenDuracion(chordList.get(i).GetDuration())));

            radioButtons.add(rb);
            buttonGroup1.add(rb);
            jPanel3.add(rb);
        }
        jPanel3.validate();

    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BaseCreatorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BaseCreatorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BaseCreatorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BaseCreatorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BaseCreatorWindow().setVisible(true);
            }
        });
    }

    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
}
