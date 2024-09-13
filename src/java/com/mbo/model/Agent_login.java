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
public class Agent_login {

    private String agent_id;
    private String agent_name;
    private String monitoring_pass;
    private String status;
    private String curr_bal;
    private String last_topup;

    public String getLast_topup() {
        return last_topup;
    }

    public void setLast_topup(String last_topup) {
        this.last_topup = last_topup;
    }

    public String getCurr_bal() {
        return curr_bal;
    }

    public void setCurr_bal(String curr_bal) {
        this.curr_bal = curr_bal;
    }
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getMonitoring_pass() {
        return monitoring_pass;
    }

    public void setMonitoring_pass(String monitoring_pass) {
        this.monitoring_pass = monitoring_pass;
    }

}
