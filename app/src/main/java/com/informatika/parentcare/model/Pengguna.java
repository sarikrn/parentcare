package com.informatika.parentcare.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Pengguna implements Serializable {
    private String nama, password, status;

    public Pengguna() {
    }

    public Pengguna(String nama, String password, String status) {
        this.nama = nama;
        this.password = password;
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() { return status;}

    public void setStatus(String status) {  this.status = status;}
}
