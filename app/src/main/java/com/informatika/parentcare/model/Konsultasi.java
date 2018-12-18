package com.informatika.parentcare.model;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@IgnoreExtraProperties
public class Konsultasi implements Serializable {
    private String jadwal, kodekasus, id_anak;
    private List<String> gejaladipilih = new ArrayList<>();

    public Konsultasi(){
    }

    public Konsultasi(String jadwal, String kodekasus, String id_anak, List<String> gejaladipilih) {
        this.jadwal = jadwal;
        this.kodekasus = kodekasus;
        this.id_anak = id_anak;
        this.gejaladipilih = gejaladipilih;
    }

    public String getJadwal() {
        return jadwal;
    }
    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public String getkodekasus() {
        return kodekasus;
    }
    public void setkodekasus(String kodekasus) {
        this.kodekasus = kodekasus;
    }

    public String getId_anak() {
        return id_anak;
    }
    public void setId_anak(String id_anak) {
        this.id_anak = id_anak;
    }

    public List<String> getgejaladipilih() {
        return gejaladipilih;
    }
    public void setgejaladipilih(List<String> gejaladipilih) {
        this.gejaladipilih = gejaladipilih;
    }
}
