/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

public class Customers implements Serializable {
  
    private Integer cuId;
    private String cuFirstname;
    private String cuLastname;
    private String cuBirthdate;
    private String cuAddress;
    private String cuPersonnummer;

    public Customers() {
    }

    public Customers(Integer cuId) {
        this.cuId = cuId;
    }

    public Customers(Integer cuId, String cuFirstname, String cuLastname, String cuBirthdate, String cuAddress, String cuPersonnummer) {
        this.cuId = cuId;
        this.cuFirstname = cuFirstname;
        this.cuLastname = cuLastname;
        this.cuBirthdate = cuBirthdate;
        this.cuAddress = cuAddress;
        this.cuPersonnummer = cuPersonnummer;
    }

    public Integer getCuId() {
        return cuId;
    }

    public void setCuId(Integer cuId) {
        this.cuId = cuId;
    }

    public String getCuFirstname() {
        return cuFirstname;
    }

    public void setCuFirstname(String cuFirstname) {
        this.cuFirstname = cuFirstname;
    }

    public String getCuLastname() {
        return cuLastname;
    }

    public void setCuLastname(String cuLastname) {
        this.cuLastname = cuLastname;
    }

    public String getCuBirthdate() {
        return cuBirthdate;
    }

    public void setCuBirthdate(String cuBirthdate) {
        this.cuBirthdate = cuBirthdate;
    }

    public String getCuAddress() {
        return cuAddress;
    }

    public void setCuAddress(String cuAddress) {
        this.cuAddress = cuAddress;
    }

    public String getCuPersonnummer() {
        return cuPersonnummer;
    }

    public void setCuPersonnummer(String cuPersonnummer) {
        this.cuPersonnummer = cuPersonnummer;
    }
}
