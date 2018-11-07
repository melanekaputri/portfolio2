/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Koneksi;
import model.Peminjaman;

/**
 *
 * @author LABP2KOMP11
 */
public class PeminjamanDao {
    Connection connection;
    
    public PeminjamanDao(Connection connection){
        this.connection = connection;
    }
    
    
    public void insert (Peminjaman peminjaman) throws SQLException{
        String sql = "insert into peminjaman values(?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, peminjaman.getKode_anggota());
        ps.setString(2, peminjaman.getKode_buku());
        ps.setString(3, peminjaman.getTgl_pinjam());
        ps.setString(4, peminjaman.getTgl_kembali());
        ps.setInt(5, peminjaman.getStatus());
        ps.executeUpdate();
    }
    
    public void update(Peminjaman peminjaman) throws SQLException{
        String sql = "update peminjaman set tgl_kembali=?, status=?" 
                + "where kode_anggota=? and kode_buku=? and tgl_pinjam=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, peminjaman.getTgl_kembali());
        ps.setInt(2, peminjaman.getStatus());
        ps.setString(3, peminjaman.getKode_anggota());
        ps.setString(4, peminjaman.getKode_buku());
        ps.setString(5, peminjaman.getTgl_pinjam());
        ps.executeUpdate();
    }
    
    public void delete(String kode_anggota,String kode_buku,String tgl_pinjam) throws SQLException{
        String sql = "delete from peminjaman"
               + "delete from where kode_anggota=? and kode_buku=? and tgl_pinjam=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, kode_anggota);
        ps.setString(2, kode_buku);
        ps.setString(3, tgl_pinjam);
        ps.executeUpdate();
    }
    
    public Peminjaman getPeminjaman (String kode_anggota, String kode_buku, String tgl_pinjam) throws SQLException{
        String sql ="Select * from peminjaman"
                +"where kode_anggota=? and kode_buku=? and tgl_pinjam=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, kode_anggota);
        ps.setString(2, kode_buku);
        ps.setString(3, tgl_pinjam);
        ResultSet rs = ps.executeQuery();
        Peminjaman peminjaman = null;
        if(rs.next()){
            peminjaman = new Peminjaman();
            peminjaman.setKode_anggota(rs.getString(1));
            peminjaman.setKode_buku(rs.getString(2));
            peminjaman.setTgl_pinjam(rs.getString(3));
            peminjaman.setTgl_kembali(rs.getString(4));
            peminjaman.setStatus(rs.getInt(5));
        }
        return peminjaman;
    }
    
    public ResultSet getAllPeminjaman(String sql) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }
    
    public static void main(String[] args){
        try {
            Koneksi k = new Koneksi();
            PengembalianDao dao = new PengembalianDao(k.getConnection());
            int hasil = dao.kurangTanggal("2017-01-02", "2017-01-01");
            JOptionPane.showMessageDialog(null, hasil);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PeminjamanDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PeminjamanDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
