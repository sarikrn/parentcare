package com.informatika.parentcare.model;

import java.util.ArrayList;

public class Kasus {
    private ArrayList<String> gejala = new ArrayList<>();
    private String kode;
    private String solusi;

    public Kasus() {
    }

    public Kasus(ArrayList<String> gejala, String kode, String solusi) {
        this.gejala = gejala;
        this.kode = kode;
        this.solusi = solusi;
    }

    public ArrayList<String> getGejala() {
        return gejala;
    }
    public void setGejala(ArrayList<String> gejala) {
        this.gejala = gejala;
    }

    public String getKode() {
        return kode;
    }
    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getSolusi() {
        return solusi;
    }
    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }
}
