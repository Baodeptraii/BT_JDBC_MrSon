package com.qlsv;

import com.qlsv.GUI.SinhVienForm;

import javax.swing.*;
import java.sql.SQLException;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException {
        javax.swing.SwingUtilities.invokeLater(() -> {

            SinhVienForm form = new SinhVienForm();
            form.setTitle("Quản lý sinh viên");
            form.setSize(600, 650);
            form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            form.setLocationRelativeTo(null);
            form.setVisible(true);


        });
    }
}
