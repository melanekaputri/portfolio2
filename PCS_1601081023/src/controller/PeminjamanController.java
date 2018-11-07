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
import model.dao.AnggotaDao;
import model.dao.BukuDao;
import model.dao.PeminjamanDao;
import view.FormPeminjaman;

/**
 *
 * @author LABP2KOMP11
 */
public class PeminjamanController {
    FormPeminjaman view;
    Koneksi k;
    Connection connection;
    PeminjamanDao peminjamanDao;
    AnggotaDao anggotaDao;
    BukuDao bukuDao;
    Peminjaman peminjaman;
    String[] tkodeanggota;
    String[] tkodebuku;
    
    
    public PeminjamanController (FormPeminjaman view){
        try {
            this.view = view;
            this.k = new Koneksi();
            this.connection = k.getConnection();
            peminjamanDao = new PeminjamanDao(connection);
            anggotaDao = new AnggotaDao(connection);
            bukuDao = new BukuDao(connection);
            peminjaman = new Peminjaman();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void insert_peminjaman(){
        try {
            peminjaman = new Peminjaman();
            peminjaman.setKode_anggota(tkodeanggota[view.getCboKode_anggota().getSelectedIndex()]);
            peminjaman.setKode_buku(tkodebuku[view.getCboKode_buku().getSelectedIndex()]);
            peminjaman.setTgl_pinjam(view.getTxtTglpinjam().getText());
            peminjaman.setTgl_kembali(view.getTxtTglkembali().getText());
            peminjaman.setStatus(0);
            peminjamanDao.insert(peminjaman);
            JOptionPane.showMessageDialog(view, "entri data ok");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Entri Data Error"+ex.getMessage());
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void update_peminjaman(){
        try {
            peminjaman = new Peminjaman();
            peminjaman.setKode_anggota(tkodeanggota[view.getCboKode_anggota().getSelectedIndex()]);
            peminjaman.setKode_buku(tkodebuku[view.getCboKode_buku().getSelectedIndex()]);
            peminjaman.setTgl_pinjam(view.getTxtTglpinjam().getText());
            peminjaman.setTgl_kembali(view.getTxtTglkembali().getText());
            peminjaman.setStatus(0);
            peminjamanDao.update(peminjaman);
            JOptionPane.showMessageDialog(view, "Update data OK");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Update Data Error");
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete_peminjaman() {
        try {
            String kodeanggota = tkodeanggota[view.getCboKode_anggota().getSelectedIndex()];
            String kodebuku = tkodebuku[view.getCboKode_buku().getSelectedIndex()];
            String Tgl_pinjam = view.getTxtTglpinjam().getText();
            peminjamanDao.delete(kodeanggota, kodebuku, Tgl_pinjam);
            JOptionPane.showMessageDialog(view, "Delete Data OK");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error :"+ex.getMessage());
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void updateTabel() {
        try {
            final ResultSet rs = peminjamanDao.getAllPeminjaman("Select * from peminjaman");
            String[][] data = new String[1][5];
            if(rs != null) {
                rs.last();
                int row = rs.getRow();
                rs.beforeFirst();
                int i = 0;
                data = new String[row][5];
                while (rs.next()) {
                    data[i][0] = rs.getString(1);
                    data[i][1] = rs.getString(2);
                    data[i][2] = rs.getString(3);
                    data[i][3] = rs.getString(4);
                    data[i][4] = rs.getString(5);
                    i++;
                }
            } else {
                data[0][0] = "No Data";
            }
            String[] kolom = {"Kode Anggota", "Kode Buku", "Tgl Pinjam", "Tgl Kembali", "Status"};
            DefaultTableModel tmodel = new DefaultTableModel(data, kolom){
                
                @Override
                public boolean isCellEditable(final int rowIndex, int colIndex){
                    return false;
                }
            };
            view.getTabelPeminjaman().setModel(tmodel);
            view.getTabelPeminjaman().setSelectionMode(0);
        } catch (SQLException ex) {
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
