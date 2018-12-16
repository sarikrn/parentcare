package com.informatika.parentcare.model;

public class Gejala {
    boolean isSelected = false;
    String kode;
    String desk;

    public Gejala(){
    }

    public Gejala(boolean isSelected, String kode, String gejala) {
        this.isSelected = isSelected;
        this.kode = kode;
        this.desk = gejala;
    }

    public boolean getSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getKode() {
        return kode;
    }
    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getDesk() {
        return desk;
    }
    public void setDesk(String gejala) {
        this.desk = gejala;
    }
}
