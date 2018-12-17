package com.informatika.parentcare.model;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


@IgnoreExtraProperties
public class Anak implements Serializable {
    private String nama, jeniskelamin, ttl, key, kode_orangtua, urutan;

    public Anak(){
    }

    public Anak(String nama, String jeniskelamin, String ttl, String kode_orangtua, String urutan) {
        this.nama = nama;
        this.jeniskelamin = jeniskelamin;
        this.ttl = ttl;
        this.kode_orangtua = kode_orangtua;
        this.urutan = urutan;
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJeniskelamin() {
        return jeniskelamin;
    }
    public void setJeniskelamin(String jeniskelamin) {
        this.jeniskelamin = jeniskelamin;
    }

    public String getTtl() {
        return ttl;
    }
    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getKode_orangtua() {
        return kode_orangtua;
    }
    public void setKode_orangtua(String kode_orangtua) {
        this.kode_orangtua = kode_orangtua;
    }

    public String getUrutan() {
        return urutan;
    }
    public void setUrutan(String urutan) {
        this.urutan = urutan;
    }
}
