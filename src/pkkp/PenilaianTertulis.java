/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pkkp;

import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public final class PenilaianTertulis extends javax.swing.JFrame {

    /**
     * Creates new form PenilaianTertulis
     */
    Connection Con;
    Statement stm;
    String tgl1;
    String pen;
    String nm;
    String noSel;
    
    DefaultTableModel tableModel = new DefaultTableModel( new Object [][] {},new String [] {"No Seleksi", "Nama","Kab. Penempatan","Total Point"});
    
    public PenilaianTertulis() {
        initComponents();
        open_db();
        inisialisasi_tabel();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        txtTgl.setDateFormat(sdf);
        baca_peserta();
        setTombol(true);
        kosong();
    }
    
          //method buka database
    private void open_db()
    {
        try{
        KoneksiMysql kon = new KoneksiMysql
        ("localhost","root","","uas_pkkp");
        Con = kon.getConnection();
        //System.out.println("Berhasil ");
        }catch (Exception e) {
        System.out.println("Error : "+e);
        }
    }
    
    private void format_tanggal()
    {String DATE_FORMAT = "yyyy-MM-dd";
    //String DATE_FORMAT = "dd-MM-yyyy"; //Refer Java DOCS for formats 
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
    //  Calendar c1 = Calendar.getInstance();
  //  int year=c1.get(Calendar.YEAR);
   // int month=c1.get(Calendar.MONTH)+1;
    //int day=c1.get(Calendar.DAY_OF_MONTH);
    //tgl1=sdf.format(txtTgl.getValue());
    tgl1=txtTgl.getText();
    txtTgl.setDateFormat(sdf);
    //tgl2=Integer.toString(year)+"-"+Integer.toString(month)+"- "+Integer.toString(day);

}
    
    private void hitung_point()
    {
        int xpp, xdaerah, xpmd , xdes , xpember, xpelopor;
        int xtot;
        
        xpp=Integer.parseInt(txtPP.getText());
        xdaerah=Integer.parseInt(txtDaerah.getText());
        xpmd=Integer.parseInt(txtPemuda.getText());
        xdes=Integer.parseInt(txtDesa.getText());
        xpember=Integer.parseInt(txtPember.getText());
        xpelopor=Integer.parseInt(txtPelopor.getText());
        
        xtot=( xpp + xdaerah + xpmd + xdes + xpember + xpelopor )*100/60;
        String xtotal=Integer.toString(xtot);
        txtTotal.setText(xtotal);
    }
    
    
    public void pesertaTerpilih(){
        SelectPeserta fDp = new SelectPeserta();
        fDp.fTls = this;
        txtNoSeleksi.setText(noSel);
        txtNama.setText(nm);
        txtPen.setText(pen);
    }
   
         
         //method simpan detail jual di tabel
    private void simpan_ditabel()
    {
        try{
        String tNoSel=txtNoSeleksi.getText();
        String tNama=txtNama.getText();
        String tPen=txtPen.getText();
        int poin = Integer.parseInt(txtTotal.getText());
        tableModel.addRow(new Object[]{tNoSel,tNama,tPen,poin});
        inisialisasi_tabel();
        }
        catch(Exception e)
        {
        System.out.println("Error : "+e);
        }
    }
        //method set model tabel
    public void inisialisasi_tabel()
    {
       tblTulis.setModel(tableModel);
    }
    
        //method baca data barang
    private void baca_peserta()
    {
        try{
        stm=Con.createStatement();
        ResultSet rs=stm.executeQuery("select * from administrasi");
        rs.beforeFirst();
        while(rs.next())
        {
        }
        rs.close();
        }
        catch(SQLException e)
        {
        System.out.println("Error : "+e);
        }
    }
    
    
    //method simpan transaksi penjualan pada table di MySql
    private void simpan_transaksi()
    {
        try{
        format_tanggal();
        String xnoseleksi=txtNoSeleksi.getText();
        String xnama = txtNama.getText();
        String xpen = txtPen.getText();
        int xtot = Integer.parseInt(txtTotal.getText());
        //stm=Con.createStatement();
        String msql="insert into seleksi_tertulis values('"+xnoseleksi+"','"+tgl1+"','"+xnama+"','"+xpen+"', "+xtot+")";
        stm.executeUpdate(msql);
        String zsql="insert into tertulis values('"+xnoseleksi+"','"+xnama+"','"+xpen+"',"+xtot+")";
        stm.executeUpdate(zsql);
      //  for(int i=0;i<tblTulis.getRowCount();i++)
       // {
        //String xnosel=(String)tblTulis.getValueAt(i,0);
       // String xnm=(String)tblTulis.getValueAt(i,1);
       // String xpenn = (String)tblTulis.getValueAt(i,2);
       // int xpoint = (Integer)tblTulis.getValueAt(i,3);
        
        //}
        }
        catch(SQLException e)
        {
        System.out.println("Error : "+e);
        }
    }

    private void kosong() {  
        txtPP.setText("");
        txtDaerah.setText("");
        txtPemuda.setText("");
        txtDesa.setText("");
        txtPember.setText("");
        txtPelopor.setText("");
        txtNoSeleksi.setText("");
        txtNama.setText("");
        txtPen.setText("");
    
    }
    
    private class PrintingTask extends SwingWorker<Object, Object> {
    private final MessageFormat headerFormat;
    private final MessageFormat footerFormat;
    private final boolean interactive;
    private volatile boolean complete = false;
    private volatile String message;

    public PrintingTask(MessageFormat header, MessageFormat footer, boolean interactive) {
        this.headerFormat = header;
        this.footerFormat = footer;
        this.interactive = interactive;
    }

    @Override
    protected Object doInBackground() {
        try {
            complete = text.print(headerFormat, footerFormat, true, null , null, interactive);
            message = "Printing " + (complete ? "complete" : "canceled");
        } catch (PrinterException ex) {
            message = "Sorry, a printer error occurred";
        } catch (SecurityException ex) {
            message = "Sorry, cannot access the printer due to security reasons";
        }
        return null;
    }

    @Override
    protected void done() {
        //message(!complete, message);
    }

    }
    
    //method set tombol on/off
    private void setTombol(boolean t)
    {
        cmdTambah.setEnabled(t);
        cmdSimpan.setEnabled(t);
        cmdHitung.setEnabled(!t);
        cmdBatal.setEnabled(!t);
        cmdKeluar.setEnabled(t);
        cmdClear.setEnabled(!t);
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
        txtNoSeleksi = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtPen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmdPilihPeserta = new javax.swing.JButton();
        cmdClear = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtPP = new javax.swing.JTextField();
        txtDaerah = new javax.swing.JTextField();
        txtPemuda = new javax.swing.JTextField();
        txtDesa = new javax.swing.JTextField();
        txtPember = new javax.swing.JTextField();
        txtPelopor = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        cmdTambah = new javax.swing.JButton();
        cmdSimpan = new javax.swing.JButton();
        cmdCetak = new javax.swing.JButton();
        cmdBatal = new javax.swing.JButton();
        cmdKeluar = new javax.swing.JButton();
        cmdHitung = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTulis = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextArea();
        txtTgl = new datechooser.beans.DateChooserCombo();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("No Seleksi");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Nama Peserta");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Kab Penempatan");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Tanggal Seleksi");

        txtNoSeleksi.setEditable(false);

        txtNama.setEditable(false);
        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });

        txtPen.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("PENILAIAN TERTULIS");

        cmdPilihPeserta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmdPilihPeserta.setText("PILIH PESERTA");
        cmdPilihPeserta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPilihPesertaActionPerformed(evt);
            }
        });

        cmdClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmdClear.setText("CLEAR");
        cmdClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdClearActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText(" Peraturan Pemerintah No. 72 Th. 2005 tentang Desa ");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("UU No. 23 Th. 2014 tentang Pemerintah Daerah");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("UU No. 40 Th. 2009 tentang Kepemudaan");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("UU No. 6 Th. 2014 tentang Desa");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Materi Pemberdayaan Masyarakat Pedesaan");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("POIN PENILAIAN");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Materi Kepeloporan Pemuda dalam Masyarakat");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 51, 51));
        jLabel13.setText("* Max 10 Point");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 51, 51));
        jLabel14.setText("* Max 10 Point");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 51, 51));
        jLabel15.setText("* Max 10 Point");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 51, 51));
        jLabel16.setText("* Max 10 Point");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 51, 51));
        jLabel17.setText("* Max 10 Point");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 51, 51));
        jLabel18.setText("* Max 10 Point");

        txtPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPPActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("TOTAL POINT");

        txtTotal.setEditable(false);

        cmdTambah.setText("Tambah");
        cmdTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTambahActionPerformed(evt);
            }
        });

        cmdSimpan.setText("Simpan");
        cmdSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSimpanActionPerformed(evt);
            }
        });

        cmdCetak.setText("Cetak");
        cmdCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCetakActionPerformed(evt);
            }
        });

        cmdBatal.setText("Batal");
        cmdBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBatalActionPerformed(evt);
            }
        });

        cmdKeluar.setText("Keluar");
        cmdKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeluarActionPerformed(evt);
            }
        });

        cmdHitung.setText("HITUNG");
        cmdHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdHitungActionPerformed(evt);
            }
        });

        tblTulis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No Seleksi", "Nama Peserta", "Kab. Penempatan", "Total Point"
            }
        ));
        jScrollPane1.setViewportView(tblTulis);

        text.setColumns(20);
        text.setRows(5);
        jScrollPane2.setViewportView(text);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmdPilihPeserta)
                                .addGap(32, 32, 32)
                                .addComponent(cmdClear, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel1))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNoSeleksi, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(177, 177, 177)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtPen))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(30, 30, 30)
                                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel14)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel6)
                                                    .addComponent(jLabel7)
                                                    .addComponent(jLabel8)
                                                    .addComponent(jLabel9)
                                                    .addComponent(jLabel12)
                                                    .addComponent(jLabel13))
                                                .addGap(41, 41, 41)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txtPP)
                                                    .addComponent(txtDaerah)
                                                    .addComponent(txtPemuda)
                                                    .addComponent(txtDesa)
                                                    .addComponent(txtPember)
                                                    .addComponent(txtPelopor, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel18)
                                            .addComponent(cmdHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel19)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(56, 56, 56)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(cmdCetak, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmdSimpan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmdTambah, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                            .addComponent(cmdKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmdBatal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtNoSeleksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(txtPen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdPilihPeserta)
                            .addComponent(cmdClear))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtPP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtDaerah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdTambah)
                                .addGap(13, 13, 13)))
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtPemuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmdSimpan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtDesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmdCetak))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtPember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmdBatal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtPelopor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmdKeluar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdHitung)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1823, 803));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdPilihPesertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPilihPesertaActionPerformed
        // TODO add your handling code here:
        SelectPeserta fDP = new SelectPeserta();
        fDP.fTls = this;
        fDP.setVisible(true);
        fDP.setResizable(false);
    }//GEN-LAST:event_cmdPilihPesertaActionPerformed

    private void cmdClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdClearActionPerformed
        // TODO add your handling code here:
        txtPP.setText("");
        txtDaerah.setText("");
        txtPemuda.setText("");
        txtDesa.setText("");
        txtPember.setText("");
        txtPelopor.setText("");
        txtNoSeleksi.setText("");
        txtNama.setText("");
        txtPen.setText("");
        cmdTambah.setEnabled(false);
    }//GEN-LAST:event_cmdClearActionPerformed

    private void txtPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPPActionPerformed

    private void cmdHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdHitungActionPerformed
        // TODO add your handling code here:
        hitung_point();
        cmdSimpan.setEnabled(true);
    }//GEN-LAST:event_cmdHitungActionPerformed

    private void cmdTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTambahActionPerformed
        // TODO add your handling code here:
        txtPP.setText("");
        txtDaerah.setText("");
        txtPemuda.setText("");
        txtDesa.setText("");
        txtPember.setText("");
        txtPelopor.setText("");
        txtNoSeleksi.setText("");
        txtNama.setText("");
        txtPen.setText("");
        cmdTambah.setEnabled(false);
        cmdHitung.setEnabled(true);
        cmdSimpan.setEnabled(false);
        cmdCetak.setEnabled(false);
        cmdClear.setEnabled(true);
    }//GEN-LAST:event_cmdTambahActionPerformed

    private void cmdSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSimpanActionPerformed
        // TODO add your handling code here:
        simpan_ditabel();
        simpan_transaksi();
        cmdCetak.setEnabled(true);
        cmdBatal.setEnabled(true);
        txtTotal.setText("");
    }//GEN-LAST:event_cmdSimpanActionPerformed

    private void cmdCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCetakActionPerformed
        // TODO add your handling code here:
        String ctk="Seleksi Wawancara\nNo Seleksi\nNo:"+txtNoSeleksi.getText()+"\nTanggal : "+txtTgl.getText();
        ctk=ctk+"\n"+"--------------------------------------------------------------------------------------------------------------------------------------------------";
        ctk=ctk+"\n"+"Nama\tKab.Penempatan\tTotal Point";
        ctk=ctk+"\n"+"--------------------------------------------------------------------------------------------------------------------------------------------------";
        for(int i=0;i<tblTulis.getRowCount();i++)
        {
            String xnama=(String)tblTulis.getValueAt(i,1);
            String xpene=(String)tblTulis.getValueAt(i,2);
            int xpointt=(Integer)tblTulis.getValueAt(i,3);
            ctk=ctk+"\n"+xnama+"\t"+xpene+"\t\t"+xpointt;
        }
        ctk=ctk+"\n"+"--------------------------------------------------------------------------------------------------------------------------------------------------";
        text.setText(ctk);
        String headerField="";
        String footerField="";
        MessageFormat header = new MessageFormat(headerField);
        MessageFormat footer = new MessageFormat(footerField);
        boolean interactive = true;//interactiveCheck.isSelected();
        boolean background = true;//backgroundCheck.isSelected();
        PenilaianTertulis.PrintingTask task = new PenilaianTertulis.PrintingTask(header, footer,interactive);
        if (background) {
            task.execute();
        } else {
            task.run();
        }
        setTombol(false);
    }//GEN-LAST:event_cmdCetakActionPerformed

    private void cmdBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBatalActionPerformed
        // TODO add your handling code here:
        setTombol(true);
    }//GEN-LAST:event_cmdBatalActionPerformed

    private void cmdKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_cmdKeluarActionPerformed

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

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
            java.util.logging.Logger.getLogger(PenilaianTertulis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PenilaianTertulis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PenilaianTertulis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PenilaianTertulis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PenilaianTertulis().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdBatal;
    private javax.swing.JButton cmdCetak;
    private javax.swing.JButton cmdClear;
    private javax.swing.JButton cmdHitung;
    private javax.swing.JButton cmdKeluar;
    private javax.swing.JButton cmdPilihPeserta;
    private javax.swing.JButton cmdSimpan;
    private javax.swing.JButton cmdTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tblTulis;
    private javax.swing.JTextArea text;
    private javax.swing.JTextField txtDaerah;
    private javax.swing.JTextField txtDesa;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNoSeleksi;
    private javax.swing.JTextField txtPP;
    private javax.swing.JTextField txtPelopor;
    private javax.swing.JTextField txtPember;
    private javax.swing.JTextField txtPemuda;
    private javax.swing.JTextField txtPen;
    private datechooser.beans.DateChooserCombo txtTgl;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
