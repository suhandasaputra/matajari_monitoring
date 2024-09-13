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
public class Monitoringbillers {
    private String billercode;
    private String curr_bal;
    private String amount;
    private String transfer_date;
    private String requesttime;
    private String statusreply;
    private String status;

    /**
     * @return the billercode
     */
    public String getBillercode() {
        return billercode;
    }

    /**
     * @param billercode the billercode to set
     */
    public void setBillercode(String billercode) {
        this.billercode = billercode;
    }

    /**
     * @return the curr_bal
     */
    public String getCurr_bal() {
        return curr_bal;
    }

    /**
     * @param curr_bal the curr_bal to set
     */
    public void setCurr_bal(String curr_bal) {
        this.curr_bal = curr_bal;
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
     * @return the statusreply
     */
    public String getStatusreply() {
        return statusreply;
    }

    /**
     * @param statusreply the statusreply to set
     */
    public void setStatusreply(String statusreply) {
        this.statusreply = statusreply;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the transfer_date
     */
    public String getTransfer_date() {
        return transfer_date;
    }

    /**
     * @param transfer_date the transfer_date to set
     */
    public void setTransfer_date(String transfer_date) {
        this.transfer_date = transfer_date;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
