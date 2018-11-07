/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcs_1601081023;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Anggota;
import model.Koneksi;
import model.dao.AnggotaDao;

/**
 *
 * @author LABP2KOMP11
 */
public class PCS_1601081023 {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PCS_1601081023 p = new PCS_1601081023();
        p.tesInsert();
    
    }
    
    public void tesInsert() {
        try {
            Koneksi k = new Koneksi();
            AnggotaDao anggotaDAO = new AnggotaDao(k.getConnection());
            Anggota anggota = new Anggota();
            anggota.setKode_anggota("A0001");
            anggota.setNama_anggota("Andi");
            anggota.setAlamat("Padang Panjang");
            anggota.setJekel("L");
            anggota.setTgl_lahir("1980-01-01");
            anggotaDAO.insert(anggota);
            JOptionPane.showMessageDialog(null, "Entri Ok");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PCS_1601081023.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PCS_1601081023.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
}
