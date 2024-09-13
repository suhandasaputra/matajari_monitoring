/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbo.util;

import com.bo.function.StringFunction;
import com.mbo.model.Agent_login;
import com.mbo.model.GetSum;
import com.mbo.model.Monitoringproductmonth;
import com.mbo.model.Monitoringtempmsg;
import com.mbo.model.Product;
import com.mbo.model.Topupagent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import org.apache.log4j.Logger;

public class DatabaseProcess {

    private static final Logger log = Logger.getLogger(DatabaseProcess.class);

    private void clearStatment(PreparedStatement stat) {
//        log.info("stat 2 : " + stat);
        if (stat != null) {
            try {
//                log.info("stat A");
                stat.clearBatch();
//                log.info("stat B");
                stat.clearParameters();
//                log.info("stat C");
                stat.close();
//                log.info("stat D");
                stat = null;
//                log.info("stat E");
            } catch (SQLException ex) {
//                log.error("clearStatment : " +ex.getMessage());
//                ex.printStackTrace();
            }
        }
    }

    private void clearDBConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException ex) {
//                log.error("clearDBConnection : "+ex.getMessage());
            }
        }
    }

    private void clearResultset(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {
//                log.error("clearResultset : "+ex.getMessage());
            }
        }
    }

    private void clearAllConnStatRS(Connection conn, PreparedStatement stat, ResultSet rs) {
        clearResultset(rs);
        clearStatment(stat);
        clearDBConnection(conn);
    }

    //proses login
    public Agent_login validate(String userlogin, String userpass) throws SQLException, ParseException {
        SimpleDateFormat requesttime1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat requesttime2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        Agent_login aa = new Agent_login();
        aa.setStatus("0001");
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT b.curr_bal, a.agent_id, a.agent_name FROM am_user a inner join am_balance b on a.agent_id = b.agent_id WHERE a.agent_id=? AND a.monitoring_pass=?");
            stat.setString(1, userlogin);
            stat.setString(2, userpass);
            rs = stat.executeQuery();
            while (rs.next()) {
                stat1 = conn.prepareStatement("select topup_date from am_update_balance where agent_id = '" + userlogin + "' and process_exe = 't' order by topup_date desc limit 1");
                rs1 = stat1.executeQuery();
                while (rs1.next()) {
                    aa.setAgent_name(rs.getString("agent_name"));
                    aa.setCurr_bal(rs.getString("curr_bal"));

                    Date requesttime = requesttime1.parse(rs1.getString("topup_date"));
                    aa.setLast_topup(requesttime2.format(requesttime));
                    aa.setStatus("0000");
                }
                aa.setStatus("0000");
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return aa;
    }

    public Product getAllproduct(String agent_id) throws SQLException {
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        Product ppob = new Product();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            stat = conn.prepareStatement("SELECT productcode, COUNT(productcode) AS jml FROM tempmsg_backup where rcinternal = '0000' and fromagent = ? and proccode in ('280000','290000')GROUP BY productcode order by jml desc limit 1");
            stat = conn.prepareStatement("select productcode, COUNT(productcode) as jml from tempmsg where rcinternal = '0000'and fromagent = ? and proccode in ('280000','290000') GROUP BY productcode\n"
                    + "union all \n"
                    + "select productcode, COUNT(productcode)as jml from tempmsg_backup where rcinternal = '0000'and fromagent = ? and proccode in ('280000','290000')\n"
                    + "GROUP BY productcode order by jml desc limit 1");
            stat.setString(1, agent_id);
            stat.setString(2, agent_id);
            rs = stat.executeQuery();
            while (rs.next()) {
                stat1 = conn.prepareStatement("SELECT trancodename FROM trancode where trancodeid = ?");
                stat1.setString(1, rs.getString("productcode"));
                rs1 = stat1.executeQuery();
                while (rs1.next()) {
                    ppob.setJml(rs.getString("jml"));
                    ppob.setProductname(rs1.getString("trancodename"));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
            clearAllConnStatRS(conn, stat1, rs1);
        }
        return ppob;
    }

    public ArrayList<Monitoringproductmonth> getProduct(String agent_id) {
        ArrayList<Monitoringproductmonth> ppobprodList = new ArrayList<Monitoringproductmonth>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select a.trancodeid, a.trancodename, a.available, b.hargajual, b.feejual, b.agent_id FROM trancode a inner join am_trancode b on a.trancodeid = b.trancodeid where b.agent_id = ?");
            stat.setString(1, agent_id);
            rs = stat.executeQuery();
            while (rs.next()) {
                Monitoringproductmonth ppobmonth = new Monitoringproductmonth();
                if (rs.getString("trancodename") == null) {
                    ppobmonth.setTrancodename("-");
                } else {
                    ppobmonth.setTrancodename(rs.getString("trancodename"));
                }
                if (rs.getString("trancodeid") == null) {
                    ppobmonth.setTrancodeid("-");
                } else {
                    ppobmonth.setTrancodeid(rs.getString("trancodeid"));
                }
                if (rs.getString("hargajual") == null) {
                    ppobmonth.setHargajual("-");
                } else {
                    String hj = rs.getString("hargajual");
                    double Dou = Double.parseDouble(hj);
                    int val = (int) Dou;
                    ppobmonth.setHargajual(StringFunction.rupiahFormat(val));
                }
                if (rs.getString("feejual") == null) {
                    ppobmonth.setFeejual("-");
                } else {
                    String fj = rs.getString("feejual");
                    double Dou1 = Double.parseDouble(fj);
                    int val1 = (int) Dou1;
                    ppobmonth.setFeejual(StringFunction.rupiahFormat(val1));
                }
                if (rs.getString("available") == null) {
                    ppobmonth.setAvailable("-");
                } else {
                    ppobmonth.setAvailable(rs.getString("available"));
                }
                ppobprodList.add(ppobmonth);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return ppobprodList;
    }

    public ArrayList<Topupagent> getAllTopupagent(String userlogin) throws ParseException {
        ArrayList<Topupagent> topupagentList = new ArrayList<Topupagent>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        SimpleDateFormat transfer_date1 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat transfer_date2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat topup_date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat topup_date2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT b.curr_bal, a.agent_id, a.amount, a.bank_name, a.acct_no, a.transfer_date, a.process_exe, a.topup_date, a.update_date, a.before_balance, a.current_balance FROM am_update_balance a inner join am_balance b on a.agent_id = b.agent_id where a.agent_id = ?");
            stat.setString(1, userlogin);
            rs = stat.executeQuery();
            while (rs.next()) {
                Topupagent topupagent = new Topupagent();

                Date topup_date = topup_date1.parse(rs.getString("topup_date"));
                topupagent.setTopup_date(topup_date2.format(topup_date));

                if (rs.getString("update_date") == null) {
                    topupagent.setUpdate_date("-");
                } else {
                    Date update_date = topup_date1.parse(rs.getString("update_date"));
                    topupagent.setUpdate_date(topup_date2.format(update_date));
                }
                topupagent.setBank_name(rs.getString("bank_name"));
                topupagent.setAcct_no(rs.getString("acct_no"));
                Date transfer_date = transfer_date1.parse(rs.getString("transfer_date"));
                topupagent.setTransfer_date(transfer_date2.format(transfer_date));
                String amn = rs.getString("amount");
                double Dou2 = Double.parseDouble(amn);
                int val2 = (int) Dou2;
                topupagent.setAmount(StringFunction.rupiahFormat(val2));
                if (rs.getString("before_balance") == null) {
                    topupagent.setBefore_balance("-");
                } else {
                    String bb = rs.getString("before_balance");
                    double Dou1 = Double.parseDouble(bb);
                    int val1 = (int) Dou1;
                    topupagent.setBefore_balance(StringFunction.rupiahFormat(val1));
                }
                if (rs.getString("current_balance") == null) {
                    topupagent.setCurrent_balance("-");
                } else {
                    String cb = rs.getString("current_balance");
                    double Dou3 = Double.parseDouble(cb);
                    int val3 = (int) Dou3;
                    topupagent.setCurrent_balance(StringFunction.rupiahFormat(val3));
                }
                topupagent.setProcess_exe(rs.getString("process_exe"));
                topupagent.setBalance(rs.getString("curr_bal"));
                topupagentList.add(topupagent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return topupagentList;
    }

    public ArrayList<Topupagent> getAllTrx(String userlogin, String start, String end) throws ParseException {
        ArrayList<Topupagent> topupagentList = new ArrayList<Topupagent>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

//        SimpleDateFormat darihtml = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Date dateStart = darihtml.parse(start);
//        Timestamp.valueOf(dateFormat.format(dateStart));
//
//        Date dateEnd = darihtml.parse(end);
//        Timestamp.valueOf(dateFormat.format(dateEnd));
//        
//        System.out.println("aaaaaaaaaa : "+dateStart);
//        System.out.println("bbbbbbbbbb : "+dateFormat.format(dateStart));
        SimpleDateFormat transfer_date1 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat transfer_date2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat topup_date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat topup_date2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT b.curr_bal, a.agent_id, a.amount, a.bank_name, a.acct_no, a.transfer_date, a.process_exe, a.topup_date, a.update_date, a.before_balance, a.current_balance FROM am_update_balance a inner join am_balance b on a.agent_id = b.agent_id where a.agent_id = ?");
            stat.setString(1, userlogin);
            rs = stat.executeQuery();
            while (rs.next()) {
                Topupagent topupagent = new Topupagent();

                Date topup_date = topup_date1.parse(rs.getString("topup_date"));
                topupagent.setTopup_date(topup_date2.format(topup_date));

                Date update_date = topup_date1.parse(rs.getString("update_date"));
                topupagent.setUpdate_date(topup_date2.format(update_date));

                topupagent.setBank_name(rs.getString("bank_name"));
                topupagent.setAcct_no(rs.getString("acct_no"));

                Date transfer_date = transfer_date1.parse(rs.getString("transfer_date"));
                topupagent.setTransfer_date(transfer_date2.format(transfer_date));

                topupagent.setAmount(rs.getString("amount"));
                topupagent.setBefore_balance(rs.getString("before_balance"));
                topupagent.setCurrent_balance(rs.getString("current_balance"));
                topupagent.setProcess_exe(rs.getString("process_exe"));
                topupagent.setBalance(rs.getString("curr_bal"));

                topupagentList.add(topupagent);
            }
//            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return topupagentList;
    }

    public ArrayList<GetSum> getSumTrx(String agent_id) {
        ArrayList<GetSum> agentlimitList = new ArrayList<GetSum>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select distinct TO_CHAR(requesttime,'YYYY-MM') as tgl from (select requesttime from tempmsg union select requesttime from tempmsg_backup) t");
            rs = stat.executeQuery();
            while (rs.next()) {
                stat1 = conn.prepareStatement("SELECT (SELECT COUNT(*)FROM tempmsg where rcinternal = '0000' and proccode in('280000', '290000')and transactionid not in ('400011') and TO_CHAR(requesttime,'YYYY-MM') = '" + rs.getString("tgl") + "' and fromagent = '" + agent_id + "') AS trx1,(SELECT COUNT(*)FROM tempmsg_backup where rcinternal = '0000' and proccode in('280000', '290000') and transactionid not in ('400011') and TO_CHAR(requesttime,'YYYY-MM') = '" + rs.getString("tgl") + "' and fromagent = '" + agent_id + "')AS trx2");
                rs1 = stat1.executeQuery();
                while (rs1.next()) {
                    GetSum ss = new GetSum();

                    ss.setY(rs.getString("tgl"));
                    int trx1 = Integer.valueOf(rs1.getString("trx1"));
                    int trx2 = Integer.valueOf(rs1.getString("trx2"));
                    int trx = trx1 + trx2;
                    ss.setItem1(String.valueOf(trx));
                    agentlimitList.add(ss);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            clearStatment(stat1);
            clearAllConnStatRS(conn, stat, rs);
            clearResultset(rs1);
        }
        return agentlimitList;
    }

    public ArrayList<Monitoringtempmsg> getAllTemp(String userlogin, String start, String end) {
        ArrayList<Monitoringtempmsg> allTempList = new ArrayList<Monitoringtempmsg>();
        SimpleDateFormat requesttime1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat requesttime2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select statusreply,requesttime,responsetime,noref,billercode,fromaccount,toaccount,responsecode,amount,proccode,transactionid,productcode,trxidbackend,fromagent,rcinternal,custno,hargajual,hargabeli,feejual,feebeli, prev_bal, curr_bal, profit, prev_biller_bal, curr_biller_bal\n"
                    + "from tempmsg where TO_CHAR(responsetime, 'YYYY-MM-DD') >= '2020-01-06' and TO_CHAR(requesttime, 'YYYY-MM-DD') >= '" + start + "' and TO_CHAR(requesttime, 'YYYY-MM-DD') <= '" + end + "' and fromagent = ? and proccode in ('280000','290000','400011') union all\n"
                    + "select statusreply,requesttime,responsetime,noref,billercode,fromaccount,toaccount,responsecode,amount,proccode,transactionid,productcode,trxidbackend,fromagent,rcinternal,custno,hargajual,hargabeli,feejual,feebeli, prev_bal, curr_bal, profit, prev_biller_bal, curr_biller_bal\n"
                    + "from tempmsg_backup where TO_CHAR(responsetime, 'YYYY-MM-DD') >= '2020-01-06' and TO_CHAR(requesttime, 'YYYY-MM-DD') >= '" + start + "' and TO_CHAR(requesttime, 'YYYY-MM-DD') <= '" + end + "' and fromagent = ? and proccode in ('280000','290000','400011') order by requesttime desc");
            stat.setString(1, userlogin);
            stat.setString(2, userlogin);
            rs = stat.executeQuery();
            while (rs.next()) {
                stat1 = conn.prepareStatement("select trancodename from trancode where trancodeid = ?");
                stat1.setString(1, rs.getString("productcode"));
                rs1 = stat1.executeQuery();
                while (rs1.next()) {
                    Monitoringtempmsg alltemp = new Monitoringtempmsg();

                    if (rs.getString("noref") == null) {
                        alltemp.setNoref("-");
                    } else {
                        alltemp.setNoref(rs.getString("noref"));
                    }
                    if (rs.getString("requesttime") == null) {
                        alltemp.setRequesttime("-");
                    } else {
                        Date requesttime = requesttime1.parse(rs.getString("requesttime"));
                        alltemp.setRequesttime(requesttime2.format(requesttime));
                    }
                    if (rs.getString("productcode") == null) {
                        alltemp.setProductcode("-");
                    } else {
                        alltemp.setProductcode(rs1.getString("trancodename"));
                    }
                    if (rs.getString("custno") == null) {
                        alltemp.setCustno("-");
                    } else {
                        alltemp.setCustno(rs.getString("custno"));
                    }

                    if (rs.getString("proccode").equals("290000")) {
                        if (rs.getString("hargajual") == null) {
                            alltemp.setAmount("-");
                        } else {
                            String hj = rs.getString("hargajual");
                            double Dou2 = Double.parseDouble(hj);
                            int val2 = (int) Dou2;
                            alltemp.setAmount(StringFunction.rupiahFormat(val2));
                        }
                    } else if (rs.getString("proccode").equals("280000")) {
                        if (rs.getString("amount") == null) {
                            alltemp.setAmount("-");
                        } else {
                            int amon = Integer.valueOf(rs.getString("amount"));
                            int feejual = Integer.valueOf(rs.getString("feejual"));
                            int jml = amon + feejual;
                            int val3 = (int) jml;
                            alltemp.setAmount(StringFunction.rupiahFormat(val3));
                        }
                    }
                    if (rs.getString("prev_bal") == null) {
                        alltemp.setPrev_bal("-");
                    } else {
                        String pb = rs.getString("prev_bal");
                        double Dou1 = Double.parseDouble(pb);
                        int val1 = (int) Dou1;
                        alltemp.setPrev_bal(StringFunction.rupiahFormat(val1));
                    }
                    if (rs.getString("curr_bal") == null) {
                        alltemp.setCurr_bal("-");
                    } else {
                        String cb = rs.getString("curr_bal");
                        double Dou = Double.parseDouble(cb);
                        int val = (int) Dou;
                        alltemp.setCurr_bal(StringFunction.rupiahFormat(val));
                    }
                    if (rs.getString("rcinternal") == null) {
                        alltemp.setRcinternal("-");
                    } else {
                        alltemp.setRcinternal(rs.getString("rcinternal"));
                    }
                    allTempList.add(alltemp);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
            clearAllConnStatRS(conn, stat1, rs1);
        }
        return allTempList;
    }

    public String addTopupagent(Topupagent Topupagent, String username) {
        Connection conn = null;
        PreparedStatement stat = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("INSERT INTO am_update_balance(agent_id, amount, bank_name, acct_no, transfer_date) VALUES (?, ?, ?, ?, ?)");
            stat.setString(1, Topupagent.getAgent_id());
            stat.setInt(2, Integer.parseInt(Topupagent.getAmount()));
            stat.setString(3, Topupagent.getBank_name());
            stat.setString(4, Topupagent.getAcct_no());
            stat.setString(5, Topupagent.getTransfer_date());
            stat.executeUpdate();
//            log.info("");
//            log.info("============== ADD TOPUP MITRA H2H : " + Topupagent.getAgent_id() + ", amount :" + Topupagent.getAmount() + ", bank :" + Topupagent.getBank_name() + ", acct number :" + Topupagent.getAcct_no() + ", Transfer Date :" + Topupagent.getTransfer_date() + " Create By :" + username + " =================");
//            log.info("");
        } catch (SQLException e) {
            return status = e.getMessage();
        } finally {
            clearDBConnection(conn);
            clearStatment(stat);
        }
        return status = "0000";
    }

    public String updateUserlogin(String userlogin, String pass1, String pass2, String new_pass) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        String status = "0001";
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("Select * from am_user where agent_id = ?");
            stat.setString(1, userlogin);
            rs = stat.executeQuery();
            while (rs.next()) {
                String pass_exis = rs.getString("monitoring_pass");
                if (pass1.equals(pass_exis) && pass2.equals(pass_exis)) {
                    stat1 = conn.prepareStatement("update am_user set monitoring_pass = ? where agent_id = ?");
                    stat1.setString(1, new_pass);
                    stat1.setString(2, userlogin);
                    stat1.executeUpdate();
                    status = "0000";
                } else {
                    status = "0001";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
            clearAllConnStatRS(conn, stat1, rs1);

        }
        return status;
    }

}
