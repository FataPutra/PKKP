/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pkkp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Kelurahan extends javax.swing.JFrame {

    /**
     * Creates new form Kelurahan
     */
     Connection Con;
    ResultSet RsKel;
    Statement stm;
    
    Boolean ada = false;
    Boolean edit=false;
    String sNamaKec;
    String sNamaKab;
    private Object[][] dataTable = null;
    private String[] header = {"Kode Kel/Desa", "Nama Kel/Desa","Kecamatan","Kabupaten","Kuota"};
    
    public Kelurahan() {
        initComponents();
        open_db();
        baca_data();
        aktif(false);
        setTombol(true);
        dataFromDataBaseToComboBox();
        dataFromDataBaseToComboBox2();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtKodeKelurahan = new javax.swing.JTextField();
        txtNamaKelurahan = new javax.swing.JTextField();
        cmbKec = new javax.swing.JComboBox<>();
        cmbKab = new javax.swing.JComboBox<>();
        txtJumlahKuota = new javax.swing.JTextField();
        cmdTambah = new javax.swing.JButton();
        cmdSimpan = new javax.swing.JButton();
        cmdEdit = new javax.swing.JButton();
        cmdHapus = new javax.swing.JButton();
        cmdBatal = new javax.swing.JButton();
        cmdKeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKel = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 24)); // NOI18N
        jLabel1.setText("Data Kelurahan / Desa");

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel2.setText("Kode Kelurahan / Desa");

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel3.setText("Nama Kelurahan / Desa");

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel4.setText("Kecamatan");

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel5.setText("Kabupaten");

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel6.setText("Kuota");

        cmbKec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKecActionPerformed(evt);
            }
        });

        cmbKab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKabActionPerformed(evt);
            }
        });

        cmdTambah.setBackground(new java.awt.Color(255, 204, 153));
        cmdTambah.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        cmdTambah.setText("Tambah");
        cmdTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTambahActionPerformed(evt);
            }
        });

        cmdSimpan.setBackground(new java.awt.Color(153, 255, 153));
        cmdSimpan.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        cmdSimpan.setText("Simpan");
        cmdSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdSimpanMouseClicked(evt);
            }
        });

        cmdEdit.setBackground(new java.awt.Color(255, 255, 153));
        cmdEdit.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        cmdEdit.setText("Edit");
        cmdEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditActionPerformed(evt);
            }
        });

        cmdHapus.setBackground(new java.awt.Color(255, 153, 153));
        cmdHapus.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        cmdHapus.setText("Hapus");
        cmdHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdHapusActionPerformed(evt);
            }
        });

        cmdBatal.setBackground(new java.awt.Color(153, 204, 255));
        cmdBatal.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        cmdBatal.setText("Batal");
        cmdBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBatalActionPerformed(evt);
            }
        });

        cmdKeluar.setBackground(new java.awt.Color(255, 51, 51));
        cmdKeluar.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        cmdKeluar.setText("Keluar");
        cmdKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeluarActionPerformed(evt);
            }
        });

        tblKel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Kel/Desa", "Nama Kel/Desa", "Kecamatan", "Kabupaten", "Kuota"
            }
        ));
        tblKel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKel);

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel7.setText("Tabel Kelurahan / Desa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(cmdTambah)
                        .addGap(18, 18, 18)
                        .addComponent(cmdSimpan))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKodeKelurahan, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtJumlahKuota, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cmbKab, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbKec, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNamaKelurahan, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(cmdEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(cmdHapus)
                        .addGap(28, 28, 28)
                        .addComponent(cmdBatal)
                        .addGap(29, 29, 29)
                        .addComponent(cmdKeluar)
                        .addGap(36, 36, 36)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(253, 253, 253))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(txtKodeKelurahan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtNamaKelurahan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbKec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(43, 43, 43)
                                        .addComponent(jLabel3))
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbKab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtJumlahKuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdTambah)
                            .addComponent(cmdSimpan)
                            .addComponent(cmdEdit)
                            .addComponent(cmdHapus)
                            .addComponent(cmdBatal)
                            .addComponent(cmdKeluar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1299, 643));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbKecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKecActionPerformed
        // TODO add your handling code here:
        JComboBox cNamaKec = (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        sNamaKec = (String)cNamaKec.getSelectedItem();
    }//GEN-LAST:event_cmbKecActionPerformed

    private void cmbKabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKabActionPerformed
        // TODO add your handling code here:
        JComboBox cNamaKab = (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        sNamaKab = (String)cNamaKab.getSelectedItem();
    }//GEN-LAST:event_cmbKabActionPerformed

    private void cmdTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTambahActionPerformed
        // TODO add your handling code here:
        aktif(true);
        setTombol(false);
        kosong();
    }//GEN-LAST:event_cmdTambahActionPerformed

    private void cmdSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdSimpanMouseClicked
        // TODO add your handling code here:
        String tKodeKel = txtKodeKelurahan.getText();
        String tNmKel=txtNamaKelurahan.getText();
        int jmlhKuota=Integer.parseInt(txtJumlahKuota.getText());
        try{
            
            if (edit==true)
            {
                 stm.executeUpdate("update kelurahan set nama_kel='"+tNmKel+"',nama_kec='"+sNamaKec+"',nama_kab='"+sNamaKab+"',kuota="+jmlhKuota+" where kode_kel='" +tKodeKel+ "'");
            }else                
            {
                stm.executeUpdate("INSERT INTO kelurahan VALUES('"+tKodeKel+"','"+tNmKel+"','"+sNamaKec+"','"+sNamaKab+"',"+jmlhKuota+")");               
            }
            tblKel.setModel(new DefaultTableModel(dataTable,header));
            baca_data();
            aktif(false);
            setTombol(true);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_cmdSimpanMouseClicked

    private void cmdEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditActionPerformed
        // TODO add your handling code here:
        edit=true;
        aktif(true);
        setTombol(false);
        txtKodeKelurahan.setEditable(false);
    }//GEN-LAST:event_cmdEditActionPerformed

    private void cmdHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdHapusActionPerformed
        // TODO add your handling code here:
        try{
        String sql="delete from kelurahan where nama_kel='" + txtNamaKelurahan.getText() + "'";
        stm.executeUpdate(sql);
        baca_data();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_cmdHapusActionPerformed

    private void cmdBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBatalActionPerformed
        // TODO add your handling code here:
        aktif(false);
        setTombol(true);
    }//GEN-LAST:event_cmdBatalActionPerformed

    private void cmdKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_cmdKeluarActionPerformed

    private void tblKelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKelMouseClicked
        // TODO add your handling code here:
        setField();
    }//GEN-LAST:event_tblKelMouseClicked

        private void dataFromDataBaseToComboBox(){
        try {
            String query = "SELECT * FROM kabupaten";
            Statement st = Con.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()) {                
                cmbKab.addItem(rs.getString("nama_kab"));
            }
            
            rs.last();
            int jumlahdata = rs.getRow();
            rs.first();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
        
        private void dataFromDataBaseToComboBox2(){
        try {
            String query = "SELECT * FROM kecamatan";
            Statement stmm = Con.createStatement();
            ResultSet rss = stmm.executeQuery(query);
            
            while (rss.next()) {                
                cmbKec.addItem(rss.getString("nama_kec"));
            }
            
            rss.last();
            int jumlahdata = rss.getRow();
            rss.first();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
        
    private void setField()
    {
        int row = tblKel.getSelectedRow();
        txtKodeKelurahan.setText((String)tblKel.getValueAt(row, 0));
        txtNamaKelurahan.setText((String)tblKel.getValueAt(row, 1));
        cmbKec.setSelectedItem((String)tblKel.getValueAt(row, 2));
        cmbKab.setSelectedItem((String)tblKel.getValueAt(row, 3));
        String jmlhKuota = Integer.toString((Integer)tblKel.getValueAt(row, 4));
        txtJumlahKuota.setText(jmlhKuota);
    }
            
    private void open_db()
    { 
        try{
        KoneksiMysql kon = new KoneksiMysql("localhost","root","","uas_pkkp");
        Con = kon.getConnection();
    //System.out.println("Berhasil ");
        }catch (Exception e) {
        System.out.println("Error : "+e);
        }
    }
    
    private void baca_data()
    {
        try{
            stm = Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            RsKel = stm.executeQuery("SELECT * FROM kelurahan ORDER BY kode_kel");
            
            ResultSetMetaData meta = RsKel.getMetaData();
            int col = meta.getColumnCount();
            int baris = 0;
            while(RsKel.next()) {
                baris = RsKel.getRow();
            }
            dataTable = new Object[baris][col];
            int x = 0;
            RsKel.beforeFirst();
            while(RsKel.next()) {
                dataTable[x][0] = RsKel.getString("kode_kel");
                dataTable[x][1] = RsKel.getString("nama_kel");
                dataTable[x][2] = RsKel.getString("nama_kec");
                dataTable[x][3] = RsKel.getString("nama_kab");
                dataTable[x][4] = RsKel.getInt("kuota");
                x++;
            }
            tblKel.setModel(new DefaultTableModel(dataTable,header));
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void kosong()
    {
        txtKodeKelurahan.setText("");
        txtNamaKelurahan.setText("");
        txtJumlahKuota.setText("");
    }
    
    //mengset aktif tidak isian data
    private void aktif(boolean x)
    {
        txtKodeKelurahan.setEditable(x);
        txtNamaKelurahan.setEditable(x);
        cmbKec.setEditable(x);
        cmbKab.setEditable(x);
        txtJumlahKuota.setEditable(x);
    }
    
    //mengset tombol on/off
    private void setTombol(boolean t)
    {
        cmdTambah.setEnabled(t);
        cmdEdit.setEnabled(t);
        cmdHapus.setEnabled(t);
        cmdSimpan.setEnabled(!t);
        cmdBatal.setEnabled(!t);
        cmdKeluar.setEnabled(t);
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
            java.util.logging.Logger.getLogger(Kelurahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Kelurahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Kelurahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Kelurahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Kelurahan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbKab;
    private javax.swing.JComboBox<String> cmbKec;
    private javax.swing.JButton cmdBatal;
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdHapus;
    private javax.swing.JButton cmdKeluar;
    private javax.swing.JButton cmdSimpan;
    private javax.swing.JButton cmdTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblKel;
    private javax.swing.JTextField txtJumlahKuota;
    private javax.swing.JTextField txtKodeKelurahan;
    private javax.swing.JTextField txtNamaKelurahan;
    // End of variables declaration//GEN-END:variables
}
