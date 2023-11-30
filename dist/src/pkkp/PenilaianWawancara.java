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
public class PenilaianWawancara extends javax.swing.JFrame {

    /**
     * Creates new form PenilaianWawancara
     */
    Connection Con;
    Statement stm;
    String tgl1;
    String pen;
    String nm;
    String noSel;
    
    DefaultTableModel tableModel = new DefaultTableModel( new Object [][] {},new String [] {"No Seleksi", "Nama","Kab. Penempatan","Total Point"});
    
    public PenilaianWawancara() {
        initComponents();
        open_db();
        baca_peserta();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        txtTgl.setDateFormat(sdf);
        inisialisasi_tabel();
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
        
        xpp=Integer.parseInt(txtMotivasi.getText());
        xdaerah=Integer.parseInt(txtPimpin.getText());
        xpmd=Integer.parseInt(txtPelopor.getText());
        xdes=Integer.parseInt(txtWira.getText());
        xpember=Integer.parseInt(txtPoten.getText());
        
        xtot=( xpp + xdaerah + xpmd + xdes + xpember)*100/50;
        String xtotal=Integer.toString(xtot);
        txtTotal.setText(xtotal);
    }
    
    public void pesertaTerpilih(){
        SelectPesertaWawancara fPs = new SelectPesertaWawancara();
        fPs.fWa = this;
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
       tblWawan.setModel(tableModel);
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
        String msql="insert into seleksi_wawancara values('"+xnoseleksi+"','"+tgl1+"','"+xnama+"','"+xpen+"', "+xtot+")";
        stm.executeUpdate(msql);
        String zsql="insert into wawancara values('"+xnoseleksi+"','"+xnama+"','"+xpen+"',"+xtot+")";
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
        txtMotivasi.setText("");
        txtPimpin.setText("");
        txtPelopor.setText("");
        txtWira.setText("");
        txtPoten.setText("");
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
        txtNoSeleksi = new javax.swing.JTextField();
        txtTgl = new datechooser.beans.DateChooserCombo();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtPen = new javax.swing.JTextField();
        cmdPilihPeserta = new javax.swing.JButton();
        cmdClear = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
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
        txtMotivasi = new javax.swing.JTextField();
        txtPimpin = new javax.swing.JTextField();
        txtPelopor = new javax.swing.JTextField();
        txtWira = new javax.swing.JTextField();
        txtPoten = new javax.swing.JTextField();
        cmdHitung = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        cmdKeluar = new javax.swing.JButton();
        cmdBatal = new javax.swing.JButton();
        cmdCetak = new javax.swing.JButton();
        cmdSimpan = new javax.swing.JButton();
        cmdTambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblWawan = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel1.setText("PENILAIAN WAWANCARA");

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setText("No Seleksi");

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setText("Tanggal Seleksi");

        txtNoSeleksi.setEditable(false);

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setText("Nama Peserta");

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setText("Kab. Penempatan");

        txtNama.setEditable(false);

        txtPen.setEditable(false);

        cmdPilihPeserta.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        cmdPilihPeserta.setText("Pilih Peserta");
        cmdPilihPeserta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPilihPesertaActionPerformed(evt);
            }
        });

        cmdClear.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        cmdClear.setText("CLEAR");
        cmdClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdClearActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel6.setText("POIN PENILAIAN");

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel8.setText("Motivasi dan Minat Terhadap Program PKKP");

        jLabel9.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel9.setText("Kepemimpinan dan Kerja Sama");

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel10.setText("Kepeloporan dan Kesukarelawan");

        jLabel11.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel11.setText("Potensi Kewirausahaan");

        jLabel12.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel12.setText("Potensi Akademik");

        jLabel13.setFont(new java.awt.Font("sansserif", 3, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 51, 51));
        jLabel13.setText("* Max 10 Point");

        jLabel14.setFont(new java.awt.Font("sansserif", 3, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 51, 51));
        jLabel14.setText("* Max 10 Point");

        jLabel15.setFont(new java.awt.Font("sansserif", 3, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 51, 51));
        jLabel15.setText("* Max 10 Point");

        jLabel16.setFont(new java.awt.Font("sansserif", 3, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 51, 51));
        jLabel16.setText("* Max 10 Point");

        jLabel17.setFont(new java.awt.Font("sansserif", 3, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 51, 51));
        jLabel17.setText("* Max 10 Point");

        cmdHitung.setText("HITUNG");
        cmdHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdHitungActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel18.setText("TOTAL POINT");

        txtTotal.setEditable(false);

        cmdKeluar.setText("Keluar");
        cmdKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeluarActionPerformed(evt);
            }
        });

        cmdBatal.setText("Batal");
        cmdBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBatalActionPerformed(evt);
            }
        });

        cmdCetak.setText("Cetak");
        cmdCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCetakActionPerformed(evt);
            }
        });

        cmdSimpan.setText("Simpan");
        cmdSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSimpanActionPerformed(evt);
            }
        });

        cmdTambah.setText("Tambah");
        cmdTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTambahActionPerformed(evt);
            }
        });

        tblWawan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No Seleksi", "Nama", "Kab. Penempatan", "Total Point"
            }
        ));
        jScrollPane1.setViewportView(tblWawan);

        text.setColumns(20);
        text.setRows(5);
        jScrollPane2.setViewportView(text);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
                        .addComponent(jSeparator2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(cmdPilihPeserta, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtTgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtNoSeleksi))
                                        .addGap(180, 180, 180)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNama)
                                            .addComponent(txtPen, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addComponent(cmdClear, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel16))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel18)
                                            .addGap(213, 213, 213)
                                            .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                                        .addComponent(cmdHitung, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel8)
                                                .addComponent(jLabel9)
                                                .addComponent(jLabel10)
                                                .addComponent(jLabel11)
                                                .addComponent(jLabel12))
                                            .addGap(51, 51, 51)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtMotivasi)
                                                .addComponent(txtPimpin)
                                                .addComponent(txtPelopor)
                                                .addComponent(txtWira)
                                                .addComponent(txtPoten, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))))
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel17))
                                .addGap(76, 76, 76)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmdBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmdKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmdCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmdSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmdTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNoSeleksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(txtPen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmdPilihPeserta)
                            .addComponent(cmdClear))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel6)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtMotivasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(txtPimpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtPelopor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(txtWira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(cmdTambah)
                                .addGap(59, 59, 59)
                                .addComponent(cmdSimpan)
                                .addGap(70, 70, 70)
                                .addComponent(cmdCetak)
                                .addGap(43, 43, 43)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel16)
                                .addGap(39, 39, 39))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(cmdBatal)
                                .addGap(24, 24, 24)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtPoten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmdHitung))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdKeluar)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1863, 837));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdPilihPesertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPilihPesertaActionPerformed
        // TODO add your handling code here:
        SelectPesertaWawancara fPs = new SelectPesertaWawancara();
        fPs.fWa = this;
        fPs.setVisible(true);
        fPs.setResizable(false);
    }//GEN-LAST:event_cmdPilihPesertaActionPerformed

    private void cmdClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdClearActionPerformed
        // TODO add your handling code here:
 
        txtMotivasi.setText("");
        txtPimpin.setText("");
        txtPelopor.setText("");
        txtWira.setText("");
        txtPoten.setText("");
        txtNoSeleksi.setText("");
        txtNama.setText("");
        txtPen.setText("");
        cmdTambah.setEnabled(false);
    }//GEN-LAST:event_cmdClearActionPerformed

    private void cmdHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdHitungActionPerformed
        // TODO add your handling code here:
        hitung_point();
        cmdSimpan.setEnabled(true);
    }//GEN-LAST:event_cmdHitungActionPerformed

    private void cmdTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTambahActionPerformed
        // TODO add your handling code here:
        txtMotivasi.setText("");
        txtPimpin.setText("");
        txtPelopor.setText("");
        txtWira.setText("");
        txtPoten.setText("");
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
        for(int i=0;i<tblWawan.getRowCount();i++)
        {
            String xnama=(String)tblWawan.getValueAt(i,1);
            String xpene=(String)tblWawan.getValueAt(i,2);
            int xpointt=(Integer)tblWawan.getValueAt(i,3);
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
        PenilaianWawancara.PrintingTask task = new PenilaianWawancara.PrintingTask(header, footer,interactive);
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
            java.util.logging.Logger.getLogger(PenilaianWawancara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PenilaianWawancara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PenilaianWawancara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PenilaianWawancara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PenilaianWawancara().setVisible(true);
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
    private javax.swing.JTable tblWawan;
    private javax.swing.JTextArea text;
    private javax.swing.JTextField txtMotivasi;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNoSeleksi;
    private javax.swing.JTextField txtPelopor;
    private javax.swing.JTextField txtPen;
    private javax.swing.JTextField txtPimpin;
    private javax.swing.JTextField txtPoten;
    private datechooser.beans.DateChooserCombo txtTgl;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtWira;
    // End of variables declaration//GEN-END:variables
}
