/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pkkp;

import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class SeleksiAdministrasi extends javax.swing.JFrame {

    /**
     * Creates new form SeleksiAdministrasi
     */
    Connection Con;
    Statement stm;
    double total=0;
    String sNik;
    String tanggal;
    String sBukti;
    String sLamaran;
    String sIjazah;
    String sKtp;
    String sSkck;
    String sKes;
    String sBebNar;
    String sPer;
    String sPas;
    
    DefaultTableModel tableModel = new DefaultTableModel( new Object [][] {},new String [] {"No Seleksi", "NIK","Nama","Keterangan","Kabupaten Asal", "Kabupaten Penempatan"});
    private SpinnerDateModel dateModel;
    private JSpinner.DateEditor dateEditor;
    
    public SeleksiAdministrasi() {
        initComponents();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM dd, yyyy");
        tgl.setDateFormat(sdf);
        open_db();
        inisialisasi_tabel();
        aktif(false);
        setTombol(true);
        dateModel = new SpinnerDateModel();
        txtTgl = new JSpinner(dateModel);
        dateEditor = new JSpinner.DateEditor(txtTgl, "yyyy/MM/dd");
        txtTgl.setEditor(dateEditor);
        //txtTgl.setEditor(new JSpinner.DateEditor(txtTgl,"yyyy/MM/dd"));   
        baca_peserta();
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
    
    //methohd baca data peserta
    private void baca_peserta()
    {
        try{
        stm=Con.createStatement();
        ResultSet rs=stm.executeQuery("select nik,nama_lengkap,kab_asal , kab_penempatan from peserta");
        rs.beforeFirst();
        while(rs.next())
        {
        cmbNik.addItem(rs.getString(1).trim());
        txtNama.setText(rs.getString(2).trim());
        txtAsal.setText(rs.getString(3).trim());
        txtPen.setText(rs.getString(4).trim());
        }
        rs.close();
        }
        catch(SQLException e)
        {
        System.out.println("Error : "+e);
        }
    }
    
    private void detail_peserta(String x)
    {
        try{
        stm=Con.createStatement();
        ResultSet rs=stm.executeQuery("select nama_lengkap,kab_asal , kab_penempatan from peserta where nik ='"+x+"'");
        rs.beforeFirst();
        while(rs.next())
        {
        txtNama.setText(rs.getString(1).trim());
        txtAsal.setText(rs.getString(2).trim());
        txtPen.setText(rs.getString(3).trim());
        }
        rs.close();
        }
        catch(SQLException e)
        {
        System.out.println("Error : "+e);
        }
    }
    
    
        //method buat nomor beli otomatis
    private void nomor_seleksi()
    {
        try{
        stm=Con.createStatement();
        ResultSet rs=stm.executeQuery("select no_seleksi from seleksi_administrasi");
        int brs=0;
        while(rs.next())
        {
        brs=rs.getRow();
        }
        if(brs==0)
        txtNoSeleksi.setText("1");
        else
        {int nom=brs+1;
        txtNoSeleksi.setText(Integer.toString(nom));
        }
        rs.close();
        }
        catch(SQLException e)
        {
        System.out.println("Error : "+e);
        }
    }
    
         //method simpan detail jual di tabel
    private void simpan_ditabel()
    {
        try{
        String tNoSeleksi = txtNoSeleksi.getText();
        String tNik=cmbNik.getSelectedItem().toString();
        String tNama=txtNama.getText();
        String tKet=txtKet.getText();
        String tAsal=txtAsal.getText();
        String tPen=txtPen.getText();
        tableModel.addRow(new Object[]{tNoSeleksi,tNik,tNama,tKet,tAsal,tPen});
        inisialisasi_tabel();
        }
        catch(Exception e)
        {
        System.out.println("Error : "+e);
        }
    }
    
    //method simpan transaksi penjualan pada table di MySql
    private void simpan_transaksi()
    {
        try{
        String xnoseleksi=txtNoSeleksi.getText();
        format_tanggal();
        String xnik=cmbNik.getSelectedItem().toString();
        String xnama = txtNama.getText();
        String xkett = txtKet.getText();
        String xasal = txtAsal.getText();
        String xpenn = txtPen.getText();
        String msql="insert into seleksi_administrasi values('"+xnoseleksi+"','"+tanggal+"','"+xnik+"','"+xnama+"', '"+xkett+"')";
        stm.executeUpdate(msql);
        String zsql="insert into administrasi values('"+xnoseleksi+"','"+xnik+"','"+xnama+"', '"+xkett+"','"+xasal+"','"+xpenn+"')";
        stm.executeUpdate(zsql);
        
       // for(int i=0;i<tblPeserta.getRowCount();i++)
       // {
       // String xnosel=(String)tblPeserta.getValueAt(i,0);
       // String xnikk=(String)tblPeserta.getValueAt(i,1);
       // String xnm=(String)tblPeserta.getValueAt(i,2);
        //String xketerangan = (String)tblPeserta.getValueAt(i,3);
        //String xasal = (String)tblPeserta.getValueAt(i,4);
        //String xpen = (String)tblPeserta.getValueAt(i,5);

        //}
        }
        catch(SQLException e)
        {
        System.out.println("Error : "+e);
        }
    }
    


//method set model tabel
    public void inisialisasi_tabel()
    {
        tblPeserta.setModel(tableModel);
    }
    
    //method pengkosongan isian
    private void kosong()
    {
        txtNama.setText("");
        txtAsal.setText("");
        txtPen.setText("");
    }

    //method membuat format tanggal sesuai dengan MySQL
    private void format_tanggal()
    {
        String DATE_FORMAT = "yyyy-MM-dd";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        Calendar c1 = Calendar.getInstance();
        int year=c1.get(Calendar.YEAR);
        int month=c1.get(Calendar.MONTH)+1;
        int day=c1.get(Calendar.DAY_OF_MONTH);
        tanggal=Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
    }
    
    //method aktif on/off isian
    private void aktif(boolean x)
    {
        cmbNik.setEnabled(x);
    }
    
    //method set tombol on/off
    private void setTombol(boolean t)
    {
        cmdTambah.setEnabled(t);
        cmdSimpan.setEnabled(t);
        cmdGenerate.setEnabled(!t);
        cmdBatal.setEnabled(!t);
        cmdKeluar.setEnabled(t);
        cmdClear.setEnabled(!t);
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
     

    
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNoSeleksi = new javax.swing.JTextField();
        tgl = new datechooser.beans.DateChooserCombo();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbNik = new javax.swing.JComboBox<>();
        txtNama = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPen = new javax.swing.JTextField();
        txtAsal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cmbBukti = new javax.swing.JComboBox<>();
        cmbLamar = new javax.swing.JComboBox<>();
        cmbIjazah = new javax.swing.JComboBox<>();
        cmbKtp = new javax.swing.JComboBox<>();
        cmbSkck = new javax.swing.JComboBox<>();
        cmbKes = new javax.swing.JComboBox<>();
        cmbBebNar = new javax.swing.JComboBox<>();
        cmbPer = new javax.swing.JComboBox<>();
        cmbPas = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        txtKet = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPeserta = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextArea();
        cmdTambah = new javax.swing.JButton();
        cmdBatal = new javax.swing.JButton();
        cmdSimpan = new javax.swing.JButton();
        cmdClear = new javax.swing.JButton();
        cmdCetak = new javax.swing.JButton();
        cmdKeluar = new javax.swing.JButton();
        cmdGenerate = new javax.swing.JButton();
        txtTgl = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setText("No Seleksi");

        txtNoSeleksi.setEditable(false);

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setText("Tanggal");

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setText("NIK Peserta");

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setText("Nama Peserta");

        cmbNik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNikActionPerformed(evt);
            }
        });

        txtNama.setEditable(false);

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setText("Kab. Penempatan");

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel6.setText("Kab. Asal");

        txtPen.setEditable(false);

        txtAsal.setEditable(false);

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel7.setText("Bukti Pendaftaran ");

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel8.setText("Surat Lamaran");

        jLabel9.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel9.setText("Fotocopy Ijazah");

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel10.setText("SELEKSI ADMINISTRASI");

        jLabel11.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel11.setText("Fotocopy KTP");

        jLabel12.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel12.setText("Fotocopy SKCK");

        jLabel13.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel13.setText("Surat Kesehatan");

        jLabel14.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel14.setText("Surat Bebas Narkoba");

        jLabel15.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel15.setText("Surat Pernyataan");

        jLabel17.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel17.setText("Pas Foto 3x4");

        cmbBukti.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "YA", "TIDAK" }));
        cmbBukti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBuktiActionPerformed(evt);
            }
        });

        cmbLamar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "YA", "TIDAK" }));
        cmbLamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbLamarActionPerformed(evt);
            }
        });

        cmbIjazah.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "YA", "TIDAK" }));
        cmbIjazah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbIjazahActionPerformed(evt);
            }
        });

        cmbKtp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "YA", "TIDAK" }));
        cmbKtp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKtpActionPerformed(evt);
            }
        });

        cmbSkck.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "YA", "TIDAK" }));
        cmbSkck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSkckActionPerformed(evt);
            }
        });

        cmbKes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "YA", "TIDAK" }));
        cmbKes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKesActionPerformed(evt);
            }
        });

        cmbBebNar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "YA", "TIDAK" }));
        cmbBebNar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBebNarActionPerformed(evt);
            }
        });

        cmbPer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "YA", "TIDAK" }));
        cmbPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPerActionPerformed(evt);
            }
        });

        cmbPas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "YA", "TIDAK" }));
        cmbPas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPasActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel18.setText("Keterangan");

        txtKet.setEditable(false);
        txtKet.setForeground(new java.awt.Color(255, 51, 0));
        txtKet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKetActionPerformed(evt);
            }
        });

        tblPeserta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No Seleksi", "NIK", "Nama", "Keterangan", "Kabupaten Asal", "Kabupaten Penempatan"
            }
        ));
        jScrollPane1.setViewportView(tblPeserta);

        text.setColumns(20);
        text.setRows(5);
        jScrollPane2.setViewportView(text);

        cmdTambah.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        cmdTambah.setText("Tambah");
        cmdTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTambahActionPerformed(evt);
            }
        });

        cmdBatal.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        cmdBatal.setText("Batal");
        cmdBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBatalActionPerformed(evt);
            }
        });

        cmdSimpan.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        cmdSimpan.setText("Simpan");
        cmdSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSimpanActionPerformed(evt);
            }
        });

        cmdClear.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        cmdClear.setText("Clear");
        cmdClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdClearActionPerformed(evt);
            }
        });

        cmdCetak.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        cmdCetak.setText("Cetak");
        cmdCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCetakActionPerformed(evt);
            }
        });

        cmdKeluar.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        cmdKeluar.setText("Keluar");
        cmdKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeluarActionPerformed(evt);
            }
        });

        cmdGenerate.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        cmdGenerate.setText("Generate");
        cmdGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdGenerateActionPerformed(evt);
            }
        });

        txtTgl.setModel(new javax.swing.SpinnerDateModel());
        txtTgl.setEditor(new javax.swing.JSpinner.DateEditor(txtTgl, "dd-MM-yyyy"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel10))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTgl, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                                    .addComponent(txtNoSeleksi))
                                .addGap(29, 29, 29)
                                .addComponent(tgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(49, 49, 49)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbNik, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(94, 94, 94))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel17))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(cmbPer, 0, 83, Short.MAX_VALUE)
                                            .addComponent(cmbBebNar, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmbKes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmbPas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(51, 51, 51)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cmdGenerate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmdCetak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmdBatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmdKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cmbSkck, javax.swing.GroupLayout.Alignment.LEADING, 0, 83, Short.MAX_VALUE)
                                        .addComponent(cmbKtp, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmdTambah)
                                .addGap(18, 18, 18)
                                .addComponent(cmdSimpan)
                                .addGap(18, 18, 18)
                                .addComponent(cmdClear))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel5))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(20, 20, 20)))
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPen, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cmbIjazah, javax.swing.GroupLayout.Alignment.LEADING, 0, 81, Short.MAX_VALUE)
                                        .addComponent(cmbLamar, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbBukti, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel18))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAsal, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtKet, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(211, 211, 211))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 779, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(26, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 821, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNoSeleksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(cmbNik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(txtKet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41)
                                .addComponent(jScrollPane2))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtPen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(cmbBukti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(cmbLamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbIjazah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(cmbKtp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(cmbSkck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(cmbKes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmdGenerate))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(cmbBebNar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmdCetak))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(cmbPer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmdBatal))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(cmbPas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmdKeluar))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmdTambah)
                                    .addComponent(cmdSimpan)
                                    .addComponent(cmdClear))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(18, 18, 18))
        );

        setSize(new java.awt.Dimension(1860, 873));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbBuktiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuktiActionPerformed
        // TODO add your handling code here:
        JComboBox cBukti = (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        sBukti = (String)cBukti.getSelectedItem();
    }//GEN-LAST:event_cmbBuktiActionPerformed

    private void cmbSkckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSkckActionPerformed
        // TODO add your handling code here:
        JComboBox cSkck = (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        sSkck = (String)cSkck.getSelectedItem();
    }//GEN-LAST:event_cmbSkckActionPerformed

    private void txtKetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKetActionPerformed

    private void cmdGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGenerateActionPerformed
        // TODO add your handling code here:
        if (sBukti.equals("YA") && sLamaran.equals("YA") && sIjazah.equals("YA") && sKtp.equals("YA") && sSkck.equals("YA") && sKes.equals("YA") && sBebNar.equals("YA") && sPer.equals("YA") && sPas.equals("YA")) 
        {
            txtKet.setText("LOLOS");
        } else {
            txtKet.setText("TIDAK LOLOS");
        }
        setTombol(true);
        cmdTambah.setEnabled(false);
        cmdCetak.setEnabled(true);
    }//GEN-LAST:event_cmdGenerateActionPerformed

    private void cmbNikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNikActionPerformed
        // TODO add your handling code here:
       JComboBox cNik = (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        sNik = (String)cNik.getSelectedItem();
        //baca_konsumen();
        detail_peserta(sNik);
    }//GEN-LAST:event_cmbNikActionPerformed

    private void cmbLamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbLamarActionPerformed
        // TODO add your handling code here:
        JComboBox cLamar = (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        sLamaran = (String)cLamar.getSelectedItem();
    }//GEN-LAST:event_cmbLamarActionPerformed

    private void cmbIjazahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbIjazahActionPerformed
        // TODO add your handling code here:
        JComboBox cIjazah = (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        sIjazah = (String)cIjazah.getSelectedItem();
    }//GEN-LAST:event_cmbIjazahActionPerformed

    private void cmbKtpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKtpActionPerformed
        // TODO add your handling code here:
        JComboBox cKtp = (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        sKtp = (String)cKtp.getSelectedItem();
    }//GEN-LAST:event_cmbKtpActionPerformed

    private void cmbKesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKesActionPerformed
        // TODO add your handling code here:
        JComboBox cKes = (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        sKes = (String)cKes.getSelectedItem();
    }//GEN-LAST:event_cmbKesActionPerformed

    private void cmbBebNarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBebNarActionPerformed
        // TODO add your handling code here:
               JComboBox cBebNar = (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        sBebNar = (String)cBebNar.getSelectedItem();
    }//GEN-LAST:event_cmbBebNarActionPerformed

    private void cmbPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPerActionPerformed
        // TODO add your handling code here:
        JComboBox cPer = (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        sPer = (String)cPer.getSelectedItem();
    }//GEN-LAST:event_cmbPerActionPerformed

    private void cmbPasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPasActionPerformed
        // TODO add your handling code here:
        JComboBox cPas = (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        sPas = (String)cPas.getSelectedItem();
    }//GEN-LAST:event_cmbPasActionPerformed

    private void cmdTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTambahActionPerformed
        // TODO add your handling code here:
        kosong();
        aktif(true);
        setTombol(false);
        nomor_seleksi();
    }//GEN-LAST:event_cmdTambahActionPerformed

    private void cmdSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSimpanActionPerformed
        // TODO add your handling code here:
        simpan_ditabel();
        simpan_transaksi();
        txtKet.setText("");
    }//GEN-LAST:event_cmdSimpanActionPerformed

    private void cmdClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdClearActionPerformed
        // TODO add your handling code here:
        kosong();
    }//GEN-LAST:event_cmdClearActionPerformed

    private void cmdBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBatalActionPerformed
        // TODO add your handling code here:
        aktif(false);
        setTombol(true);
    }//GEN-LAST:event_cmdBatalActionPerformed

    private void cmdKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_cmdKeluarActionPerformed

    private void cmdCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCetakActionPerformed
        // TODO add your handling code here:
        String ctk="Seleksi Administrasi\nNo Seleksi\nNo:"+txtNoSeleksi.getText()+"\nTanggal : "+tanggal;
        ctk=ctk+"\n"+"--------------------------------------------------------------------------------------------------------------------------------------------------";
        ctk=ctk+"\n"+"NIK\t\tNama\t\tKeterangan\tKab. Asal\tKab. Penempatan";
        ctk=ctk+"\n"+"--------------------------------------------------------------------------------------------------------------------------------------------------";
        for(int i=0;i<tblPeserta.getRowCount();i++)
        {
            String xnik=(String)tblPeserta.getValueAt(i,1);
            String xnama=(String)tblPeserta.getValueAt(i,2);
            String xket=(String)tblPeserta.getValueAt(i,3);
            String xasal=(String)tblPeserta.getValueAt(i,4);
            String xpen=(String)tblPeserta.getValueAt(i,5);
            ctk=ctk+"\n"+xnik+"\t"+xnama+"\t\t"+xket+"\t"+xasal+"\t"+xpen;
        }
        ctk=ctk+"\n"+"--------------------------------------------------------------------------------------------------------------------------------------------------";
        text.setText(ctk);
        String headerField="";
        String footerField="";
        MessageFormat header = new MessageFormat(headerField);
        MessageFormat footer = new MessageFormat(footerField);
        boolean interactive = true;//interactiveCheck.isSelected();
        boolean background = true;//backgroundCheck.isSelected();
        SeleksiAdministrasi.PrintingTask task = new SeleksiAdministrasi.PrintingTask(header, footer,interactive);
        if (background) {
            task.execute();
        } else {
            task.run();
        }
        setTombol(false);
    }//GEN-LAST:event_cmdCetakActionPerformed

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
            java.util.logging.Logger.getLogger(SeleksiAdministrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SeleksiAdministrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SeleksiAdministrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SeleksiAdministrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SeleksiAdministrasi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbBebNar;
    private javax.swing.JComboBox<String> cmbBukti;
    private javax.swing.JComboBox<String> cmbIjazah;
    private javax.swing.JComboBox<String> cmbKes;
    private javax.swing.JComboBox<String> cmbKtp;
    private javax.swing.JComboBox<String> cmbLamar;
    private javax.swing.JComboBox<String> cmbNik;
    private javax.swing.JComboBox<String> cmbPas;
    private javax.swing.JComboBox<String> cmbPer;
    private javax.swing.JComboBox<String> cmbSkck;
    private javax.swing.JButton cmdBatal;
    private javax.swing.JButton cmdCetak;
    private javax.swing.JButton cmdClear;
    private javax.swing.JButton cmdGenerate;
    private javax.swing.JButton cmdKeluar;
    private javax.swing.JButton cmdSimpan;
    private javax.swing.JButton cmdTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JTable tblPeserta;
    private javax.swing.JTextArea text;
    private datechooser.beans.DateChooserCombo tgl;
    private javax.swing.JTextField txtAsal;
    private javax.swing.JTextField txtKet;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNoSeleksi;
    private javax.swing.JTextField txtPen;
    private javax.swing.JSpinner txtTgl;
    // End of variables declaration//GEN-END:variables
}
