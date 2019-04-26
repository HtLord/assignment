package com.htlord.assignment.general;

import com.htlord.assignment.model.PhoneCall;

import java.util.ArrayList;
import java.util.List;

public class PhoneCallPool {
    private static PhoneCallPool pcp;
    private static List<PhoneCall> pcc;
    private static List<PhoneCall> spcc;
    private static int maxPC = 0;

    public PhoneCallPool(){}

    public static PhoneCallPool getInstance(){
        if(pcp==null){
            pcp = new PhoneCallPool();
            pcp.pcc = new ArrayList<PhoneCall>();
            pcp.spcc = new ArrayList<PhoneCall>();
            return pcp;
        }else{
            return pcp;
        }
    }

    public void importPC(List<PhoneCall> outer){
        if(pcp==null) {
            pcp = new PhoneCallPool();
            pcp.pcc = new ArrayList<PhoneCall>();
            pcp.spcc = new ArrayList<PhoneCall>();
            this.pcp.pcc.addAll(outer);
            this.pcp.maxPC = outer.size();
        }else{
            this.pcp.pcc.addAll(outer);
            this.pcp.maxPC = outer.size();
        }
    }

    public void dumpPC(){
        for(PhoneCall pc:this.getInstance().pcc){
            System.out.println(pc.toString());
        }
    }

    synchronized public PhoneCall pullPC(){
        return !this.pcp.pcc.isEmpty()?this.pcp.pcc.remove(0):null;
    }

    synchronized public void pushPC(PhoneCall pc){
        this.pcp.pcc.add(pc);
    }

    synchronized public void pushSPC(PhoneCall pc){
        this.pcp.spcc.add(pc);
    }

    public int getMaxPC(){
        return this.pcp.maxPC;
    }

    public int getSPCSize(){
        return this.pcp.spcc.size();
    }
}
