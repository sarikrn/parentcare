package com.informatika.parentcare.model;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


@IgnoreExtraProperties
public class Anak implements Serializable {
    private String nama, gender, tanggal_lahir, key;
    private ArrayList<Konsultasi> konsultasi= new ArrayList<>();

    public Anak(){
    }

    public Anak(String nama, String gender, String tanggal_lahir) {
        this.nama = nama;
        this.gender = gender;
        this.tanggal_lahir = tanggal_lahir;
    }

    public Anak(String nama, String gender, String tanggal_lahir, ArrayList<Konsultasi> konsultasi) {
        this.nama = nama;
        this.gender = gender;
        this.tanggal_lahir = tanggal_lahir;
        this.konsultasi = konsultasi;
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }
    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<Konsultasi> getKonsultasi() {
        return konsultasi;
    }
    public void setKonsultasi(ArrayList<Konsultasi> konsultasi) {
        this.konsultasi = konsultasi;
    }
}
