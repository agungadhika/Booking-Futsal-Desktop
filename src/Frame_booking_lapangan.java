/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.util.HashMap;
import javax.swing.Timer;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;




/**
 *
 * @author agung
 */
public class Frame_booking_lapangan extends javax.swing.JFrame implements Runnable {
    
    
    Thread T;
    boolean kanan = true;
    boolean kiri = false;
    boolean berjalan = true;
    int x,y;
    
    private DefaultTableModel model;


    public void setTanggal(){
    java.util.Date skrg = new java.util.Date();
    java.text.SimpleDateFormat kal = new
    java.text.SimpleDateFormat("dd/MM/yyyy");
    lbltanggal.setText(kal.format(skrg));
    }
    
    public void clearFields(){
    kode.setText(null);
    lapangan1.setText(null);
    namapemesan.setText(null);
    nohp.setText(null);
    namakasir.setText(null);
    tanggal.setText(null);
    mulai.setText(null);
    selesai.setText(null);
    lamabermain.setText(null);
    hargasewa.setText(null);
    totalbayar.setText(null);
    pembayaran.setSelectedItem(null);
    lamabermain.setText(null);
    }

  public void setJam(){
    ActionListener taskPerformer = new ActionListener() {

    public void actionPerformed(ActionEvent evt) {
        String nol_jam = "", nol_menit = "",nol_detik = "";

        java.util.Date dateTime = new java.util.Date();
            int nilai_jam = dateTime.getHours();
            int nilai_menit = dateTime.getMinutes();
            int nilai_detik = dateTime.getSeconds();

            if(nilai_jam <= 9) nol_jam= "0";
            if(nilai_menit <= 9) nol_menit= "0";
            if(nilai_detik <= 9) nol_detik= "0";

            String waktu = nol_jam + Integer.toString(nilai_jam);
            String menit = nol_menit + Integer.toString(nilai_menit);
            String detik = nol_detik + Integer.toString(nilai_detik);

            lblwaktu.setText(waktu+":"+menit+":"+detik+"");
        }
    };
new Timer(1000, taskPerformer).start();
}
    
    /**
     * Creates new form Frame_booking_lapangan
     */
    
    public Frame_booking_lapangan() {
        initComponents();
        setTanggal();
        setJam();
        
        this.setTitle("Welcome To TEKNIK FUTSAL STADIUM");
                this.setLocationRelativeTo(this);
                x = LBJALAN.getX();
                y = LBJALAN.getY();
        
                T = new Thread(this);
                T.start();
 
                
        this.setLocationRelativeTo(null);
         model = new DefaultTableModel ( );
             tblbooking.setModel(model);
             model.addColumn("Kode");
             model.addColumn("Nama Kasir");
             model.addColumn("Lapangan");
             model.addColumn("Nama Pemesan");
             model.addColumn("No Handphone");
             model.addColumn("Status");
             model.addColumn("Mulai");
             model.addColumn("Berakhir");
             model.addColumn("Tanggal");
             model.addColumn("Lama Bermain");
             model.addColumn("Harga");
             model.addColumn("Total");
             model.addColumn("Pembayaran");
        
      
        
        pembayaran.addItem("Lunas");
        pembayaran.addItem("Belum Lunas");
        getData();
    }
         public void JumlahKarakter(KeyEvent e) {
        if (kode.getText().length() == 6) {
            e.consume();
            //Pesan Dialog Boleh Di Hapus Ini Hanya Sebagai Contoh
            JOptionPane.showMessageDialog(null, "Maksimal 5 Karakter", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }else if(lapangan1.getText().length() == 4){
            e.consume();
            JOptionPane.showMessageDialog(null, "Maksimal 3 Karakter", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
        else if(tanggal.getText().length() == 16){
            e.consume();
            JOptionPane.showMessageDialog(null, "Maksimal 15 Karakter", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
        else if(namakasir.getText().length() == 13){
            e.consume();
            JOptionPane.showMessageDialog(null, "Maksimal 12 Karakter", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
        else if(namapemesan.getText().length() == 13){
            e.consume();
            JOptionPane.showMessageDialog(null, "Maksimal 12 Karakter", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
        else if(nohp.getText().length() == 13){
            e.consume();
            JOptionPane.showMessageDialog(null, "Maksimal 12 Karakter", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }else if(mulai.getText().length() == 10){
            e.consume();
            JOptionPane.showMessageDialog(null, "Maksimal 4 Karakter", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }else if(selesai.getText().length() == 5){
            e.consume();
            JOptionPane.showMessageDialog(null, "Maksimal 4 Karakter", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
     public void FilterHuruf(KeyEvent a) {
        if (Character.isDigit(a.getKeyChar())) {
            a.consume();
            //Pesan Dialog Boleh Di Hapus Ini Hanya Sebagai Contoh
            JOptionPane.showMessageDialog(null, "Masukan Hanya Huruf", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
     
       public void FilterAngka(KeyEvent b) {
        if (Character.isAlphabetic(b.getKeyChar())) {
            b.consume();
            //Pesan Dialog Boleh Di Hapus Ini Hanya Sebagai Contoh
            JOptionPane.showMessageDialog(null, "Masukan Hanya Angka", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
      
    @Override
    public void run(){
           while(true){
        if(berjalan){
            if(x >= jPanel3.getWidth()-630){
                kiri = true;
                kanan = false;
            }
            if(kiri){
                x -- ;
                LBJALAN.setLocation(x, y);
            }
            if(x<=5){
                kanan = true;
                kiri = false;
            }
            if(kanan){
                x++;
                LBJALAN.setLocation(x, y);
            }
        }
        try {
            Thread.sleep(10);
        }catch (InterruptedException ex){
            Logger.getLogger(Frame_booking_lapangan.class.getName()).log(Level.SEVERE,null, ex);
        }
        repaint();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel2 = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblbooking = new javax.swing.JTable();
        btnPesan = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        namapemesan = new javax.swing.JTextField();
        btnHapus = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        nohp = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lapangan = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        hargasewa = new javax.swing.JTextField();
        member = new javax.swing.JRadioButton();
        nonmember = new javax.swing.JRadioButton();
        totalbayar = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        pembayaran = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lamabermain = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        namakasir = new javax.swing.JTextField();
        kode = new javax.swing.JTextField();
        lapangan1 = new javax.swing.JTextField();
        mulai = new javax.swing.JTextField();
        selesai = new javax.swing.JTextField();
        tanggal = new javax.swing.JTextField();
        edit1 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        LBJALAN = new javax.swing.JLabel();
        lbltanggal = new javax.swing.JLabel();
        lblwaktu = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        btnPesan1 = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        btnCari = new javax.swing.JButton();
        Searching = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtCetak = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Kode Booking");

        status.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        status.setText("Status");

        tblbooking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblbooking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbookingMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblbooking);

        btnPesan.setBackground(new java.awt.Color(0, 204, 0));
        btnPesan.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        btnPesan.setText("PESAN");
        btnPesan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPesanMouseClicked(evt);
            }
        });
        btnPesan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesanActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Nama Pemesan");

        namapemesan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        namapemesan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                namapemesanKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                namapemesanKeyTyped(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(255, 0, 0));
        btnHapus.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        btnHapus.setText("DELETE");
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusMouseClicked(evt);
            }
        });
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("No Handphone");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Kasir");

        nohp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nohp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nohpActionPerformed(evt);
            }
        });
        nohp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nohpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nohpKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Waktu");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Sampai");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Tanggal Pemesanan");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText(":");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText(":");

        lapangan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lapangan.setText("Lapangan");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText(":");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText(":");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setText(":");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setText(":");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Lama Bermain");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Harga Sewa");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setText(":");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setText(":");

        hargasewa.setText("KOSONGKAN");
        hargasewa.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        hargasewa.setDoubleBuffered(true);
        hargasewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hargasewaActionPerformed(evt);
            }
        });
        hargasewa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hargasewaKeyReleased(evt);
            }
        });

        buttonGroup1.add(member);
        member.setText("Member");
        member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memberActionPerformed(evt);
            }
        });

        buttonGroup1.add(nonmember);
        nonmember.setText("Non Member");

        totalbayar.setText("KOSONGKAN");
        totalbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalbayarActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Total");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setText(":");

        pembayaran.setToolTipText("");
        pembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pembayaranActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Pembayaran");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel25.setText(":");

        lamabermain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lamabermainActionPerformed(evt);
            }
        });
        lamabermain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lamabermainKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lamabermainKeyTyped(evt);
            }
        });

        namakasir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namakasirActionPerformed(evt);
            }
        });
        namakasir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                namakasirKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                namakasirKeyTyped(evt);
            }
        });

        kode.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        kode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeActionPerformed(evt);
            }
        });
        kode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kodeKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                kodeKeyTyped(evt);
            }
        });

        lapangan1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lapangan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lapangan1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lapangan1KeyTyped(evt);
            }
        });

        mulai.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        mulai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mulaiActionPerformed(evt);
            }
        });
        mulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mulaiKeyPressed(evt);
            }
        });

        selesai.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        selesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selesaiActionPerformed(evt);
            }
        });
        selesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                selesaiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                selesaiKeyTyped(evt);
            }
        });

        tanggal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tanggalActionPerformed(evt);
            }
        });
        tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tanggalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tanggalKeyTyped(evt);
            }
        });

        edit1.setBackground(new java.awt.Color(255, 255, 255));
        edit1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        edit1.setText("REFRESH");
        edit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit1ActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel27.setText(":");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel28.setText(":");

        LBJALAN.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 24)); // NOI18N
        LBJALAN.setText("Mohon Konfirmasi Pesanan 30 Menit Sebelum Bermain!");
        LBJALAN.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
                LBJALANAncestorMoved(evt);
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LBJALAN, javax.swing.GroupLayout.PREFERRED_SIZE, 782, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LBJALAN)
                .addContainerGap())
        );

        lbltanggal.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lbltanggal.setText("label2");

        lblwaktu.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblwaktu.setText("label1");

        jLabel35.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel35.setText("TIME");

        jLabel36.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel36.setText("DATE");

        btnPesan1.setBackground(new java.awt.Color(255, 0, 0));
        btnPesan1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        btnPesan1.setText("RESET");
        btnPesan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPesan1MouseClicked(evt);
            }
        });
        btnPesan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesan1ActionPerformed(evt);
            }
        });

        jLabel38.setIcon(new javax.swing.ImageIcon("E:\\fix.png")); // NOI18N

        jLabel39.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 36)); // NOI18N
        jLabel39.setText("BOOKING LAPANGAN");

        jLabel42.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel42.setText("TEKNIK FUTSAL STADIUM");

        jLabel40.setIcon(new javax.swing.ImageIcon("E:\\fix.png")); // NOI18N

        btnCari.setIcon(new javax.swing.ImageIcon("E:\\cari png.png")); // NOI18N
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        Searching.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchingActionPerformed(evt);
            }
        });
        Searching.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SearchingKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                SearchingKeyTyped(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jButton1.setText("EDIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCetakActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 13)); // NOI18N
        jButton2.setText("CETAK ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lapangan)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(457, 457, 457)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel19)
                                                    .addComponent(jLabel9)
                                                    .addComponent(jLabel22))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(kode, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(lapangan1, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                                                    .addComponent(namapemesan)
                                                    .addComponent(nohp)
                                                    .addComponent(namakasir))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel24)
                                            .addComponent(jLabel10)
                                            .addComponent(status))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(23, 23, 23))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(186, 186, 186)
                                                .addComponent(jLabel35)
                                                .addGap(66, 66, 66)
                                                .addComponent(lblwaktu))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lamabermain, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(totalbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(hargasewa, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(member)
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nonmember)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel36)
                                        .addGap(87, 87, 87)
                                        .addComponent(lbltanggal)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(pembayaran, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tanggal, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(mulai)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(selesai, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(44, 44, 44))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(edit1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(btnPesan, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnHapus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnPesan1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(48, 48, 48))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Searching, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(1103, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16)
                                    .addComponent(member)
                                    .addComponent(nonmember))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)
                            .addComponent(pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(mulai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selesai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel35)
                                    .addComponent(lblwaktu)
                                    .addComponent(jLabel36)
                                    .addComponent(lbltanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(kode, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lapangan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lapangan1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(namapemesan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nohp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel6))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPesan, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPesan1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(edit1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel27)
                                    .addComponent(namakasir, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel21)
                                            .addComponent(lamabermain, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(9, 9, 9)
                                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(hargasewa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel23))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(totalbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel20))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(84, 84, 84)
                                        .addComponent(txtCetak))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2)))
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Searching, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel40)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPesanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesanActionPerformed
  
       
    }//GEN-LAST:event_btnPesanActionPerformed

    private void namapemesanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_namapemesanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_namapemesanKeyPressed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed

    }//GEN-LAST:event_btnHapusActionPerformed

    private void nohpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nohpKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nohpKeyPressed

    private void nohpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nohpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nohpActionPerformed

    private void memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_memberActionPerformed

    private void pembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pembayaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pembayaranActionPerformed

    private void totalbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalbayarActionPerformed
      totalbayar.setEditable(false);
   
    }//GEN-LAST:event_totalbayarActionPerformed

    private void hargasewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hargasewaActionPerformed
          hargasewa.setEditable(false);
    }//GEN-LAST:event_hargasewaActionPerformed

    private void hargasewaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hargasewaKeyReleased
        
        
    }//GEN-LAST:event_hargasewaKeyReleased

    private void lamabermainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lamabermainActionPerformed
    lamabermain.setEditable(false);        // TODO add your handling code here:
    }//GEN-LAST:event_lamabermainActionPerformed

    private void lamabermainKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lamabermainKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_lamabermainKeyReleased

    private void namakasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namakasirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namakasirActionPerformed

    private void namakasirKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_namakasirKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_namakasirKeyReleased

    private void btnPesanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPesanMouseClicked
        String kodee = kode.getText();
        String nama = namakasir.getText();
        String lapangan = lapangan1.getText();
        String namasewa = namapemesan.getText();
        String nohpp = nohp.getText();
        String mulaii = mulai.getText();
        String selesaii = selesai.getText();
        String tanggall = tanggal.getText();
        String lamabermainn = lamabermain.getText();
        String hargasewaa = hargasewa.getText();
        String total = totalbayar.getText();
        String pembayarann = (""+pembayaran.getSelectedItem());
        
        
         String lamaabermain = "";
        int mulaiii, done, hasil;
        mulaiii = Integer.parseInt(mulai.getText());
        done = Integer.parseInt(selesai.getText());
        hasil = done - mulaiii;
        lamaabermain = String.valueOf(hasil);
        lamabermain.setText(lamaabermain);
        
        
       String memberr = "";
        int jumlah;
        if(member.isSelected()){
                memberr = member.getText();
            hargasewa.setText("100000");
        }else {
            memberr = nonmember.getText();
            hargasewa.setText("150000");
         }
        
        String totall = ""; 
        int lama, harg, jml;
        lama = Integer.parseInt(lamabermain.getText());
        harg = Integer.parseInt(hargasewa.getText());
        jml = lama * harg;
        totall = String.valueOf(jml);
        totalbayar.setText(totall);
        
        if(checkID(kodee))
        {
            JOptionPane.showMessageDialog(null, "Terdapat DATA yang sama! Mohon dicek Kembali!");
        }
        else if(checkNAMA(nama))
        {
            JOptionPane.showMessageDialog(null, "Terdapat DATA yang sama! Mohon dicek Kembali!");
        }else{

         PreparedStatement ps;
         String query = "INSERT INTO `tbframe`(`kode`,`namakasir`,`tanggal`,`jam1`,`jam2`,`lamamain`,`hargasewa`,`total`,`lapangan`,`nohp`,`pembayaran`,`status`,`namapenyewa`)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            try {
                ps =ConnectionMysql.getConnection().prepareStatement(query);
        ps.setString(1, kodee);
        ps.setString(2, nama);
        ps.setString(3, tanggall);
        ps.setString(4, mulaii);
        ps.setString(5, selesaii);
        ps.setString(6, lamabermainn);
        ps.setString(7, hargasewaa);
        ps.setString(8, totall);
        ps.setString(9, lapangan);
        ps.setString(10, nohpp);
        ps.setString(11, pembayarann);
        ps.setString(12, memberr);
        ps.setString(13, namasewa);
        
        
        
   
       
         if(ps.executeUpdate() > 0)
                {
                    JOptionPane.showMessageDialog(null, "Pembuatan Data Berhasil!,Mohon Di-Refresh");
                }
            } catch (SQLException ex) {
            Logger.getLogger(Frame_booking_lapangan.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    }//GEN-LAST:event_btnPesanMouseClicked

    private void kodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kodeKeyPressed

    private void lapangan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lapangan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lapangan1KeyPressed

    private void mulaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mulaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mulaiActionPerformed

    private void mulaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mulaiKeyPressed
      String huruf = "[]';,';'/;/@%{}()~#%^!*";
        int idex = huruf.indexOf(evt.getKeyChar());
        if (idex>=0) evt.consume();
        JumlahKarakter(evt);
        FilterAngka(evt);
    }//GEN-LAST:event_mulaiKeyPressed

    private void selesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selesaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selesaiActionPerformed

    private void selesaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_selesaiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_selesaiKeyPressed

    private void tanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tanggalActionPerformed

    private void tanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tanggalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tanggalKeyPressed

    private void btnHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseClicked
        // TODO add your handling code here:
             try {
        PreparedStatement ps; 
        
        String query =("DELETE FROM tbframe WHERE " + "kode= '" + model.getValueAt(tblbooking.getSelectedRow(), 0) + "'");
             ps =ConnectionMysql.getConnection().prepareStatement(query);
            ps.execute();
            
            JOptionPane.showMessageDialog(this, "Berhasil di hapus,Mohon Di-Refresh");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }      
         
    }//GEN-LAST:event_btnHapusMouseClicked

    private void edit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit1ActionPerformed
        Frame_booking_lapangan xx;
        xx = new Frame_booking_lapangan();
        xx.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_edit1ActionPerformed

    private void kodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kodeActionPerformed

    private void kodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kodeKeyTyped
        String huruf = "[]';,';'/.;/@%{}()~#%^!*";
        int idex = huruf.indexOf(evt.getKeyChar());
        if (idex>=0) evt.consume();
        JumlahKarakter(evt);
    }//GEN-LAST:event_kodeKeyTyped

    private void lapangan1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lapangan1KeyTyped
        String huruf = "[]';,';'/.;/@%{}()~#%^!*";
        int idex = huruf.indexOf(evt.getKeyChar());
        if (idex>=0) evt.consume();
        JumlahKarakter(evt);
        FilterAngka(evt);
    }//GEN-LAST:event_lapangan1KeyTyped

    private void namakasirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_namakasirKeyTyped
       String huruf = "[]';,';'/.;/@%{}()~#%^!*";
        int idex = huruf.indexOf(evt.getKeyChar());
        if (idex>=0) evt.consume();
        JumlahKarakter(evt);
        FilterHuruf(evt);
    }//GEN-LAST:event_namakasirKeyTyped

    private void namapemesanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_namapemesanKeyTyped
       String huruf = "[]';,';'/.;/@%{}()~#%^!*";
        int idex = huruf.indexOf(evt.getKeyChar());
        if (idex>=0) evt.consume();
        JumlahKarakter(evt);
        FilterHuruf(evt);
    }//GEN-LAST:event_namapemesanKeyTyped

    private void nohpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nohpKeyTyped
       String huruf = "[]';,';'/.;/@%{}()~#%^!*";
        int idex = huruf.indexOf(evt.getKeyChar());
        if (idex>=0) evt.consume();
        JumlahKarakter(evt);
        FilterAngka(evt);
    }//GEN-LAST:event_nohpKeyTyped

    private void selesaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_selesaiKeyTyped
      String huruf = "[]';,';'/;/@%{}()~#%^!*";
        int idex = huruf.indexOf(evt.getKeyChar());
        if (idex>=0) evt.consume();
        JumlahKarakter(evt);
        FilterAngka(evt);
    }//GEN-LAST:event_selesaiKeyTyped

    private void tanggalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tanggalKeyTyped
       String huruf = "[]';,';'/.;/@%{}()~#%^!*";
        int idex = huruf.indexOf(evt.getKeyChar());
        if (idex>=0) evt.consume();
        JumlahKarakter(evt);
    }//GEN-LAST:event_tanggalKeyTyped

    private void lamabermainKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lamabermainKeyTyped
        String huruf = "[]';,';'/.;/@%{}()~#%^!*";
        int idex = huruf.indexOf(evt.getKeyChar());
        if (idex>=0) evt.consume();
        JumlahKarakter(evt);
        FilterAngka(evt);
    }//GEN-LAST:event_lamabermainKeyTyped

    private void LBJALANAncestorMoved(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_LBJALANAncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_LBJALANAncestorMoved

    private void btnPesan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPesan1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPesan1MouseClicked

    private void btnPesan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesan1ActionPerformed
       clearFields();
    }//GEN-LAST:event_btnPesan1ActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        int statusSearching = 1;
        if(Searching.getText().isEmpty())
        { statusSearching = 0; }
        else if(statusSearching==1){
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Kode");
            model.addColumn("Nama Kasir");
            model.addColumn("Lapangan");
            model.addColumn("Nama Pemesan");
            model.addColumn("No Handphone");
            model.addColumn("Status");
            model.addColumn("Mulai");
            model.addColumn("Berakhir");
            model.addColumn("Tanggal");
            model.addColumn("Lama Bermain");
            model.addColumn("Harga");
            model.addColumn("Total");
            model.addColumn("Pembayaran");
            String cari = Searching.getText();
            try {
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost/dblogin","root","");
                Statement st=c.createStatement();
                String sql = " Select * from tbframe where kode like '"+cari+"'"
                + " OR namakasir like '"+cari+"' OR lapangan like '"+cari+"' OR namapenyewa like '"+cari+"' OR nohp like '"+cari+"' OR status like '"+cari+"' OR jam1 like '"+cari+"' OR jam2 like '"+cari+"' OR tanggal like '"+cari+"' OR lamamain like '"+cari+"' OR hargasewa like '"+cari+"' OR total like '"+cari+"' OR pembayaran like '"+cari+"' ORDER BY kode";
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Object[] o=new Object[15];
                    o[0]=rs.getString("kode");
                    o[1]=rs.getString("namakasir");
                    o[2]=rs.getString("lapangan");
                    o[3]=rs.getString("namapenyewa");
                    o[4]=rs.getString("nohp");
                    o[5]=rs.getString("status");
                    o[6]=rs.getString("jam1");
                    o[7]=rs.getString("jam2");
                    o[8]=rs.getString("tanggal");
                    o[9]=rs.getString("lamamain");
                    o[10]=rs.getInt("hargasewa");
                    o[11]=rs.getInt("total");
                    o[12]=rs.getString("pembayaran");
                    model.addRow(o);
                }
                tblbooking.setModel(model);
            }   catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Data yang dicari tidak ada!");
            }
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void SearchingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchingActionPerformed

    private void SearchingKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchingKeyReleased
        // TODO add your handling code here:
        try {
            Connection c=DriverManager.getConnection("jdbc:mysql://localhost/dblogin","root","");
            Statement st=c.createStatement();
            String sql = " SELECT * FROM tbframe WHERE kode=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, Searching.getText());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String kodee = rs.getString("kode");
                kode.setText(kodee);
                String nama = rs.getString("namakasir");
                namakasir.setText(nama);
                String lapangann = rs.getString("lapangan");
                lapangan.setText(lapangann);
                String namasewa = rs.getString("namapenyewa");
                namapemesan.setText(namasewa);
                String nohpp = rs.getString("nohp");
                nohp.setText(nohpp);
                String mulaii = rs.getString("jam1");
                mulai.setText(mulaii);
                String selesaii = rs.getString("jam2");
                selesai.setText(selesaii);
                String tanggall = rs.getString("tanggal");
                tanggal.setText(tanggall);
                String lamabermainn = rs.getString("lamamain");
                lamabermain.setText(lamabermainn);
                String hargasewaa = rs.getString("hargasewa");
                hargasewa.setText(hargasewaa);
                String total = rs.getString("total");
                totalbayar.setText(total);
                String pembayarann = rs.getString("pembayaran");
                pembayaran.setSelectedItem(pembayarann);
                String memberr = rs.getString("status");
                int jumlah;
                if(member.isSelected()){
                    memberr = member.getText();
                    hargasewa.setText("100000");
                }else {
                    memberr = nonmember.getText();
                    hargasewa.setText("150000");
                }

            }
        }
        catch(Exception e){

        }
    }//GEN-LAST:event_SearchingKeyReleased

    private void SearchingKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchingKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchingKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost/dblogin","root","");
            c.createStatement().executeUpdate("Update tbframe SET namapenyewa ='"+namapemesan.getText()+"',pembayaran ='"+pembayaran.getSelectedItem()+"',nohp ='"+nohp.getText()+"',total ='"+totalbayar.getText()+"',lapangan ='"+lapangan.getText()+"',hargasewa ='"+hargasewa.getText()+"',lamamain ='"+lamabermain.getText()+"',jam1 ='"+mulai.getText()+"',jam2 ='"+selesai.getText()+"',namakasir ='"+namakasir.getText()+"',tanggal ='"+tanggal.getText()+"' where kode ='"+kode.getText()+"'");
            JOptionPane.showMessageDialog(this, "Berhasil di EDIT,Mohon Di-Refresh");
        } catch (SQLException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblbookingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbookingMouseClicked
        // TODO add your handling code here:
               int row = tblbooking.getSelectedRow();
       kode.setText(tblbooking.getValueAt(row, 0).toString());
       namakasir.setText(tblbooking.getModel().getValueAt(row, 1).toString());
       lapangan1.setText(tblbooking.getModel().getValueAt(row, 2).toString());
       namapemesan.setText(tblbooking.getModel().getValueAt(row, 3).toString());
       nohp.setText(tblbooking.getModel().getValueAt(row, 4).toString());
        
        String memberr = (tblbooking.getValueAt(row, 5).toString());
            if(memberr.equals(memberr)){
                member.setSelected(true);
            }else{
                nonmember.setSelected(true);
            }
        tanggal.setText(tblbooking.getModel().getValueAt(row, 8).toString());
        pembayaran.setSelectedItem(tblbooking.getModel().getValueAt(row, 12).toString());
        mulai.setText(tblbooking.getModel().getValueAt(row, 6).toString());
        selesai.setText(tblbooking.getModel().getValueAt(row, 7).toString());
        lamabermain.setText(tblbooking.getModel().getValueAt(row, 9).toString());
        hargasewa.setText(tblbooking.getModel().getValueAt(row, 10).toString());
        totalbayar.setText(tblbooking.getModel().getValueAt(row, 11).toString());
    }//GEN-LAST:event_tblbookingMouseClicked

    private void txtCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCetakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCetakActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         try {
         String path="E:\\File n Tugas\\Pemro test\\java\\TUBES 90%\\src\\reportBooking.jasper";
         HashMap parameter = new HashMap();
        
        parameter.put("kode",txtCetak.getText());
        JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("reportBooking.jasper"), parameter, ConnectionMysql.getConnection());
        JasperViewer.viewReport(jp, false);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
  public boolean checkNAMA(String nama)
    {
        PreparedStatement ps;
        ResultSet rs;
        boolean checkNAMA = false;
        String query = "SELECT * FROM `tbframe` WHERE`namapenyewa` =?";
        
        try {
            ps = ConnectionMysql.getConnection().prepareStatement(query);
            ps.setString(1, nama);
            
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                checkNAMA = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormRegitrasi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkNAMA;
    }
  public boolean checkID(String kodee)
    {
        PreparedStatement ps;
        ResultSet rs;
        boolean checkID = false;
        String query = "SELECT * FROM `tbframe` WHERE`kode` =?";
        
        try {
            ps = ConnectionMysql.getConnection().prepareStatement(query);
            ps.setString(1, kodee);
            
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                checkID = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormRegitrasi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkID;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame_booking_lapangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_booking_lapangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_booking_lapangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_booking_lapangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Frame_booking_lapangan().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LBJALAN;
    private javax.swing.JTextField Searching;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnPesan;
    private javax.swing.JButton btnPesan1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton edit1;
    private javax.swing.JTextField hargasewa;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField kode;
    private javax.swing.JTextField lamabermain;
    private javax.swing.JLabel lapangan;
    private javax.swing.JTextField lapangan1;
    private javax.swing.JLabel lbltanggal;
    private javax.swing.JLabel lblwaktu;
    private javax.swing.JRadioButton member;
    private javax.swing.JTextField mulai;
    private javax.swing.JTextField namakasir;
    private javax.swing.JTextField namapemesan;
    private javax.swing.JTextField nohp;
    private javax.swing.JRadioButton nonmember;
    private javax.swing.JComboBox pembayaran;
    private javax.swing.JTextField selesai;
    private javax.swing.JLabel status;
    private javax.swing.JTextField tanggal;
    private javax.swing.JTable tblbooking;
    private javax.swing.JTextField totalbayar;
    private javax.swing.JTextField txtCetak;
    // End of variables declaration//GEN-END:variables

    private String lamabermain() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     public void getData( ){
     
     try {
            Connection c=DriverManager.getConnection("jdbc:mysql://localhost/dblogin","root","");
            Statement st=c.createStatement();
            //Perintah QUERY :
            String sql = "SELECT * FROM tbframe";
            //Menjalankan perintah Query :
            ResultSet rs=st.executeQuery(sql);
            
            while (rs.next()) {
                Object[] o=new Object[15];
                o[0]=rs.getString("kode");
                o[1]=rs.getString("namakasir");
                o[2]=rs.getString("lapangan");
                o[3]=rs.getString("namapenyewa");
                o[4]=rs.getString("nohp");
                o[5]=rs.getString("status");
                o[6]=rs.getString("jam1");
                o[7]=rs.getString("jam2");
                o[8]=rs.getString("tanggal");
                o[9]=rs.getString("lamamain");
                o[10]=rs.getInt("hargasewa");
                o[11]=rs.getInt("total");
                o[12]=rs.getString("pembayaran");
                model.addRow(o);
            }
            rs.close();
            st.close();
            
        } catch (SQLException e) {
            System.out.println("Terjadi Error dalam pengambilan data"+e);
        }  
     }
    }
