/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbo.model;

/**
 *
 * @author syukur
 */
public class Monitoringtempmsg {
    private String noref;
    private String requesttime;
    private String responsetime;
    private String productcode;
    private String custno;
        private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getCustno() {
        return custno;
    }

    public void setCustno(String custno) {
        this.custno = custno;
    }

    public String getPrev_bal() {
        return prev_bal;
    }

    public void setPrev_bal(String prev_bal) {
        this.prev_bal = prev_bal;
    }

    public String getCurr_bal() {
        return curr_bal;
    }

    public void setCurr_bal(String curr_bal) {
        this.curr_bal = curr_bal;
    }
    private String prev_bal;
    private String curr_bal;

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }
//    private String rcbiller;
    private String rcinternal;
    private String fromagent;
//    private String billercode;
    
    private String hargajual;
    private String hargabeli;
    private String feejual;
    private String feebeli;
    private String sa_commission; 

    public String getSa_commission() {
        return sa_commission;
    }

    public void setSa_commission(String sa_commission) {
        this.sa_commission = sa_commission;
    }
    

    /**
     * @return the requesttime
     */
    public String getRequesttime() {
        return requesttime;
    }

    /**
     * @param requesttime the requesttime to set
     */
    public void setRequesttime(String requesttime) {
        this.requesttime = requesttime;
    }

    /**
     * @return the responsetime
     */
    public String getResponsetime() {
        return responsetime;
    }

    /**
     * @param responsetime the responsetime to set
     */
    public void setResponsetime(String responsetime) {
        this.responsetime = responsetime;
    }

    /**
     * @return the rcbiller
     */
//    public String getRcbiller() {
//        return rcbiller;
//    }
//
//    /**
//     * @param rcbiller the rcbiller to set
//     */
//    public void setRcbiller(String rcbiller) {
//        this.rcbiller = rcbiller;
//    }

    /**
     * @return the rcinternal
     */
    public String getRcinternal() {
        return rcinternal;
    }

    /**
     * @param rcinternal the rcinternal to set
     */
    public void setRcinternal(String rcinternal) {
        this.rcinternal = rcinternal;
    }

    /**
     * @return the fromagent
     */
    public String getFromagent() {
        return fromagent;
    }

    /**
     * @param fromagent the fromagent to set
     */
    public void setFromagent(String fromagent) {
        this.fromagent = fromagent;
    }

    /**
     * @return the billercode
     */
//    public String getBillercode() {
//        return billercode;
//    }
//
//    /**
//     * @param billercode the billercode to set
//     */
//    public void setBillercode(String billercode) {
//        this.billercode = billercode;
//    }

    /**
     * @return the noref
     */
    public String getNoref() {
        return noref;
    }

    /**
     * @param noref the noref to set
     */
    public void setNoref(String noref) {
        this.noref = noref;
    }
    
    
    public String getHargajual() {
        return hargajual;
    }

    /**
     * @param rcinternal the rcinternal to set
     */
    public void setHargajual(String hargajual) {
        this.hargajual = hargajual;
    }
    
    public String getHargabeli() {
        return hargabeli;
    }

    /**
     * @param rcinternal the rcinternal to set
     */
    public void setHargabeli(String hargabeli) {
        this.hargabeli = hargabeli;
    }
    
    
    
    
     public String getFeejual() {
        return feejual;
    }

    /**
     * @param rcinternal the rcinternal to set
     */
    public void setFeejual(String feejual) {
        this.feejual = feejual;
    }
    
    public String getFeebeli() {
        return feebeli;
    }

    /**
     * @param rcinternal the rcinternal to set
     */
    public void setFeebeli(String feebeli) {
        this.feebeli = feebeli;
    }
    
    
    
    
    
}
