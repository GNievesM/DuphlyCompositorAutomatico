package interfaz;

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

/**
 *
 * @author alumnoFI
 */
public class VentanaCreadorDeBase extends javax.swing.JFrame {

    /**
     * Creates new form VPruebaBotones
     */
    private ArrayList<JRadioButton> rbotones = new ArrayList<JRadioButton>();
    private ArrayList<Double> durNotas = new ArrayList<Double>();
    private ArrayList<String> notas = new ArrayList<String>();

    private ArrayList<String> reglasUsadas = new ArrayList<String>();

    private List<Chord> listaChords = new ArrayList<Chord>();

    private ArrayList<ArrayList<Chord>> listaUndo = new ArrayList<ArrayList<Chord>>();
    
    public String textoAcorde(Chord c){
     String texto = "";
            if(c.isFlat())
                texto+="b";
            if(c.isSharp())
                texto+="#";            
            
            texto += c.GetGrade();
            
            if(c.isMinor())
                texto+="m";            
            if(c.isDiminishedMode())
                texto+="d";
            if (c.GetSeptima()) 
                texto += " :7";
            if(c.isbTone())
                texto+="b";
            if(c.isSixth())
                texto+=" 6";
            if(c.isMSeventh())
                texto+= "M7";
            if(c.isSeventhComa())
                texto+=" 7'";
            if(c.isSevenPlusFive())
                texto+="7+5";
            if(c.isNinth())
                texto+=" 9";
            if(c.isEmptyMode())
                texto+="o7";
            if(c.isThirtinth())
                texto+=" 13";
      return texto;
    }
    
    

    public VentanaCreadorDeBase() {

        initComponents();
     
        // BaseChordCreator bcc = new BaseChordCreator();
        listaChords = (ArrayList<Chord>) DuphlyMusicComposer.getInstance().CreateSimpleBase();
        jList2.setListData(DuphlyMusicComposer.getInstance().getExistingChordRules().toArray());
        rbotones = new ArrayList<JRadioButton>();
        jPanel3.setLayout(new GridLayout(1, listaChords.size()));
        for (int i = 0; i < listaChords.size(); i++) {
            String texto = this.textoAcorde(listaChords.get(i));
            /*if(listaChords.get(i).isFlat())
                texto+="b";
            if(listaChords.get(i).isSharp())
                texto+="#";            
            
            texto += Integer.toString(listaChords.get(i).GetGrade());
            
            if(listaChords.get(i).isMinor())
                texto+="m";            
            if(listaChords.get(i).isDiminishedMode())
                texto+="d";
            if (listaChords.get(i).GetSeptima()) {
                texto += " :7";
            }*/
            JRadioButton rb = new JRadioButton(texto);//Integer.toString(listaChords.get(i).GetGrade()) + " :" + listaChords.get(i).getse);
            rb.setToolTipText(Integer.toString(i));
            rb.setIcon(new ImageIcon(imagenesNotas.ImagenDuracion(listaChords.get(i).GetDuration())));
            buttonGroup1.add(rb);
            rbotones.add(rb);
            jPanel3.add(rb);
        }
        //  listaUndo.add((ArrayList<Chord>) listaChords);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Creador de base");

        jButton1.setText("Aplicar Regla");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jList1);

        jScrollPane2.setViewportView(jList2);
        jList2.getAccessibleContext().setAccessibleName("");

        jPanel3.setAutoscrolls(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1195, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );

        jButton2.setText("Undo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Salvar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Cargar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Terminar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Random");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Preview");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("CREADOR DE BASE");

        jButton8.setText("Cancelar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jButton8)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jButton5))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(495, 495, 495)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton1)
                                    .addComponent(jButton2)
                                    .addComponent(jButton8)
                                    .addComponent(jButton5)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addGap(40, 40, 40)
                                .addComponent(jButton4)
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton6)
                                    .addComponent(jButton7))
                                .addGap(68, 68, 68))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        ArrayList<Chord> clon3 = new ArrayList<Chord>();
        for (int i = 0; i < listaChords.size(); i++) {
            clon3.add(listaChords.get(i));
        }

       // listaUndo.add(clon3);
        int pos = 0;
        //String nuevaNota = "";
        // int nuevaDur = 0;

        //ver que rb esta seleccionado
        for (int i = 0; i < rbotones.size(); i++) {
            if (rbotones.get(i).isSelected()) {
                try {
                    pos = Integer.parseInt(rbotones.get(i).getToolTipText());
                    Chord antes = listaChords.get(pos);
                    //nuevaNota = notas.get(pos);//rbotones.get(i).getText();
                    //int nuevadur = listaChords.get(pos).GetDuration() / 2;

                    int numRegla = jList2.getSelectedIndex();
                    listaChords = (ArrayList<Chord>) DuphlyMusicComposer.getInstance().ApplyRulePerPosition(pos, listaChords, numRegla);
                    rbotones.get(pos).setIcon(new ImageIcon(imagenesNotas.ImagenDuracion(listaChords.get(pos).GetDuration())));

                    if (!antes.equals(listaChords.get(pos))) {
                        reglasUsadas.add("Se uso  " + jList2.getSelectedValue() + " en la posicion " + pos++);
                        jList1.removeAll();
                        jList1.setListData(reglasUsadas.toArray());
                         listaUndo.add(clon3);
                    } //en caso de que se use mal la regla 3
          //          else {
           //             listaUndo.remove(listaUndo.size() - 1);
            //        }

                } catch (Exception e) {
                    System.out.println("i: " + i + " pos: " + pos);
                }
            }
        }

        actualizarBotones();


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        listaChords = listaUndo.get(listaUndo.size() - 1);
        listaUndo.remove(listaUndo.size() - 1);
        reglasUsadas.remove(reglasUsadas.size() - 1);
        jList1.removeAll();
        jList1.setListData(reglasUsadas.toArray());

        actualizarBotones();


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        int userSelection = fileChooser.showSaveDialog(parentFrame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
       
          
               ArchivoGrabacion arch = new ArchivoGrabacion(fileChooser.getSelectedFile().getAbsolutePath()+ ".duphlyBase");
          
         for (int i = 0; i < listaUndo.size(); i++) {
            for (int j = 0; j < listaUndo.get(i).size(); j++) {
                Chord a = listaUndo.get(i).get(j);
                arch.grabarLinea(a.GetDuration() + "@" + a.GetGrade() + "@" + a.GetSeptima());
            }
            arch.grabarLinea("#");
        }
            for (int i = 0; i < this.listaChords.size(); i++) {
                Chord aux = this.listaChords.get(i);
               arch.grabarLinea(aux.GetDuration() + "@" + aux.GetGrade() + "@" + aux.GetSeptima());
            }
          arch.grabarLinea("#");
        arch.cerrar();
        
             ArchivoGrabacion hist = new ArchivoGrabacion(fileChooser.getSelectedFile().getAbsolutePath()+ ".duphlyBaseDuphlyHistory");
            for (int i = 0; i < this.reglasUsadas.size(); i++) {
                hist.grabarLinea(this.reglasUsadas.get(i));
            }
            hist.grabarLinea("#");
            hist.cerrar();
        JOptionPane.showMessageDialog(null, "se grabo exitosamente");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        JFileChooser jf = new JFileChooser();
        jf.showOpenDialog(null);
        jList1.removeAll();
        ArchivoLectura a = new ArchivoLectura(jf.getSelectedFile().getAbsolutePath());//DuphlyHistory
        ArrayList<Chord> lisChord = new ArrayList<Chord>();
        listaUndo.clear();
        while (a.hayMasLineas()) {
            if (a.linea().equals("#")) {
                listaUndo.add(lisChord);
                lisChord = new ArrayList<Chord>();
            } else {
                String lin = a.linea();
                String[] ChordArray = lin.split("@");
                Chord acorde = new Chord();

                acorde.SetDuration(Integer.parseInt(ChordArray[0]));
                acorde.SetGrade(Integer.parseInt(ChordArray[1]));
                if (ChordArray[2].equals("false")) {
                    acorde.SetSeptima(false);
                } else {
                    acorde.SetSeptima(true);
                }
                lisChord.add(acorde);
            }
        }

        listaChords = listaUndo.get(listaUndo.size() - 1);
         listaUndo.remove(listaUndo.size() - 1);
   
        ///////////////////////////////////////////////////////////////
        
      
        ArchivoLectura b = new ArchivoLectura(jf.getSelectedFile().getAbsolutePath()+"DuphlyHistory");//DuphlyHistory
        while (b.hayMasLineas()) {
            if (!b.linea().equals("#")) {
                String lin = b.linea();
               
                this.reglasUsadas.add(lin);
            }
        }
         jList1.removeAll();
         jList1.setListData(reglasUsadas.toArray());
         jList1.revalidate();
         
       
   
        actualizarBotones();


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        MenuImprovisar m = new MenuImprovisar();
        m.setVisible(true);
        DuphlyMusicComposer.getInstance().setInternalBase(listaChords);
        dispose();

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        listaChords = DuphlyMusicComposer.getInstance().CreateRandomBase();
        listaUndo.add((ArrayList<Chord>) listaChords);
        jList1.removeAll();
        jList1.setListData(reglasUsadas.toArray());
        actualizarBotones();

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Play.midi(DuphlyMusicComposer.getInstance().ConvertChordToJmusic(listaChords, 60));        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        MenuImprovisar m = new MenuImprovisar();
        m.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    public void actualizarBotones() {
        rbotones.clear();
        jPanel3.removeAll();
        buttonGroup1 = new ButtonGroup();
        for (int i = 0; i < listaChords.size(); i++) {
           String texto = this.textoAcorde(listaChords.get(i));

  /*          if(listaChords.get(i).isFlat())
                texto+="b";
            if(listaChords.get(i).isSharp())
                texto+="#";            
            
            texto += Integer.toString(listaChords.get(i).GetGrade());
            
            if(listaChords.get(i).isMinor())
                texto+="m";            
            if(listaChords.get(i).isDiminishedMode())
                texto+="d";
            if (listaChords.get(i).GetSeptima()) {
                texto += " :7";
            }
*/          
          /*texto += Integer.toString(listaChords.get(i).GetGrade());
            if (listaChords.get(i).GetSeptima()) {
                texto += " :7";
            }*/
            JRadioButton rb = new JRadioButton(texto);

            rb.setToolTipText(Integer.toString(i));
            rb.setIcon(new ImageIcon(imagenesNotas.ImagenDuracion(listaChords.get(i).GetDuration())));

            rbotones.add(rb);
            buttonGroup1.add(rb);
            jPanel3.add(rb);
        }
        jPanel3.validate();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaCreadorDeBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaCreadorDeBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaCreadorDeBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaCreadorDeBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaCreadorDeBase().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    // End of variables declaration//GEN-END:variables
}
