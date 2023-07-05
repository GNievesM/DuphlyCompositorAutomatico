package interface;

import DataDefinition.Chord;
import DataDefinition.Note;
import file.FileReader;
import duphly.DuphlyMusicComposer;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javafx.util.Pair;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;

public class ImproviseMenu extends javax.swing.JFrame {

    private boolean hasBase = false;
    private boolean hasRules = false;

    public ImproviseMenu() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main Menu");
        setResizable(false);

        jButton1.setText("Create Base");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Load Base");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Improvise Rules");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Improvise");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MAIN MENU");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addComponent(jButton1)
                .addGap(49, 49, 49)
                .addComponent(jButton2)
                .addGap(52, 52, 52)
                .addComponent(jButton3)
                .addGap(42, 42, 42)
                .addComponent(jButton4)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pack();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {

        JFileChooser jf = new JFileChooser();
        jf.showOpenDialog(null);
        FileReader a = new FileReader(jf.getSelectedFile().getAbsolutePath());
        ArrayList<Chord> chordList = new ArrayList<Chord>();

        while (a.hasMoreLines()) {
            if (a.getLine().equals("#")) {
                chordList = new ArrayList<Chord>();
            } else {
                String line = a.getLine();
                String[] chordArray = line.split("@");
                Chord chord = new Chord();

                chord.setDuration(Integer.parseInt(chordArray[0]));
                chord.setGrade(Integer.parseInt(chordArray[1]));
                if (chordArray[2].equals("false")) {
                    chord.setSeventh(false);
                } else {
                    chord.setSeventh(false);
                }
                chordList.add(chord);
            }
        }
        DuphlyMusicComposer.getInstance().setInternalBase(chordList);

        JOptionPane.showMessageDialog(null, "Successfully loaded.");
        DuphlyMusicComposer.getInstance().setHasBase(true);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        BaseCreatorWindow vp1 = new BaseCreatorWindow();
        DuphlyMusicComposer.getInstance().setHasBase(true);
        vp1.setVisible(true);
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        DuphlyMusicComposer.getInstance().setHasRules(true);
        RuleSelector s = new RuleSelector(false);
        s.setVisible(true);
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        if (DuphlyMusicComposer.getInstance().hasBase() && DuphlyMusicComposer.getInstance().hasRules()) {
            List<Pair<String, String>> ruleList = DuphlyMusicComposer.getInstance().getMelodyRules();
            List<Note> noteList = DuphlyMusicComposer.getInstance().applyImprovisationRuleListByName(ruleList, DuphlyMusicComposer.getInstance().getInternalBase(), null);
            DuphlyMusicComposer.getInstance().setImprovisationNoteList((ArrayList<Note>) noteList);
            Score musicComposition = DuphlyMusicComposer.getInstance().convertImprovisationAndBaseToJmusic(noteList, DuphlyMusicComposer.getInstance().getInternalBase(), 60);
            DuphlyMusicComposer.getInstance().setMelody(musicComposition);
            this.setVisible(false);
            Improviser imp = new Improviser();
            imp.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Missing base or rules.");
        }
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
            java.util.logging.Logger.getLogger(ImproviseMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ImproviseMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ImproviseMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImproviseMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ImproviseMenu().setVisible(true);
            }
        });
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
}
