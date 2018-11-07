/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Koneksi;
import model.Peminjaman;
import model.Pengembalian;
import model.dao.AnggotaDao;
import model.dao.BukuDao;
import model.dao.PeminjamanDao;
import model.dao.PengembalianDao;
import view.FormPengembalian;

/**
 *
 * @author LABP2KOMP11
 */
public class PengembalianController {
    FormPengembalian view;
    AnggotaDao anggotaDao;
    BukuDao bukuDao;
    PeminjamanDao peminjamanDao;
    PengembalianDao pengembalianDao;
    Connection connection;
    String[] tkodeanggota;
    String[] tkodebuku;
    Pengembalian pengembalian;
    
    public PengembalianController(FormPengembalian view){
        try {
            this.view = view;
            Koneksi k = new Koneksi();
            connection = k.getConnection();
            anggotaDao = new AnggotaDao(connection);
            bukuDao = new BukuDao(connection);
            peminjamanDao = new PeminjamanDao(connection);
            pengembalianDao = new PengembalianDao(connection);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void isiCboAnggota(){
        view.getCboKode_anggota().removeAllItems();
        try {
            ResultSet rs = anggotaDao.getAllAnggota("select * from anggota");
            rs.last();
            int jumlahdata = rs.getRow();
            tkodeanggota = new String[jumlahdata];
            int counter=0;
            rs.beforeFirst();
            while(rs.next()){
                view.getCboKode_anggota().addItem(rs.getString(2));
                tkodeanggota[counter] = rs.getString(1);
                counter++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    public void isiCboBuku(){
        view.getCboKode_buku().removeAllItems();
        try {
            ResultSet rs = bukuDao.getAllBuku("select * from buku");
            rs.last();
            int jumlahdata = rs.getRow();
            tkodebuku = new String[jumlahdata];
            int counter=0;
            rs.beforeFirst();
            while(rs.next()){
                view.getCboKode_buku().addItem(rs.getString(2));
                tkodebuku[counter] = rs.getString(1);
                counter++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cariPeminjaman(){
        try {
            String kodeanggota = tkodeanggota[view.getCboKode_anggota().getSelectedIndex()];
            String kodebuku = tkodebuku[view.getCboKode_buku().getSelectedIndex()];
            String tglpinjam = view.getTxtTgl_pinjam().getText();
            Peminjaman peminjaman = peminjamanDao.getPeminjaman(kodeanggota, kodebuku, tglpinjam);
            view.getTxtTgl_kembali().setText(peminjaman.getTgl_kembali());
        } catch (SQLException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void proses(){
        try {
            String tglkembali = view.getTxtTgl_kembali().getText();
            String tgldikembalikan = view.getTxtTgl_dikembalikan().getText();
            int terlambat = pengembalianDao.kurangTanggal(tgldikembalikan, tglkembali);
            double denda = 5000 * terlambat;
            view.getTxtTerlambat().setText(""+terlambat);
            view.getTxtDenda().setText(""+denda);
        } catch (SQLException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void insert_pengembalian(){
        try {
            pengembalian = new Pengembalian();
            pengembalian.setKode_anggota(tkodeanggota[view.getCboKode_anggota().getSelectedIndex()]);
            pengembalian.setKode_buku(tkodebuku[view.getCboKode_buku().getSelectedIndex()]);
            pengembalian.setTgl_pinjam(view.getTxtTgl_pinjam().getText());
            pengembalian.setTgl_dikembalikan(view.getTxtTgl_dikembalikan().getText());
            pengembalian.setTerlambat(Integer.parseInt(view.getTxtTerlambat().getText()));
            pengembalian.setDenda(Double.parseDouble(view.getTxtDenda().getText()));
            pengembalianDao.insert(pengembalian);
            JOptionPane.showMessageDialog(view, "entri data ok");
        } catch (SQLException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void update_pengembalian(){
        try {
            pengembalian = new Pengembalian();
            pengembalian.setKode_anggota(tkodeanggota[view.getCboKode_anggota().getSelectedIndex()]);
            pengembalian.setKode_buku(tkodebuku[view.getCboKode_buku().getSelectedIndex()]);
            pengembalian.setTgl_pinjam(view.getTxtTgl_pinjam().getText());
            pengembalian.setTgl_dikembalikan(view.getTxtTgl_dikembalikan().getText());
            pengembalian.setTerlambat(Integer.parseInt(view.getTxtTerlambat().getText()));
            pengembalian.setDenda(Double.parseDouble(view.getTxtDenda().getText()));
            pengembalianDao.update(pengembalian);
        } catch (SQLException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    
     public void delete_pengembalian(){
        try {
            String kodeanggota = tkodeanggota[view.getCboKode_anggota().getSelectedIndex()];
            String kodebuku = tkodebuku[view.getCboKode_buku().getSelectedIndex()];
            String Tgl_pinjam = view.getTxtTgl_pinjam().getText();
            pengembalianDao.delete(kodeanggota, kodebuku, Tgl_pinjam);
            JOptionPane.showMessageDialog(view, "Delete Data OK");
        } catch (SQLException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void updateTabel() {
        try {
            final ResultSet rs = pengembalianDao.getAllPengembalian("Select * from pengembalian");
            String[][] data = new String[1][6];
            if(rs != null) {
                rs.last();
                int row = rs.getRow();
                rs.beforeFirst();
                int i = 0;
                data = new String[row][6];
                while (rs.next()) {
                    data[i][0] = rs.getString(1);
                    data[i][1] = rs.getString(2);
                    data[i][2] = rs.getString(3);
                    data[i][3] = rs.getString(4);
                    data[i][4] = rs.getString(5);
                    data[i][5] = rs.getString(6);
                    
                    i++;
                }
            } else {
                data[0][0] = "No Data";
            }
            String[] kolom = {"Kode Anggota", "Kode Buku", "Tgl Pinjam", 
                "Tgl Dikembalikan", "Terlambat", "Denda"};
            DefaultTableModel tmodel = new DefaultTableModel(data, kolom){
                
                @Override
                public boolean isCellEditable(final int rowIndex, int colIndex){
                    return false;
                }
            };
            view.getTabelPengembalian().setModel(tmodel);
            view.getTabelPengembalian().setSelectionMode(0);
        } catch (SQLException ex) {
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
}

/*
cekout = Integer.parseInt(editCekOut.getText().toString());
        harga = Double.parseDouble(editHrgHari.getText().toString());
*/


            
            

