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
import model.Anggota;
import model.Koneksi;
import model.dao.AnggotaDao;
import view.FormAnggota;

/**
 *
 * @author LABP2KOMP11
 */
public class AnggotaController {
    FormAnggota view;
    AnggotaDao anggotaDao;
    Connection connection;
    Anggota anggota;
    
    public AnggotaController(FormAnggota view) {
        try {
            this.view = view;
            Koneksi k = new Koneksi();
            connection = k.getConnection();
            anggotaDao = new AnggotaDao(connection);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void insert_anggota() {
        try {
            anggota = new Anggota();
            anggota.setKode_anggota(view.getTxtkode_anggota().getText());
            anggota.setNama_anggota(view.getTxtnama_anggota().getText());
            anggota.setAlamat(view.getTxtalamat().getText());
            anggota.setJekel(view.getCbojekel().getSelectedItem().toString());
            anggota.setTgl_lahir(view.getTxttgl_lahir().getText());
            anggotaDao.insert(anggota);
            JOptionPane.showMessageDialog(view, "Entri  Data Ok");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update_anggota() {
        try {
            anggota = new Anggota();
            anggota.setKode_anggota(view.getTxtkode_anggota().getText());
            anggota.setNama_anggota(view.getTxtnama_anggota().getText());
            anggota.setAlamat(view.getTxtalamat().getText());
            anggota.setJekel(view.getCbojekel().getSelectedItem().toString());
            anggota.setTgl_lahir(view.getTxttgl_lahir().getText());
            anggotaDao.update(anggota);
            JOptionPane.showMessageDialog(view, "Update  Data Ok");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void delete_anggota() {
        try {
            String kodeanggota = view.getTxtkode_anggota().getText();
            anggotaDao.delete(kodeanggota);
            JOptionPane.showMessageDialog(view, "Delete  Data Ok");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cari() {
        try {
            String kodeanggota = view.getTxtkode_anggota().getText();
            anggota = anggotaDao.getAnggota(kodeanggota);
            if(anggota != null) {
                view.getTxtnama_anggota().setText(anggota.getNama_anggota());
                view.getTxtalamat().setText(anggota.getAlamat());
                view.getTxttgl_lahir().setText(anggota.getTgl_lahir());
                view.getCbojekel().setSelectedItem(anggota.getJekel());
            } else {
                JOptionPane.showMessageDialog(view, "Data tidak ada");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateTabel() {
        try {
            final ResultSet rs = anggotaDao.getAllAnggota("Select * from anggota");
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
            String[] kolom = {"Kode Anggota", "Nama Anggota", "Alamat", "Jekel", "Tgl Lahir"};
            DefaultTableModel tmodel = new DefaultTableModel(data, kolom){
                
                @Override
                public boolean isCellEditable(final int rowIndex, int colIndex){
                    return false;
                }
            };
            view.getTabelAnggota().setModel(tmodel);
            view.getTabelAnggota().setSelectionMode(0);
        } catch (SQLException ex) {
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
