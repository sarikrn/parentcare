package com.informatika.parentcare.model;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;


@IgnoreExtraProperties
public class Konsultasi implements Serializable {
    private String jadwal;
    private int id_kasus;

    public Konsultasi(){
    }

    public Konsultasi(String jadwal, int id_kasus) {
        this.jadwal = jadwal;
        this.id_kasus = id_kasus;
    }

    public String getJadwal() {
        return jadwal;
    }
    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public int getId_kasus() {
        return id_kasus;
    }
    public void setId_kasus(int id_kasus) {
        this.id_kasus = id_kasus;
    }
}
