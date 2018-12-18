package com.informatika.parentcare.model;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@IgnoreExtraProperties
public class Konsultasi implements Serializable {
    private String jadwal, solusi, id_anak;
    private List<String> gejalaDipilih = new ArrayList<>();

    public Konsultasi(){
    }

    public Konsultasi(String jadwal, String solusi, String id_anak, List<String> gejaladipilih) {
        this.jadwal = jadwal;
        this.solusi = solusi;
        this.id_anak = id_anak;
        this.gejalaDipilih = gejaladipilih;
    }

    public String getJadwal() {
        return jadwal;
    }
    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public String getSolusi() {
        return solusi;
    }
    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }

    public String getId_anak() {
        return id_anak;
    }
    public void setId_anak(String id_anak) {
        this.id_anak = id_anak;
    }

    public List<String> getgejaladipilih() {
        return gejalaDipilih;
    }
    public void setgejaladipilih(List<String> gejaladipilih) {
        this.gejalaDipilih = gejaladipilih;
    }
}
