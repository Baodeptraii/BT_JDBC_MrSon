package com.qlsv.model;

public class SinhVien {
    private String maSV;
    private String tenSV;
    private String lopSV;
    private String GPA;
    // Để GPA là Double thì trong sql sẽ kểu 3.0000000000002356

    public SinhVien(String maSV, String tenSV, String lopSV, String GPA) {
        this.maSV = maSV;
        this.tenSV = tenSV;
        this.lopSV = lopSV;
        this.GPA = GPA;
    }

    public String getMaSV() {
        return maSV;
    }


    public String getTenSV() {
        return tenSV;
    }


    public String getLopSV() {
        return lopSV;
    }


    public String getGPA() {
        return GPA;
    }

}
