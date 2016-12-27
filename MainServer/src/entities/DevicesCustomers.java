/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

/**
 *
 * @author Chris
 */
public class DevicesCustomers implements Serializable {

    private static final long serialVersionUID = 1L;
    private String devId;
    private String devName;
    private Customers customersCuId;

    public DevicesCustomers() {
    }

    public DevicesCustomers(String devId) {
        this.devId = devId;
    }

    public DevicesCustomers(String devId, String devName) {
        this.devId = devId;
        this.devName = devName;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }
    
    public Customers getCustomersCuId() {
        return customersCuId;
    }

    public void setCustomersCuId(Customers customersCuId) {
        this.customersCuId = customersCuId;
    }

}
