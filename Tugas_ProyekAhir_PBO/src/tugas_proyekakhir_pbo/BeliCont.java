/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugas_proyekakhir_pbo;

/**
 *
 * @author ASUS
 */
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import static tugas_proyekakhir_pbo.BeliMod.DB_URL;
import static tugas_proyekakhir_pbo.BeliMod.JDBC_DRIVER;
import static tugas_proyekakhir_pbo.BeliMod.PASS;
import static tugas_proyekakhir_pbo.BeliMod.USER;
public class BeliCont {
    BeliView bv;
    BeliMod bm;
    
            static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
            static final String DB_URL = "jdbc:mysql://localhost/apotek";
            static final String USER = "root";
            static final String PASS = "";

    Connection koneksi;
    Statement statement;

    public BeliCont(BeliView bv, BeliMod bm) {
        this.bv = bv;
        this.bm = bm;
         try{
            Class.forName(JDBC_DRIVER);
            koneksi = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Koneksi Berhasil");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println("Koneksi Gagal");
        }
        
                     if (bm.getBanyakData()!=0) {
            String dataKontak[][] = bm.readBeli();
            bv.tabel.setModel((new JTable(dataKontak, bv.namaKolom)).getModel());
        }
        else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada");
        }
                     
             bv.btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(bv.tfharga.getText().equals("") || bv.tfnamaobat.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Inputan Harus Berisi Karakter");  
                }
                else{
                       
                String harga = bv.getHarga();
                String namaobat = bv.getId();
                String tanggal = bv.getTanggal();
                String jumlah = bv.getTfjumlah();
                String namapemb = bv.getTfnamapemb();
                String nomorpemb = bv.getnmr();
                
                bm.insertData(namaobat,harga,jumlah,namapemb,tanggal,nomorpemb);

                String dataapotek[][] = bm.readBeli();
                bv.tabel.setModel((new JTable(dataapotek, bv.namaKolom)).getModel());
                }
             
            }
        });
             
         bv.btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(bv.tfharga.getText().equals("") || bv.tfnamaobat.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Inputan Harus Berisi Karakter");  
                }
                else{
                    String harga = bv.getHarga();
                    String namaobat = bv.getId();
                    String tanggal = bv.getTanggal();
                    String jumlah = bv.getTfjumlah();
                    String namapemb = bv.getTfnamapemb();
                    String nomorpemb = bv.getnmr();;
                
                 bm.update(namaobat,harga,jumlah,namapemb,tanggal,nomorpemb);

              String dataapotek[][] = bm.readBeli();
                bv.tabel.setModel((new JTable(dataapotek, bv.namaKolom)).getModel());
                }
               
            }
        });
                              bv.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String dataKontak[][] = bm.readBeli();
                bv.tabel.setModel((new JTable(dataKontak, bv.namaKolom)).getModel());
            }
        });
                 bv.btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                 MenuView menuView = new MenuView();
                    new ControllerMenu(menuView);
                    bv.dispose();
            }
        });
                 
         bv.btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String NIM = bv.getCari();

                if (NIM.equals("")) {
                    JOptionPane.showMessageDialog(null, "Data Kosong");
                }
                else {
                    String dataKontak[][] = bm.carinama(NIM);
                    bv.tabel.setModel((new JTable(dataKontak, bv.namaKolom)).getModel());
                }

            }
        });
         
          bv.btnHarga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String dataKontak[][] = bm.sort("Harga");
                bv.tabel.setModel((new JTable(dataKontak, bv.namaKolom)).getModel());
            }
        });
          
           bv.btnnamaobat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String dataKontak[][] = bm.sort("Nama_obat");
                bv.tabel.setModel((new JTable(dataKontak, bv.namaKolom)).getModel());
            }
        });
            bv.btnTgl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String dataKontak[][] = bm.sort("Tanggal_pembelian");
                bv.tabel.setModel((new JTable(dataKontak, bv.namaKolom)).getModel());
            }
        });
            
            
            bv.tabel.addMouseListener(new MouseAdapter() {
                                              @Override
     public void mouseClicked(MouseEvent e) {
          super.mousePressed(e);
          int baris = bv.tabel.getSelectedRow();
          int kolom = bv.tabel.getSelectedColumn(); // ga kepake sekarang

         String namaobat = bv.tabel.getValueAt(baris, 0).toString();
          String nama= bv.tabel.getValueAt(baris,1 ).toString();
          
            
              String namaobat1="", harga="", jumlah="", namapemb="", tanggal="", nomorpemb="";
                  try{
                   String query="Select * From pembelian  WHERE Nomor_pembelian = '"+namaobat1+"'";
                   statement = (Statement) koneksi.createStatement();
                      ResultSet resultSet = statement.executeQuery(query);
           
            
              while (resultSet.next()){
               namaobat=resultSet.getString("Nama_obat");
               harga=resultSet.getString("Harga");
               jumlah=resultSet.getString("Jumlah_yang_dibeli");
               namapemb=resultSet.getString("Nama_Pembeli");
               tanggal=resultSet.getString("Tanggal_pembelian");
               nomorpemb=resultSet.getString("Nomor_pembelian");
              
            }
              
            
                  }
                 catch (Exception sql) {
            System.out.println(sql.getMessage());
            JOptionPane.showMessageDialog(null, sql.getMessage());
                     System.out.println("gagal");
        }
            
          
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
Date temp = null;
String dateInString = jumlah;
                                                  try {
                                                      Date date = formatter.parse(dateInString);temp=date;
                                                  } catch (ParseException ex) {
                                                      Logger.getLogger(BeliCont.class.getName()).log(Level.SEVERE, null, ex);
                                                  }

          

           String[] options = {"Delete","Update"};
        
        int input = JOptionPane.showOptionDialog(null, "Apa anda ingin menghapus Anggota : " + nama+ "? atau Update Data ?", "Pilih Opsi...",
                
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        
          if (input == 0) {
              bm.delete(namaobat);
              String dataKontak[][] = bm.readBeli();
              bv.tabel.setModel(new JTable(dataKontak, bv.namaKolom).getModel());
              
              
          } else {
              JOptionPane.showMessageDialog(null, "Tidak Jadi Dihapus");
              bv.tfnamaobat.setText(namaobat);
              bv.tfharga.setText(harga);
              bv.tfjumlah.setText(jumlah);
              bv.tfnamapemb.setText(namapemb);
              bv.tfnmr.setText(nomorpemb);
              bv.date.setDate(temp);
              
          }
      }
  }
        );
            
                     bv.btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                bv.tfnamaobat.setText(null);
                bv.tfharga.setText(null);
                bv.tfjumlah.setText(null);
                bv.tfnamapemb.setText(null);
                bv.tfnmr.setText(null);
                bv.date.setDate(null);
               
            }
        });
            
            
    }
    
    
}
