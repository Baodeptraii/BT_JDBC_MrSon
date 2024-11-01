package com.qlsv.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.qlsv.model.SinhVien;
import com.qlsv.DB.DBConnect;

public class SinhVienDAO {
    public List<SinhVien> getAllSinhVien() throws SQLException {
        List<SinhVien> list = new ArrayList<>();
        String sql = "select * from sinhvien";
        try {
            Connection conn = DBConnect.getConn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maSV = rs.getString("MaSV");
                String tenSV = rs.getString("TenSV");
                String lopSV = rs.getString("LopSV");
                String GPA = rs.getString("GPA");
                SinhVien sv = new SinhVien(maSV, tenSV, lopSV, GPA);
                list.add(sv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addSinhVien(SinhVien sv) throws SQLException {
        String sql = "insert into sinhvien values(?,?,?,?)";
        try {
            Connection conn = DBConnect.getConn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sv.getMaSV());
            ps.setString(2, sv.getTenSV());
            ps.setString(3, sv.getLopSV());
            ps.setString(4, sv.getGPA());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSinhVien(SinhVien sv) throws SQLException {
        String sql = "update sinhvien set TenSV = ?, LopSV = ?, GPA = ? where MaSV = ?";
        try {
            Connection conn = DBConnect.getConn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sv.getTenSV());
            ps.setString(2, sv.getLopSV());
            ps.setString(3, sv.getGPA());
            ps.setString(4, sv.getMaSV());
            ps.executeUpdate();
            System.out.println("Update sinh vien: " + sv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSinhVien(String msv) throws SQLException {
        String sql = "delete from sinhvien where MaSV = ?";
        try {
            Connection conn = DBConnect.getConn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, msv);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkMSV(String msv) throws SQLException {
        String sql = "select count(*) from sinhvien where MaSV = ?";
        try {
            Connection conn = DBConnect.getConn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, msv);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}



