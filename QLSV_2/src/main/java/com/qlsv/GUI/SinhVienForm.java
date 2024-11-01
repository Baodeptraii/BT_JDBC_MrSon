package com.qlsv.GUI;

import com.qlsv.DAO.SinhVienDAO;
import com.qlsv.model.SinhVien;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class SinhVienForm extends JFrame {
    private JTextField txtMaSV;
    private JTextField txtTenSV;
    private JTextField txtLopSV;
    private JTextField txtGPA;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnReset;
    private JButton btnDisplay;
    private JTable table1;
    private SinhVienDAO dao;

    public SinhVienForm() {
        dao = new SinhVienDAO();
        initComponents();
        addEvents();
        setTitle("Quản lý Sinh Viên");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        // Khởi tạo các thuộc tính của sinh viên
        txtMaSV = new JTextField();
        txtTenSV = new JTextField();
        txtLopSV = new JTextField();
        txtGPA = new JTextField();

        // Tạo các nút
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnReset = new JButton("Reset");
        btnDisplay = new JButton("Hiển Thị");

        // Thêm các trường và nút vào panel
        inputPanel.add(new JLabel("Mã SV:"));
        inputPanel.add(txtMaSV);
        inputPanel.add(new JLabel("Tên SV:"));
        inputPanel.add(txtTenSV);
        inputPanel.add(new JLabel("Lớp:"));
        inputPanel.add(txtLopSV);
        inputPanel.add(new JLabel("GPA:"));
        inputPanel.add(txtGPA);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnReset);
        buttonPanel.add(btnDisplay);

        // Tạo bảng
        table1 = new JTable();
        JScrollPane scrollPane = new JScrollPane(table1);

        // Thêm các panel vào frame
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Set up table
        table1.setModel(new DefaultTableModel(new Object[]{"Mã SV", "Tên SV", "Lớp", "GPA"}, 0));
    }

    private void addEvents() {

        // Nút hiển thị
        btnDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySinhViens();
            }
        });

        // Nút thêm
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSinhVien();
            }
        });

        // Nút sửa
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSinhVien();
            }
        });

        // Nút xoá
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSinhVien();
            }
        });

        // Nút reset
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        // Sự kiện khi chọn một sinh viên trong bảng
        table1.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table1.getSelectedRow() != -1) {
                loadSelectedSinhVienToForm();
            }
        });
    }

    private void displaySinhViens() {
        try {
            List<SinhVien> sinhViens = dao.getAllSinhVien();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            model.setRowCount(0); // Clear existing rows
            for (SinhVien sv : sinhViens) {
                model.addRow(new Object[]{sv.getMaSV(), sv.getTenSV(), sv.getLopSV(), sv.getGPA()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể hiển thị danh sách sinh viên", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addSinhVien() {
        String maSV = txtMaSV.getText();
        String tenSV = txtTenSV.getText();
        String lop = txtLopSV.getText();
        String gpa = txtGPA.getText();

        if (maSV.isEmpty() || tenSV.isEmpty() || lop.isEmpty() || gpa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!gpa.matches("^\\d+(\\.\\d+)?$") || Double.parseDouble(gpa) < 0 || Double.parseDouble(gpa) > 4) {
            JOptionPane.showMessageDialog(this, "GPA không hợp lệ", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }



        SinhVien sv = new SinhVien(maSV, tenSV, lop, gpa);

        try {
            if(dao.checkMSV(maSV)) {
                JOptionPane.showMessageDialog(this, "Mã sinh viên đã tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dao.addSinhVien(sv);
            JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công");
            displaySinhViens();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể thêm sinh viên", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSinhVien() {
        String maSV = txtMaSV.getText();
        String tenSV = txtTenSV.getText();
        String lop = txtLopSV.getText();
        String gpa = txtGPA.getText();
        SinhVien sv = new SinhVien(maSV, tenSV, lop, gpa);
        System.out.println("Update sinh vien: " + sv);
        try {


            dao.updateSinhVien(sv);
            JOptionPane.showMessageDialog(this, "Cập nhật sinh viên thành công");
            displaySinhViens();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể cập nhật sinh viên", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSinhVien() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên để xoá", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maSV = (String) table1.getValueAt(selectedRow, 0);

        try {
            dao.deleteSinhVien(maSV);
            JOptionPane.showMessageDialog(this, "Xoá sinh viên thành công");
            displaySinhViens();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể xoá sinh viên", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        txtMaSV.setText("");
        txtTenSV.setText("");
        txtLopSV.setText("");
        txtGPA.setText("");
    }

    private void loadSelectedSinhVienToForm() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            txtMaSV.setText((String) table1.getValueAt(selectedRow, 0));
            txtTenSV.setText((String) table1.getValueAt(selectedRow, 1));
            txtLopSV.setText((String) table1.getValueAt(selectedRow, 2));
            txtGPA.setText(table1.getValueAt(selectedRow, 3).toString());
        }
    }
}
