package interface;

import DataDefinition.Note;
import com.sun.javafx.sg.prism.NGCanvas;
import duphly.DuphlyMusicComposer;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import javax.swing.JList;
import javax.swing.JOptionPane;
import jm.music.data.Score;


public class RuleSelector extends javax.swing.JFrame {

    private List<Pair<String, String>> availableRules;

    private List<Pair<String, String>> selectedRules;

    private int generateCount = 0;
    private boolean option = false;

    public RuleSelector(boolean op) {
        initComponents();
        availableRules = DuphlyMusicComposer.getInstance().getImprovisationRulesNamesAndType();
        option = op;
        if (op) {
            List<Pair<String, String>> intermediate = DuphlyMusicComposer.getInstance().getImprovisationRulesNamesAndType();
            ArrayList<Pair<String, String>> intermediate2 = new ArrayList<>();
            for (Pair<String, String> rule : intermediate) {
                if (!rule.getValue().equals("Generate")) {
                    intermediate2.add(rule);
                }
            }
            availableRules = intermediate2;
        }

        selectedRules = new ArrayList<Pair<String, String>>();
        List<Pair<String, String>> list = DuphlyMusicComposer.getInstance().getImprovisationRulesNamesAndType();

        jList1.setListData(availableRules.toArray());
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rule Selector");
        setResizable(false);

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

        jScrollPane2.setViewportView(jList2);

        jButton1.setText(">>");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("<<");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Finish");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("RULE SELECTOR");

        jButton4.setText("Exit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(368, 368, 368))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(438, 438, 438)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jButton1)
                        .addGap(44, 44, 44)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {

        Pair<String, String> selection = (Pair<String, String>) jList1.getSelectedValue();
        String value = ((Pair<String, String>) selection).getValue();
        if (generateCount == 0 || (generateCount > 0 && !value.equals("Generate"))) {
            selectedRules.add(selection);
            availableRules.remove(selection);
            jList1.removeAll();
            jList2.removeAll();
            jList1.setListData(availableRules.toArray());
            jList2.setListData(selectedRules.toArray());
            jList1.setBackground(Color.WHITE);
            jList2.setBackground(Color.WHITE);
            jList1.validate();
            jList2.validate();
            if (value.equals("Generate")) {
                generateCount++;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Only one generate rule can be selected");
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {

        Pair<String, String> selection = (Pair<String, String>) jList2.getSelectedValue();
        String value = ((Pair<String, String>) selection).getValue();
        availableRules.add(selection);
        selectedRules.remove(selection);
        jList1.removeAll();
        jList2.removeAll();
        jList1.setListData(availableRules.toArray());
        jList2.setListData(selectedRules.toArray());
        jList1.setBackground(Color.WHITE);
        jList2.setBackground(Color.WHITE);
        if (value.equals("Generate")) {
            generateCount--;
        }
        jList1.validate();
        jList2.validate();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {

        if (generateCount == 1 || option) {
            DuphlyMusicComposer.getInstance().setMelodyRules(selectedRules);
            if (option) {
                List<Note> listNotes = DuphlyMusicComposer.getInstance().applyImprovisationRuleListByName(selectedRules, DuphlyMusicComposer.getInstance().getInternalBase(), DuphlyMusicComposer.getInstance().getImprovisationNoteList());
                DuphlyMusicComposer.getInstance().setImprovisationNoteList((ArrayList<Note>) listNotes);
                Score musicComposition = DuphlyMusicComposer.getInstance().convertImprovisationAndBaseToJMusic(listNotes, DuphlyMusicComposer.getInstance().getInternalBase(), 60);
                DuphlyMusicComposer.getInstance().setMelody(musicComposition);
            }
            if (option) {
                Improviser imp = new Improviser();
                imp.setVisible(true);
            } else {
                MenuImprovisation menu = new MenuImprovisation();
                menu.setVisible(true);
            }
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "There must be one generate rule selected");
        }
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        if (option) {
            Improviser imp = new Improviser();
            imp.setVisible(true);
        } else {
            MenuImprovisation menu = new MenuImprovisation();
            menu.setVisible(true);
        }
        this.dispose();    
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
            java.util.logging.Logger.getLogger(SelectorDeReglas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectorDeReglas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectorDeReglas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectorDeReglas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SelectorDeReglas(false).setVisible(true);
            }
        });
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
}
