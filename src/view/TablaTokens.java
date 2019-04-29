package view;

import Main.Token;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TablaTokens extends javax.swing.JFrame {

    private DefaultTableModel modeloTokens, modeloErrores;
    private ArrayList<Token> tokens, errores;
    private boolean error;
    
    /**
     * Creates new form TablaTokens
     */
    public TablaTokens(ArrayList tokens, ArrayList errores, String archivo) {
        initComponents();
        setTitle("Analizador léxico");
        setLocationRelativeTo(null);
        error = false;
        modeloTokens = new DefaultTableModel();
        modeloErrores = new DefaultTableModel();
        modeloTokens.addColumn("Tipo");
        modeloTokens.addColumn("Lexema");
        this.tokens = tokens;
        this.errores = errores; 
        setTableSet();
        tblTabla.setModel(modeloTokens);
        lblNombreArchivo.setText(archivo);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblNombreArchivo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTabla = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Archivo seleccionado:");

        lblNombreArchivo.setText("placeholder");

        jLabel2.setText("Tokens:");

        tblTabla.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblTabla);
        if (tblTabla.getColumnModel().getColumnCount() > 0) {
            tblTabla.getColumnModel().getColumn(0).setResizable(false);
            tblTabla.getColumnModel().getColumn(1).setResizable(false);
        }

        jButton1.setText("Tabla de errores");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Acerca de");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(lblNombreArchivo)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNombreArchivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(error){
            tblTabla.setModel(modeloTokens);
            error = !error;
            jButton1.setText("Tabla de errores");
        }else{
            tblTabla.setModel(modeloErrores);
            error = !error;
            jButton1.setText("Tabla de tokens");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    
    public void setTableSet(){
        Object[][] arrayTokens = new Object[tokens.size()][2];
        Object[][] arrayErrores = new Object[errores.size()][2];
        String[] nombres = {"Tipo", "Lexema"};
        for (int i = 0; i < tokens.size(); i++) {
            arrayTokens[i][0] = tokens.get(i).getTipo();
            arrayTokens[i][1] = tokens.get(i).getLexema();
        }
        for (int i = 0; i < errores.size(); i++) {
            arrayErrores[i][0] = errores.get(i).getTipo();
            arrayErrores[i][1] = errores.get(i).getLexema();
        }
        modeloTokens.setDataVector(arrayTokens, nombres);
        modeloErrores.setDataVector(arrayErrores, nombres);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNombreArchivo;
    private javax.swing.JTable tblTabla;
    // End of variables declaration//GEN-END:variables
}